FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Common

RUN mkdir -p /Towelove/Towelove-Common
COPY staging/Towelove-Common-2.0.jar /Towelove/Towelove-Common/

CMD [ "java", "-jar", "/Towelove/Towelove-Common/Towelove-Common-2.0.jar" ]
