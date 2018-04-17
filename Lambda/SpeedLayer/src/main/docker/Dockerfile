FROM p7hb/docker-spark:2.1.0

MAINTAINER Saevel

#EXPOSE $HTTP_PORT

COPY . /usr/src/SpeedLayer

WORKDIR /usr/src/SpeedLayer

ENTRYPOINT java -Xmx1g -jar SpeedLayer-1.0-standalone.jar