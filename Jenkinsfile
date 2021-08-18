pipeline {
    agent any
    stages {
        stage('Installing Dependancies') {
            steps {
                sh 'cd core-library'
                sh 'git submodule update --init --recursive'
                sh 'mvn -DskipTests clean package'
                sh 'cd ..'
                sh 'mvn -DskipTests clean package'
            }
        }
        // stage('SonarQube analysis') {
        //     steps {
        //         withSonarQubeEnv('sonarScanner') {
        //             sh 'mvn sonar:sonar'
        //         }
        //     }
        // }
        // stage('Quality Gate'){
        //     steps {
        //         waitForQualityGate abortPipeline: true
        //     }
        // }
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarScanner') {
                    sh 'mvn -DskipTests clean package sonar:sonar'
                } // SonarQube taskId is automatically attached to the pipeline context
            }
        }
        stage("Quality Gate"){
            steps {
                timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }
        stage('Placing Environmental Variables') {
            steps {
                sh 'export $(cat .env | xargs)'
            }
        }
        stage('Build') {
            steps {
                sh 'java -jar -Dspring.profiles.active=dev users-api/target/users-api-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}