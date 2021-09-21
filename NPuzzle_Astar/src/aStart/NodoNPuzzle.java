package aStart;

/**
 * Clase NodoNpuzzles
 * Contiene un tablero NxN
 * Nodo Padre
 * Movimiento realizado desde el Nodo Padre
 * Peso G
 * Peso H
 * Peso F = g + h
 * Autor: Paulo Cesar Ruiz Lozano
 */
public class NodoNPuzzle {
    private NodoNPuzzle nodoPadre;
    private int tablero[][];
    private String movimiento;
    private int gnMovimientos;
    private float pesohs = 0;
    private float valorF = 0;

    /**
     * Clase NodoNpuzzles
     * Contiene un tablero NxN
     * Nodo Padre
     * Movimiento realizado desde el Nodo Padre
     * Peso G
     * Peso H    
     * Peso F = g + h
     * @param nodo
     * @param movimiento
     */
    public NodoNPuzzle(int nodo[][], String movimiento ){
        this.nodoPadre = null;
        this.tablero = nodo;
        this.movimiento = movimiento;
    }

    /**
     * Clase NodoNpuzzles
     * Contiene un tablero NxN
     * Nodo Padre
     * Movimiento realizado desde el Nodo Padre
     * Peso G
     * Peso H    
     * Peso F = g + h
     * @param nodoPadre
     * @param tablero
     * @param movimiento
     * @param gnMovimientos
     */
    public NodoNPuzzle(NodoNPuzzle nodoPadre,int tablero[][], String movimiento, int gnMovimientos ){
        this.nodoPadre = nodoPadre;
        this.tablero = tablero;
        this.movimiento = movimiento;
        this.gnMovimientos = gnMovimientos;
    }

    public NodoNPuzzle getPadre(){
        return this.nodoPadre;
    }

    public int[][] Tablero(){
        return this.tablero;
    }

    public String Movimiento(){
        return this.movimiento;
    }

    public int gnMovimientos(){
        return this.gnMovimientos;
    }

    public float getValorF(){
        return this.valorF;
    }

    public float getPesoHs(){
        return this.pesohs;
    }

    public void setPadre(NodoNPuzzle nPadre){
        this.nodoPadre = nPadre;
    }

    public void setGnMovimientos(int n){
        this.gnMovimientos = n;
    }

    public void setPesoHs(float n){
        this.pesohs = n;
    }

    public void setValorF(float n){
        this.valorF = n;
    }
}
