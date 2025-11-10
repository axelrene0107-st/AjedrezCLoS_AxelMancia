package Datos;
import java.awt.Color;

public class PiezaZombie extends Pieza {

    public PiezaZombie(Color color) {
        super("Zombie", color, 1, 0, 1);
    }

    @Override
    public boolean esMovimientoValido(int r1, int c1, int r2, int c2) {
        // ❌ El zombie no se mueve
        return false;
    }

    @Override
    public boolean puedeUsarHabilidadEspecial() {
        return false;
    }
}
