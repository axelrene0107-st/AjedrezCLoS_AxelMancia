package Datos;

import java.awt.Color;

public abstract class Pieza {
    // Atributos protegidos: accesibles por las subclases (Lobo, Vampiro, Muerte)
    protected String tipo;
    public Color color; // Se mantiene pública para fácil acceso en la clase Partida
    protected int vida;
    protected int solidez;
    protected int ataque;

    public Pieza(String tipo, Color color, int vida, int solidez, int ataque) {
        this.tipo = tipo;
        this.color = color;
        this.vida = vida;
        this.solidez = solidez;
        this.ataque = ataque;
    }

    // --- Getters ---

    public String getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }

    public int getSolidez() {
        return solidez;
    }

    public int getAtaque() {
        return ataque;
    }

    // --- Lógica de Combate ---

    public void recibirDaño(int daño) {
        // 1. El daño se aplica primero a la Solidez
        int dañoRestante = Math.max(0, daño - solidez);
        this.solidez = Math.max(0, solidez - daño);

        // 2. Si queda daño restante, se aplica a la Vida
        if (dañoRestante > 0) {
            this.vida = Math.max(0, vida - dañoRestante);
        }
    }

    public boolean estaViva() {
        return vida > 0;
    }

    // --- MÉTODO ABSTRACTO (IMPLEMENTADO EN LAS SUBCLASES) ---

    /**
     * Verifica si el patrón de movimiento de (r1, c1) a (r2, c2) es legal para esta pieza.
     * La verificación de bloqueo de ruta (si aplica) se realiza en la clase Partida.
     * @param r1 Fila de origen
     * @param c1 Columna de origen
     * @param r2 Fila de destino
     * @param c2 Columna de destino
     * @return true si el patrón de movimiento es válido.
     */
    public abstract boolean esMovimientoValido(int r1, int c1, int r2, int c2);
}