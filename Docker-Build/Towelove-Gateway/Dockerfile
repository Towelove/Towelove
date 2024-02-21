FROM eclipse-temurin:17-jdk-jammy

ENV TZ=Asia/Shanghai \
    DEBIAN_FRONTEND=noninteractive

RUN apt update \
    && apt install -y tzdata \
    && ln -fs /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo ${TZ} > /etc/timezone \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Gateway

RUN mkdir -p /Towelove/Towelove-Gateway
COPY staging/Towelove-Gateway.jar /Towelove/Towelove-Gateway/

EXPOSE 8080/tcp

CMD [ "java", "-Xmx2048m", "-Xms1024m", "-jar", "/Towelove/Towelove-Gateway/Towelove-Gateway.jar" ]