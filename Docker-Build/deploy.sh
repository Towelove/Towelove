#!/bin/bash

# 定义日志颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 日志函数
# log() {
#     local level=$1
#     local message=$2

#     case "$level" in
#         INFO)
#             echo -e "${GREEN}[INFO]${NC} $message"
#             ;;
#         WARNING)
#             echo -e "${YELLOW}[WARNING]${NC} $message"
#             ;;
#         ERROR)
#             echo -e "${RED}[ERROR]${NC} $message"
#             ;;
#         *)
#             echo -e "[UNKNOWN] $message"
#             ;;
#     esac
# }

log() {
    local level=$1
    local message=$2
    local color=$NC

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

    # 整个消息都会以指定的颜色显示
    echo -e "${color}[$level]${NC} ${color}$message${NC}"
}

# 构建 docker run 命令公共函数
# 示例调用
# run_towelove_container "towelove-gateway" "10.0.0.21" "8080" "0.2.0"
run_towelove_container() {
    if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ]; then
        log ERROR "Missing required arguments for run_towelove_container."
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
        if ! sudo docker pull "$full_name"; then
            log ERROR "Failed to pull image $full_name."
            exit 1
        fi
    else
        log INFO "Image $full_name exists locally."
    fi

    local run_cmd="sudo docker run -d --restart=always --name ${service_name} --privileged=true \
--net=venus --ip ${service_ip} -p ${service_port}:${service_port} \
-v /etc/localtime:/etc/localtime -e TZ=Asia/Shanghai \
${full_name}"

    log INFO "Executing command: $run_cmd"
    # 使用命令替换和管道捕获命令的输出和错误
    local cmd_output
    cmd_output=$(eval "$run_cmd" 2>&1)
    local cmd_exit_status=$?
    
    if [ $cmd_exit_status -ne 0 ]; then
        log ERROR "Failed to execute: $run_cmd\nError Output: $cmd_output"
        exit 1
    else
        log INFO "Successfully executed: $run_cmd\nOutput: $cmd_output"
    fi
}

# 示例调用
# run_gateway "0.2.0"
# 需要 image_version 参数
run_gateway() {
    local image_version=${1:-"0.1.5-test3"}
    log INFO "Running towelove-gateway with image version $image_version"
    run_towelove_container "towelove-gateway" "10.0.0.21" "8080" "$image_version"
}

run_auth() {
    local image_version=${1:-"0.1.5-test3"}
    log INFO "Running towelove-auth with image version $image_version"
    run_towelove_container "towelove-auth" "10.0.0.22" "9204" "$image_version"
}

run_loves() {
    local image_version=${1:-"0.1.5-test3"}
    log INFO "Running towelove-loves with image version $image_version"
    run_towelove_container "towelove-loves" "10.0.0.23" "9203" "$image_version"
}

run_msg() {
    local image_version=${1:-"0.1.5-test3"}
    log INFO "Running towelove-msg with image version $image_version"
    run_towelove_container "towelove-msg" "10.0.0.24" "9202" "$image_version"
}

run_server_center() {
    local image_version=${1:-"0.1.5-test3"}
    log INFO "Running towelove-server-center with image version $image_version"
    run_towelove_container "towelove-server-center" "10.0.0.25" "9201" "$image_version"
}

run_user() {
    local image_version=${1:-"0.1.5-test3"}
    log INFO "Running towelove-user with image version $image_version"
    run_towelove_container "towelove-user" "10.0.0.26" "9205" "$image_version"
}


# operate_towelove_container() {
#     local operation=$1
#     local service_name=$2
#     local container_name="${service_name}"

#     # 根据操作执行相应的 Docker 命令并检查执行结果
#     case $operation in
#         "start"|"stop"|"restart")
#             if ! sudo docker $operation $container_name > /dev/null 2>&1; then
#                 echo "Warning: Failed to $operation container $container_name."
#             fi
#             ;;
#         "remove")
#             if ! sudo docker rm -f $container_name > /dev/null 2>&1; then
#                 echo "Error: Failed to remove container $container_name."
#                 exit 1
#             fi
#             ;;
#         *)
#             echo "Unknown operation: $operation"
#             return 1
#     esac

#     echo "Operation $operation on $container_name completed."
# }

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
deploy_service() {
    local original_service_name=$1
    local image_version=$2
    local service_name=$(echo "$original_service_name" | tr '[:upper:]' '[:lower:]')

    if sudo docker ps -q -f name=^/${service_name}$ > /dev/null; then
        log WARNING "Container ${service_name} is running. Stopping and removing..."
        if ! sudo docker rm -f ${service_name} > /dev/null 2>&1; then
            log ERROR "Failed to remove container ${service_name}."
            exit 1
        fi
    else
        log INFO "Container ${service_name} is not running."
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
    fi
}

main() {
    local original_service_name=$1
    local image_version=$2

    deploy_service "${original_service_name}" "${image_version}"
}

main "$@"