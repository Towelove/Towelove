#!/bin/bash
exec java -Xmx2048m -Xms1024m -jar /Towelove/${SERVICE_NAME}/${SERVICE_NAME}.jar "$@"