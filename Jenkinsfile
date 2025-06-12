pipeline {
  agent any

  environment {
    IMAGE_NAME = 'zechih/carbonsense'
    JIRA_ISSUE = 'CAR-1'
    JIRA_SITE = 'MyJira'
    DOCKER_CREDENTIALS = credentials('docker-creds') // Replace with actual ID
  }

  stages {
    stage('Clone Repository') {
      steps {
        git credentialsId: 'github-creds', branch: 'main', url: 'https://github.com/zechih/CarbonSense'
      }
    }

    stage('Build and Push') {
      steps {
        sh """
          echo "${DOCKER_CREDENTIALS_PSW}" | docker login -u "${DOCKER_CREDENTIALS_USR}" --password-stdin
          docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
          docker push ${IMAGE_NAME}:${BUILD_NUMBER}
        """
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
          jiraAddComment site: "${env.JIRA_SITE}", idOrKey: "${env.JIRA_ISSUE}", comment: comment
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
          site: "${env.JIRA_SITE}",
          idOrKey: "${env.JIRA_ISSUE}",
          comment: '❌ Build failed. Please check the logs.'
        )
      }
    }
  }
}
