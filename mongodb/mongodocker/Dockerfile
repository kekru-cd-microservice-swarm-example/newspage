FROM mongo:3.4.1
LABEL author="Kevin Krummenauer"
ADD . /data

# eine Secondary Mongo DB darf lesen
RUN echo "rs.slaveOk()" > /etc/mongorc.js \
 && chmod 755 /data/entrypoint-initreplicas.sh

ENTRYPOINT ["/data/entrypoint-initreplicas.sh"]
CMD ["mongod", "--replSet", "newspage-mongo-set"]
