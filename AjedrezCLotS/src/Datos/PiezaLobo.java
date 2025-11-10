package Datos;

import java.awt.Color;

public class PiezaLobo extends Pieza {
    
    // Estadísticas Actualizadas
    private static final int VIDA_BASE = 5;
    private static final int SOLIDEZ_BASE = 2; // Escudo
    private static final int ATAQUE_BASE = 5;

    public PiezaLobo(Color color) {
        super("Lobo", color, VIDA_BASE, SOLIDEZ_BASE, ATAQUE_BASE);
    }

    @Override
    public boolean esMovimientoValido(int r1, int c1, int r2, int c2) {
        int dr = Math.abs(r1 - r2);
        int dc = Math.abs(c1 - c2);
        
        // Movimiento de hasta 2 casillas en cualquier dirección.
        return dr <= 2 && dc <= 2 && (dr + dc > 0);
    }
}