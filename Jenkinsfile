pipeline {
    agent any
    triggers {
        githubPush()
    }
    stages {
        stage('configure'){
           steps{
               sh 'git stash'
               sh 'git pull'
            }
        }
        stage('Apply Right'){
           steps{
               sh 'chmod +x gradlew'
            }
         }
        stage('Clean'){
           steps{
               sh './gradlew clean'
            }
         }
        stage('Build Debug App'){
           steps{
               sh './gradlew assembleDebug'
            }
         }
        stage('Build Release App'){
             steps{
               sh './gradlew assembleRelease'
             }
        }
        stage('Unit Tests'){
             steps{
               sh './gradlew test'
             }
        }
    }
}
