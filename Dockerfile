FROM openjdk:11.0.9

MAINTAINER techgrow<656418510@qq.com>

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /usr/local/techgrow-openapi-java/

COPY ./target/techgrow-openapi-java.jar $WORKDIR

EXPOSE 8080

CMD java -Xms256m -Xmx512m -Dfile.encoding=UTF-8 -jar /usr/local/techgrow-openapi-java/techgrow-openapi-java.jar

