pipeline {
    agent any
    stages{

        stage('Build And Deploy Docker Image'){
                    steps{
                        script{
                            echo "deploying the application"
                            sh "mvn package -P build-docker-image"

                        }
                    }
                }
            }
  }

