pipeline {
    agent any
    stages {
        stage('Installing Dependancies') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('SonarQube analysis') {
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
        // Deplotment
    }
}