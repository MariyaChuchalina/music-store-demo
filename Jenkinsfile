pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage("Build") {
            steps{
                echo 'Building the application...'
                sh 'mvn clean install'
            }
        }
    }
}