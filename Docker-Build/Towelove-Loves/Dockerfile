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

#Towelove-Loves

RUN mkdir -p /Towelove/Towelove-Loves
COPY staging/Towelove-Loves.jar /Towelove/Towelove-Loves/

EXPOSE 9203/tcp

CMD [ "java", "-Xmx2048m", "-Xms1024m", "-jar", "/Towelove/Towelove-Loves/Towelove-Loves.jar" ]