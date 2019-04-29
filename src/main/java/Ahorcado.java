
import database.DbMySql;
import models.Abcedario;
import models.Jugador;
import models.Palabra;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ahorcado {

    public static void main(String[] args) {

        DbMySql db = new DbMySql();
        Abcedario abc = new Abcedario();
        String palabra = "";

        try {
            db.startConnection();       // Se conecta a la base de datos
            palabra = db.getPalabra();      // Obtiene una palabra aleatoria
        }catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos");
        }finally {
            db.closeConnection();       // Siempre cierra la conexion
        }
        Palabra p = new Palabra(palabra);

        // Lista de jugadores: Recomendados 2 o 3
        List<Jugador> jugadores = Arrays.asList(new Jugador("SANTIAGO", abc, p),
                                                new Jugador("BERNARDO", abc, p),
                                                new Jugador("MORENITA", abc, p));

        // Comienza la ejecucion de los hilos
        for(Jugador j: jugadores) {
            j.start();
        }
        // El hilo Main sigue ejecutando, pero con join le digo que espere a esos hilos
        for(Jugador j: jugadores) {
            try {
                j.join();       // Unifica cada hilo con el main cuando terminan
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // Imprimo resultados del juego
        System.out.println("### PUNTAJES: ###\n");
        for(Jugador j: jugadores) {
            System.out.println("[ JUGADOR " + j.getNombre() + " HIZO " + j.getPuntos() + " PUNTOS" +
                                " Y LE QUEDARON " + j.getVidas() + " VIDAS ]");
        }

        // Calculo ganador los guardo en la misma lista ya que pueden ser varios por empate
        jugadores = calcularGanador(jugadores);

        // Muestro y guardo en base de datos el ganador/es
        // En caso de empate guardo todos los que empataron
        try {
            db.startConnection();
            if (jugadores.size() == 1) {
                System.out.println("\n[ EL GANADOR DEL AHORCADO ES " + jugadores.get(0).getNombre() + "!! PUNTOS: " +
                        jugadores.get(0).getPuntos() + ", VIDAS: " + jugadores.get(0).getVidas() + " ]");
                db.guardarGanador(jugadores.get(0));
            } else {
                System.out.println("\n[ EMPATE! LOS GANADORES SON: ");
                for (Jugador j : jugadores) {
                    System.out.println(j.getNombre() + ", PUNTOS: " + j.getPuntos() + ", VIDAS: " + j.getVidas() + " ]");
                    db.guardarGanador(j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }


    public static List<Jugador> calcularGanador(List<Jugador> jugadores) {
        Jugador ganador = null;
        int maxPuntaje = 0;
        // Guardo el puntaje mas alto
        for(Jugador j: jugadores) {
           if(j.getPuntos() > maxPuntaje) {
               maxPuntaje = j.getPuntos();
           }
        }
        // Guardo la cantidad de vidas mas alta
        int maxVidas = 0;
        for(Jugador j: jugadores) {
            if (j.getPuntos() == maxPuntaje) {
                if(j.getVidas() > maxVidas) {
                    maxVidas = j.getVidas();
                }
            }
        }
        // Las almaceno en constante para poder usarla en expresion Lambda filter
        final int puntaje = maxPuntaje;
        final int vidas = maxVidas;

        // Filtro todos los jugadores que hayan obtenido el maximo puntaje, en caso
        // de empate desempatan las vidas, si persiste se guardas todos los filtrados
        jugadores = jugadores.stream()
                            .filter(jug -> jug.getPuntos() == puntaje)
                            .filter(jug-> jug.getVidas() == vidas)
                            .collect(Collectors.toList());

        return jugadores;
    }


}
