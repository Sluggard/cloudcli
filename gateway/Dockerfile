FROM openjdk:8
MAINTAINER "18306079883@163.com"

WORKDIR /opt
ADD target/gateway-0.0.1.jar /opt/gateway.jar

EXPOSE 8080

CMD java -jar -XX:+UseG1GC -XX:SurvivorRatio=4 -Xmx2g -Xms2g -Xmn2g -Duser.timezone=GMT+08 /opt/gateway.jar --spring.profiles.active=dev