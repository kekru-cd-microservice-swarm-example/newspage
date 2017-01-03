FROM openjdk:8
RUN echo "Europe/Berlin" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

RUN mkdir --parents /usr/src/app
ADD . /usr/src/app
WORKDIR /usr/src/app

CMD java -jar Newspage.jar
EXPOSE 8081