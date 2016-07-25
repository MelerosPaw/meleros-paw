package melerospaw.memoryutils;

import java.io.Serializable;

/**
 * Created by Juan José Melero on 23/07/2016.
 */
public class MiObjeto implements Serializable{

    private String palabra;

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
}
