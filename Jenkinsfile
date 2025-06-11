pipeline {
  agent any

  environment {
    DOCKER_CRED = credentials('docker-hub')
    JIRA_CRED = credentials('jira-cred')
    IMAGE_NAME = 'zechih/CarbonSense'
    JIRA_SITE = 'https://02zechih-1749311651446.atlassian.net/jira' // defined in Jenkins Global Config
    JIRA_ISSUE = 'CAR-1'      // Replace with actual issue key
  }

  stages {
    stage('Clone Repository') {
      steps {
        git credentialsId: 'github', url: 'https://github.com/Pudd11ng/CarbonSense'
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
          def summary = "Build #${env.BUILD_NUMBER} - Docker Image pushed, JMeter test completed"
          jiraAddComment site: "${JIRA_SITE}", idOrKey: "${JIRA_ISSUE}", comment: summary
        }
      }
    }
  }

  post {
    success {
      echo 'Build completed successfully.'
    }
    failure {
      script {
        jiraAddComment site: "${JIRA_SITE}", idOrKey: "${JIRA_ISSUE}", comment: "Build #${env.BUILD_NUMBER} failed."
      }
    }
  }
}