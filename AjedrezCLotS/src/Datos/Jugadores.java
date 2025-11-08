//Clase para guardar jugadores 
package Datos;

import java.util.ArrayList;

/**
 *
 * @author axelr
 */
public class Jugadores {
    public ArrayList<Jugador> Jugadores;
    
    public Jugadores(){
        Jugadores= new ArrayList<>();
    }
    
    //Metodo para guardar objetos jugador en el ArrayList
    public void guardarJugadores(Jugador player){
        Jugadores.add(player);
    }
    
    //Metodo para eliminar objetos jugador del Arraylist
    public void eliminarJugadores(Jugador player){
        Jugadores.remove(player);
    }
    
    //Metodo para revisar si el nombre que se da como parametro existe.
    public boolean revisarNombres(String nombre){
        for(Jugador player: Jugadores){
            if(player.getNombre().equals(nombre)){
                System.out.println("Encontrado");
                return true;
            }
        }
        System.out.println("No encontrado");
        return false;
    }
    
    public boolean revisarPassword(char[] contrasena){
        for(Jugador player: Jugadores){
            if(player.getPassword().equals(contrasena)){
                System.out.println("Encontrado");
                return true;
            }
        }
        System.out.println("No encontrado");
        return false;
    }
    
}
