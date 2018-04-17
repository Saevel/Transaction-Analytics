FROM p7hb/docker-spark:2.1.0

MAINTAINER Saevel

COPY . /usr/src/Tests

WORKDIR /usr/src/Tests

ENTRYPOINT java -Dspark.home=$SPARK_HOME -Dtest.kind=$TEST_KIND -Dhttp.host=$HTTP_HOST -Dhttp.port=$HTTP_PORT -Xmx2g -jar Tests-1.0-standalone.jar