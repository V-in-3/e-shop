FROM adoptopenjdk/maven-openjdk11

WORKDIR /e-shop

COPY target/*.war e-shop.war

EXPOSE 8080

ENTRYPOINT exec java -server \
-noverify \
-XX:TieredStopAtLevel=1 \
-Dspring.jmx.enabled=false \
$JAVA_OPTS \
-jar e-shop.war