FROM java:8
MAINTAINER labsoft-2018

ADD target/deliveries-0.0.1-SNAPSHOT-standalone.jar /srv/deliveries.jar

EXPOSE 8029

CMD ["java", "-jar", "/srv/deliveries.jar"]
