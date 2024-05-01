#!/bin/bash

# 定义日志文件路径
log_file="/opt/ops/logs/deploy-web.log"

tmp_dir="/home/ops"
tmp_file="web.tar.gz"
parent_dir="/opt/www"
web_dir="web.towelove.cn"

full_tmp_path="$tmp_dir/$tmp_file"
full_path="$parent_dir/$web_dir"

# 日志函数，支持颜色输出和输出到文件
log() {
    local level=$1
    local message=$2
    local timestamp=$(date +'%Y-%m-%d %H:%M:%S')

    # 颜色输出到控制台
    case "$level" in
        INFO) color="\033[32m";; # 绿色
        WARNING) color="\033[33m";; # 黄色
        ERROR) color="\033[31m";; # 红色
    esac

    # 输出到控制台
    echo -e "${color}[${timestamp}] [${level}] - ${message}\033[0m"

    # 输出到日志文件
    echo "[${timestamp}] [${level}] - ${message}" >> "$log_file"
}

# 检查目录是否存在，不存在则创建
ensure_directory() {
    local directory=$1

    if [ ! -d "$directory" ]; then
        mkdir -p "$directory"
        log INFO "Directory $directory created."
    fi
}

# 复制文件，若成功则记录日志，否则输出错误日志并退出
copy_file() {
    local source=$1
    local destination=$2

    cp "$source" "$destination"
    if [ "$?" -eq 0 ]; then
        log INFO "File $source copied to $destination successfully."
    else
        log ERROR "Failed to copy $source to $destination. Exiting."
        exit 1
    fi
}

# 解压文件，若成功则记录日志，否则输出错误日志并退出
extract_archive() {
    local archive=$1
    local destination=$2

    tar -zxvf "$archive" -C "$destination"
    if [ "$?" -eq 0 ]; then
        log INFO "Archive $archive extracted successfully to $destination."
    else
        log ERROR "Failed to extract $archive. Exiting."
        exit 1
    fi
}

# 设置目录权限
set_permissions() {
    local directory=$1

    chmod -R 755 "$directory"
    log INFO "Permissions set to 755 for $directory."
}

# 删除文件
delete_file() {
    local file=$1

    rm "$file"
    if [ "$?" -eq 0 ]; then
        log INFO "File $file deleted successfully."
    else
        log ERROR "Failed to delete $file. Please check permissions."
    fi
}

# 主逻辑
main() {
    # 脚本开始执行的提示和分隔线
    log INFO "==================== Deploy Web Script Start ===================="
    log INFO "Script execution begins at $(date '+%Y-%m-%d %H:%M:%S')"

    ensure_directory "$parent_dir"
    ensure_directory "$tmp_dir"

    if [ ! -f "$full_tmp_path" ]; then
        log ERROR "$tmp_file does not exist in $tmp_dir. Exiting."
        exit 1
    fi

    if [ -d "$full_path" ]; then
        timestamp=$(date +'%Y%m%d%H%M%S')
        mv "$full_path" "$full_path-$timestamp"
        log INFO "Directory $full_path exists. Renamed to $web_dir-$timestamp."
    else
        mkdir -p "$full_path"
        log INFO "Directory $full_path does not exist. Created."
    fi

    copy_file "$full_tmp_path" "$parent_dir"
    extract_archive "$full_tmp_path" "$parent_dir"
    set_permissions "$full_path"
    delete_file "$full_tmp_path"

    # 脚本结束执行的提示和分隔线
    log INFO "Script execution ends at $(date '+%Y-%m-%d %H:%M:%S')"
    log INFO "==================== Deploy Web  Script  End ===================="
}

main