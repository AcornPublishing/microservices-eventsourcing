FROM adoptopenjdk/openjdk15:x86_64-tumbleweed-jre-15.0.2_7
VOLUME /tmp
COPY target/transfermoney-eventsourcing-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Duser.timezone='Asia/Seoul'", "-Djava.security.egd=file:/dev/./urandom", "-Xmx256m", "-jar","/app.jar"]

