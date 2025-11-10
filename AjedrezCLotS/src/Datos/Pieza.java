package Datos;

import java.awt.Color;

public abstract class Pieza {
    // Atributos protegidos: accesibles por las subclases (Lobo, Vampiro, Muerte)
    protected String tipo;
    public Color color; 
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
        //El daño se aplica primero a la Solidez
        int dañoRestante = Math.max(0, daño - solidez);
        this.solidez = Math.max(0, solidez - daño);

        //Si queda daño restante, se aplica a la Vida
        if (dañoRestante > 0) {
            this.vida = Math.max(0, vida - dañoRestante);
        }
    }

    public boolean estaViva() {
        return vida > 0;
    }


    public abstract boolean esMovimientoValido(int fila1, int columna1, int fila2, int columna2);
    
    public boolean puedeUsarHabilidadEspecial() {
        return false; //Por defecto no tiene habilidad especial
    }

    public void usarHabilidadEspecial(Pieza objetivo) {
        //Implementado en las subclases que lo necesiten
    }
}