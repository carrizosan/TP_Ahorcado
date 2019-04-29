package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Abcedario {

    private static List<Character> abc;
    private static boolean ocupado = false;

    public Abcedario () {

        abc = new ArrayList<>(Arrays.asList('A','B','C','D','E','F','G','H','I',
                                            'J','K','L','M','N','O','P','Q','R',
                                            'S','T','U','V','W','X','Y','Z'));

    }

    /* Devuelve el abecedario. Se sincroniza para que solo acceda un hilo a la vez.
    No cambia la variable ocupado: Si entra en el wait, es porque hay otro hilo
    modificando la lista en otro metodo, y debe esperar a que este termine de
    modificar la lista y lo notifique
     */
    public synchronized List<Character> getAbc() {
        while(ocupado) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("Intep ex");
            }
        }
        return abc;
    }

    // Extrae una letra aleatoria del abecedario, la elimina de la lista y la retorna. Sincronizado
    public synchronized Character getLetra() {
        while(ocupado) {
            try {
                wait();
            }catch(InterruptedException e ) {
                System.out.println("Interrumped Ex");
            }
        }
        // Zona critica
        ocupado = true;
        Character l = ' ';
        if(!abc.isEmpty()) {
            Random rand = new Random();
            l = abc.get(rand.nextInt(abc.size()));  // Obtengo un indice entero random entre 0 y el tamaño del array
            abc.remove(l);
        }
        ocupado = false;
        notifyAll();
        return l;
    }
}
