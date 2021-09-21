pipeline {
    agent any

    environment {
        serviceName = 'user-service'
        awsRegion = 'us-east-1'
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
                withSonarQubeEnv('SonarQubeServer') {
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
                sh 'mvn clean package -P ${mavenProfile} -Dskiptests'
            }
        }
        stage('Docker Image Build and ECR Image Push') {
            steps {
                withCredentials([string(credentialsId: 'publicNumber', variable: 'awsID')]) {
                    sh '''
                        # authenticate aws account
                        aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/${awsID}

                        docker build -t public.ecr.aws/${awsID}/dev-${serviceName}:${commitIDShort} .
                        docker push public.ecr.aws/${awsID}/dev-${serviceName}:${commitIDShort}

                        docker build -t public.ecr.aws/${awsID}/dev-${serviceName}:latest .
                        docker push public.ecr.aws/${awsID}/dev-${serviceName}:latest

                    '''
                }
            }
        }
    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
    post {
        success {
            sh 'echo "SUCCESS"'
        }
    }
}
