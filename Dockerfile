FROM openjdk:8
RUN echo "Europe/Berlin" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

RUN mkdir --parents /usr/src/app
ADD target/Newspage-0.0.1-SNAPSHOT.jar /usr/src/app/Newspage.jar
WORKDIR /usr/src/app

HEALTHCHECK --interval=40s --timeout=5s --retries=3 CMD curl -f http://localhost:8081/ || exit 1

CMD java -jar Newspage.jar
EXPOSE 8081
