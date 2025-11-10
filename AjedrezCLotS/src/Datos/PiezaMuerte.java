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
    public boolean esMovimientoValido(int fila1, int columna1, int fila2, int columna2) {
        int direccionFila = Math.abs(fila1 - fila2);
        int direccionColumna = Math.abs(columna1 - columna2);

        // 🔹 Permite moverse en línea recta o diagonal a cualquier distancia
        boolean enLineaRecta = (fila1 == fila2) || (columna1 == columna2);
        boolean enDiagonal = (direccionFila == direccionColumna);

        return (enLineaRecta || enDiagonal) && (direccionFila + direccionColumna > 0);
    }
    
    
    @Override
    public boolean puedeUsarHabilidadEspecial() {
        return true;
    }
}