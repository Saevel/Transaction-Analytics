pipeline {
    agent any

    environment {
        DOCKER_MACHINE_URL = credentials('DOCKER_MACHINE_URL')
        DOCKER_CERT_PATH = credentials('DOCKER_CERT_PATH')
    }

    stages {

        stage('Clean') {
            steps {
                bat 'gradlew.bat clean'
            }
        }

        stage('Build') {
            steps {
                bat 'gradlew.bat build'
            }
        }

        stage('Test') {
            steps {
                bat 'gradlew.bat test'
            }
        }

        stage('IntegrationTest') {
            steps {
                bat 'gradlew.bat :Classical:it :Lambda:SpeedLayer:it :Lambda:BatchLayer:it'
            }
        }

        stage('Deploy') {
            steps {
                bat 'gradlew.bat deploy'
            }
        }

        // TODO: Actual acceptance tests call
        stage('Acceptance Tests: Classical') {
            steps {
                bat 'gradlew.bat :Classical:acceptanceTestDockerize'
            }
        }

        // TODO: Actual acceptance tests call
        stage('Acceptance Tests: Lambda') {
            steps {
                bat 'gradlew.bat :Lambda:acceptanceTestDockerize'
            }
        }

        // TODO: Deploy using DCOS + Docker Repository
    }
}