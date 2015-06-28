#!/bin/sh
test -f ./setEnvironment_solarisv9.sh && . ./setEnvironment_solarisv9.sh
$JAVA_HOME/bin/java -d64 -Djava.library.path=$JAVA_LIBRARY_PATH -cp $CLASSPATH demo.pkcs.pkcs11.GetInfo $@

