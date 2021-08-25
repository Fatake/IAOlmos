import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Puzzle {

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
            return null;
        }
        return lineas;
    }
    public static void main(String[] args) {
        Puzzle p = new Puzzle();
        System.out.println("Programa que explora el 8puzzle");
        if (args.length < 0) {
            System.out.println("[!] No paso un archivo como argumento");
            System.exit(1);
        }

        List<String> lineasArchivo = p.lectorArchivo(args[0]);
        int tamMatriz = Integer.parseInt(lineasArchivo.get(0));
        int tableroInicial[][] = new int[tamMatriz][tamMatriz];
        int tableroFinal[][] = new int[tamMatriz][tamMatriz];

        System.out.println("Tamaño del Tablero: "+tamMatriz);

        System.out.println("Tablero Inicial:");
        for (int i = 1; i < tamMatriz+1; i++) {
            // de la fila se sacan los elementos de la columna
            String[] aux = lineasArchivo.get(i).split(",");
            int j = 0;
            // se rellena cada columna
            for (String value : aux) {
                tableroInicial[i-1][j] = Integer.parseInt(value);
                System.out.print(tableroInicial[i-1][j]+ " ");
                j++;
            }
            System.out.println();
        }

        System.out.println("Tablero Final:");
        int k = 0;
        for (int i = lineasArchivo.size()-1; i > lineasArchivo.size()-tamMatriz-1; i--) {
            String[] aux = lineasArchivo.get(i).split(",");

            int j = tamMatriz-1;
            // se rellena cada columna
            for (String value : aux) {
                tableroFinal[k][j] = Integer.parseInt(value);
                System.out.print(tableroFinal[][j]+ " ");
                j--;
            }
            System.out.println();
            k++;
        }

    }
}
