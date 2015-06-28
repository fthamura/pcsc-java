#!/bin/bash
test -f ./setEnvironment_linux_x64.sh && . ./setEnvironment_linux_x64.sh
$JAVA_HOME/bin/java -d64 -Djava.library.path=$JAVA_LIBRARY_PATH -cp $CLASSPATH demo.pkcs.pkcs11.GetInfo $@

