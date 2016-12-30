#!/bin/bash

/data/join-replicaset.sh &

# Starte den urspruenglichen entrypoint mit den Parametern, die das aktuelle Script erhalten hat
/entrypoint.sh "$@"
