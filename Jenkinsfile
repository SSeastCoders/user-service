pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'git submodule update --init --recursive'
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
