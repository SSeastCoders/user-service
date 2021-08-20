
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
        // Learn to interpolate jenkin variables to make this pipeline
        // more reusable for all spring boot services
        stage('ECR Image Push') {
            steps {
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 326848027964.dkr.ecr.us-east-1.amazonaws.com'
                sh 'docker tag user-service:latest 326848027964.dkr.ecr.us-east-1.amazonaws.com/user-service:latest'
                sh 'docker push 326848027964.dkr.ecr.us-east-1.amazonaws.com/user-service:latest'
            }
        }
    }
}