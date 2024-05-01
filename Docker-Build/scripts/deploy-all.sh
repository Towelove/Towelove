#!/bin/bash

# 版本号
version=0.1.0

# 定义要检查的文件
file_to_check="/opt/ops/deploy.sh"

# 检查文件是否存在
if [ ! -e "$file_to_check" ]; then
    echo "Error: File $file_to_check does not exist."
    exit 1  # 非零退出代码表示异常
fi

# 如果文件存在，继续后续操作
echo "File $file_to_check exists. Proceeding with script..."

run_all(){
    local git_hash=$1
    local full_version="${version}-${git_hash}"
    sudo /opt/ops/deploy.sh Towelove-Gateway "$full_version"
    sudo /opt/ops/deploy.sh Towelove-Auth "$full_version"
    sudo /opt/ops/deploy.sh Towelove-User "$full_version"
    sudo /opt/ops/deploy.sh Towelove-Server-Center "$full_version"
    sudo /opt/ops/deploy.sh Towelove-Loves "$full_version"
    sudo /opt/ops/deploy.sh Towelove-Msg "$full_version"
}

main(){
    run_all "$1"
}

main "$@"