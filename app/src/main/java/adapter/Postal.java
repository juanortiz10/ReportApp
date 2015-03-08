package adapter;


import java.util.HashMap;

public class Postal {
    HashMap<Integer, String> codigos=new HashMap<>();

    public Postal(){
        setCodes();
    }
    public void setCodes() {
        codigos.put(64000,"Monterrey");
        codigos.put(66600,"Apodaca");
        codigos.put(66050,"Escobedo");
        codigos.put(67100,"Guadalupe");
        codigos.put(67260,"Juarez");
        codigos.put(67485,"Cadereyta");
        codigos.put(66488,"San Nicolas");
        codigos.put(64520,"Monterrey");
        codigos.put(67940,"Aramberri");
        codigos.put(64410,"Monterrey");
        codigos.put(0,"Monterrey");
    }
    public String getCodes(int codigo) {
        return codigos.get(codigo);
    }
}
