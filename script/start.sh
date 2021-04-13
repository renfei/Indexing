#!/usr/bin/env bash
echo "###################################"
echo "#      --=== Indexing ===--       #"
echo "#  author: renfei (i@renfei.net)  #"
echo "###################################"
DIR_NAME=$0
if [ "${DIR_NAME:0:1}" = "/" ];then
    # shellcheck disable=SC2006
    DIR_PATH=`dirname "$DIR_NAME"`
else
    # shellcheck disable=SC2006
    # shellcheck disable=SC2123
    DIR_PATH="`pwd`"/"`dirname "$DIR_NAME"`"
fi
nohup "$DIR_PATH"/bin/java -jar "$DIR_PATH"/Indexing.jar > "$DIR_PATH"/Indexing.log 2>&1 & echo $! > "$DIR_PATH"/Indexing.pid