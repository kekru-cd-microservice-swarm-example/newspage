#!/bin/bash

sleep 15

EIGENE_IP_ADRESSE=$(hostname --ip-address)

if [[ $(curl -s 'http://webdis:7379/EXISTS/newspage-mongo-primary' | jq -r '.EXISTS') == 0 ]]; then

   curl -s "http://webdis:7379/SET/newspage-mongo-primary/$EIGENE_IP_ADRESSE"
   mongo --eval "rs.initiate({'_id':'newspage-mongo-set','members':[{'_id':0,'host':'$EIGENE_IP_ADRESSE:27017'}]})"
   echo "ReplicaSet initialisiert auf $EIGENE_IP_ADRESSE"   

else
   
   MONGO_PRIMARY=$(curl -s 'http://webdis:7379/GET/newspage-mongo-primary' | jq -r '.GET')
   mongo "$MONGO_PRIMARY:27017" --eval "rs.add('$EIGENE_IP_ADRESSE:27017')"
   echo "Registriert (Eigene IP: $EIGENE_IP_ADRESSE) an Primary: $MONGO_PRIMARY"

fi
