pipeline {
    agent any
    environment{}
    stages {
        /* Init Stage */
        stage() {
            steps {
                script {
                    sh "docker login";
                }
            }
        }

         /* Build Stage */
        stage("Building Docker images") {
            steps {
                script {
                    sh "mvn clean package -P build-docker-image";
                }
            }
        }

        /* Deploy Stage */
        stage("Deploy Docker Images"){
            steps {
                script {

                }
            }
        }
    }
}