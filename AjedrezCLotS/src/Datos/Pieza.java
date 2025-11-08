package Datos;

import java.awt.Color;

/**
 *
 * @author axelr
 */
public class Pieza {
    public String tipo; // Muerte, Vampiro, Lobo
    public Color color; // Color del jugador (ej. Color.RED o Color.BLUE)

    public Pieza(String tipo, Color color) {
        this.tipo = tipo;
        this.color = color;
    }
}

