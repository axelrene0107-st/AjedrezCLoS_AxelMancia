package Interfaz;

import Datos.Pieza;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.net.URL;

public class Partida extends JFrame {
    private static final int SIZE = 6;
    private static final int TILE_SIZE = 80;

    private JPanel panel;
    private JButton[][] casillas;
    private Pieza[][] tableroLogico; // Almacena la ubicación de las piezas
    
    // Variables para manejar la selección y movimiento
    private JButton casillaSeleccionada = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    
    // Íconos de las piezas (se cargarán una vez)
    private ImageIcon muerteIcon;
    private ImageIcon vampiroIcon;
    private ImageIcon loboIcon;

    public Partida() {
        setTitle("Vampire Wargame Chess");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 700));
        setLayout(null);
        
        tableroLogico = new Pieza[SIZE][SIZE];
        
        cargarIconosDePiezas();
        iniciarComponentes();
        
        // Colocar las 6 piezas iniciales
        colocarPiezasIniciales();

        setVisible(true);
    }
    
    // --- LÓGICA DE CARGA DE PIEZAS ---
    private void cargarIconosDePiezas() {
        int iconSize = TILE_SIZE - 10; // Un poco más pequeño que la casilla
        
        muerteIcon = cargarYRedimensionarIcono("Muerte.png", iconSize);
        vampiroIcon = cargarYRedimensionarIcono("Vampiro.png", iconSize);
        loboIcon = cargarYRedimensionarIcono("Lobo.png", iconSize);
    }

    private ImageIcon cargarYRedimensionarIcono(String fileName, int size) {
        ImageIcon originalIcon = null;
        try {
            // Intenta cargar desde la carpeta del proyecto o el classpath
            URL url = Partida.class.getResource("/" + fileName);
            if (url != null) {
                originalIcon = new ImageIcon(url);
            } else {
                originalIcon = new ImageIcon(fileName);
            }
            
            if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                 throw new Exception("Error al cargar la imagen: " + fileName);
            }

            Image img = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo cargar el ícono de la pieza " + fileName + ". " + e.getMessage());
            return new ImageIcon(); // Retorna un ícono vacío si falla
        }
    }

    // --- LÓGICA DE INICIO DE PARTIDA ---
    private void colocarPiezasIniciales() {
        // 1. Reiniciar el tablero lógico
        tableroLogico = new Pieza[SIZE][SIZE];

        // 🔴 JUGADOR ROJO (6 piezas: 2 Muerte, 2 Vampiro, 2 Lobo)        
        // Fila 0
        tableroLogico[0][0] = new Pieza("Muerte", Color.RED);   // a6
        tableroLogico[0][1] = new Pieza("Vampiro", Color.RED);  // b6
        tableroLogico[0][2] = new Pieza("Lobo", Color.RED);     // c6
        tableroLogico[0][3] = new Pieza("Lobo", Color.RED);     // d6
        tableroLogico[0][4] = new Pieza("Vampiro", Color.RED);  // e6
        tableroLogico[0][5] = new Pieza("Muerte", Color.RED);   // f6
    
        // 🔵 JUGADOR AZUL (6 piezas: 2 Muerte, 2 Vampiro, 2 Lobo)        
        // Fila 5
        tableroLogico[5][0] = new Pieza("Muerte", Color.BLUE);  // a1
        tableroLogico[5][1] = new Pieza("Vampiro", Color.BLUE); // b1
        tableroLogico[5][2] = new Pieza("Lobo", Color.BLUE);    // c1
        tableroLogico[5][3] = new Pieza("Lobo", Color.BLUE);    // d1
        tableroLogico[5][4] = new Pieza("Vampiro", Color.BLUE); // e1
        tableroLogico[5][5] = new Pieza("Muerte", Color.BLUE);  // f1
    
    // Actualiza el aspecto de las casillas en la GUI
        actualizarTableroGUI();
    }
    
    // Asigna el ícono y color del texto a la casilla basándose en el modelo lógico
    private void actualizarTableroGUI() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton casilla = casillas[row][col];
                Pieza pieza = tableroLogico[row][col];
                
                casilla.setIcon(null);
                casilla.setText(null);

                if (pieza != null) {
                    ImageIcon icon = getIconoPieza(pieza.tipo);
                    casilla.setIcon(icon);
                    // Opcional: poner una etiqueta de texto con el color del jugador
                    casilla.setForeground(pieza.color);
                    casilla.setText(""); // Si quieres que se muestre el color, si no, solo el ícono
                    casilla.setHorizontalTextPosition(SwingConstants.CENTER);
                    casilla.setVerticalTextPosition(SwingConstants.CENTER);
                }
            }
        }
    }
    
    private ImageIcon getIconoPieza(String tipo) {
        switch (tipo) {
            case "Muerte": return muerteIcon;
            case "Vampiro": return vampiroIcon;
            case "Lobo": return loboIcon;
            default: return new ImageIcon();
        }
    }

    private void iniciarComponentes() {
        colocarPanel();
        colocarLabels(); 
        colocarTablero(); 
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, 700, 700);
        panel.setLayout(null);
        add(panel);
    }

    private void colocarTablero() {
        int boardSize = TILE_SIZE * SIZE;
        int posX = (700 - boardSize) / 2;
        int posY = (700 - boardSize) / 2 - 30;

        JPanel tablero = new JPanel(new GridLayout(SIZE, SIZE));
        tablero.setBounds(posX, posY, boardSize, boardSize);
        tablero.setOpaque(false);

        Color bordeTablero = new Color(90, 0, 0); 
        tablero.setBorder(BorderFactory.createLineBorder(bordeTablero, 8, true));

        casillas = new JButton[SIZE][SIZE];
        
        // Puedes cambiar los colores planos por tus PNG como lo hicimos en el ejemplo anterior:
        // (Sería necesario cargar PanelClaro.png y PanelNegro.png aquí)
        Color colorClaro = new Color(200, 180, 150); 
        Color colorOscuro = new Color(60, 30, 30);

        for (int fila = 0; fila < SIZE; fila++) {
            for (int col = 0; col < SIZE; col++) {
                JButton casilla = new JButton();
                Color color = ((fila + col) % 2 == 0) ? colorClaro : colorOscuro;

                casilla.setBackground(color);
                casilla.setOpaque(true);
                casilla.setBorder(null);
                casilla.setFocusPainted(false);
                casilla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                // --- CLAVE: Añadir el listener de movimiento ---
                final int finalFila = fila;
                final int finalCol = col;
                casilla.addActionListener(e -> manejarClicCasilla(finalFila, finalCol));

                casillas[fila][col] = casilla;
                tablero.add(casilla);
            }
        }

        panel.add(tablero);
        panel.setComponentZOrder(tablero, 0); 
    }

    private void colocarLabels() {
        // 🖼️ Fondo
        // Asumiendo que "Fondo4.jpg" existe
        ImageIcon iconFondo = new ImageIcon("Fondo4.jpg"); 
        JLabel fondo = new JLabel(new ImageIcon(iconFondo.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 700, 700);
        panel.add(fondo);
        panel.setComponentZOrder(fondo, panel.getComponentCount() - 1);

        // 🩶 Logo
        // Asumiendo que "Logo.png" existe
        ImageIcon iconLogo = new ImageIcon("Logo.png");
        JLabel logo = new JLabel(new ImageIcon(iconLogo.getImage().getScaledInstance(200, 130, Image.SCALE_SMOOTH)));
        logo.setBounds(240, 550, 200, 130);
        panel.add(logo);
    }
    
    // --- LÓGICA DE MOVIMIENTO DE PIEZAS ---
    private void manejarClicCasilla(int targetRow, int targetCol) {
        Pieza piezaEnDestino = tableroLogico[targetRow][targetCol];
        JButton targetCasilla = casillas[targetRow][targetCol];
        
        // 1. Si no hay pieza seleccionada, intenta seleccionar una
        if (casillaSeleccionada == null) {
            if (piezaEnDestino != null) {
                // Selecciona la pieza
                casillaSeleccionada = targetCasilla;
                selectedRow = targetRow;
                selectedCol = targetCol;
                
                // Resalta la casilla seleccionada
                targetCasilla.setBorder(new LineBorder(Color.YELLOW, 3, true));
                System.out.println("Pieza seleccionada en: " + targetRow + ", " + targetCol);
            } else {
                System.out.println("Casilla vacía. Nada para seleccionar.");
            }
        
        // 2. Ya hay una pieza seleccionada
        } else {
            // A. Clic en la misma pieza: Deseleccionar
            if (targetCasilla == casillaSeleccionada) {
                casillaSeleccionada.setBorder(null);
                casillaSeleccionada = null;
                selectedRow = -1;
                selectedCol = -1;
                System.out.println("Selección cancelada.");
                
            // B. Clic en una casilla diferente: Intentar mover
            } else {
                // Realizar el movimiento (aquí iría la lógica de movimiento de ajedrez)
                
                // Simulación de movimiento simple:
                // 1. Mueve la pieza del origen al destino
                tableroLogico[targetRow][targetCol] = tableroLogico[selectedRow][selectedCol];
                
                // 2. Limpia la casilla de origen
                tableroLogico[selectedRow][selectedCol] = null;
                
                // 3. Quita el resaltado y deselecciona
                casillaSeleccionada.setBorder(null);
                casillaSeleccionada = null;
                selectedRow = -1;
                selectedCol = -1;
                
                // 4. Actualiza la GUI
                actualizarTableroGUI();
                System.out.println("Movimiento completado a: " + targetRow + ", " + targetCol);
            }
        }
    }
}