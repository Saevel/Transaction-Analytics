FROM sequenceiq/spark:1.6.0

EXPOSE 7077

EXPOSE 8080

EXPOSE 8042

EXPOSE 4040

CMD ./usr/local/spark/sbin/start-master.sh

CMD ./usr/local/spark/sbin/start-slave.sh spark://localhost:7077