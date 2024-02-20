FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-Msg

RUN mkdir -p /Towelove/Towelove-Msg
COPY staging/Towelove-Msg.jar /Towelove/Towelove-Msg/

EXPOSE 9202/tcp

CMD [ "java", "-jar", "/Towelove/Towelove-Msg/Towelove-Msg.jar" ]