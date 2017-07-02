pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                gradle 'clean build'
            }
        }
        stage('Test'){
            steps {
                gradle 'test'
            }
        }
        /*
        stage('Deploy') {
            steps {
                sh 'make publish'
            }
        }
        */
    }
}