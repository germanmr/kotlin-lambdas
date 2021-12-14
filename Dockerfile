FROM adoptopenjdk/openjdk11:alpine

ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080
ENV APP_JAR="launches-backend-kotlin-experimental-0.0.1-SNAPSHOT.jar"

VOLUME /tmp

ADD build/libs/$APP_JAR spacex-app.jar

EXPOSE $SERVER_PORT

ENTRYPOINT ["java", \
            "-jar", \
            "/spacex-app.jar", \
            "-Dserver.port=$SERVER_PORT", \
            "-Dspring.profiles.active=$SPRING_PROFILES_ACTIVE"]