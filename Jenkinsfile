pipeline {
    agent any
    triggers {
        githubPush()
    }
    stages {
        stage('Configure'){
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
        stage('Copy APK'){
             steps{
               sh 'sudo cp app/build/outputs/apk/release/app-release-unsigned.apk /var/ww/html/release.apk'
               sh 'sudo cp app/build/outputs/apk/debug/app-debug.apk /var/ww/html/debug.apk'
             }
        }
    }
    post {
        always {
            script{
                emailext (body: '${DEFAULT_CONTENT}',
                          recipientProviders: [[$class: 'CulpritsRecipientProvider']],
                          subject: '${DEFAULT_SUBJECT}',
                          to: 'benjamin.bappel@student.hogent.be')
            }
        }
    }
}
