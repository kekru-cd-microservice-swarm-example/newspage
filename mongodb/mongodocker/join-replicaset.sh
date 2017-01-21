#!/bin/bash

#15 bis 25 Sekunden schlafen
SLEEPTIME=$(( ($RANDOM %10) + 15))
echo "Sleep $SLEEPTIME seconds"
sleep $SLEEPTIME

EIGENE_IP_ADRESSE=$(hostname --ip-address)

MONGO_PRIMARY=$(/data/redi.sh -g newspage-mongo-primary) 2>/dev/null

if [[ -z $MONGO_PRIMARY ]]; then
   echo "$EIGENE_IP_ADRESSE" | /data/redi.sh -s newspage-mongo-primary
   mongo --eval "rs.initiate({'_id':'newspage-mongo-set','members':[{'_id':0,'host':'$EIGENE_IP_ADRESSE:27017'}]})"
   echo "ReplicaSet initialisiert auf $EIGENE_IP_ADRESSE"   

else
   mongo "$MONGO_PRIMARY:27017" --eval "rs.add('$EIGENE_IP_ADRESSE:27017')"
   echo "Registriert (Eigene IP: $EIGENE_IP_ADRESSE) an Primary: $MONGO_PRIMARY"

fi
