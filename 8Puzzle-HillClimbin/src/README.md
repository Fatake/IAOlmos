# 8 Puzzle con Algoritmo Hill Climbin

En esta carpeta se explora una solucion con Busqueda Informada (Heuristica) del problema 8 puzzle con el algoritmo de Hill Climbin

---

### Forma de Ejecutar

Este programa fue compilado en java `jdk-16.0.2`

#### Compilar y Ejecutar de forma Manual

```bash
$ javac hillclimbing/Puzzle.java
$ java hillclimbing.Puzzle entrada.ini
```

#### Compila y Ejecuta con bash Script

```bash
$ chmod 754 ejecuta.sh
$ ./ejecuta.sh
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
