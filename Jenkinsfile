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


        stage('build maven') {
            steps {
                echo "begin pkg"
                sh "ls -a"
                sh "mvn -v"
                sh "mvn -U -am clean package"

            }
        }
    }
}
