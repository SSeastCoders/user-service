
pipeline {
    agent any
    environment {
        serviceName = 'dev-user'
        servicePort = 8222
        awsRegion = 'us-east-2'
        appEnv = 'jtdo'
        mavenProfile='dev'
        healthPath = '/users/actuator/health'
        organizationName = 'SSEastCoders'
    }
    stages {
        stage('Checkstyle stage') {
           steps {
             sh 'mvn checkstyle:check'
             }
        }
        stage('Clean and Test') {
            steps {
                sh 'mvn clean -Dskiptests'
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
                sh 'mvn package -P ${mavenProfile} -DskipTests'
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

    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
}