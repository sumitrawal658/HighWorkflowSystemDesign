pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-repo/load-tests.git'
            }
        }
        stage('Run Load Tests') {
            steps {
                sh 'k6 run load_test.js'
            }
        }
        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: 'results/**', fingerprint: true
            }
        }
    }
}
