# 基础镜像
FROM eclipse-temurin:17-jdk-jammy

# 构建参数
ARG SERVICE_NAME

ENV TZ=Asia/Shanghai \
    DEBIAN_FRONTEND=noninteractive

# 安装时区数据
RUN apt-get update && apt-get install -y tzdata \
    && ln -fs /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* 

WORKDIR /Towelove

# 创建服务目录并复制JAR文件
COPY staging/${SERVICE_NAME}.jar /Towelove/${SERVICE_NAME}/${SERVICE_NAME}.jar
#COPY config/bootstrap.yml /Towelove/config/

# 为应用创建非root用户
# RUN groupadd -r towelove && useradd --no-log-init -r -g towelove towelove \
#     && chown -R towelove:towelove /Towelove
# USER towelove

ENTRYPOINT ["java"]
CMD ["-Xmx2048m", "-Xms1024m", "-jar", "/Towelove/${SERVICE_NAME}/${SERVICE_NAME}.jar"]