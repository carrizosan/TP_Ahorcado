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

    // Devuelve el abecedario. Se sincroniza para que solo acceda un hilo a la vez
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
        int size = abc.size();
        Random rand = new Random();
        l = abc.get(rand.nextInt(size));
        abc.remove(l);
        ocupado = false;
        notify();
        return l;
    }
}
