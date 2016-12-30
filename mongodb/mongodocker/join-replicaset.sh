#!/bin/bash

#Ein paar Sekunden schlafen, da parallel erstmal die Mongo DB gestartet wird
sleep 5

#Lese bisherige Anzahl der Mongo-Instanzen
#nslookup gibt die IP Adressen der Instanzen aus
#sed entfernt alle Zeilen, die nicht mit Address anfangen
#awk gibt die dritte Spalte aus, dort stehen die IP Adressen
#wc -l zaehlt die Zeilen
MONGO_INSTANZEN=$(nslookups tasks.$MONGO_DNS_NAME | sed '/^Address/!d' | awk '{print $3}')
ANZAHL_MONGO_INSTANZEN=$(echo $MONGO_INSTANZEN | wc -l)

if [[ ANZAHL_MONGO_INSTANZEN <= 1 ]] ; then
   EIGENE_IP_ADRESSE=$(hostname --ip-address)
   mongo --eval "rs.initiate({'_id':'newspage-mongo-set','members':[{'_id':0,'host':'$EIGENE_IP_ADRESSE:27017'}]})"
   echo "ReplicaSet initialisiert"
else

   ADRESSE_PRIMARY=""
   
   for i in $MONGO_INSTANZEN; do
      #Frage auf allen Mongo Instanzen nach der Primary
      ADRESSE_PRIMARY=$(mongo $i --quiet --eval "rs.isMaster()['primary']")
      
      if [[ ! -z $ADRESSE_PRIMARY ]] ; then
         break
      if
   done
   
   if [[ -z $ADRESSE_PRIMARY ]] ; then
       echo "Primary Adresse nicht gefunden"
       exit 1
   fi
    
   echo "Primary ist $ADRESSE_PRIMARY"
fi

# Starte den urspruenglichen entrypoint mit den Parametern, die das aktuelle Script erhalten hat
/entrypoint.sh "$@"
