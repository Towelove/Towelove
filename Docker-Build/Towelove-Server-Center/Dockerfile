FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Server-Center

RUN mkdir -p /Towelove/Towelove-Server-Center
COPY staging/Towelove-Server-Center.jar /Towelove/Towelove-Server-Center/

EXPOSE 9201/tcp

CMD [ "java", "-jar", "/Towelove/Towelove-Server-Center/Towelove-Server-Center.jar" ]