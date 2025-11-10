package Datos;

import java.awt.Color;

public class PiezaLobo extends Pieza {
    
    private static final int VIDA_BASE = 5;
    private static final int SOLIDEZ_BASE = 2; 
    private static final int ATAQUE_BASE = 5;

    public PiezaLobo(Color color) {
        super("Lobo", color, VIDA_BASE, SOLIDEZ_BASE, ATAQUE_BASE);
    }

    @Override
    public boolean esMovimientoValido(int fila1, int columna1, int fila2, int columna2) {
        int direccionFila = Math.abs(fila1 - fila2);
        int direccionColumna = Math.abs(columna1 - columna2);
        
        // Movimiento de hasta 2 casillas en cualquier dirección.
        return direccionFila <= 2 && direccionColumna <= 2 && (direccionFila + direccionColumna > 0);
    }
}