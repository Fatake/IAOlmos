import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Clase Puzzle
 */
public class Puzzle {

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
    private List<String > lectorArchivo(String nombreArchivo) {
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
            System.exit(1);
        }

        List<String> lineasArchivo = p.lectorArchivo(args[0]);
        int tamMatriz = Integer.parseInt(lineasArchivo.get(0));
        int tableroInicial[][] = new int[tamMatriz][tamMatriz];
        int tableroFinal[][] = new int[tamMatriz][tamMatriz];

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
    }
}
