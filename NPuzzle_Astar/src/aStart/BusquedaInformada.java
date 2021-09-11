package aStart;


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
     * Funcion hillCliming
     * @return
     */
    public List<String> aStar(){
        List<Sucesor> sucesores = new ArrayList<>();
        List<String> movimientos = new ArrayList<>();
        List<Sucesor> open = new ArrayList<>();
        List<Sucesor> close = new ArrayList<>();
        List<Sucesor> neighbour = new ArrayList<>();


        // Paso 1 Seleccion el nodo Inicial
        int[][] estadoActual = new int[tableroInicial.length][tableroInicial.length];
        for (int i = 0; i < tableroInicial.length; i++) {
            System.arraycopy(tableroInicial[i], 0, estadoActual[i], 0, tableroInicial.length); // Y
        }
        Sucesor nodoactual = new Sucesor(null, estadoActual, "", 0);
        nodoactual.setValorF(funcionEval(nodoactual));

        // Aregar NODO Inicial a la lista cerrada con g = 0;
        close.add(nodoactual);
        
        // Inicia A*
        // Mientras el NodoFinal no se encuentre en la lista Cerrada
        while (true) {
            break;
        }
        /**
        Mientras nodo final no se encuentre en la lista cerrada
            Nodo actual = último elemento agregado a la lista cerrada
            Agregar los nodos vecinos del nodo actual en la lista vecinos excepto nodos que
            estén en la lista cerrada
            Para todo nodo de la lista vecinos calcular los atributos:
                g = g del nodo actual + costo del nodo vecino al nodo actual
                h = costo (directo) aproximado desde el nodo vecino al nodo final
                f = g + h
                p = nodo actual
            fin para

            Para cada nodo de la lista vecinos
                Si el nodo vecino no se encuentra en la lista abierta ni en la lista cerrada
                    Copiar nodo vecino en la lista abierta
                Fin si
                else{
                    Si nodo vecino ya se encuentra en la lista abierta
                        Si g del nodo vecino < al g del mismo nodo de la lista abierta
                            Actualizar atributos del nodo de la lista abierta con atributos del mismo nodo
                            de la lista vecinos
                        Fin si
                    Fin si
                }
            Fin para

            Vaciar nodos de la lista vecinos
            Elegir nodo con menor f de la lista abierta y moverlo a la lista cerrada (si existe
            empate, el primero de la lista)
        Fin mientras (volver a mientras)
        
        Crear lista ruta corta
        Lista ruta corta = Invertir (Leer nodo final y trazar ruta por medio de los nodos padres
        (p) hasta llegar a nodo inicio)
        Retornar lista ruta corta
         */
        // sucesores = generaSucesores(nodoactual.Nodo());
        
        return movimientos;
    }

    /**
     * Funcion generaSucesores
     * Permite dado un nodo inicial,optener todos los suceroes
     * o movimientos posibles
     * retorna una lista de sucesores
     * @param nodo
     * @return
     */
    private List<Sucesor> generaSucesores(int nodo[][]){
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
                sucesores.add(aplicaMovimiento(nodo,movimiento));
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
    private Sucesor aplicaMovimiento(int nodo[][], String movimiento)throws ArrayIndexOutOfBoundsException {
        int[][] nuevo = new int[nodo.length][nodo.length];
        for (int i = 0; i < nodo.length; i++) { // X
            System.arraycopy(nodo[i], 0, nuevo[i], 0, nodo.length); // Y
        }

        int[] cero = localizaNumero(nodo,0);
        int x = cero[0];
        int y = cero[1];
        int aux = 0;

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
        return new Sucesor(nodo,nuevo, movimiento,gContador+1);
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
     * h3
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
     * h4
     * @param nodo
     * @return
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
     * Funcionde evaluacion
     * Requiere de un Nodo Sucesor
     * @param evalua
     * @return
     */
    private float funcionEval(Sucesor evalua){
        int [][] nodo = evalua.Nodo();
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
