pipeline {
    agent any
    stages{

        stage('Compile-package'){
                    steps{
                        script{
                            sh 'mvn package'
                        }
                    }
                }
                stage('Sonarqube Analysis'){
                    steps{
                        script{
                            def mvnHome = tool name: 'maven', type: 'maven'
                            withSonarQubeEnv('sonar2'){
                                sh "${mvnHome}/bin/mvn sonar:sonar"
                            }
                        }
                    }
                }
                stage("Quality gate status check") {
                    steps {
                        script{
                        sleep(60)
                      timeout(time: 1, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        if(qg.status != 'OK') {
                            slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#jenkins-notifications', color: 'danger', message: 'Pipeline aborted: Sonarqube Analysis marked as failed', teamDomain: 'Legion14', tokenCredentialId: 'slack-channel'
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                        }
                      }
                    }
                  }
                stage('Email Notification'){
                    steps{
                        script{
                            mail bcc: '', body: '''Hi,
        Welcome to jenkins email alerts.
        Thanks,
        Anas''', cc: '', from: '', replyTo: '', subject: 'Jenkins Job', to: 'anasbo7@hotmail.com'
                        }
                    }
                }
                stage('Slack Notification'){
                    steps{
                        script{
                            slackSend baseUrl: 'https://hooks.slack.com/services/', channel: '#jenkins-notifications', color: 'good', message: 'Welcome to jenkins notifications channel, legionaries. Sent from Jenkins', teamDomain: 'Legion14', tokenCredentialId: 'slack-channel'
                        }
                    }
                }

        stage('Build And Deploy Docker Image'){
                    steps{
                        script{
                            echo "deploying the application"
                            sh "mvn package -P build-docker-image"

                        }
                    }
                }

        stage('kubernetes init'){
            steps{
                script{
                    sh "minikube start --driver=virtualbox"
                }
            }
        }

        stage('kubernetes deploying to cluster'){
            steps{
                script{
                    sh "kubectl apply -f k8s/minikube/bootstrap/postgres"
                    sh "kubectl apply -f k8s/minikube/bootstrap/zipkin"
                    sh "kubectl apply -f k8s/minikube/bootstrap/rabbitmq"
                    sh "kubectl apply -f k8s/minikube/services/customer"
                    sh "kubectl apply -f k8s/minikube/services/fraud"
                    sh "kubectl apply -f k8s/minikube/services/notification"
                }
            }
        }
    }
  }

