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
    public boolean esMovimientoValido(int r1, int c1, int r2, int c2) {
        int dr = Math.abs(r1 - r2);
        int dc = Math.abs(c1 - c2);       
        // Movimiento de 1 casilla en cualquier dirección.
        return dr <= 1 && dc <= 1 && (dr + dc > 0);
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

        System.out.println("🩸 " + this.getTipo() + " absorbió vida de " + objetivo.getTipo());
    }
}