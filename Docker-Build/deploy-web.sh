#!/bin/bash

# 日志函数，支持颜色输出
log() {
    case "$1" in
        INFO) color="32m";; # 绿色
        WARNING) color="33m";; # 黄色
        ERROR) color="31m";; # 红色
    esac
    echo -e "\033[${color}[${1}] $(date +'%Y-%m-%d %H:%M:%S') - ${2}\033[0m"
}

# 检查 /home/git/www 目录下是否存在 web.tar.gz
if [ -f "/home/git/www/web.tar.gz" ]; then
    log INFO "web.tar.gz found. Proceeding with the script."
else
    log ERROR "web.tar.gz does not exist in /home/git/www. Exiting."
    exit 1
fi

# 检查 /opt/www/web.towelove.cn 是否存在，存在则重命名
if [ -d "/opt/www/web.towelove.cn" ]; then
    timestamp=$(date +'%Y%m%d%H%M%S')
    mv /opt/www/web.towelove.cn /opt/www/web.towelove.cn-${timestamp}
    log INFO "Directory /opt/www/web.towelove.cn exists. Renamed to web.towelove.cn-${timestamp}."
else
    log WARNING "/opt/www/web.towelove.cn does not exist. No need to rename."
fi

# 复制 web.tar.gz 到 /opt/www/ 目录下
cp /home/git/www/web.tar.gz /opt/www/
if [ "$?" -eq 0 ]; then
    log INFO "web.tar.gz copied to /opt/www/ successfully."
else
    log ERROR "Failed to copy web.tar.gz to /opt/www/. Exiting."
    exit 1
fi

# 解压 web.tar.gz
cd /opt/www/
tar -zxvf web.tar.gz
if [ "$?" -eq 0 ]; then
    log INFO "web.tar.gz extracted successfully."
    # 解压成功后删除 web.tar.gz
    rm web.tar.gz
    if [ "$?" -eq 0 ]; then
        log INFO "web.tar.gz deleted successfully from /opt/www/."
    else
        log ERROR "Failed to delete web.tar.gz from /opt/www/. Please check permissions."
    fi
else
    log ERROR "Failed to extract web.tar.gz. Exiting."
    exit 1
fi

# 给解压出来的目录设置权限
chmod -R 755 /opt/www/web.towelove.cn
log INFO "Permissions set to 755 for /opt/www/web.towelove.cn."

# 删除原始的 web.tar.gz 文件
rm /home/git/www/web.tar.gz
log INFO "Original web.tar.gz deleted from /home/git/www/."

