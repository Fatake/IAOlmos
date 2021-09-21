#!/bin/bash
if [[ -z $1 && -z $2 ]]; then
    echo "[!] Debe lanzar el script con los parametros"
    echo "[i] ./ejecuta.sh fileName #Iteraciones"
    exit
fi
rm aStart/*.class
javac aStart/Main.java
java aStart.Main $1 $2 $3