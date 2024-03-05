pipeline {
    agent any

    tools {
        maven 'Host' // 在Jenkins全局工具配置中定义的maven标识符
        jdk 'Host' // 在Jenkins全局工具配置中定义的jdk标识符
    }

    environment {
        TAG = "0.1.1-test88"
        HARBOR_URL = "harbor.towelove.cn"
        BASE_NAME = "towelove"
        IMAGE_URL = "${env.HARBOR_URL}/${env.BASE_NAME}" // 修正了变量名的拼写错误
    }

    stages {
        stage('检查环境') {
            steps {
                script {
                    sh 'java -version'
                    sh 'mvn -v'
                    sh 'git --version'
                    sh 'docker buildx --help'
                }
            }
        }
        stage('拉取代码') {
            steps {
                // 确保定义了env.credentialsId, env.GIT_REPO_URL, 和env.GIT_BRANCH
                git(credentialsId: "${env.credentialsId}", url: "${env.GIT_REPO_URL}", branch: "${env.GIT_BRANCH}")
            }
        }
        stage('构建jar包') {
            steps {
                script {
                    sh 'mvn -B package --file pom.xml -DskipTests -P pro'
                    sh 'mkdir staging && cp Towelove-*/target/*.jar staging'
                    sh 'ls -al staging'
                }
            }
        }
        stage('登录harbor仓库') {
            steps {
                script {
                    sh "docker login -u ops -p Venus20230829 https://${env.HARBOR_URL}"
                }
            }
        }
        stage('构建推送docker镜像') {
            steps {
                script {
                    buildAndPushDocker('Towelove-Gateway', 'towelove-gateway')
                    buildAndPushDocker('Towelove-Auth', 'towelove-auth')
                    buildAndPushDocker('Towelove-User', 'towelove-user')
                    buildAndPushDocker('Towelove-Loves', 'towelove-loves')
                    buildAndPushDocker('Towelove-Server-Center', 'towelove-server-center')
                    buildAndPushDocker('Towelove-Msg', 'towelove-msg')
                }
            }
        }
        stage('部署docker容器') {
            steps {
                script {
                    deploy('towelove-gateway')
                    deploy('towelove-auth')
                    deploy('towelove-user')
                    deploy('towelove-loves')
                    deploy('towelove-server-center')
                    deploy('towelove-msg')
                }
            }
        }
    }
    post {
        always {
            // 清理工作，例如删除不再需要的文件等
            script {
                sh 'mvn clean'
                sh 'rm -rf staging'
                sh 'ls -al'
            }
            echo '构建完成.'
        }
    }
}

// 外部方法定义
void buildAndPushDocker(String jarName, String imageName) {
    def fullTag = "${env.IMAGE_URL}/${imageName}:${env.TAG}"
    try {
        // sh "docker buildx build --file ./Docker-Build/${jarName}/Dockerfile --tag ${fullTag} ."
        sh "docker buildx build --build-arg SERVICE_NAME=${jarName} --file ./Docker-Build/Dockerfile --tag ${fullTag} ."
        sh "docker push ${fullTag}"
    } catch (Exception e) {
        echo "构建或推送Docker镜像失败: ${e.getMessage()}"
        // 可以在这里添加更多的错误处理逻辑，例如发送通知给团队成员
        throw new RuntimeException("构建或推送Docker镜像失败", e)
    }
}

void deploy(String imageName) {
    try {
        sshPublisher(
            publishers: [
                sshPublisherDesc(
                    configName: 'cn-beijing-1',
                    transfers: [
                        sshTransfer(
                            execCommand: """
                            docker login -u ops -p Venus20230829 https://${env.HARBOR_URL}
                            sudo /opt/ops/deploy-harbor.sh ${imageName} ${env.TAG}
                            """
                        )
                    ]
                )
            ]
        )
    } catch (Exception e) {
        echo "部署失败: ${e.getMessage()}"
        // 添加更多的错误处理逻辑，例如回滚操作
        throw new RuntimeException("部署失败", e)
    }
}