#!/bin/bash

mkdir -p ./logs
java -classpath "./lib/*" \
 -Xloggc:"./logs/gc.log" \
 -XX:+PrintGCTimeStamps \
 -XX:+PrintGCDetails \
 -XX:+UseParallelGC \
 -XX:-DisableExplicitGC \
 -XX:+UseGCLogFileRotation \
 -XX:NumberOfGCLogFiles=10 \
 -XX:GCLogFileSize=1M \
 -XX:+HeapDumpOnOutOfMemoryError \
 -XX:HeapDumpPath="./logs/battle-x.hprof" \
 -XX:+UnlockDiagnosticVMOptions \
  org.dmonix.battlex.Battlex

