
pipeline {
    agent any
    environment {
        dockerImageName = "eastcodersbank/user-service"
        dockerImage = ''
    }
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
        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Docker Image Build') {
            steps {
                sh 'mvn clean package'
                sh 'pwd'
                sh 'ls'
                sh 'ls target'
                sh 'ls users-api'
                sh 'ls users-api/target'
                script {
                    dockerImage = docker.build dockerImageName
                }
            }
        }
        // Stage Deployment
    }
}