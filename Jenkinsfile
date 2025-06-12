pipeline {
  agent any

  environment {
    IMAGE_NAME = 'zechih/carbonsense'
    JIRA_ISSUE = 'CAR-1'
    JIRA_SITE = 'MyJira'
  }

  stages {
    stage('Clone Repository') {
      steps {
        git credentialsId: 'github-creds', branch: 'main', url: 'https://github.com/zechih/CarbonSense'
      }
    }

    stages {
        stage('Build and Push') {
            steps {
                sh """
                    echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin
                    docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                    docker push ${IMAGE_NAME}:${BUILD_NUMBER}
                """
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
          jiraAddComment site: "${env.JIRA_SITE}", idOrKey: "${env.JIRA_ISSUE}", comment: 'Build failed.'
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
              comment: 'Build failed. Please check the logs.'
          )
      }
    }
  }
}
