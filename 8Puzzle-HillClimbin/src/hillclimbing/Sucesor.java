package hillclimbing;


public class Sucesor {
    private int nodoPadre[][];
    private int nodo[][];
    private String movimiento;
    private int nMovimientos;

    public Sucesor(int nodo[][], String movimiento ){
        this.nodoPadre = null;
        this.nodo = nodo;
        this.movimiento = movimiento;
    }

    public Sucesor(int nodoPadre[][],int nodo[][], String movimiento, int nMovimientos ){
        this.nodoPadre = nodoPadre;
        this.nodo = nodo;
        this.movimiento = movimiento;
        this.nMovimientos = nMovimientos;
    }

    public int[][] nodoPadre(){
        return this.nodoPadre;
    }

    public int[][] Nodo(){
        return this.nodo;
    }

    public String Movimiento(){
        return this.movimiento;
    }

    public int nMovimientos(){
        return this.nMovimientos;
    }
}
