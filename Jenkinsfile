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
    
    stage('Wait for App to Become Ready') {
      steps {
        script {
          def appReady = false
          def retries = 10
          for (int i = 0; i < retries; i++) {
            def response = bat(
              script: 'curl -s -o NUL -w "%%{http_code}" http://localhost:8090/login',
              returnStdout: true
            ).trim()
    
            if (response == '200') {
              echo "App is ready! (HTTP $response)"
              appReady = true
              break
            }
    
            echo "App not ready yet (HTTP $response), waiting..."
            sleep time: 10, unit: 'SECONDS'
          }
    
          if (!appReady) {
            error("App did not become ready in time.")
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
