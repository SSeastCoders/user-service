
pipeline {
    agent any
    environment {
        serviceName = 'dev-user'
        awsRegion = 'us-east-2'
        mavenProfile = 'dev'
        commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
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
        stage('Maven Build') {
            steps {
                sh 'mvn package -P ${mavenProfile} -Dskiptests'
            }
        }

        stage('Docker Image Build and ECR Image Push') {
            steps {
                withCredentials([string(credentialsId: 'awsAccountNumber', variable: 'awsID')]) {
                    sh '''
                        aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.us-east-2.amazonaws.com
                        docker build -t ${awsID}.dkr.ecr.us-east-2.amazonaws.com/${serviceName}:latest .
                        docker push ${awsID}.dkr.ecr.us-east-2.amazonaws.com/${serviceName}:latest
                    '''
                }
            }
        }
        stage('catch me') {
          steps {
             sh "echo 'after docker'"
          }
        }

    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
}