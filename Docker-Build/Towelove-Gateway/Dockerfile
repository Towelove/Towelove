FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Gateway

RUN mkdir -p /Towelove/Towelove-Gateway
COPY staging/Towelove-Gateway.jar /Towelove/Towelove-Gateway/

EXPOSE 8080/tcp

CMD [ "java", "-jar", "/Towelove/Towelove-Gateway/Towelove-Gateway.jar" ]