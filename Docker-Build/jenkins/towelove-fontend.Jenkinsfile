pipeline {
    agent any
    
    tools {
        nodejs 'Host'
    }

    stages {
        stage('检查环境') {
            steps {
                script {
                    sh 'node -v'
                    sh 'npm -v'
                    sh 'pnpm -v'
                }
            }
        }
        stage('拉取代码') {
            steps {
                git(credentialsId: "${env.credentialsId}", url: "${env.GIT_REPO_URL}", branch: "${env.GIT_BRANCH}")
            }
        }
        stage('安装依赖') {
            steps {
                sh 'pnpm install'
            }
        }
        stage('构建项目') {
            steps {
                sh 'pnpm build:pro' // 假设你的package.json中配置了"build"脚本
            }
        }
        stage('打包制品') {
            steps {
                sh 'mkdir -p ./web.towelove.cn'
                sh 'cp -r ./dist/* ./web.towelove.cn/'
                sh 'tar -zcvf web.tar.gz ./web.towelove.cn/'
            }
        }
        stage('传输文件') {
            steps {
                sshPublisher(
                    continueOnError: false, // 出错时停止
                    failOnError: true, // 出错时标记构建失败
                    publishers: [
                        sshPublisherDesc(
                            configName: 'cn-beijing-1',
                            verbose: true, // 打印详细日志
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'web.tar.gz', // 要传输的文件，可以使用通配符
                                    //removePrefix: 'target', // 移除文件路径前的这部分
                                    remoteDirectory: './www', // 目标服务器上的目录
                                    execCommand: 'echo "File transferred."' // 传输后在远程服务器上执行的命令
                                    // execCommand: """
                                    // 
                                    // 
                                    // """
                                )
                            ]
                        )
                    ]
                )
            }
        }
    }
}
