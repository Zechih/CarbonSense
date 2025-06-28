pipeline {
  agent any

  environment {
    IMAGE_NAME = 'zechih/carbonsense'
    JIRA_ISSUE = 'CAR-1'
    JIRA_SITE = 'MyJira'
    DOCKER_HOST = 'npipe:////./pipe/dockerDesktopLinuxEngine'
  }

  stages {
    stage('Clone Repository') {
      steps {
        git credentialsId: 'github-creds', branch: 'main', url: 'https://github.com/zechih/CarbonSense'
      }
    }

  stage('Build and Push') {
    steps {
      withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        script {
          docker.withRegistry('https://index.docker.io/v1/', 'docker-creds') {
  
            // Build and push Spring Boot app image
            def appImage = docker.build("zechih/carbonsense-app:${env.BUILD_NUMBER}", '.')
            appImage.push()
  
            // Build and push MySQL image using Dockerfile.mysql
            def dbImage = docker.build("zechih/carbonsense-db:${env.BUILD_NUMBER}", '-f Dockerfile.mysql .')
            dbImage.push()
          }
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
          def comment = "✅ Build #${env.BUILD_NUMBER} succeeded. Docker image pushed and JMeter tests completed."
          jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: comment
        }
      }
    }
  }

  post {
    success {
      script {
        def comment = "🎉 Build #${env.BUILD_NUMBER} passed successfully."
        jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: comment
      }
    }

    failure {
      script {
        jiraAddComment site: env.JIRA_SITE, idOrKey: env.JIRA_ISSUE, comment: '❌ Build failed. Please check the logs.'
      }
    }
  }
}
