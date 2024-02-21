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

#Towelove-Server-Center

RUN mkdir -p /Towelove/Towelove-Server-Center
COPY staging/Towelove-Server-Center.jar /Towelove/Towelove-Server-Center/

EXPOSE 9201/tcp

CMD [ "java", "-Xmx2048m", "-Xms1024m", "-jar", "/Towelove/Towelove-Server-Center/Towelove-Server-Center.jar" ]