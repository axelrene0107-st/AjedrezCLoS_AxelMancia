package Datos;

import java.awt.Color;

public class PiezaVampiro extends Pieza {
    
    // Estadísticas Actualizadas
    private static final int VIDA_BASE = 4;
    private static final int SOLIDEZ_BASE = 5; // Escudo muy alto
    private static final int ATAQUE_BASE = 3;
    private static final int VIDA_MAXIMA = 4; // Se mantiene

    public PiezaVampiro(Color color) {
        super("Vampiro", color, VIDA_BASE, SOLIDEZ_BASE, ATAQUE_BASE);
    }

    @Override
    public boolean esMovimientoValido(int fila1, int columna1, int fila2, int columna2) {
        int direccionFila = Math.abs(fila1 - fila2);
        int direccionColumna = Math.abs(columna1 - columna2);       
        // Movimiento de 1 casilla en cualquier dirección.
        return direccionFila <= 1 && direccionColumna <= 1 && (direccionFila + direccionColumna > 0);
    }
    
    @Override
    public boolean puedeUsarHabilidadEspecial() {
        return true;
    }
    
    @Override
    public void usarHabilidadEspecial(Pieza objetivo) {
        if (objetivo == null || !objetivo.estaViva()) return;

        // Quita 1 de vida al enemigo y se cura 1 (sin pasar el máximo)
        objetivo.recibirDaño(1);
        this.vida = Math.min(this.vida + 1, VIDA_MAXIMA);

        System.out.println(this.getTipo() + " absorbió vida de " + objetivo.getTipo());
    }
}