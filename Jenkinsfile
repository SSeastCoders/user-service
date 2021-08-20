
pipeline {
    agent any
    environment {
        dockerImageName = "user-service"
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
        // once this is pushed to develop, use 'mvn -P dev clean package'
        // and remove extra parameter for jar file in Dockerfile 
        stage('Docker Image Build') {
            steps {
                sh 'mvn clean package'
                script {
                    dockerImage = docker.build dockerImageName
                }
            }
        }
        // Stage Deployment
        stage('ECR Image Push') {
            steps {
                script {
                    docker.withRegistry('public.ecr.aws/f2j6g2j3/user-service')
                    dockerImage.push('latest')
                }
            }
        }
    }
}