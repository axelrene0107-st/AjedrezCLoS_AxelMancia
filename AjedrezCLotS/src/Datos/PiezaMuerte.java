package Datos;

import java.awt.Color;

public class PiezaMuerte extends Pieza {
    
    private static final int VIDA_BASE = 3;
    private static final int SOLIDEZ_BASE = 2;
    private static final int ATAQUE_BASE = 1;

    public PiezaMuerte(Color color) {
        super("Muerte", color, VIDA_BASE, SOLIDEZ_BASE, ATAQUE_BASE);
    }

    @Override
    public boolean esMovimientoValido(int r1, int c1, int r2, int c2) {
        int dr = Math.abs(r1 - r2);
        int dc = Math.abs(c1 - c2);
        
        // La Muerte se mueve exactamente 1 casilla en cualquier dirección.
        return dr <= 1 && dc <= 1 && (dr + dc > 0);
    }
}