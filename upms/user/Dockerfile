FROM openjdk:8
MAINTAINER "18306079883@163.com"

WORKDIR /opt
ADD target/user-0.0.1.jar /opt/user.jar

EXPOSE 8080

CMD java -jar -XX:+UseG1GC -XX:SurvivorRatio=4 -Xmx1g -Xms1g -Xmn1g -Duser.timezone=GMT+08 /opt/user.jar --spring.profiles.active=dev