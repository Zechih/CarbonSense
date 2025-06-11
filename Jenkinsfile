pipeline {
  agent any

  environment {
    DOCKER_CRED = credentials('docker-hub')
    JIRA_CRED = credentials('jira-cred') // Atlassian username + API token
    IMAGE_NAME = 'zechih/carbonsense'
    JIRA_ISSUE = 'CAR-1' // Replace with real JIRA issue key
    JIRA_SITE = '02zechih-1749311651446.atlassian.net' // Must match Jenkins JIRA site ID
  }

  stages {
    stage('Clone Repository') {
      steps {
        git credentialsId: 'github', url: 'https://github.com/zechih/CarbonSense'
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          docker.withRegistry('https://index.docker.io/v1/', DOCKER_CRED) {
            def appImage = docker.build("${IMAGE_NAME}:${env.BUILD_NUMBER}")
            appImage.push()
          }
        }
      }
    }

    stage('Run JMeter Performance Test') {
      steps {
        sh 'jmeter -n -t tests/performance_test.jmx -l results/result.jtl'
      }
    }

    stage('Publish JMeter Report') {
      steps {
        publishPerformanceTestResultReport sourceDataFiles: 'results/result.jtl'
      }
    }

    stage('Update JIRA') {
      steps {
        script {
          def comment = "✅ Build #${env.BUILD_NUMBER} succeeded. Docker image pushed and JMeter tests completed."
          jiraAddComment site: 'my-jira', idOrKey: 'PROJECT-123', comment: 'Build failed.'
        }
      }
    }
  }

  post {
    success {
      script {
        def comment = "🎉 Build #${env.BUILD_NUMBER} passed successfully."
        jiraAddComment site: "${env.JIRA_SITE}", idOrKey: "${env.JIRA_ISSUE}", comment: comment
      }
    }

    failure {
      script {
          jiraAddComment(
              site: 'MyJira',
              idOrKey: 'CAR-1',
              comment: 'Build failed. Please check the logs.'
          )
      }
    }
  }
}
