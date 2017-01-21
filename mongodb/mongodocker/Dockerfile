FROM mongo:3.4.1
LABEL author="Kevin Krummenauer"

ADD . /data
ADD https://raw.githubusercontent.com/crypt1d/redi.sh/master/redi.sh /data/redi.sh.binary

RUN echo "#!/bin/bash\n/data/redi.sh.binary -H redis -P 6379 "'"$@"' > /data/redi.sh

ENV MONGO_DNS_NAME="newspage-mongo"

# eine Secondary Mongo DB darf lesen
RUN echo "rs.slaveOk()" > /etc/mongorc.js \
 && chmod -R 755 /data/

ENTRYPOINT ["/data/entrypoint-initreplicas.sh"]
CMD ["mongod", "--replSet", "newspage-mongo-set"]
