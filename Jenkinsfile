
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
        stage('Maven Build') {
            steps {
                sh 'mvn clean package'
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
        stage('Docker Image Build and ECR Image Push') {
            steps {
                withCredentials([string(credentialsId: 'awsAccountNumber', variable: 'awsID')]) {
                    sh '''
                        aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.us-east-1.amazonaws.com
                        docker build -t ${awsID}.dkr.ecr.us-east-1.amazonaws.com/user-service:latest .
                        docker push ${awsID}.dkr.ecr.us-east-1.amazonaws.com/user-service:latest
                    '''
                }
            }
        }
    }
    // add post stage to delete docker images that are old
}