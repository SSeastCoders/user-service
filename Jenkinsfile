pipeline {
    agent any
    stages {
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
<<<<<<< HEAD
                    sh "mvn sonar:sonar -Dsonar.login=12367eaccd39c2d74a819f9cbaaa2b01db70aaee"
=======
                    sh "mvn sonar:sonar"
>>>>>>> 7048be7f946085aac0950faefa2d05cdba60bb7f
                }
            }
        }
        stage('Quality Gate'){
            steps {
<<<<<<< HEAD
                waitForQualityGate abortPipeline: true
            }
        }
=======
                waitForQualityGate abortPipeline= true
            }
        } 
>>>>>>> 7048be7f946085aac0950faefa2d05cdba60bb7f
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
/*        stage('Test') {
            steps {
                sh 'mvn test'
            }
        } */
    }
    post {
/*        always {
            junit 'target/surefire-reports/*.xml'
        } */
        success {
            archiveArtifacts artifacts: '**/*.jar'
        }
    }
}
