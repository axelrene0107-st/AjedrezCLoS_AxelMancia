package Datos;

import java.awt.Color;

public class PiezaMuerte extends Pieza {
    
    // Estadísticas Actualizadas
    private static final int VIDA_BASE = 3; // Se mantiene
    private static final int SOLIDEZ_BASE = 1; // Escudo bajo
    private static final int ATAQUE_BASE = 4;

    public PiezaMuerte(Color color) {
        super("Muerte", color, VIDA_BASE, SOLIDEZ_BASE, ATAQUE_BASE);
    }

    @Override
    public boolean esMovimientoValido(int r1, int c1, int r2, int c2) {
        int dr = Math.abs(r1 - r2);
        int dc = Math.abs(c1 - c2);

        // 🔹 Permite moverse en línea recta o diagonal a cualquier distancia
        boolean enLineaRecta = (r1 == r2) || (c1 == c2);
        boolean enDiagonal = (dr == dc);

        return (enLineaRecta || enDiagonal) && (dr + dc > 0);
    }
    
    
    @Override
    public boolean puedeUsarHabilidadEspecial() {
        return true;
    }
}