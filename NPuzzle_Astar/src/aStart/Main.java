package aStart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase Main
 * Autor: Paulo Cesar Ruiz Lozano
 */
public class Main {
    
    /**
     * Funcion lectorArchivo
     * Lee un archivo y regresa una lista las lineas Leidas
     * @param nombreArchivo
     * @return
     */
    private static List<String> lectorArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        List<String> lineas = new ArrayList<>();

        try {
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                lineas.add(data);
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("[!] Archivo No encontrado");
            System.exit(1);
        }
        return lineas;
    }
    
    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        BusquedaInformada p ;
        boolean verbose = false;
        System.out.println("\tPrograma que explora el Npuzzle con A*\n");

        // Checa los Argumentos
        if (args.length < 0) {
            System.out.println("[!] No paso un archivo como argumento");
            System.out.println("[i] Uso \n$ java bin file");
            System.exit(1);
        }
        if (args.length == 2) {
            verbose = true;
        }
        List<String> movimientos; 
        List<String> lineasArchivo = lectorArchivo(args[0]);

        int tamMatriz = Integer.parseInt(lineasArchivo.get(0));
        int[][]tableroInicial = new int[tamMatriz][tamMatriz];
        int[][]tableroFinal= new int[tamMatriz][tamMatriz];
        

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
        p = new BusquedaInformada(tableroInicial,tableroFinal,20);

        if (verbose) {
            System.out.println("Tamaño del Tablero: " + tamMatriz);
            System.out.println("Tablero Inicial:");
            p.printTablero(tableroInicial);
            System.out.println("Tablero Final:");
            p.printTablero(tableroFinal);
        }
        
        // Lanza el algoritmo A*
        movimientos = p.aStar();

        // Imprime el Path
        for (String m : movimientos) {
            System.out.print(m + " ");
        }
        System.out.println();
    }
}
