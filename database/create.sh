#!/bin/bash
export PGPASSWORD='Beta150@'
BASEDIR=$(dirname $0)
DATABASE=boardgame_db
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/boardgameDB.sql"