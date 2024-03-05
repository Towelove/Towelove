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

#Towelove-Server-Center
#COPY config/bootstrap.yml /Towelove/config
COPY staging/Towelove-Server-Center.jar /Towelove/Towelove-Server-Center/

#EXPOSE 9201/tcp

ENTRYPOINT ["java"]
CMD ["-Xmx2048m", "-Xms1024m", "-jar", "/Towelove/Towelove-Server-Center/Towelove-Server-Center.jar"]