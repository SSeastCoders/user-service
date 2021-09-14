//
// pipeline {
//     agent any
//     environment {
//     serviceName = 'user-service'
// //     awsRegion = 'us-east-1'
// //     mavenProfile = 'dev'
//      commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
//     }
//     stages {
//         stage('Initialize') {
//             steps {
//                 sh '''
//                     echo "PATH = ${PATH}"
//                     echo "M2_HOME = ${M2_HOME}"
//                     '''
//             }
//         }
//         stage ('Build') {
//             steps {
//                 echo 'This is a pipeline'
//             }
//
//         }
//     }
//
// }

pipeline {
    agent any
        tools {
            Maven '3.8.2'
//             jdk 'jdk8'
        }
    environment {
    serviceName = 'user-service'
    //awsRegion = 'us-east-1'
    mavenProfile = 'dev'
    commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
    }

    stages {
        stage('update submodule'){
        steps{
            sh 'git status'
        }
        }
        stage('Clean and Test') {
        steps {
        //sh 'git submodule update --init --recursive'
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
// stage('Maven Build') {
// steps {
// sh 'mvn clean package -P ${mavenProfile} -Dskiptests'
//             }
//         }
//         stage('Docker Image Build and ECR Image Push') {
//             steps {
//                 withCredentials([string(credentialsId: 'awsAccountNumber', variable: 'awsID')]) {
//                     sh '''
//                         # authenticate aws account
//                         aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.${awsRegion}.amazonaws.com
//
//                         docker build -t ${awsID}.dkr.ecr.us-east-1.amazonaws.com/${serviceName}:${commitIDShort} .
//                         docker push ${awsID}.dkr.ecr.us-east-1.amazonaws.com/${serviceName}:${commitIDShort}
//
//                         docker build -t ${awsID}.dkr.ecr.us-east-1.amazonaws.com/${serviceName}:latest .
//                         docker push ${awsID}.dkr.ecr.us-east-1.amazonaws.com/${serviceName}:latest
//                     '''
//                 }
//             }
//         }
//     }
//     post {
//         success {
//             sh 'docker image prune -af'
//         }
//     }
}
}