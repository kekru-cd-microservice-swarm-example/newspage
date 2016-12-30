FROM mongo:3.4.1
LABEL author="Kevin Krummenauer"

#RUN apt-get update && apt-get install -y dnsutils

ADD https://github.com/crypt1d/redi.sh/blob/master/redi.sh /data/redi.sh
ADD . /data

ENV MONGO_DNS_NAME="newspage-mongo"

# eine Secondary Mongo DB darf lesen
RUN echo "rs.slaveOk()" > /etc/mongorc.js \
 && chmod -R 755 /data/

ENTRYPOINT ["/data/entrypoint-initreplicas.sh"]
CMD ["mongod", "--replSet", "newspage-mongo-set"]
