FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Loves

RUN mkdir -p /Towelove/Towelove-Loves
COPY staging/Towelove-Loves.jar /Towelove/Towelove-Loves/

EXPOSE 9203/tcp

CMD [ "java", "-jar", "/Towelove/Towelove-Loves/Towelove-Loves.jar" ]