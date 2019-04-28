package models;

public class Jugador extends Thread {

    private String nombre;
    private Abcedario abc;
    private Palabra palabra;   // Tiene dos Strings: La palabra a la que hay que llegar, y la palabra formada hasta el momento
    private int puntos;
    private int vidas;
    private static boolean turno = false;
    private static boolean enJuego = true;

    public Jugador (String nombre, Abcedario abc, Palabra p) {
        this.nombre = nombre;
        this.puntos = 0;
        this.vidas = 7;
        this.abc = abc;
        this.palabra = p;
    }

    public void run(){
       boolean sigasiga = true;
       // Ejecuta siempre que el juego no haya terminado
       while(sigasiga) {
           synchronized (abc) {
               sigasiga = jugar();
           }
           try {
               Thread.currentThread().sleep(2000);      // Sleep solo estetico para el output
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

    }

    public synchronized boolean jugar() {

        // Exclusion mutua
        while(turno) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Zona Critica
        turno = true;

        Character letra = ' ';
        /* Puede darse que un hilo ingrese aqui luego del notify() y antes del retorno
           por lo que se vuelve a verificar que la variable enJuego sea true.
           Se verifica que el abecedario todavia tenga letras, sino termina el juego. */
        if (!abc.getAbc().isEmpty() && enJuego) {
            System.out.println("| TURNO DE " + nombre);
            letra = abc.getLetra();         // Extrae letra del abecedario
            System.out.println("| PALABRA EN JUEGO: " + this.palabra.getPalabra());
            System.out.println("| OBTUVO LA LETRA: " + letra);

            if(palabra.containtsChar(letra)) {      // Verifico si la letra pertenece a la palabra objetivo
                palabra.putLetra(letra);            // Agrego la letra donde corresponda en la palabra parcial
                this.puntos++;
                System.out.println("| ACIERTO!");
            } else {
                this.vidas--;
                System.out.println("| PIERDE UNA VIDA!");
            }
            System.out.println("| PALABRA FORMADA: " + this.palabra.getPalabraEnJuego());
            System.out.println("| PUNTOS: " + this.puntos);
            System.out.println("| VIDAS: " + this.vidas);
            System.out.println("-----------------------------\n|");
            // El juego termina si uno de los jugadores queda sin vidas
            if(this.vidas == 0) {
                enJuego = false;
                System.out.println("--- JUGADOR " + nombre + " SE HA QUEDADO SIN VIDAS! GAME OVER ---\n");
            }
            // El juego termina si un jugador completa la palabra
            if(this.palabra.getPalabra().equals(this.palabra.getPalabraEnJuego().toString())) {
                enJuego = false;
                System.out.println("--- JUGADOR " + nombre + " HA COMPLETADO LA PALABRA! GAME OVER ---\n");
            }
            // El juego termina si se terminan las letras del abecedario
            if (abc.getAbc().isEmpty()) {
                System.out.println("--- YA SE HAN USADO TODAS LAS LETRAS! GAME OVER ---\n");
            }
        } else {
            enJuego = false;
        }
        turno = false;
        notify();   // Fin zona critica, notifico a los hilos que estan esperando para entrar
        return enJuego;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVidas() {
        return vidas;
    }

    public String getPalabraCompleta() {   // Palabra Objetivo
        return palabra.getPalabra();
    }
}
