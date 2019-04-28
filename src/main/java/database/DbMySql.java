package database;

import models.Jugador;
import java.sql.*;
import java.util.Calendar;

public class DbMySql {

    private Connection cn;

    public DbMySql() {}

    // Abro la conexion
    public void startConnection() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost/ahorcado", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query con parametros y sin retorno
    public void guardarGanador(Jugador ganador) throws SQLException {
        Statement st = this.cn.createStatement();
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        PreparedStatement ps = cn.prepareStatement("INSERT INTO ganadores (nombre, puntaje, vidas, fecha, palabra) " +
                                                        "VALUES (?,?,?,?,?)");
        ps.setString(1, ganador.getNombre());
        ps.setInt(2, ganador.getPuntos());
        ps.setInt(3, ganador.getVidas());
        ps.setDate(4, date);
        ps.setString(5, ganador.getPalabraCompleta());
        ps.execute();
    }

    // Obtiene palabra aleatoria. Query sin parametros y con retorno
    public String getPalabra() throws SQLException {
        String palabra = "";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery("SELECT palabra FROM palabras ORDER BY RAND() LIMIT 1");

        while (rs.next()) {
            palabra = rs.getString("palabra");
        }
        return palabra;
    }








}
