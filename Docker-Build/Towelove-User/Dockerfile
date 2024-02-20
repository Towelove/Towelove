FROM eclipse-temurin:17-jdk-jammy

WORKDIR /Towelove

RUN mkdir -p /Towelove/config
COPY config/bootstrap.yml /Towelove/config

#Towelove-User

RUN mkdir -p /Towelove/Towelove-User
COPY staging/Towelove-User.jar /Towelove/Towelove-User/

EXPOSE 9205/tcp

CMD [ "java", "-jar", "/Towelove/Towelove-User/Towelove-User.jar" ]