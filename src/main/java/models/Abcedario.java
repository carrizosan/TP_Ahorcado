package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Abcedario {

    private static List<Character> abc;
    //private static boolean ocupado = false;
    private Palabra palabra;  // Tiene dos Strings: La palabra a la que hay que llegar, y la palabra formada hasta el momento
    private boolean turno = false;
    private boolean enJuego = true;

    public Abcedario (Palabra p) {

        abc = new ArrayList<>(Arrays.asList('A','B','C','D','E','F','G','H','I',
                                            'J','K','L','M','N','O','P','Q','R',
                                            'S','T','U','V','W','X','Y','Z'));
        this.palabra = p;
    }

    // getAbc y getLetra se llaman desde metodo sincronizado. Por eso no se vuelven a sincronizar.
    public List<Character> getAbc() {
        return abc;
    }

    // Extrae una letra aleatoria del abecedario, la elimina de la lista y la retorna.
    public  Character getLetra() {
        Character l = ' ';
        if(!abc.isEmpty()) {
            Random rand = new Random();
            l = abc.get(rand.nextInt(abc.size()));  // Obtengo un indice entero random entre 0 y el tamaño del array
            abc.remove(l);
        }
        return l;
    }

    public synchronized boolean jugar(Jugador j) {

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
        if (!this.getAbc().isEmpty() && enJuego) {
            System.out.println("| TURNO DE " + j.getNombre());
            letra = this.getLetra();         // Extrae letra del abecedario
            System.out.println("| PALABRA EN JUEGO: " + this.getPalabraCompleta());
            System.out.println("| OBTUVO LA LETRA: " + letra);

            if(palabra.containtsChar(letra)) {      // Verifico si la letra pertenece a la palabra objetivo
                palabra.putLetra(letra);            // Agrego la letra donde corresponda en la palabra parcial
                j.setPuntos(j.getPuntos() + 1);
                System.out.println("| ACIERTO!");
            } else {
                j.setVidas(j.getVidas() - 1);
                System.out.println("| PIERDE UNA VIDA!");
            }
            System.out.println("| PALABRA FORMADA: " + this.palabra.getPalabraEnJuego());
            System.out.println("| PUNTOS: " + j.getPuntos());
            System.out.println("| VIDAS: " + j.getVidas());
            System.out.println("-----------------------------\n|");
            // El juego termina si uno de los jugadores queda sin vidas
            if(j.getVidas() == 0) {
                enJuego = false;
                System.out.println("--- JUGADOR " + j.getNombre() + " SE HA QUEDADO SIN VIDAS! GAME OVER ---\n");
            }
            // El juego termina si un jugador completa la palabra
            if(this.palabra.getPalabra().equals(this.palabra.getPalabraEnJuego().toString())) {
                enJuego = false;
                System.out.println("--- JUGADOR " + j.getNombre() + " HA COMPLETADO LA PALABRA! GAME OVER ---\n");
            }
            // El juego termina si se terminan las letras del abecedario
            if (this.getAbc().isEmpty()) {
                System.out.println("--- YA SE HAN USADO TODAS LAS LETRAS! GAME OVER ---\n");
            }
        } else {
            enJuego = false;
        }
        turno = false;
        notify();   // Fin zona critica, notifico a los hilos que estan esperando para entrar
        return enJuego;
    }


    public String getPalabraCompleta() {   // Palabra Objetivo
        return palabra.getPalabra();
    }

}
