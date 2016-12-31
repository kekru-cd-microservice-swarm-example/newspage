#!/bin/bash

#Ein paar Sekunden schlafen, da parallel erstmal die Mongo DB gestartet wird
sleep 5

#Lese die IP Adressen der bisherigen Mongo Instanzen
MONGO_INSTANZEN=$(getent hosts tasks.$MONGO_DNS_NAME | awk '{ print $1 }')
ANZAHL_MONGO_INSTANZEN=$(echo $MONGO_INSTANZEN | wc -w)

echo "Mongo Instanzen ($ANZAHL_MONGO_INSTANZEN)"
echo $MONGO_INSTANZEN

if [[ $ANZAHL_MONGO_INSTANZEN -le 1 ]] ; then
   EIGENE_IP_ADRESSE=$(hostname --ip-address)
   mongo --eval "rs.initiate({'_id':'newspage-mongo-set','members':[{'_id':0,'host':'$EIGENE_IP_ADRESSE:27017'}]})"
   echo "ReplicaSet initialisiert auf $EIGENE_IP_ADRESSE"
else

   ADRESSE_PRIMARY=""
   
   for i in $MONGO_INSTANZEN; do
      #Frage auf allen Mongo Instanzen nach der Primary
      ADRESSE_PRIMARY=$(mongo $i --quiet --eval "rs.isMaster()['primary']")
      echo "$i nach Primary gefragt"

      if [[ ! -z $ADRESSE_PRIMARY ]] ; then
         break
      fi
   done
   
   if [[ -z $ADRESSE_PRIMARY ]] ; then
       echo "Primary Adresse nicht gefunden"
       exit 1
   fi
    
   echo "Primary ist $ADRESSE_PRIMARY"
fi

# Starte den urspruenglichen entrypoint mit den Parametern, die das aktuelle Script erhalten hat
/entrypoint.sh "$@"
