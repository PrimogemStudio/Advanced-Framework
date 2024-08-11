#!/bin/bash
JAVA_HOME=~/zulu21.34.19-ca-jdk21.0.3-linux_x64/zulu21.34.19-ca-jdk21.0.3-linux_x64 LD_PRELOAD=/usr/lib/librenderdoc.so ./gradlew :runClient
