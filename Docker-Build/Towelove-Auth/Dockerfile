FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Auth

RUN mkdir -p /Towelove/Towelove-Auth
COPY staging/Towelove-Auth.jar /Towelove/Towelove-Auth/

EXPOSE 9204/tcp

CMD [ "java", "-jar", "/Towelove/Towelove-Auth/Towelove-Auth.jar" ]