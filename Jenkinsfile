
pipeline {
    agent any
    stages {
        stage('Clean and Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarScanner') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Quality Gate'){
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        // Stage Docker image
        // Stage Deplotment
    }
}