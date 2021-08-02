pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn -e -DskipTests clean package'
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: '**/*.jar'
        }
    }
}
