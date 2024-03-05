FROM eclipse-temurin:17-jdk-jammy

ARG SERVICE_NAME
ENV SERVICE_NAME=$SERVICE_NAME
ENV TZ=Asia/Shanghai \
    DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y tzdata \
    && ln -fs /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* 

WORKDIR /Towelove

COPY staging/${SERVICE_NAME}.jar /Towelove/${SERVICE_NAME}/${SERVICE_NAME}.jar
#COPY config/bootstrap.yml /Towelove/config/

# 为应用创建非root用户
# RUN groupadd -r towelove && useradd --no-log-init -r -g towelove towelove \
#     && chown -R towelove:towelove /Towelove
# USER towelove

COPY Docker-Build/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]