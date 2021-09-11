package aStart;

public class Sucesor {
    private int nodoPadre[][];
    private int nodo[][];
    private String movimiento;
    private int gnMovimientos;
    private int pesoh1 = 0;
    private int pesoh2 = 0;
    private int pesoh3 = 0;
    private int pesoh4 = 0;
    private float valorF = 0;

    public Sucesor(int nodo[][], String movimiento ){
        this.nodoPadre = null;
        this.nodo = nodo;
        this.movimiento = movimiento;
    }

    public Sucesor(int nodoPadre[][],int nodo[][], String movimiento, int gnMovimientos ){
        this.nodoPadre = nodoPadre;
        this.nodo = nodo;
        this.movimiento = movimiento;
        this.gnMovimientos = gnMovimientos;
    }

    public Sucesor(int nodoPadre[][],
                    int nodo[][], 
                    String movimiento, 
                    int gnMovimientos,
                    int pesoh1,
                    int pesoh2,
                    int pesoh3,
                    int pesoh4,
                    Float valorF ){
        this.nodoPadre = nodoPadre;
        this.nodo = nodo;
        this.movimiento = movimiento;
        this.gnMovimientos = gnMovimientos;
        this.pesoh1 = pesoh1;
        this.pesoh2 = pesoh2;
        this.pesoh3 = pesoh3;
        this.pesoh4 = pesoh4;
        this.valorF = valorF;
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

    public int gnMovimientos(){
        return this.gnMovimientos;
    }

    public int getPesoh1(){
        return this.pesoh1;
    }

    public int getPesoh2(){
        return this.pesoh2;
    }

    public int getPesoh3(){
        return this.pesoh3;
    }

    public int getPesoh4(){
        return this.pesoh4;
    }

    public float getValorF(){
        return this.valorF;
    }

    public void setGnMovimientos(int n){
        this.gnMovimientos = n;
    }

    public void setPesoh1(int n){
        this.pesoh1 = n;
    }

    public void setPesoh2(int n){
        this.pesoh2 = n;
    }

    public void setPesoh3(int n){
        this.pesoh3 = n;
    }

    public void setPesoh4(int n){
        this.pesoh4 = n;
    }

    public void setValorF(float n){
        this.valorF = n;
    }
}
