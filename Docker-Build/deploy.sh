#!/bin/bash

# 定义日志颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 定义日志文件路径
LOG_FILE="/opt/ops/logs/deploy.log"

# 日志函数
log() {
    local level=$1
    local message=$2
    local color=$NC
    # 获取当前时间，格式为 2024-02-21 20:05:05.132
    local timestamp=$(date "+%Y-%m-%d %H:%M:%S.%3N")

    case "$level" in
        INFO)
            color=$GREEN
            ;;
        WARNING)
            color=$YELLOW
            ;;
        ERROR)
            color=$RED
            ;;
        *)
            message="Unknown level: $message"
            ;;
    esac

    # 确保日志文件存在
    if [ ! -f "$LOG_FILE" ]; then
        touch "$LOG_FILE"
    fi

    # 检查是否为多行消息
    if echo "$message" | grep -q $'\n'; then
        # 处理多行消息
        echo -e "$message" | while IFS= read -r line; do
            local log_message="${color}[$timestamp] [$level]${NC} ${color}$line${NC}"
            echo -e "$log_message"
            # 追加日志消息到日志文件，移除颜色代码以便于文件阅读
            echo -e "[$timestamp] [$level] $line" >> "$LOG_FILE"
        done
    else
        # 单行消息处理
        local log_message="${color}[$timestamp] [$level]${NC} ${color}$message${NC}"
        echo -e "$log_message"
        # 追加日志消息到日志文件，移除颜色代码以便于文件阅读
        echo -e "[$timestamp] [$level] $message" >> "$LOG_FILE"
    fi
}

# 构建 docker run 命令公共函数
# 示例调用
# run_container "towelove-gateway" "10.0.0.21" "8080" "0.2.0"
run_container() {
    if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ]; then
        log ERROR "Missing required arguments for run_container."
        exit 1
    fi

    local service_name=$1
    local service_ip=$2
    local service_port=$3
    local image_version=$4
    local image_base="ghcr.nju.edu.cn/towelove"
    local full_name="${image_base}/${service_name}:${image_version}"

    if ! sudo docker image inspect "$full_name" > /dev/null 2>&1; then
        log INFO "Image $full_name does not exist locally. Pulling..."

        # 使用命令替换和重定向来捕获 docker pull 命令的输出
        local pull_output=$(sudo docker pull "$full_name" 2>&1)
        local pull_exit_status=$?

        if [ $pull_exit_status -ne 0 ]; then
            log ERROR "Failed to pull image $full_name. Error Output: \n$pull_output"
            exit 1
        else
            log INFO "Successfully pulled image $full_name. Output: \n$pull_output"
        fi
    else
        log INFO "Image $full_name exists locally."
    fi


    local run_cmd="sudo docker run -d --restart=always --name ${service_name} --privileged=true \
--net=venus --ip ${service_ip} -p ${service_port}:${service_port} \
-v /etc/localtime:/etc/localtime:ro -e TZ=Asia/Shanghai \
${full_name}"

    log INFO "Executing command: $run_cmd"
    # 使用命令替换和管道捕获命令的输出和错误
    local cmd_output
    cmd_output=$(eval "$run_cmd" 2>&1)
    local cmd_exit_status=$?
    
    if [ $cmd_exit_status -ne 0 ]; then
        log ERROR "Failed to execute command. Error Output: $cmd_output"
        exit 1
    else
        log INFO "Successfully executed command. Output: $cmd_output"
    fi
}

# 示例调用
# run_gateway "0.2.0"
# 需要 image_version 参数
run_gateway() {
    local image_version=${1:-"0.1.11-test3"}
    log INFO "Running towelove-gateway with image version $image_version"
    run_container "towelove-gateway" "10.0.0.21" "8080" "$image_version"
}

run_auth() {
    local image_version=${1:-"0.1.11-test3"}
    log INFO "Running towelove-auth with image version $image_version"
    run_container "towelove-auth" "10.0.0.22" "9204" "$image_version"
}

run_loves() {
    local image_version=${1:-"0.1.11-test3"}
    log INFO "Running towelove-loves with image version $image_version"
    run_container "towelove-loves" "10.0.0.23" "9203" "$image_version"
}

run_msg() {
    local image_version=${1:-"0.1.11-test3"}
    log INFO "Running towelove-msg with image version $image_version"
    run_container "towelove-msg" "10.0.0.24" "9202" "$image_version"
}

run_server_center() {
    local image_version=${1:-"0.1.11-test3"}
    log INFO "Running towelove-server-center with image version $image_version"
    run_container "towelove-server-center" "10.0.0.25" "9201" "$image_version"
}

run_user() {
    local image_version=${1:-"0.1.11-test3"}
    log INFO "Running towelove-user with image version $image_version"
    run_container "towelove-user" "10.0.0.26" "9205" "$image_version"
}

# 函数映射服务名到启动函数
declare -A service_to_function_map=(
    ["towelove-gateway"]="run_gateway"
    ["towelove-auth"]="run_auth"
    ["towelove-loves"]="run_loves"
    ["towelove-msg"]="run_msg"
    ["towelove-server-center"]="run_server_center"
    ["towelove-user"]="run_user"
)

# 综合操作函数
# 示例调用
# deploy_service "auth" "0.2.0"
# deploy_service() {
#     local original_service_name=$1
#     local image_version=$2
#     local service_name=$(echo "$original_service_name" | tr '[:upper:]' '[:lower:]')

#     local old_image_id=""

#     # 检查容器是否正在运行
#     running_container_id=$(sudo docker ps -q -f name=^/${service_name}$)

#     if [ -n "$running_container_id" ]; then
#         log WARNING "Container ${service_name} is running. Stopping and removing..."

#         # 获取旧容器使用的镜像ID
#         old_image_id=$(sudo docker inspect $running_container_id --format='{{.Image}}' | sed 's/sha256://')

#         if ! sudo docker rm -f ${service_name} > /dev/null 2>&1; then
#             log ERROR "Failed to remove container ${service_name}."
#             exit 1
#         else
#             log INFO "Successfully removed container ${service_name}."
#         fi

#     else
#         log INFO "Container ${service_name} is not running."
#     fi


#     if [[ ${service_to_function_map[$service_name]+_} ]]; then
#         log INFO "Deploying service ${original_service_name} with image version $image_version"
#         ${service_to_function_map[$service_name]} "$image_version"
#     else
#         log ERROR "Service name '${original_service_name}' is not recognized."
#         return 1
#     fi

#     sleep 5  # 等待容器启动

#     if ! sudo docker ps -q -f status=running -f name=^/${service_name}$ > /dev/null; then
#         log ERROR "Failed to start container ${service_name}."
#         exit 1
#     else
#         log INFO "Container ${service_name} has started successfully."
        
#         # 如果有旧的镜像ID，尝试删除旧镜像
#         if [[ ! -z "$old_image_id" ]]; then
#             if sudo docker rmi "$old_image_id" > /dev/null 2>&1; then
#                 log INFO "Successfully removed old image $old_image_id."
#             else
#                 log WARNING "Failed to remove old image $old_image_id. It may be used by other containers."
#             fi
#         fi
#     fi
# }
deploy_service() {
    local original_service_name=$1
    local image_version=$2
    local service_name=$(echo "$original_service_name" | tr '[:upper:]' '[:lower:]')

    local old_image_id=""
    local new_image_id=""

    # 检查容器是否正在运行
    running_container_id=$(sudo docker ps -q -f name=^/${service_name}$)

    if [ -n "$running_container_id" ]; then
        log WARNING "Container ${service_name} is running. Stopping and removing..."

        # 获取旧容器使用的镜像ID
        old_image_id=$(sudo docker inspect $running_container_id --format='{{.Image}}' | sed 's/sha256://')

        if ! sudo docker rm -f ${service_name} > /dev/null 2>&1; then
            log ERROR "Failed to remove container ${service_name}."
            exit 1
        else
            log INFO "Successfully removed container ${service_name}."
        fi
    else
        log INFO "Container ${service_name} is not running."
    fi

    # 构建新镜像的完整名称
    local image_base="ghcr.nju.edu.cn/towelove"
    local full_name="${image_base}/${service_name}:${image_version}"
    # 获取新镜像的ID
    new_image_id=$(sudo docker images -q $full_name 2>/dev/null)

    # 检查新旧镜像ID是否相同
    if [[ "$old_image_id" == "$new_image_id" && -n "$new_image_id" ]]; then
        log INFO "The image version for ${service_name} remains unchanged (${image_version}). No deployment necessary."
        return 0
    fi

    if [[ ${service_to_function_map[$service_name]+_} ]]; then
        log INFO "Deploying service ${original_service_name} with image version $image_version"
        ${service_to_function_map[$service_name]} "$image_version"
    else
        log ERROR "Service name '${original_service_name}' is not recognized."
        return 1
    fi

    sleep 5  # 等待容器启动

    if ! sudo docker ps -q -f status=running -f name=^/${service_name}$ > /dev/null; then
        log ERROR "Failed to start container ${service_name}."
        exit 1
    else
        log INFO "Container ${service_name} has started successfully."
        
        log INFO "old_image_id: ${old_image_id:0:12}. new_image_id:${new_image_id:0:12}."

        # 删除旧镜像，如果有且不等于新镜像
        if [[ ! -z "$old_image_id" && "${old_image_id:0:12}" != "${new_image_id:0:12}" ]]; then
            if sudo docker rmi "$old_image_id" > /dev/null 2>&1; then
                log INFO "Successfully removed old image $old_image_id."
            else
                log WARNING "Failed to remove old image $old_image_id. It may be used by other containers."
            fi
        else
            # 如果没有旧镜像需要删除，或者旧镜像与新镜像相同，输出一个相应的日志消息
            if [[ -z "$old_image_id" ]]; then
                log INFO "No old image to remove for service ${service_name}."
            elif [[ "${old_image_id:0:12}" == "${new_image_id:0:12}" ]]; then
                log INFO "Old image is the same as the new image for service ${service_name}, no removal necessary."
            fi
        fi

    fi
}


main() {
    local original_service_name=$1
    local image_version=$2

    # 脚本开始执行的提示和分隔线
    log INFO "==================== Script Start ===================="
    log INFO "Script execution begins at $(date '+%Y-%m-%d %H:%M:%S')"

    deploy_service "${original_service_name}" "${image_version}"

    # 脚本结束执行的提示和分隔线
    log INFO "Script execution ends at $(date '+%Y-%m-%d %H:%M:%S')"
    log INFO "==================== Script End ===================="

}

main "$@"