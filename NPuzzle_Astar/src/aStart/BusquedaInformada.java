package aStart;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase de Busqueda informada
 * Autor: Paulo Cesar Ruiz Lozano
 */
public class BusquedaInformada {
    public int tableroInicial[][]; // Estado Inicial
    public int tableroFinal[][]; // Estado Final
    public int gContador = 0;
    public int horizonte = 300;

    /**
     * Constructor, requiere tablero Inicial y Final
     * @param tableroInicial
     * @param tableroFinal
     */
    public BusquedaInformada(int tableroInicial[][],int tableroFinal[][], int horizonte ){
        this.tableroInicial = tableroInicial;
        this.tableroFinal = tableroFinal;
        this.horizonte = horizonte;
    }

    /**
     * Funcion A*
     * @return
     */
    public List<String> aStar( ){
        int contador = 0;
        // Lista de movimientos
        List<String> movimientos = new ArrayList<>();
        List<NodoNPuzzle> sucesores = new ArrayList<>();
        LinkedList<NodoNPuzzle> open = new LinkedList<NodoNPuzzle>();
        LinkedList<NodoNPuzzle> close = new LinkedList<NodoNPuzzle>();

        if (igual(tableroInicial, tableroFinal)) {
            System.out.println("[i] Tablero Inicial es igual al final");
            return null;
        }

        // Paso 1 Seleccion el nodo Inicial
        NodoNPuzzle nodoActual = new NodoNPuzzle(null, tableroInicial, "init", 0);
        nodoActual.setValorF(funcionEval(nodoActual));
        nodoActual.setPesoHs(funcionesHs(nodoActual));

        // Aregar NODO Inicial a la lista abierta con g = 0;
        open.add(nodoActual);

        // Inicial A* Mientras No se ecuentre el tablero final en Close
        while (!estaElementoLista(close, tableroFinal)) {

            if(open.isEmpty()){
                NodoNPuzzle aux = close.getLast();
                movimientos = encuentraPath(aux);
                break;
            }
            // Ordena Open del menor al mayor
            Collections.sort(open, ( o1,  o2)-> {
                    if (o1.getValorF() > o2.getValorF()) {
                        return -1;
                    } else if(o1.getValorF() < o2.getValorF()){
                        return 1;
                    } else if(o1.getValorF() == o2.getValorF()){
                        return 0;
                    }
                    return 0;
                } 
            );
            
            // Agrega elnodo con el mejor F(n)
            nodoActual = open.removeLast();
            
            if (igual(nodoActual.Tablero(), tableroFinal)) {
                System.out.println("[i] Camino Encontrado");
                close.add(nodoActual);
                movimientos = encuentraPath(nodoActual);
                break;
            }else if(nodoActual.gnMovimientos() == this.horizonte){
                // Horizonte Limitado
                System.out.println("[i] No encontre Solucion en el horizonte Limitado");
                NodoNPuzzle aux = close.getLast();
                movimientos = encuentraPath(aux);
                break;
            }

            sucesores = generaSucesores(nodoActual);
            // Para todo n' de n
            for (NodoNPuzzle sucesor : sucesores) {
                // Calcular para todo n' g, h, f, padre
                sucesor.setPesoHs(funcionesHs(sucesor));
                sucesor.setValorF(funcionEval(sucesor));
                sucesor.setPadre(nodoActual);

                // Si no está en open  y Close Agregar a Close
                if (!estaElementoLista(open, sucesor.Tablero()) &&
                    !estaElementoLista(close, sucesor.Tablero())) {
                    // open.removeIf(nodo -> (igual(nodo.Tablero(), sucesor.Tablero())));
                    open.add(sucesor);
                }

                // si ya existe un elemento en Open
                if(estaElementoLista(open, sucesor.Tablero())){
                    // y ademas es mejor
                    int in = retornaIndice(open,sucesor.Tablero());
                    if (open.get(in).getValorF() < sucesor.getValorF()) {
                        continue;
                    }else{
                        open.remove(in);
                        open.add(sucesor);
                    }
                }
                // Si ya existe un elemento en Close
                if(estaElementoLista(close, sucesor.Tablero())){
                    // y ademas es mejor
                    int in = retornaIndice(close,sucesor.Tablero());
                    // Si existe una instanvcia mejor en close
                    // Descartar y continuar
                    if (close.get(in).getValorF() < sucesor.getValorF()) {
                        continue;
                    }else{
                        close.remove(in);
                        open.add(sucesor);
                    }
                }
            }
            // Lo Agrega a la lista close
            close.add(nodoActual);
        }
        return movimientos;
    }

    /**
     * funcion
     * @param nodo
     * @return
     */
    public List<String> encuentraPath(NodoNPuzzle nodo){
        NodoNPuzzle nActual = nodo;
        List<String> movimientos = new ArrayList<>();
        movimientos.add("");
        while(nActual.getPadre() != null){
            movimientos.add(nActual.Movimiento());
            movimientos.add(",");
            nActual = nActual.getPadre();
        }
        if (",".equals(movimientos.get(movimientos.size()-1))) {
            movimientos.remove(movimientos.size()-1);
        }
        Collections.reverse(movimientos);
        return movimientos;
    }

    /**
     * retornaIndice Regresa el index de una lista dado un tablero
     * @param lista
     * @param tablero
     * @return
     */
    private int retornaIndice(LinkedList<NodoNPuzzle> lista, int[][] tablero){
        int i = 0;
        for (NodoNPuzzle is : lista) {
            if (igual(is.Tablero(), tablero)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Funcion que retorna si un tablero matriz
     * se encuentra en la lista
     * @param lista
     * @param tablero
     * @return
     */
    private boolean estaElementoLista(LinkedList<NodoNPuzzle> lista, int[][] tablero){
        return lista.stream().anyMatch(nodo -> (igual(nodo.Tablero(), tablero)));
    }

    /**
     * Funcion Igualdad entre dos matrices
     * @param m1
     * @param m2
     * @return
     */
    private boolean igual(int m1[][], int m2[][]){
        for (int i = 0; i < m2.length; i++) {
            for (int j = 0; j < m2.length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Funcion generaSucesores
     * Permite dado un nodo inicial,optener todos los Hijos
     * o movimientos posibles
     * retorna una lista de sucesores
     * @param nodo
     * @return
     */
    private List<NodoNPuzzle> generaSucesores(NodoNPuzzle padre){
        int[][] nodo = padre.Tablero();
        List<NodoNPuzzle> sucesores = new ArrayList<>();
        String[] opera;

        int[] ceroActual = localizaNumero(nodo,0);
        // ceroActual[0] = Y
        // ceroActual[1] = X
        if (ceroActual[0] == 0 && ceroActual[1] == 0) {
            // Equina Superior Izqierda
            String[] operadores = {"D","R"};   
            opera = operadores;     
        }else if (ceroActual[0] == 0 && ceroActual[1] == nodo.length-1) {
            // Equina Superior Derecha
            String[] operadores = {"D","L"};   
            opera = operadores; 
        }
        else if (ceroActual[0] == nodo.length-1 && ceroActual[1] == 0) {
            // Equina Inferior Izquierda
            String[] operadores = {"U","R"};   
            opera = operadores; 
        }else if (ceroActual[0] == nodo.length-1 && ceroActual[1] == nodo.length-1) {
            // Equina Inferior Derecha
            String[] operadores = {"U","L"};   
            opera = operadores; 
        }else if (ceroActual[0] == 0) {
            // Arista Superior
            String[] operadores = {"D","R","L"};   
            opera = operadores; 
        }else if (ceroActual[0] == nodo.length-1 ) {
            // Arista Inferior
            String[] operadores = {"U","R","L"};   
            opera = operadores; 
        }else if (ceroActual[1] == nodo.length-1) {
            // Arista Derecha
            String[] operadores = {"U","L","D"};   
            opera = operadores; 
        }else if (ceroActual[1] == 0) {
            // Arista Izquierda
            String[] operadores = {"U","R","D"};   
            opera = operadores; 
        }else{
            String[] operadores = {"U","R","D","L"};   
            opera = operadores; 
        }

        for (String movimiento : opera) {
            try {
                sucesores.add(aplicaMovimiento(padre,movimiento));
            } catch (ArrayIndexOutOfBoundsException  e) {
                System.out.println("[!] Error de Indice Saliendo");
                System.exit(1);
            }
            
        }
        return sucesores;
    }

    /**
     * Funcion aplicaMovimiento
     * Permite dado una matris y un movimiento 
     * "u","r","d","l"
     * up
     * rigth
     * down
     * lefth
     * Esta funcion no se pregunta si se puede realizar o no el moivmiento
     * 
     * @param nodo
     * @param movimiento
     * @return
     */
    private NodoNPuzzle aplicaMovimiento(NodoNPuzzle padre, String movimiento)throws ArrayIndexOutOfBoundsException {
        int[][] nodo = padre.Tablero();
        int[][] nuevo = new int[nodo.length][nodo.length];
        for (int i = 0; i < nodo.length; i++) { // X
            System.arraycopy(nodo[i], 0, nuevo[i], 0, nodo.length); // Y
        }

        int[] cero = localizaNumero(nodo,0);
        int x = cero[0];
        int y = cero[1];
        int aux;

        switch (movimiento) {
            case "U" -> {
                // Arriba
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x-1][y];
                nuevo[x-1][y] = aux;
            }

            case "D" -> {
                // Abajo
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x+1][y];
                nuevo[x+1][y] = aux;
            }
        
            case "L" -> {
                // Izquierda
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x][y-1];
                nuevo[x][y-1] = aux;
            }

            case "R" -> {
                // Derecha
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x][y+1];
                nuevo[x][y+1] = aux;
            }
        }
        return new NodoNPuzzle(padre,nuevo, movimiento,padre.gnMovimientos()+1);
    }

    /**
     * Funcion Localiza Un numero y retorna
     * un Arreglo con las coordenadas
     * a[0] = x
     * a[1] = y
     * @param nodo
     * @param numero
     * @return
     */
    private int[] localizaNumero(int nodo[][],int numero){
        int [] coordenada = new int[2];
        for (int i = 0; i < nodo.length; i++) { // X
            for (int j = 0; j < nodo.length; j++) { // Y
                if (nodo[i][j] == numero) {
                    coordenada[0] = i;
                    coordenada[1] = j;
                    return coordenada;
                }
            }
        }
        return null;
    }

    /**
     * Funcion Heurista 1
     * Funcion que calcula la distancia de manhattan
     * entre nodo actual y tablero final
     * @param nodo
     * @return
     */
    private int h1DistanciaManhattan(int nodo[][]){
        int h = 0;
        int size = tableroFinal.length*tableroFinal.length;
        for (int i = 0; i < size ;i++) {
            int[] n = localizaNumero(nodo,i);
            int[] tf = localizaNumero(tableroFinal,i);
            h += Math.abs(n[0]-tf[0]) + Math.abs(n[1]-tf[1]);
        }
        return h;
    }

    /**
     * Funcion Heurista 2
     * Piezas Faltantes
     * Calcula cuantas piezas son diferentes desde
     * el nodo actual hasta el tablero final
     * @param nodo
     * @return
     */
    private int h2PiezasFaltantesFinal(int nodo[][]){
        int h = 0;
        for(int i = 0; i < nodo.length; i++){
            for(int j = 0; j < nodo.length; j++){
                if(nodo[i][j] == tableroFinal[i][j]){
                    h++; 
                }
            }      
        }
        return h;
    }

    /**
     * Funcion Heurista 3
     * Compara el tablero del nodo actual 
     * retorna el numero de piezas diferentes con respecto 
     * al tablero Inicial
     * @param nodo
     * @return
     */
    private int h3PiezasFaltantesInicial(int nodo[][]){
        int g = 0;
        for(int i = 0; i < nodo.length; i++){
            for(int j = 0; j < nodo.length; j++){
                if(nodo[i][j] != tableroInicial[i][j]){
                    g++; 
                }
            }      
        }
        return g;
    }

    /**
     * Funcion Heurista 4
     * Compara el numero de piezas que estan en su
     * fila o columna pero no en su posicion
     * @param nodo
     * @return entero
     */
    private int h4PiezasColumnaFila(int nodo[][]){
        int f = 0; // Fichas iguales solo en filas
        int c = 0; // Fichas iguales solo en columnas
        
        for(int i = 0; i < nodo.length; i++){ 
            for(int j = 0; j < nodo.length; j++){ 
                for(int k = 0; k < tableroFinal.length; k++){
                    if(nodo[i][j] == tableroFinal[i][k]){
                        f++;
                    }
                    if(nodo[j][i] == tableroFinal[k][i]){
                        c++;
                    }
                }
            }
        } 
        return (f + c);
    }

    /**
     * Funcion que solo evalua las
     * Heuristicas
     * @param evalua
     * @return
     */
    private float funcionesHs(NodoNPuzzle evalua){
        int [][] nodo = evalua.Tablero();
        return  h1DistanciaManhattan(nodo) +
            h2PiezasFaltantesFinal(nodo) +
            h3PiezasFaltantesInicial(nodo) +
            h4PiezasColumnaFila(nodo);
    }

    /**
     * Funcionde evaluacion
     * Requiere de un Nodo NodoNPuzzle
     * @param evalua
     * @return
     */
    private float funcionEval(NodoNPuzzle evalua){
        int [][] nodo = evalua.Tablero();
        return  evalua.gnMovimientos() + 
            h1DistanciaManhattan(nodo) +
            h2PiezasFaltantesFinal(nodo) +
            h3PiezasFaltantesInicial(nodo) +
            h4PiezasColumnaFila(nodo);
    }

    /**
     * Funcion printTablero
     * Dado Un Int[][] Imprime como se ve
     * @param tablero
     */
    public void printTablero(int tablero[][]){
        for (int[] tablero1 : tablero) {
            for (int j = 0; j < tablero.length; j++) {
                System.out.print(tablero1[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
