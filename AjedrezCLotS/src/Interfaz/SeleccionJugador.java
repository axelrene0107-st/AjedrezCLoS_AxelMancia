package Interfaz;

import Datos.Jugador;
import Datos.Jugadores;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

public class SeleccionJugador extends JFrame {
    private JPanel panel;
    private Jugadores listaJugadores;
    private Jugador jugadorActivo;

    public SeleccionJugador(Jugador jugadorActivo, Jugadores listaJugadores) {
        this.jugadorActivo = jugadorActivo;
        this.listaJugadores = listaJugadores;

        setTitle("Seleccionar oponente");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        colocarPanel();
        colocarBotones();
        colocarLabels();
        
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        add(panel);
    }

    private void colocarLabels() {
        
        ImageIcon iconLogo= new ImageIcon("Logo.png");   
        JLabel logo= new JLabel();//Agregamos un label para el fondo
        logo.setBounds(100, 20, 300, 200);
        logo.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
        panel.add(logo);//Label del fondo
        
        JLabel titulo = new JLabel("Elige a tu Oponente");
        titulo.setFont(new Font("Perpetua Titling MT", Font.BOLD, 18));
        titulo.setForeground(Color.YELLOW);
        titulo.setBounds(140, 200, 400, 40);
        panel.add(titulo);

        ImageIcon fondoIcon = new ImageIcon("Fondo4.jpg");
        JLabel fondo = new JLabel(new ImageIcon(fondoIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 500, 500);
        panel.add(fondo);
        panel.setComponentZOrder(fondo, panel.getComponentCount() - 1);
    }

    private void colocarBotones() {
        // Botón "Seleccionar jugador existente"
        ImageIcon iconJugador = new ImageIcon("BtnJugador.png");
        JButton btnJugador = new JButton(new ImageIcon(iconJugador.getImage().getScaledInstance(230, 120, Image.SCALE_SMOOTH)));
        btnJugador.setBounds(140, 220, 220, 120);
        btnJugador.setBorder(null);
        btnJugador.setContentAreaFilled(false);
        btnJugador.setFocusPainted(false);
        btnJugador.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnJugador);

        btnJugador.addActionListener(e -> seleccionarJugadorExistente());

        // Botón "Jugador invitado"
        ImageIcon iconInvitado = new ImageIcon("BtnCPU.png");
        JButton btnInvitado = new JButton(new ImageIcon(iconInvitado.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH)));
        btnInvitado.setBounds(140, 310, 220, 60);
        btnInvitado.setBorder(null);
        btnInvitado.setContentAreaFilled(false);
        btnInvitado.setFocusPainted(false);
        btnInvitado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnInvitado);

        btnInvitado.addActionListener(e -> iniciarContraInvitado());

        // Botón "Volver"
        ImageIcon iconVolver = new ImageIcon("BtnVolver.png");
        JButton btnVolver = new JButton(new ImageIcon(iconVolver.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnVolver.setBounds(140, 340, 220, 130);
        btnVolver.setBorder(null);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(btnVolver);

        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Permite seleccionar un jugador registrado de la lista (excluyendo al jugador activo).
     */
    private void seleccionarJugadorExistente() {
        List<String> nombres = new ArrayList<>();
        for (Jugador j : listaJugadores.jugadores) {
            if (!j.getNombre().equals(jugadorActivo.getNombre())) {
                nombres.add(j.getNombre());
            }
        }

        if (nombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay otros jugadores registrados.", "Sin jugadores", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String elegido = (String) JOptionPane.showInputDialog(this, "Selecciona al jugador oponente:", "Elegir jugador", JOptionPane.QUESTION_MESSAGE, null, nombres.toArray(), nombres.get(0));

        if (elegido != null) {
            Jugador oponente = listaJugadores.jugadores.stream()
                    .filter(j -> j.getNombre().equals(elegido))
                    .findFirst().orElse(null);

            if (oponente != null) {
                JOptionPane.showMessageDialog(this, jugadorActivo.getNombre() + " vs " + oponente.getNombre(),"Comienza la partida", JOptionPane.INFORMATION_MESSAGE);

                Partida partida = new Partida(jugadorActivo.getNombre(), oponente.getNombre());
                partida.setVisible(true);
                dispose();
            }
        }
    }

    private void iniciarContraInvitado() {
        Jugador invitado = new Jugador("Invitado", new char[]{'x'});
        invitado.setEstado(false); // Para marcarlo como no-registrado

        JOptionPane.showMessageDialog(this, jugadorActivo.getNombre() + " vs Invitado", "Comienza la partida", JOptionPane.INFORMATION_MESSAGE);

        Partida partida = new Partida(jugadorActivo.getNombre(), "Invitado");
        partida.setVisible(true);
        dispose();
    }
}
