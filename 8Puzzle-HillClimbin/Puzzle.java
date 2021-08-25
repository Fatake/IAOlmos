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

        System.out.println("Tamaño del Tablero: "+tamMatriz);

        System.out.println("Tablero Inicial:");
        for (int i = 1; i < tamMatriz+1; i++) {
            System.out.println(lineasArchivo.get(i));
        }

        System.out.println("Tablero Final:");
        for (int i = lineasArchivo.size()-1; i > lineasArchivo.size()-tamMatriz-1; i--) {
            System.out.println(lineasArchivo.get(i));
        }

    }
}
