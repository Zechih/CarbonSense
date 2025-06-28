pipeline {
  agent any

  environment {
    IMAGE_NAME = 'zechih/carbonsense'
    JIRA_ISSUE = 'CAR-1'
    JIRA_SITE = 'MyJira'
    DOCKER_HOST = 'npipe:////./pipe/dockerDesktopLinuxEngine'
    BUILD_TAG = "${env.BUILD_NUMBER}"
  }

  stages {
    stage('Clone Repository') {
      steps {
        git credentialsId: 'github-creds', branch: 'main', url: 'https://github.com/zechih/CarbonSense'
      }
    }

    stage('Build and Push Images') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          script {
            docker.withRegistry('https://index.docker.io/v1/', 'docker-creds') {
              def appImage = docker.build("zechih/carbonsense-app:${env.BUILD_NUMBER}", '.')
              appImage.push()
              def dbImage = docker.build("zechih/carbonsense-db:${env.BUILD_NUMBER}", '-f Dockerfile.mysql .')
              dbImage.push()
            }
          }
        }
      }
    }

    stage('Deploy with Docker Compose') {
      steps {
        script {
          def original = readFile('docker-compose.yml')
          def replaced = original.replaceAll('\\$\\{BUILD_NUMBER\\}', env.BUILD_NUMBER)
          writeFile file: 'docker-compose.generated.yml', text: replaced

          bat 'docker-compose -f docker-compose.generated.yml down || exit 0'
          bat 'docker-compose -f docker-compose.generated.yml up -d'
        }
      }
    }
    
    stage('Wait for MySQL Ready') {
      steps {
        script {
          def dbReady = false
          def retries = 10
          for (int i = 0; i < retries; i++) {
            def result = bat(
              script: 'docker exec mysql-carbonsense mysql -uroot -proot -e "SELECT 1;"',
              returnStatus: true
            )
            if (result == 0) {
              echo "✅ MySQL is ready!"
              dbReady = true
              break
            }
            echo "⌛ MySQL not ready yet. Waiting..."
            sleep time: 5, unit: 'SECONDS'
          }
          if (!dbReady) {
            error("❌ MySQL did not become ready in time.")
          }
        }
      }
    }



    stage('Run JMeter Performance Test') {
      steps {
        bat 'jmeter -n -t tests\\performance_test.jmx -l results\\result.jtl'
      }
    }

    stage('Publish JMeter Report') {
      steps {
        performanceReport sourceDataFiles: 'results/result.jtl'
      }
    }

    stage('Update JIRA') {
      steps {
        script {
          def comment = "✅ Build #${env.BUILD_NUMBER} succeeded. Docker images pushed, app deployed, and JMeter tests completed."
          jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: comment
        }
      }
    }
  } 

  post {
    failure {
      script {
        if (isUnix()) {
          sh 'docker-compose -f docker-compose.generated.yml down || true'
        } else {
          bat '''
          if exist docker-compose.generated.yml (
            docker-compose -f docker-compose.generated.yml down
          )
          exit /b 0
          '''
        }
  
        jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: "❌ Build failed. Containers cleaned up. Please check the logs."
      }
    }
  
    success {
      script {
        jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: "🎉 Build #${env.BUILD_NUMBER} passed successfully. Containers left running."
      }
    }
  }
}
