package aStart;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Busqueda informada
 */
public class BusquedaInformada {
    public int tableroInicial[][]; // Estado Inicial
    public int tableroFinal[][]; // Estado Final
    public int estadoActual[][];
    public int gContador = 0;

    public BusquedaInformada(int tableroInicial[][],int tableroFinal[][] ){
        this.tableroInicial = tableroInicial;
        this.tableroFinal = tableroFinal;
    }

    /**
     * Funcion A*
     * @return
     */
    public List<String> aStar(){
        // Lista de movimientos
        List<String> movimientos = new ArrayList<>();
        List<Sucesor> sucesores = new ArrayList<>();

        if (igual(tableroInicial, tableroFinal)) {
            System.out.println("[i] Tablero Inicial es igual al final");
            return null;
        }

        LinkedList<Sucesor> open = new LinkedList<Sucesor>();
        LinkedList<Sucesor> close = new LinkedList<Sucesor>();
        LinkedList<Sucesor> neighbors = new LinkedList<Sucesor>();

        // Paso 1 Seleccion el nodo Inicial
        Sucesor nodoActual = new Sucesor(null, tableroInicial, "", 0);
        nodoActual.setValorF(funcionEval(nodoActual));
        nodoActual.setPesoHs(funcionesHs(nodoActual));

        // Aregar NODO Inicial a la lista cerrada con g = 0;
        close.add(nodoActual);
        System.out.println("Entrando a A*");
        // Inicia A*
        // Mientras el NodoFinal no se encuentre en la lista Cerrada
        while (!estaElementoLista(close,tableroFinal)) {
            // último elemento agregado a la lista cerrada
            nodoActual = close.getLast();

            // Genera los vecinos del nodo actual
            sucesores = generaSucesores(nodoActual);

            // Agregar los nodos vecinos del nodo actual en la 
            // lista vecinos excepto nodos que estén en la lista cerrada
            for (Sucesor nSucesor : sucesores) {
                if (!estaElementoLista(close, nSucesor.Tablero()) ) {
                    neighbors.add(nSucesor);
                }
            }

            // Para todo vecino en la lista calcular
            // g, h, f, padre
            for (Sucesor sucesor : neighbors) {
                sucesor.setPesoHs(funcionesHs(sucesor));
                sucesor.setValorF(funcionEval(sucesor));
                sucesor.setPadre(nodoActual);
            }

            for (Sucesor sucesor : neighbors) {
                // Si el nodo vecino no se encuentra en la
                //  lista abierta ni en la lista cerrada
                if (!estaElementoLista(open, sucesor.Tablero()) && 
                    !estaElementoLista(close, sucesor.Tablero())) {
                    open.add(sucesor);
                }
                /*else{
                    if (estaElementoLista(open, sucesor.Tablero())) {
                        Sucesor os = regresaElementoLista(open, sucesor.Tablero());
                        if (sucesor.gnMovimientos() < os.gnMovimientos()) {
                            sucesor = os;
                            // Actualizar atributos del nodo de la lista
                            // abierta con atributos del mismo nodo
                            // de la lista vecinos
                        }
                    }
                }*/
            }

            neighbors.clear();
            float aux = 0;
            int index = 0;
            // Elegir nodo con menor f de la lista abierta y
            // moverlo a la lista cerrada
            for (int i = 0; i < open.size(); i++) {
                if (open.get(i).getValorF() > aux) {
                    index = i;
                    aux = open.get(i).getValorF();
                }
            }

            close.add(open.remove(index));
        }
        for (Sucesor sucesor : close) {
            System.out.println(sucesor.Movimiento());
            printTablero(sucesor.Tablero());
        }
        System.out.println("Termine");
        /**
        Fin mientras (volver a mientras)
        
        Crear lista ruta corta
        Lista ruta corta = Invertir (Leer nodo final y trazar ruta
         por medio de los nodos padres
        (p) hasta llegar a nodo inicio)
        Retornar lista ruta corta
         */
        
        return movimientos;
    }

    private Sucesor regresaElementoLista(LinkedList<Sucesor> cola, int[][] tablero){
        for (Sucesor nodo : cola) {
            if (igual(nodo.Tablero(), tablero)) {
                return nodo;
            }
        }
        return null;
    }

    private boolean estaElementoLista(LinkedList<Sucesor> cola, int[][] tablero){
        if (cola.stream().anyMatch(nodo -> (igual(nodo.Tablero(), tablero)))) {
            return true;
        }
        return false;
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
     * Permite dado un nodo inicial,optener todos los suceroes
     * o movimientos posibles
     * retorna una lista de sucesores
     * @param nodo
     * @return
     */
    private List<Sucesor> generaSucesores(Sucesor padre){
        int[][] nodo = padre.Tablero();
        List<Sucesor> sucesores = new ArrayList<>();
        String[] opera;

        int[] ceroActual = localizaNumero(nodo,0);
        // ceroActual[0] = Y
        // ceroActual[1] = X
        if (ceroActual[0] == 0 && ceroActual[1] == 0) {
            // Equina Superior Izqierda
            String[] operadores = {"d","r"};   
            opera = operadores;     
        }else if (ceroActual[0] == 0 && ceroActual[1] == nodo.length-1) {
            // Equina Superior Derecha
            String[] operadores = {"d","l"};   
            opera = operadores; 
        }
        else if (ceroActual[0] == nodo.length-1 && ceroActual[1] == 0) {
            // Equina Inferior Izquierda
            String[] operadores = {"u","r"};   
            opera = operadores; 
        }else if (ceroActual[0] == nodo.length-1 && ceroActual[1] == nodo.length-1) {
            // Equina Inferior Derecha
            String[] operadores = {"u","l"};   
            opera = operadores; 
        }else if (ceroActual[0] == 0) {
            // Arista Superior
            String[] operadores = {"d","r","l"};   
            opera = operadores; 
        }else if (ceroActual[0] == nodo.length-1 ) {
            // Arista Inferior
            String[] operadores = {"u","r","l"};   
            opera = operadores; 
        }else if (ceroActual[1] == nodo.length-1) {
            // Arista Derecha
            String[] operadores = {"u","l","d"};   
            opera = operadores; 
        }else if (ceroActual[1] == 0) {
            // Arista Izquierda
            String[] operadores = {"u","r","d"};   
            opera = operadores; 
        }else{
            String[] operadores = {"u","r","d","l"};   
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
    private Sucesor aplicaMovimiento(Sucesor padre, String movimiento)throws ArrayIndexOutOfBoundsException {
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
            case "u" -> {
                // Arriba
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x-1][y];
                nuevo[x-1][y] = aux;
            }

            case "d" -> {
                // Abajo
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x+1][y];
                nuevo[x+1][y] = aux;
            }
        
            case "l" -> {
                // Izquierda
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x][y-1];
                nuevo[x][y-1] = aux;
            }

            case "r" -> {
                // Derecha
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x][y+1];
                nuevo[x][y+1] = aux;
            }
        }
        return new Sucesor(padre,nuevo, movimiento,padre.gnMovimientos()+1);
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
    private float funcionesHs(Sucesor evalua){
        int [][] nodo = evalua.Tablero();
        return  h1DistanciaManhattan(nodo) +
            h2PiezasFaltantesFinal(nodo) +
            h3PiezasFaltantesInicial(nodo) +
            h4PiezasColumnaFila(nodo);
    }

    /**
     * Funcionde evaluacion
     * Requiere de un Nodo Sucesor
     * @param evalua
     * @return
     */
    private float funcionEval(Sucesor evalua){
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
