package Interfaz;

import Datos.Jugador;
import java.awt.*;
import javax.swing.*;

public class MiCuenta extends JFrame {

    private JPanel panel;
    private Jugador jugadorActivo;

    public MiCuenta(Jugador jugadorActivo) {
        this.jugadorActivo = jugadorActivo;
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Mi Cuenta - Vampire Wargame Chess");
        setLocationRelativeTo(null);
        setResizable(false);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        colocarPanel();
        colocarBotonVolver();
        colocarLabels();
        
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        getContentPane().add(panel);
    }

    private void colocarLabels() {
        // --- Título principal ---
        JLabel titulo = new JLabel("Perfil del Jugador");
        titulo.setFont(new Font("Perpetua Titling MT", Font.BOLD, 24));
        titulo.setForeground(Color.YELLOW);
        titulo.setBounds(160, 30, 400, 40);
        panel.add(titulo);

        // --- Información del jugador ---
        JLabel lblNombre = new JLabel("Nombre: " + jugadorActivo.getNombre());
        lblNombre.setFont(new Font("Perpetua Titling MT", Font.BOLD, 14));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(100, 120, 400, 30);
        panel.add(lblNombre);

        JLabel lblPuntos = new JLabel("Puntos acumulados: " + jugadorActivo.getPuntos());
        lblPuntos.setFont(new Font("Perpetua Titling MT", Font.BOLD, 14));
        lblPuntos.setForeground(Color.WHITE);
        lblPuntos.setBounds(100, 160, 400, 30);
        panel.add(lblPuntos);

        // Fecha de ingreso (convertir Calendar a texto legible)
        String fecha = jugadorActivo.getFechaIngreso().getTime().toString();
        JLabel lblFecha = new JLabel("Fecha de ingreso: " + fecha);
        lblFecha.setFont(new Font("Perpetua Titling MT", Font.BOLD, 14));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setBounds(100, 200, 450, 30);
        panel.add(lblFecha);

        JLabel lblEstado = new JLabel("Estado: " + (jugadorActivo.isEstado() ? "Activo" : "Inactivo"));
        lblEstado.setFont(new Font("Perpetua Titling MT", Font.BOLD, 14));
        lblEstado.setForeground(jugadorActivo.isEstado() ? Color.GREEN : Color.RED);
        lblEstado.setBounds(100, 240, 300, 30);
        panel.add(lblEstado);

        // --- Fondo ---
        ImageIcon iconFondo = new ImageIcon("Fondo3.jpg");
        JLabel fondo = new JLabel(new ImageIcon(iconFondo.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 600, 500);
        panel.add(fondo);
        panel.setComponentZOrder(fondo, panel.getComponentCount() - 1);
    }

    private void colocarBotonVolver() {
        ImageIcon iconVolver = new ImageIcon("BtnVolver.png");
        JButton btnVolver = new JButton(new ImageIcon(iconVolver.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnVolver.setBounds(190, 360, 220, 60);
        btnVolver.setBorder(null);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnVolver.addActionListener(e -> MiCuenta.this.setVisible(false));
        panel.add(btnVolver);
    }
}
