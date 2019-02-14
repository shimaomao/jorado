#!/bin/sh
APP_HOME="/data/services/hotword-api"
CLASSPATH=$APP_HOME/bin
MAIN_FUNCTION="com.jorado.search.hotwordapi.Startup"
echo $1
if [ -n "$1" ]
then
        MAIN_FUNCTION=$1
fi

CLASSPATH="$CLASSPATH":"$APP_HOME"/classes/
for i in "$APP_HOME"/bin/*.jar
do
 CLASSPATH="$CLASSPATH":"$i"
done
export CLASSPATH=.:$CLASSPATH
echo ${CLASSPATH}
echo $MAIN_FUNCTION
java -Xms1024m -Xmx4096m `echo $MAIN_FUNCTION`
exit
