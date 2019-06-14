pipeline {
    agent any
    triggers {
        pollSCM 'H/10 * * * *'
    }
    tools {
        maven 'maven-3.6.0'
        jdk 'amazon-corretto-11'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean install deploy -Dmaven.javadoc.skip=true'
            }
        }
    }
}
