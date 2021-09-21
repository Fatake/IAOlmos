package aStart;


public class NodoNPuzzle {
    private NodoNPuzzle nodoPadre;
    private int tablero[][];
    private String movimiento;
    private int gnMovimientos;
    private float pesohs = 0;
    private float pesoh1 = 0;
    private float pesoh2 = 0;
    private float pesoh3 = 0;
    private float pesoh4 = 0;
    private float valorF = 0;

    public NodoNPuzzle(int nodo[][], String movimiento ){
        this.nodoPadre = null;
        this.tablero = nodo;
        this.movimiento = movimiento;
    }

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

    public float getPesoh1(){
        return this.pesoh1;
    }

    public float getPesoh2(){
        return this.pesoh2;
    }

    public float getPesoh3(){
        return this.pesoh3;
    }

    public float getPesoh4(){
        return this.pesoh4;
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

    public void setPesoh1(float n){
        this.pesoh1 = n;
    }

    public void setPesoh2(float n){
        this.pesoh2 = n;
    }

    public void setPesoh3(float n){
        this.pesoh3 = n;
    }

    public void setPesoh4(float n){
        this.pesoh4 = n;
    }

    public void setPesoHs(float n){
        this.pesohs = n;
    }

    public void setValorF(float n){
        this.valorF = n;
    }

}