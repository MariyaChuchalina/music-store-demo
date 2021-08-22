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
        stage("Build Docker Image") {
            steps{
                echo 'Building the Docker image...'
                sh 'docker build --build-arg artifactid=music-store-demo --build-arg version=0.0.1 -t mc/music-store-demo .'
            }
        }
        stage("Run Container") {
            steps{
                echo 'Running the container...'
                sh 'docker run -ti -p 9000:5000 mc/music-store-demo'
            }
        }
    }
}