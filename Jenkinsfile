
pipeline {
    agent any
    environment {
        dockerImageName = "user-service"
        awsRegion = 'us-east-1'
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
                withCredentials([string(credentialsId: 'awsAccountNumber', variable: 'awsID')]) {
                    sh '''
                        aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.us-east-1.amazonaws.com
                        docker tag user-service:latest ${awsID}.dkr.ecr.us-east-1.amazonaws.com/user-service:latest
                        docker push ${awsID}.dkr.ecr.us-east-1.amazonaws.com/user-service:latest
                    '''
                }

                // sh 'aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsAccountID}.dkr.ecr.us-east-1.amazonaws.com'
                // sh 'docker tag user-service:latest ${awsAccountID}.dkr.ecr.us-east-1.amazonaws.com/user-service:latest'
                // sh 'docker push ${awsAccountID}.dkr.ecr.us-east-1.amazonaws.com/user-service:latest'
            }
        }
    }
}