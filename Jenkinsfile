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
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarScanner') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Quality Gate'){
            steps {
                waitForQualityGate abortPipeline: true
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
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}