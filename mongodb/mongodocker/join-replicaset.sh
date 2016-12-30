#!/bin/bash



PRI=$(mongo --quiet --eval "rs.isMaster()['primary']")


# Starte den urspruenglichen entrypoint mit den Parametern, die das aktuelle Script erhalten hat
/entrypoint.sh "$@"
