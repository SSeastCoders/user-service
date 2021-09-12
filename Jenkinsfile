
pipeline {
    agent any
    environment {
    serviceName = 'user-service'
    awsRegion = 'us-east-1'
    mavenProfile = 'dev'
    commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
    }
    stages {
    stage('Clean and Test') {
    steps {
    sh 'mvn clean test'
    }

}