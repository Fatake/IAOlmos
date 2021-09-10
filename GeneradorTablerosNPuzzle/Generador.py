import os
import sys
import numpy as np
import argparse
from shutil import rmtree


def _argumentos():
    ap = argparse.ArgumentParser(description="Generador de tableros NPuzzle")
    ap.add_argument("-s","--size", 
                    default=3,
                    type=int,
                    help="Tamaño del tablero")
    
    ap.add_argument("-n","--numero", required=True, type=int,
        help="Numero de elementos a generar almenos 1")

    ap.add_argument("-o","--outPut", type=str, default="Gen",
        help="Nombre Prefijo de Archivo")

    args = vars(ap.parse_args())
    return args

def generaArchivo(sizeTablero, numeroElementos, prefijo):
    rmtree("generador")

    if not os.path.exists("./generador/"):
        os.makedirs("generador")

    nombresArchivos = []
    for i in range(0,numeroElementos):
        nombresArchivos.append(prefijo+str(i)+".ini")

    for filename in nombresArchivos:
        with open("generador/"+filename,"x") as file:
            file.write(str(sizeTablero))
            largo = sizeTablero*sizeTablero
            tableroFinal = np.arange(largo)
            tableroInicial =  np.random.choice(range(largo), largo, replace=False)
            
            c = 0
            for j in range(0,sizeTablero*sizeTablero):
                if j % sizeTablero == 0:
                    file.write("\n")
                file.write(str(tableroInicial[j]))
                c += 1
                if c < sizeTablero:
                    file.write(",")
                else:
                    c = 0
            c = 0
            for j in range(0,sizeTablero*sizeTablero):
                if j % sizeTablero == 0:
                    file.write("\n")
                file.write(str(tableroFinal[j]))
                c += 1
                if c < sizeTablero:
                    file.write(",")
                else:
                    c = 0


if __name__ == "__main__":
    args = _argumentos()

    sizeTablero = args["size"]
    numeroElementos = args["numero"]
    prefijoArchivo = args["outPut"]

    if numeroElementos <= 0:
        print("[!] No se pueden generar {} Tableros".format(numeroElementos))
        sys.exit(1)
    if sizeTablero < 3:
        print("[!] No se pueden generar Tableros menores a size 3")
        sys.exit(1)


    print("[i] Se van a generar {} Elementos".format(numeroElementos))
    print("[i] El prefijo del archivo salida sera {}".format(prefijoArchivo))
    print("[i] El tamaño del tablero sera {}".format(sizeTablero))

        
    generaArchivo(sizeTablero,numeroElementos,prefijoArchivo)