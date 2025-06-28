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
          // Replace image tags in docker-compose dynamically
          sh """
            sed "s/\\\${BUILD_NUMBER}/${env.BUILD_NUMBER}/g" docker-compose.yml > docker-compose.generated.yml
            docker-compose -f docker-compose.generated.yml down || true
            docker-compose -f docker-compose.generated.yml up -d
          """
        }
      }
    }

    stage('Wait for App to Become Ready') {
      steps {
        script {
          def appReady = false
          def retries = 10
          for (int i = 0; i < retries; i++) {
            def response = sh(script: "curl -s -o /dev/null -w '%{http_code}' http://localhost:8090/actuator/health || true", returnStdout: true).trim()
            if (response == '200') {
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
    always {
      script {
        sh "docker-compose -f docker-compose.generated.yml down || true"
      }
    }

    success {
      script {
        jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: "🎉 Build #${env.BUILD_NUMBER} passed successfully."
      }
    }

    failure {
      script {
        jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: "❌ Build failed. Please check the logs."
      }
    }
  }
}
