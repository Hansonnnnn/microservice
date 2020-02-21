#!/bin/bash
pipeline {
    agent any
    tools {
    maven 'Maven'
    }
    environment {
        REPOSITORY = "https://github.com/ZhiTingXin/microservice.git"
        SERVICE_DIR = "microservice"
    }
    stages {
        stage('pull code') {
            steps {
                echo "start fetch code from git:${REPOSITORY}"
                deleteDir()
                git "${REPOSITORY}"
                script {
                    time = sh(returnStdout: true, script: 'date "+%Y%m%d%H%M"').trim()
                    git_version = sh(returnStdout: true, script: 'git log -1 --pretty=format:"%h"').trim()
                    build_tag = time + git_version
                }
            }
        }
        
        stage('quality test') {
            steps {
                echo "begin quality test"
                sh "mvn sonar:sonar -Dsonar.host.url=http://172.17.0.4:9000 -Dsonar.login=ea73c1fc82c1ec062f7e9ee2bb999a00c17f02b9"
            }
        }


        stage('build maven') {
            steps {
                echo "begin pkg"
                sh "ls -a"
                sh "mvn -v"
                '''sh "mvn -U -am clean package"'''

            }
        }
        
        stage("docker build") {
            steps {
                sh "pwd"
                sh "docker build -t ${SERVICE_DIR}:${build_tag} -f ./authserver/Dockerfile ."
            }
        }
    }
}
