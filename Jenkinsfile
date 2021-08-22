pipeline {
    agent any
    tools {
        maven 'Maven'
        dockerTool 'Docker'
    }
    stages {
        stage("Build") {
            steps{
                echo 'Building the application...'
                sh 'mvn clean install'
            }
        }
        stage("Deploy") {
            steps{
                echo 'Deploying the application...'
                sh 'docker build --build-arg artifactid=music-store-demo --build-arg version=0.0.1 -t mc/music-store-demo - < Dockerfile'
                sh 'docker run -ti -p 9000:5000 mc/music-store-demo'
            }
        }
    }
}