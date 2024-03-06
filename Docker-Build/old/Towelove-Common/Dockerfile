FROM eclipse-temurin:17-jdk-jammy

ENV TZ=Asia/Shanghai \
    DEBIAN_FRONTEND=noninteractive

# 合并RUN命令，优化层结构
RUN apt-get update && apt-get install -y tzdata \
    && ln -fs /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* 

WORKDIR /Towelove

#Towelove-Common
#COPY config/bootstrap.yml /Towelove/config
COPY staging/Towelove-Common-2.0.jar /Towelove/Towelove-Common/

ENTRYPOINT ["java"]
CMD ["-Xmx2048m", "-Xms1024m", "-jar", "/Towelove/Towelove-Common/Towelove-Common-2.0.jar"]