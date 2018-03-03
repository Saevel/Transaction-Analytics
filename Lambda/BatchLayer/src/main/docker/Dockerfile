#FROM java:8
FROM koenr/bb-docker-spark-java-8

MAINTAINER Saevel

#EXPOSE $HTTP_PORT

COPY . /usr/src/BatchLayer

WORKDIR /usr/src/BatchLayer

ENTRYPOINT crontab -e */5 * * * * java -jar -Xmx8g BatchLayer-1.0-standalone.jar