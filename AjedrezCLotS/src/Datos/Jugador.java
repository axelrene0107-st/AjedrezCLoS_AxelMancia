
package Datos;

import java.util.Calendar;

/**
 *
 * @author axelr
 */
public class Jugador {
    public String nombre;
    public char[] password;
    public int puntos;
    public Calendar fechaIngreso;
    public boolean estado;
    
    public Jugador(String nombre, char[] password){
        this.nombre=nombre;
        this.password=password;
        puntos=0;
        fechaIngreso=Calendar.getInstance();
        estado=true;
    }

    public String getNombre() {
        return nombre;
    }

    public char[] getPassword() {
        return password;
    }

    public int getPuntos() {
        return puntos;
    }

    public Calendar getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setFechaIngreso(Calendar fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
    

