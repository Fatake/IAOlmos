import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase Puzzle
 */
public class Puzzle {
    public static int tableroInicial[][]; // Estado Inicial
    public static int tableroFinal[][]; // Estado Final
    public static int estadoActual[][];

    /**
     * Funcion hillCliming
     * @return
     */
    private List<String> hillCliming(){
        int pesoEstadoActual = 100;
        int pesoEstadoSucesor = 0;

        // Paso 1 Seleccion el nodo n0
        int[][] estadoActual = new int[tableroInicial.length][tableroInicial.length];
        for (int i = 0; i < tableroInicial.length; i++) { // X
            for (int j = 0; j < tableroInicial.length; j++) { // Y
                estadoActual[i][j] = tableroInicial[i][j];
            }
        }

        pesoEstadoActual = funcionEval(estadoActual);

        // Busca la colina
        while (true) {
            // Paso 2 Generar los Sucesores
            List<Sucesor> sucesores = new ArrayList<Sucesor>();
            sucesores = generaSucesores(estadoActual);
            System.exit(2);
            // optener f(ni) y comparar cual de estos es el 
            // peso mayor
            int i = 0; // contador de sucesores
            int selec = 0; // index del sucesor con mayor peso
            for (Sucesor is : sucesores) {
                int pesoAux = funcionEval(is.Nodo());
                if (pesoAux > pesoEstadoSucesor) {
                    pesoEstadoSucesor = pesoAux;
                    selec = i;
                }
                i++;
            }

            // Paso 3 pesoEstadoActual < pesoEstadoSucesor
            if (pesoEstadoActual < pesoEstadoSucesor) {
                estadoActual = sucesores.get(selec).Nodo();
                System.out.println(sucesores.get(selec).Movimiento());
            }else{
                break;
            }
        }
        return null;
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
        List<Sucesor> sucesores = new ArrayList<Sucesor>();
        String[] opera;

        int[] ceroActual = localizaCero(nodo);
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
            for (int j = 0; j < nodo.length; j++) { // Y
                nuevo[i][j] = nodo[i][j];
            }
        }

        int[] cero = localizaCero(nodo);
        int x = cero[0];
        int y = cero[1];
        int aux = 0;

        switch (movimiento) {
            case "u":// Arriba
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x-1][y];
                nuevo[x-1][y] = aux;
            break;

            case "d":// Abajo
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x+1][y];
                nuevo[x+1][y] = aux;
            break;
        
            case "l":// Izquierda
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x][y-1];
                nuevo[x][y-1] = aux;
            break;

            case "r":// Derecha
                aux = nuevo[x][y];
                nuevo[x][y] = nuevo[x][y+1];
                nuevo[x][y+1] = aux;
            break;
        }
        return new Sucesor(nodo,nuevo, movimiento);
    }

    /**
     * Funcion localizaCero
     * Permite dado una matriz loicalizar la ficha 0
     * @param nodo
     * @return
     */
    private int[] localizaCero(int nodo[][]){
        int [] coordenada = new int[2];
        for (int i = 0; i < nodo.length; i++) { // X
            for (int j = 0; j < nodo.length; j++) { // Y
                if (nodo[i][j] == 0) {
                    coordenada[0] = i;
                    coordenada[1] = j;
                    return coordenada;
                }
            }
        }
        return null;
    }

    /**
     * Funcion g
     * Dado Un Int[][] regresa el numero de piezas fuera de su lugar
     * @param tablero
     */
    private int gPiezasFueraLugar(int nodo[][]){
        int g = 0;
        for(int i = 0; i<nodo.length; i++){
            for(int j =0; j<nodo.length; j++){
                if(nodo[i][j] != tableroFinal[i][j]){
                    g++; 
                }
            }      
        }
        return g;
    }

    private int H1PiezasColumnaFila(int nodo[][]){
        int h = 0;

        
        return h;
    }

    private int H2PiezasColumnaFila(int nodo[][]){
        int h = 0;

        
        return h;
    }

    private int H3PiezasColumnaFila(int nodo[][]){
        int h = 0;

        
        return h;
    }

    private int H4PiezasColumnaFila(int nodo[][]){
        int h = 0;

        
        return h;
    }

    private int funcionEval(int nodo[][]){
        
        return 0;
    }

    /**
     * Funcion printTablero
     * Dado Un Int[][] Imprime como se ve
     * @param tablero
     */
    private void printTablero(int tablero[][]){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Funcion lectorArchivo
     * Lee un archivo y regresa una lista las lINEAS LEIDAS
     * @param nombreArchivo
     * @return
     */
    private List<String> lectorArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        List<String> lineas = new ArrayList<String>();

        try {
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                lineas.add(data);
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("[!] Archivo No encontrado");
            e.printStackTrace();
            System.exit(1);
        }
        return lineas;
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        Puzzle p = new Puzzle();
        System.out.println("Programa que explora el 8puzzle con Hill Climbing");

        // Checa los Argumentos
        if (args.length < 0) {
            System.out.println("[!] No paso un archivo como argumento");
            System.out.println("[i] Uso \n$ java bin file");
            System.exit(1);
        }

        List<String> lineasArchivo = p.lectorArchivo(args[0]);
        int tamMatriz = Integer.parseInt(lineasArchivo.get(0));
        tableroInicial = new int[tamMatriz][tamMatriz];
        tableroFinal= new int[tamMatriz][tamMatriz];

        for (int i = 1; i < tamMatriz+1; i++) {
            // de la fila se sacan los elementos de la columna
            String[] aux = lineasArchivo.get(i).split(",");
            int j = 0;
            // se rellena cada columna
            for (String value : aux) {
                tableroInicial[i-1][j] = Integer.parseInt(value);
                j++;
            }
        }

        int k = tamMatriz-1;
        for (int i = lineasArchivo.size()-1; i > lineasArchivo.size()-tamMatriz-1; i--) {
            String[] aux = lineasArchivo.get(i).split(",");

            int j = 0;
            for (String value : aux) {
                tableroFinal[k][j] = Integer.parseInt(value);
                j++;
            }
            k--;
        }

        System.out.println("Tamaño del Tablero: " + tamMatriz);
        System.out.println("Tablero Inicial:");
        p.printTablero(tableroInicial);
        System.out.println("Tablero Final:");
        p.printTablero(tableroFinal);

        // Lanza el algoritmo
        p.hillCliming();
    }
}
