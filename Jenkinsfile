pipeline {
    agent any
    stages {
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn sonar:sonar -Dsonar.login=12367eaccd39c2d74a819f9cbaaa2b01db70aaee"
                }
            }
        }
        stage('Quality Gate'){
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: '**/*.jar'
        }
    }
}
