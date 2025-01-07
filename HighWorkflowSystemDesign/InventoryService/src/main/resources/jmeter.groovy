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
                sh 'jmeter -n -t test-plan.jmx -l results.jtl'
            }
        }
        stage('Publish Results') {
            steps {
                publishPerformanceReport sourceDataFiles: 'results.jtl'
            }
        }
    }
}