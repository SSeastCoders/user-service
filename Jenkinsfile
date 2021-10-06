
pipeline {
    agent any
    environment {
        serviceName = 'dev-user'
        servicePort = 8222
        awsRegion = 'us-east-2'
        appEnv = 'jtdo'
        mavenProfile='dev'
        healthPath = '/users/health'
        organizationName = 'SSEastCoders'
        //commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
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



                        docker build -t ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com/${serviceName}:latest .
                        docker push ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com/${serviceName}:latest
                    '''
                }
            }
        }
        stage('catch me') {
          steps {
             sh "echo 'after docker'"
          }
        }
        stage('Deploy') {
          steps {
                sh '''
                    aws cloudformation deploy \
                    --stack-name ${serviceName}-stack \
                    --template-file deploy-stack.yml \
                    --parameter-overrides \
                        AppEnv=${appEnv} \
                        AppName=${organizationName} \
                        ServiceName=${serviceName} \
                        ServicePort=${servicePort} \
                        HealthPath=${healthPath} \
                    --capabilities CAPABILITY_NAMED_IAM \
                    --no-fail-on-empty-changeset \
                    --region ${awsRegion}
                    '''
          }
        }
        stage('after') {
          steps {
            sh "echo 'after deploy'"
          }
        }

    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
}