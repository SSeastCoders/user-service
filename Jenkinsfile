
pipeline {
    agent any
    environment {
        serviceName = 'user-service'
        awsRegion = 'us-east-1'
        commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
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
                        aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com
                        docker build -t ${awsID}.dkr.ecr.us-east-1.amazonaws.com/${serviceName}:${commitIDShort} .
                        docker push ${awsID}.dkr.ecr.us-east-1.amazonaws.com/${serviceName}:${commitIDShort}
                    '''
                }
            }
        }
    }
    // On pipeline success delete docker images by docker image ID
    post {
        success {
            sh ''' docker rmi $(docker images -a | grep aws | awk \\'{print $3}\\') '''
        }
    }
}