#!/bin/bash

# In separatem Thread dem Replicaset beitreten
/data/join-replicaset.sh &

# Starte den urspruenglichen entrypoint mit den Parametern, die das aktuelle Script erhalten hat
/entrypoint.sh "$@"
