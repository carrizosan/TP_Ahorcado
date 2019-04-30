package models;

public class Jugador extends Thread {

    private String nombre;
    private Abcedario abc;
    private int puntos;
    private int vidas;


    public Jugador (String nombre, Abcedario abc) {
        this.nombre = nombre;
        this.puntos = 0;
        this.vidas = 7;
        this.abc = abc;
    }

    public void run(){
       boolean sigasiga = true;
       // Ejecuta siempre que el juego no haya terminado
       while(sigasiga) {
               sigasiga = abc.jugar(this);
           try {
               Thread.currentThread().sleep(1000);      // Sleep solo estetico para el output
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

       }

    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getVidas() {
        return vidas;
    }

}
