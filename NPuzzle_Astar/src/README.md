# N Puzzle con Algoritmo A*

En esta carpeta se explora una solucion con Busqueda Informada (Heuristica) del problema N puzzle con el algoritmo de A*

---

### Forma de Ejecutar

Este programa fue compilado en java `jdk-16.0.2`

#### Compilar y ejecutar Manual

```bash
$ javac aStart/Main.java
$ java aStart.Main entrada.ini # Ejecuta con un horizonte limitado de 6000
$ java aStart.Main entrada.ini 900 # Ejecuta con un horizonte limitado de 900
$ java aStart.Main entrada.ini 600 -v # Ejecuta modo Verbose
```

#### Compilar y Ejecutar en Script

```bash
$ chmod 754 ejecuta.sh # Hacer solo una vez
$ ./ejecuta.sh nombreArchivo.ini 
$ ./ejecuta.sh nombreArchivo.ini 6000 #Horizonte Limitado
$ ./ejecuta.sh nombreArchivo.ini 600 -v #Para modo verbose
```

---

### Formato del Archivo Input

Este archivo sirve como descripto de el tamaño de puzzle (tamaño del tablero o matriz) y tambien da el estado Inicial y el estado final

```
int tamMatriz # Entero que indica el alto y ancho de una matriz
int,int,...,int # rellenado del Estado Inicial de tamMatriz * tamMatriz
.
.
int,int,....int
int,int,...,int # rellenado del Estado Final de tamMatriz * tamMatriz
.
.
int,int,....int

```

Ejemplo

```
3
6,5,7
1,3,0
2,4,8
1,2,3
4,5,6
7,8,0

```


---

### Extras

Se puede encontrar un generador de **entradas.ini** en la carpeta principal de este repositorio en el paquete [GeneradorTablerosNPuzzle](https://github.com/Fatake/IAOlmos/tree/master/GeneradorTablerosNPuzzle)
