package Datos;

import java.util.ArrayList;
import java.util.Arrays;

public class Jugadores {

    private ArrayList<Jugador> jugadores;

    public Jugadores() {
        jugadores = new ArrayList<>();
    }

    // ✅ Agrega un jugador nuevo
    public void guardarJugadores(Jugador player) {
        jugadores.add(player);
    }

    // ✅ Elimina un jugador
    public void eliminarJugadores(Jugador player) {
        jugadores.remove(player);
    }

    // ✅ Revisa si existe un nombre de usuario
    public boolean revisarNombres(String nombre) {
        for (Jugador player : jugadores) {
            if (player.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Nombre encontrado: " + nombre);
                return true;
            }
        }
        System.out.println("Nombre no encontrado: " + nombre);
        return false;
    }

    // ⚠️ Comparar contraseñas correctamente
    public boolean revisarPassword(char[] contrasena) {
        for (Jugador player : jugadores) {
            if (Arrays.equals(player.getPassword(), contrasena)) {
                System.out.println("Contraseña encontrada");
                return true;
            }
        }
        System.out.println("Contraseña no encontrada");
        return false;
    }

    // ✅ Busca jugador por nombre y contraseña
    public Jugador buscarJugador(String nombre, char[] contrasena) {
        for (Jugador player : jugadores) {
            if (player.getNombre().equalsIgnoreCase(nombre) &&
                Arrays.equals(player.getPassword(), contrasena)) {
                return player;
            }
        }
        return null; // No encontrado
    }

    // ✅ Devuelve toda la lista (si necesitas usarla en otra clase)
    public ArrayList<Jugador> getLista() {
        return jugadores;
    }
}
