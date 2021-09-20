package aStart;

import java.util.Comparator;

public class OrdenaNodos implements Comparator<Sucesor>{

    @Override
    public int compare(Sucesor o1, Sucesor o2) {
        if (o1.getValorF() > o2.getValorF()) {
            return -1;
        } else if(o1.getValorF() < o2.getValorF()){
            return 1;
        } else if(o1.getValorF() == o2.getValorF()){
            return 0;
        }
        return 0;
    } 
}
