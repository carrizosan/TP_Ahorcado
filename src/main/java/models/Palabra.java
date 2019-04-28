package models;


public class Palabra {

    // Palabra a la cual hay que llegar. Ej: MOUSE
    private static String palabra;
    /* Se usa StringBuilder ya que provee metodos para reemplazar caracteres en una posicion especifica.
    Palabra compuesta por guiones (-) de la misma longitud que la palabra */
    private static StringBuilder palabraEnJuego;
    private static boolean ocupado = false;

    public Palabra (String p) {
        this.palabra = p;
        this.palabraEnJuego = new StringBuilder("");

        // Agrega tantos guines como letras tiene la palabra objetivo
        for(int i=0; i < this.palabra.length(); i++) {
            this.palabraEnJuego = palabraEnJuego.append("-");
        }
    }

    public String getPalabra() {
        return palabra;
    }

    public static StringBuilder getPalabraEnJuego() {
        return palabraEnJuego;
    }

    // Si la letra se encuentra en la palabra la reemplaza en las posiciones correspondientes
    public synchronized void putLetra(Character letra){
        while(ocupado) {
            try {
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        // Zona Critica
        ocupado = true;
        if(this.containtsChar(letra)) {         // containsChar verifica en la palabra objetivo
            for(int i = 0; i < this.getPalabra().length(); i++) {
                if(this.getPalabra().charAt(i) == letra) {
                    this.palabraEnJuego.setCharAt(i, letra);
                }
            }
        }
        ocupado = false;
        notify();
    }

    // Devuelve true si la palabra objetivo contiene el caracter pasado por parametro
    public boolean containtsChar (Character c) {
        return this.getPalabra().contains(c.toString());
    }
}
