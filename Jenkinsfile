pipeline {
    agent any

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
                bat 'gradlew.bat :Classical:it :Lambda:SpeedLayer:it :Lambda:BatchLayer:it'
            }
        }
        // TODO: Enable when ready
        /*
        stage('Dockerize') {
            steps {
                bat 'gradlew.bat dockerize'
            }
        }
        */
        // TODO: Deploy using DCOS + Docker Repository
    }
}