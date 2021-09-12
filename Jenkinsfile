
pipeline {
    agent any
    environment {
    serviceName = 'user-service'
//     awsRegion = 'us-east-1'
//     mavenProfile = 'dev'
     commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                    '''
            }
        }
        stage ('Build') {
            steps {
                echo 'This is a pipeline'
                }
           }
        }
    }

}