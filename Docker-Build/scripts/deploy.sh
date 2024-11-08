#!/bin/bash

# 定义日志颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 定义日志文件路径
LOG_FILE="/opt/ops/logs/deploy.log"
# eg: REGISTRY_URL="ghcr.nju.cn/"
REGISTRY_URL="ghcr.hbut.net/"
DEFAULT_IMAGE_VERSION="latest"  # 假设这是你的默认版本号

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
    local image_base="${REGISTRY_URL}towelove"
    local full_name="${image_base}/${service_name}:${image_version}"

    local run_cmd="sudo docker run -d --restart=always --name ${service_name} --privileged=true \
--net=venus --ip ${service_ip}  \
-v /etc/localtime:/etc/localtime:ro -e TZ=Asia/Shanghai \
-v /opt/docker_data/towelove/logs:/Towelove/logs -e TZ=Asia/Shanghai \
${full_name}"

#     local run_cmd="sudo docker run -d --restart=always --name ${service_name} --privileged=true \
# --net=venus --ip ${service_ip} -p ${service_port}:${service_port} \
# -v /etc/localtime:/etc/localtime:ro -e TZ=Asia/Shanghai \
# ${full_name}"

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
    local image_version=${1:-$DEFAULT_IMAGE_VERSION}
    log INFO "Running towelove-gateway with image version $image_version"
    run_container "towelove-gateway" "10.0.0.21" "8080" "$image_version"
}

run_auth() {
    local image_version=${1:-$DEFAULT_IMAGE_VERSION}
    log INFO "Running towelove-auth with image version $image_version"
    run_container "towelove-auth" "10.0.0.22" "9204" "$image_version"
}

run_loves() {
    local image_version=${1:-$DEFAULT_IMAGE_VERSION}
    log INFO "Running towelove-loves with image version $image_version"
    run_container "towelove-loves" "10.0.0.23" "9203" "$image_version"
}

run_msg() {
    local image_version=${1:-$DEFAULT_IMAGE_VERSION}
    log INFO "Running towelove-msg with image version $image_version"
    run_container "towelove-msg" "10.0.0.24" "9202" "$image_version"
}

run_server_center() {
    local image_version=${1:-$DEFAULT_IMAGE_VERSION}
    log INFO "Running towelove-server-center with image version $image_version"
    run_container "towelove-server-center" "10.0.0.25" "9201" "$image_version"
}

run_user() {
    local image_version=${1:-$DEFAULT_IMAGE_VERSION}
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

# 运行新容器，并检查执行结果
run_new_container() {
    local service_function=${service_to_function_map[$service_name]}
    
    if [ -z "$service_function" ]; then
        log ERROR "No deployment function found for service: ${service_name}"
        exit 1
    fi

    log INFO "Attempting to start new container for ${service_name} with image version ${image_version}."

    # 调用对应的函数来运行新容器
    $service_function "$image_version"
    local run_status=$?
    
    if [ $run_status -ne 0 ]; then
        log ERROR "Failed to start new container for ${service_name}."
        exit 1
    else
        log INFO "Successfully started new container for ${service_name}."
    fi
}

deploy_service() {
    local original_service_name=$1
    local image_version=${2:-$DEFAULT_IMAGE_VERSION}
    local service_name=$(echo "$original_service_name" | tr '[:upper:]' '[:lower:]')

    # 构建新镜像的完整名称
    local image_base="${REGISTRY_URL}towelove"
    local full_name="${image_base}/${service_name}:${image_version}"

    # 检查服务名称是否存在于映射中
    if [[ -z "${service_to_function_map[$service_name]}" ]]; then
        log ERROR "Service name '${original_service_name}' is not recognized. Please check the service name and try again."
        exit 1
    fi

    # 检查本地是否存在此版本的镜像
    if ! sudo docker image inspect "$full_name" > /dev/null 2>&1; then
        log INFO "Image $full_name does not exist locally. Pulling..."
        if ! sudo docker pull "$full_name"; then
            log ERROR "Failed to pull image $full_name."
            exit 1
        else
            log INFO "Successfully pulled image $full_name."
        fi
    else
        log INFO "Image $full_name exists locally."
    fi

    # 检查容器是否正在运行
    running_container_id=$(sudo docker ps -q -f name=^/${service_name}$)
    if [ -n "$running_container_id" ]; then
        log WARNING "Container ${service_name} is running. Stopping and removing..."
        if ! sudo docker rm -f ${service_name} > /dev/null 2>&1; then
            log ERROR "Failed to remove running container ${service_name}."
            exit 1
        else
            log INFO "Successfully removed running container ${service_name}."
        fi
    fi

    # # 运行新容器
    # ${service_to_function_map[$service_name]} "$image_version"
    # 在删除旧镜像之前，调用改进后的运行新容器函数
    run_new_container

    # 删除旧版本的镜像（如果存在）
    local old_image_id=$(sudo docker images --format '{{.Repository}}:{{.Tag}} {{.ID}}' | grep "^${image_base}/${service_name}:" | grep -v "$full_name" | awk '{print $2}')
    if [ ! -z "$old_image_id" ]; then
        log INFO "Found old image(s) to remove: $old_image_id"
        if sudo docker rmi $old_image_id > /dev/null 2>&1; then
            log INFO "Successfully removed old image(s): $old_image_id."
        else
            log WARNING "Failed to remove old image(s) $old_image_id. It may be used by other containers."
        fi
    else
        log INFO "No old images to remove."
    fi
}

main() {
    local original_service_name=$1
    local image_version=$2

    # 脚本开始执行的提示和分隔线
    log INFO "==================== Deploy Script Start ===================="
    log INFO "Script execution begins at $(date '+%Y-%m-%d %H:%M:%S')"

    deploy_service "${original_service_name}" "${image_version}"

    # 脚本结束执行的提示和分隔线
    log INFO "Script execution ends at $(date '+%Y-%m-%d %H:%M:%S')"
    log INFO "==================== Deploy  Script  End ===================="

}

main "$@"