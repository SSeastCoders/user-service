
pipeline {
    agent any
    environment {
        serviceName = 'user-service'
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
                sh 'mvn clean package -P ${mavenProfile} -Dskiptests'
            }
        }
        stage('Docker Image Build and ECR Image Push') {
            steps {
                withCredentials([string(credentialsId: 'awsAccountNumber', variable: 'awsID')]) {
                        echo 'building.....'
//                     sh '''
//                         # authenticate aws account
//                         aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin 326848027964.dkr.ecr.${awsRegion}.amazonaws.com
//
//                         docker context use myenv
//
//                         docker build -t ${awsID}.dkr.ecr.us-east-2.amazonaws.com/${serviceName}:${commitIDShort} .
//                         docker push ${awsID}.dkr.ecr.us-east-2.amazonaws.com/${serviceName}:${commitIDShort}
//
//                         docker build -t ${awsID}.dkr.ecr.us-east-2.amazonaws.com/${serviceName}:latest .
//                         docker push ${awsID}.dkr.ecr.us-east-2.amazonaws.com/${serviceName}:latest
//                     '''
//                 }
            }
        }
    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
}