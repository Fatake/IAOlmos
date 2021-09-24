#!/bin/bash
if [[ -z $1 ]]; then
    echo "[!] Debe lanzar el script con los parametros"
    echo "[i] ./ejecuta.sh fileName [-v verboso]"
    exit
fi
rm aStart/*.class
javac aStart/Main.java
java aStart.Main $1 $2 