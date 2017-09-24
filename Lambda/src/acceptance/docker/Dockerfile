FROM java:8

MAINTAINER Saevel

#EXPOSE $HTTP_PORT

COPY . /usr/src/Lambda-Acceptance-Tests

WORKDIR /usr/src/Lambda-Acceptance-Tests

ENTRYPOINT java -jar Lambda-Acceptance-Tests.jar