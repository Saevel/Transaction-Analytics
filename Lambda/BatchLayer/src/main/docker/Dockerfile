FROM p7hb/docker-spark:2.1.0

MAINTAINER Saevel

#EXPOSE $HTTP_PORT

COPY . /usr/src/BatchLayer

RUN apt-get update && apt-get -y install cron

# Add crontab file in the cron directory
ADD crontab /etc/cron.d/hello-cron

# Give execution rights on the cron job
RUN chmod 0644 /etc/cron.d/hello-cron

# Create the log file to be able to run tail
RUN touch /var/log/cron.log

WORKDIR /usr/src/BatchLayer

# Run the command on container startup
CMD cron && tail -f /var/log/cron.log

#ENTRYPOINT crontab -e */5 * * * * java -jar -Xmx8g BatchLayer-1.0-standalone.jar