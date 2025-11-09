package Interfaz;

// Importaciones de las clases concretas de piezas
import Datos.Pieza; // Clase base (necesaria para la matriz)
import Datos.PiezaLobo;
import Datos.PiezaVampiro;
import Datos.PiezaMuerte;

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
    private Pieza[][] tableroLogico;    

    // Variables de control de juego
    private JButton casillaSeleccionada = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Color turnoActual = Color.RED; // 🔴 El juego comienza con el jugador ROJO

    // Íconos de las piezas (se asume que están configurados para 6 imágenes)
    private ImageIcon muerteIcon;
    private ImageIcon vampiroIcon;
    private ImageIcon loboIcon;
    private ImageIcon muerteClaraIcon;
    private ImageIcon vampiroClaraIcon;
    private ImageIcon loboClaraIcon;
    
    private static final Color COLOR_MOVIMIENTO_VALIDO = new Color(50, 200, 50, 100); // Verde semi-transparente
    private static final Color COLOR_ATAQUE_VALIDO = new Color(200, 50, 50, 100);    // Rojo semi-transparente
    private static final Color COLOR_SELECCIONADO = Color.YELLOW;

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
        colocarPiezasIniciales();
    }
    
    // --- LÓGICA DE CARGA DE PIEZAS (Se mantiene igual) ---
    private void cargarIconosDePiezas() {
        int iconSize = TILE_SIZE - 10; 

        muerteIcon = cargarYRedimensionarIcono("Muerte.png", iconSize);
        vampiroIcon = cargarYRedimensionarIcono("Vampiro.png", iconSize);
        loboIcon = cargarYRedimensionarIcono("Lobo.png", iconSize);
        
        muerteClaraIcon = cargarYRedimensionarIcono("Muerte_Blanco.png", iconSize);
        vampiroClaraIcon = cargarYRedimensionarIcono("Vampiro_Blanco.png", iconSize);
        loboClaraIcon = cargarYRedimensionarIcono("Lobo_Blanco.png", iconSize);
    }

    private ImageIcon cargarYRedimensionarIcono(String fileName, int size) {
        ImageIcon originalIcon = null;
        try {
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
            return new ImageIcon();
        }
    }

    // --- LÓGICA DE INICIO DE PARTIDA (ACTUALIZADA con clases concretas) ---
    private void colocarPiezasIniciales() {
        tableroLogico = new Pieza[SIZE][SIZE];

        // 🔴 JUGADOR ROJO (6 piezas: 2 Muerte, 2 Vampiro, 2 Lobo)
        // Fila 0
        tableroLogico[0][0] = new PiezaMuerte(Color.RED);
        tableroLogico[0][1] = new PiezaVampiro(Color.RED);
        tableroLogico[0][2] = new PiezaLobo(Color.RED);
        tableroLogico[0][3] = new PiezaLobo(Color.RED);
        tableroLogico[0][4] = new PiezaVampiro(Color.RED);
        tableroLogico[0][5] = new PiezaMuerte(Color.RED);
        
        // 🔵 JUGADOR AZUL (Color.BLUE)
        // Fila 5
        tableroLogico[5][0] = new PiezaMuerte(Color.BLUE);
        tableroLogico[5][1] = new PiezaVampiro(Color.BLUE);
        tableroLogico[5][2] = new PiezaLobo(Color.BLUE);
        tableroLogico[5][3] = new PiezaLobo(Color.BLUE);
        tableroLogico[5][4] = new PiezaVampiro(Color.BLUE);
        tableroLogico[5][5] = new PiezaMuerte(Color.BLUE);
        
        actualizarTableroGUI();
    }
    
    // El método getIconoPieza se mantiene igual, ya que usa el atributo color de la pieza.
    private ImageIcon getIconoPieza(Pieza pieza) {
        if (pieza.color.equals(Color.RED)) {
            switch (pieza.getTipo()) { // Usa getTipo() ya que ahora es privada en Pieza
                case "Muerte": return muerteIcon;
                case "Vampiro": return vampiroIcon;
                case "Lobo": return loboIcon;
            }
        } else if (pieza.color.equals(Color.BLUE)) {
            switch (pieza.getTipo()) {
                case "Muerte": return muerteClaraIcon;
                case "Vampiro": return vampiroClaraIcon;
                case "Lobo": return loboClaraIcon;
            }
        }
        return new ImageIcon();
    }

    // El resto de los métodos de inicialización (actualizarTableroGUI, iniciarComponentes, etc.)
    // se mantienen igual.

    private void actualizarTableroGUI() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton casilla = casillas[row][col];
                Pieza pieza = tableroLogico[row][col];
                
                casilla.setIcon(null);

                if (pieza != null) {
                    ImageIcon icon = getIconoPieza(pieza);
                    casilla.setIcon(icon);
                    // Opcional: Mostrar la vida restante/solidez en el texto para debug
                    // casilla.setText("V:" + pieza.getVida() + "/S:" + pieza.getSolidez());
                    // casilla.setHorizontalTextPosition(SwingConstants.CENTER);
                    // casilla.setVerticalTextPosition(SwingConstants.BOTTOM);
                }
            }
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
        // Asumiendo que "Fondo4.jpg" y "Logo.png" existen
        ImageIcon iconFondo = new ImageIcon("Fondo4.jpg"); 
        JLabel fondo = new JLabel(new ImageIcon(iconFondo.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 700, 700);
        panel.add(fondo);
        panel.setComponentZOrder(fondo, panel.getComponentCount() - 1);

        ImageIcon iconLogo = new ImageIcon("Logo.png");
        JLabel logo = new JLabel(new ImageIcon(iconLogo.getImage().getScaledInstance(200, 130, Image.SCALE_SMOOTH)));
        logo.setBounds(240, 550, 200, 130);
        panel.add(logo);
    }
    

    // --- LÓGICA DE MOVIMIENTO Y COMBATE (ACTUALIZADA) ---
    private void manejarClicCasilla(int targetRow, int targetCol) {
        Pieza piezaEnDestino = tableroLogico[targetRow][targetCol];
        JButton targetCasilla = casillas[targetRow][targetCol];
    
        // 1. Si NO hay pieza seleccionada, intentar seleccionar una
        if (casillaSeleccionada == null) {
            if (piezaEnDestino != null) {
                // 🛑 RESTRICCIÓN DE TURNO: Solo se puede seleccionar la pieza si es tu turno
                if (piezaEnDestino.color.equals(turnoActual)) {
                    seleccionarPieza(targetCasilla, targetRow, targetCol);
                } else {
                    JOptionPane.showMessageDialog(this, "No es el turno del jugador " + (turnoActual.equals(Color.RED) ? "ROJO" : "AZUL"), "Turno Incorrecto", JOptionPane.WARNING_MESSAGE);
                }
            }
        // 2. Ya hay una pieza seleccionada
        } else {
            // A. Clic en la misma pieza: Deseleccionar
            if (targetCasilla == casillaSeleccionada) {
                deseleccionarPieza();
            
            // B. Clic en una casilla diferente: Intentar mover o atacar
            } else {
                Pieza piezaOrigen = tableroLogico[selectedRow][selectedCol];

                // 🛑 VALIDACIÓN FINAL SIMPLIFICADA (Usando el color de resaltado de la GUI)
                Color targetColor = targetCasilla.getBackground();

                if (targetColor.equals(COLOR_MOVIMIENTO_VALIDO)) {
                    // Movimiento a casilla VACÍA
                    realizarMovimiento(targetRow, targetCol, piezaOrigen);
                    cambiarTurno();
                
                } else if (targetColor.equals(COLOR_ATAQUE_VALIDO)) {
                    // Ataque a pieza ENEMIGA
                    // NOTA: piezaEnDestino NUNCA será null si el color es COLOR_ATAQUE_VALIDO
                    realizarAtaque(piezaOrigen, piezaEnDestino, targetRow, targetCol);
                    cambiarTurno();

                } else if (piezaEnDestino != null && piezaEnDestino.color.equals(piezaOrigen.color)) {
                    // Clic en pieza ALIADA: Cambiar selección (sin cambiar turno)
                    deseleccionarPieza(); 
                    seleccionarPieza(targetCasilla, targetRow, targetCol);
                
                } else {
                    // Intento de movimiento o ataque a casilla no válida/aliada/fuera de rango (Color Base)
                    JOptionPane.showMessageDialog(this, "Movimiento ILEGAL: La casilla no está en rango.", "Movimiento Inválido", JOptionPane.ERROR_MESSAGE);
                    deseleccionarPieza();
                }
            }
        }
    }
    
    // --- MÉTODOS AUXILIARES ---
    

    private void seleccionarPieza(JButton casilla, int row, int col) {
        // 1. Limpiar cualquier resaltado anterior (importante si se cambia de selección)
        limpiarResaltados(); 

        // 2. Establecer la nueva selección
        casillaSeleccionada = casilla;
        selectedRow = row;
        selectedCol = col;
    
        // 3. Resaltar la pieza seleccionada y MOSTRAR LOS RANGOS
        casilla.setBorder(new LineBorder(COLOR_SELECCIONADO, 3, true));
    
        // Muestra los rangos de movimiento y ataque
        mostrarMovimientosValidos(row, col); 

        System.out.println("Seleccionada: " + tableroLogico[row][col].getTipo() + " en (" + row + ", " + col + ")");
    }   


    private void deseleccionarPieza() {
        // Usamos el nuevo método de limpieza que hace todo:
        limpiarResaltados(); 
    
        casillaSeleccionada = null;
        selectedRow = -1;
        selectedCol = -1;
        System.out.println("Selección cancelada.");
    }
    
    
    private void realizarMovimiento(int targetRow, int targetCol, Pieza piezaOrigen) {
        // 1. Mueve la pieza en el modelo lógico
        tableroLogico[targetRow][targetCol] = piezaOrigen;
        tableroLogico[selectedRow][selectedCol] = null;
        
        // 2. Deselecciona y actualiza la GUI
        deseleccionarPieza();
        actualizarTableroGUI();
        System.out.println("Movimiento completado a: (" + targetRow + ", " + targetCol + ")");
    }
    
    private void realizarAtaque(Pieza atacante, Pieza defensor, int targetRow, int targetCol) {
        // 1. El atacante hace daño al defensor
        int daño = atacante.getAtaque();
        defensor.recibirDaño(daño);
        
        System.out.println(atacante.getTipo() + " (" + atacante.color.equals(Color.RED) + ") ataca a " + defensor.getTipo() + " (Daño: " + daño + ")");
        System.out.println("Defensor vida/solidez restante: " + defensor.getVida() + "/" + defensor.getSolidez());

        // 2. Si el defensor muere, el atacante ocupa su lugar
        if (!defensor.estaViva()) {
            System.out.println("¡" + defensor.getTipo() + " ha sido derrotado!");
            
            // Mover el atacante a la posición del defensor
            tableroLogico[targetRow][targetCol] = atacante;
            tableroLogico[selectedRow][selectedCol] = null; // Limpiar origen
            
            // Opcional: Implementar lógica de fin de partida aquí
        } else {
            // Si el defensor sobrevive, solo se limpia la selección del atacante (no hay movimiento)
            System.out.println("El defensor sobrevivió al ataque.");
        }
        
        // 3. Deselecciona y actualiza la GUI
        deseleccionarPieza();
        actualizarTableroGUI();
    }
    
    private Color obtenerColorBaseCasilla(int row, int col) {
    Color colorClaro = new Color(200, 180, 150); 
    Color colorOscuro = new Color(60, 30, 30);
    return ((row + col) % 2 == 0) ? colorClaro : colorOscuro;
    }
    
        // --- LÓGICA DE VALIDACIÓN DE RUTA ---
    
    // --- LÓGICA DE VALIDACIÓN DE RUTA (CORREGIDA, con control de límites) ---
    private boolean estaRutaBloqueada(int r1, int c1, int r2, int c2) {
        // 1. Calcula la dirección del movimiento (+1, 0, o -1)
        int dr = Integer.compare(r2, r1); // Dirección en fila
        int dc = Integer.compare(c2, c1); // Dirección en columna

        // Si el movimiento es de una sola casilla (distancia 1), no hay ruta para bloquear.
        if (Math.abs(r1 - r2) <= 1 && Math.abs(c1 - c2) <= 1) {
            return false;
        }
    
        // Inicia en la casilla inmediatamente después del origen
        int currentRow = r1 + dr;
        int currentCol = c1 + dc;

        // 2. Bucle para revisar TODAS las casillas INTERMEDIAS.
        // El bucle se detiene justo ANTES de revisar el destino (r2, c2).
        while (currentRow != r2 || currentCol != c2) { 
        
            // **!!! VERIFICACIÓN DE LÍMITES CRÍTICA !!!**
            // Si por alguna razón el cálculo nos lleva fuera del tablero, debe fallar aquí.
            // Esto es principalmente una defensa contra lógica de movimiento no válida,
            // pero previene el ArrayIndexOutOfBoundsException.
            if (currentRow < 0 || currentRow >= SIZE || currentCol < 0 || currentCol >= SIZE) {
                 // Esto no debería pasar con el movimiento del Lobo (Rango 2) 
                // si la pieza está viva, pero es una buena guardia.
                return false; 
            }

            // 3. Verifica si hay una pieza en la casilla intermedia
            if (tableroLogico[currentRow][currentCol] != null) {
            return true; // Ruta bloqueada
            }

            // 4. Avanza al siguiente paso en la ruta
            currentRow += dr;
            currentCol += dc;
        }

        // Si el bucle terminó sin encontrar piezas, la ruta está libre.
        return false;
    }   
    
        // Limpia todos los resaltados del tablero
    private void limpiarResaltados() {
        // 1. Limpia el borde de la casilla seleccionada
        if (casillaSeleccionada != null) {
        casillaSeleccionada.setBorder(null);
        }
    
        // 2. Restaura el color de fondo de TODAS las casillas
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
            casillas[row][col].setBackground(obtenerColorBaseCasilla(row, col));
            }
        }
    }
    
    private void mostrarMovimientosValidos(int startRow, int startCol) {
        Pieza piezaOrigen = tableroLogico[startRow][startCol];

        for (int targetRow = 0; targetRow < SIZE; targetRow++) {
            for (int targetCol = 0; targetCol < SIZE; targetCol++) {
            
                // No resaltar la casilla de origen
                if (startRow == targetRow && startCol == targetCol) {
                    continue;
                }

                // 1. VALIDACIÓN LÓGICA: ¿Puede la pieza moverse a esa posición?
                if (!piezaOrigen.esMovimientoValido(startRow, startCol, targetRow, targetCol)) {
                    continue;
                }

                // 2. VALIDACIÓN DE BLOQUEO: ¿Hay algo en el camino?
                // Si el movimiento es de más de un paso (como el Lobo), revisamos el bloqueo.
                if ((Math.abs(startRow - targetRow) > 1 || Math.abs(startCol - targetCol) > 1) &&
                    estaRutaBloqueada(startRow, startCol, targetRow, targetCol)) {
                    continue;
                }
            
                // 3. DETERMINAR TIPO DE RESALTADO (Movimiento vs. Ataque)
            
                Pieza piezaEnDestino = tableroLogico[targetRow][targetCol];
                JButton targetCasilla = casillas[targetRow][targetCol];

                if (piezaEnDestino == null) {
                    // Casilla vacía: Movimiento válido
                    targetCasilla.setBackground(COLOR_MOVIMIENTO_VALIDO);
                
                } else if (!piezaEnDestino.color.equals(piezaOrigen.color)) {
                    // Casilla con enemigo: Ataque válido
                    targetCasilla.setBackground(COLOR_ATAQUE_VALIDO);
                
                } 
                // Si la piezaEnDestino es ALIADA, simplemente no se resalta (no es un movimiento/ataque válido).
            }
        }
    }
    
    private void cambiarTurno() {
        turnoActual = turnoActual.equals(Color.RED) ? Color.BLUE : Color.RED;
        System.out.println("\n--- Es el turno del jugador " + (turnoActual.equals(Color.RED) ? "ROJO" : "AZUL") + " ---\n");
    }
}