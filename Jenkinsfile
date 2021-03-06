pipeline {
    agent any

    environment {
        DOCKER_MACHINE_URL = credentials('DOCKER_MACHINE_URL')
        DOCKER_CERT_PATH = credentials('DOCKER_CERT_PATH')
        DOCKER_USERNAME = credentials('DOCKER_USERNAME')
        DOCKER_REGISTRY_URL = credentials('DOCKER_REGISTRY_URL')
        DOCKER_USER_EMAIL = credentials('DOCKER_USER_EMAIL')
        DOCKER_PASSWORD = credentials('DOCKER_PASSWORD')

        DOCS_URL = credentials('DCOS_URL')
    }

    stages {
        stage('Clean'){
            steps {
                bat 'gradlew.bat clean'
            }
        }
        stage('Build') {
            steps {
                bat 'gradlew.bat build'
            }
        }
        stage('Test'){
            steps {
                bat 'gradlew.bat test'
            }
        }
        stage('IntegrationTest'){
            steps {
                bat 'gradlew.bat it'
            }
        }

        stage('Dockerize') {
            steps {
                bat 'gradlew.bat dockerize'
            }
        }

        stage('Deploy') {
            steps {
                bat 'gradlew.bat deploy'
            }
        }

        stage('Acceptance Tests: Classical') {
            steps {
                bat 'gradlew.bat :Classical:acceptanceTestDockerize'
            }
        }

        stage('Acceptance Tests: Lambda') {
            steps {
                bat 'gradlew.bat :Lambda:acceptanceTestDockerize'
            }
        }
    }
}