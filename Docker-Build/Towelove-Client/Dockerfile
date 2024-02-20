FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Client

RUN mkdir -p /Towelove/Towelove-Client
COPY staging/Towelove-Client-2.0.jar /Towelove/Towelove-Client/

CMD [ "java", "-jar", "/Towelove/Towelove-Client/Towelove-Client-2.0.jar" ]
