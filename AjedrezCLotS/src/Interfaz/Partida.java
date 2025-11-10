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
    private static final int SIZE = 6;//tamaño
    private static final int TILE_SIZE = 80;//tamaño de las casillas
    private JPanel panel;//El panel principal
    private JButton[][] casillas;//Array de botones que imitan casilals
    private Pieza[][] tableroLogico;//Array de objetos de la clase Pieza    
    private JPanel panelPiezasRojasPerdidas;//Panel para las piezas Negras perdidas
    private JPanel panelPiezasAzulesPerdidas;//Panel para las piezas blancas perdidas
    private JTextArea areaMensajes;//Un textarea para los mensajes
    
    private JButton casillaSeleccionada = null;//Boton para la casilla seleccionada, inicia vacia.
    private int selectedRow = -1;//Las filas son 6x6, pero su seleccion es de 0-5 entonces se les resta uno
    private int selectedCol = -1;//Lo mismo con la columna
    private Color turnoActual = Color.BLUE;//Guarda el turno actual como un color, en este caso es el azul(Blanco) despues el rojo(Negro) 
    private JButton btnRendirse;//Boton de rendicion
    private JLabel barraJugadorBlanco;//Barra para el jugador blanco
    private JLabel barraJugadorNegro;//Barra para el jugador negro
    private String nombreJugadorBlanco;//Nombre del jugador blanco
    private String nombreJugadorNegro;//Nombre del negro

    
    private Ruleta ruleta; //Objeto Ruleta
    private String piezaPermitida = null;//String para el metodo de piezapermitida
    
    //Contadores de giros por jugador
    private int girosDisponiblesRojo = 1;
    private int girosDisponiblesAzul = 1;
    
    //Parte de habilidad especial
    private JButton btnHabilidadEspecial;//Boton para la habilidad especial
    private boolean modoInvocacion = false;//Entre en modo invocacion en el caso de la muerte
    private boolean modoDrenaje = false;//Entra en modo drenaje para el caso del vampiro
    private java.util.List<Point> casillasInvocacion = null;//Lista para las casillas de invocacion
    private java.util.List<Point> casillasDrenaje = null;//Lista para casillas de drenaje

    //Iconos de las piezas negras y blancas
    private ImageIcon muerteIcon;
    private ImageIcon vampiroIcon;
    private ImageIcon loboIcon;
    private ImageIcon zombieIcon;
    private ImageIcon muerteClaraIcon;
    private ImageIcon vampiroClaraIcon;
    private ImageIcon loboClaraIcon;
    private ImageIcon zombieClaraIcon;
    
    //Los colores de movimientos y ataques, seran colores que indicaran las distintas caracteristicas de las piezas
    private static final Color COLOR_MOVIMIENTO_VALIDO = new Color(50, 200, 50, 100); // Verde semi-transparente
    private static final Color COLOR_ATAQUE_VALIDO = new Color(200, 50, 50, 100);    // Rojo semi-transparente
    private static final Color COLOR_ATAQUE_LARGO = new Color(160, 50, 200, 120); // Morado semitransparente
    private static final Color COLOR_SELECCIONADO = Color.YELLOW;

    //Constructor de partida(los parametros son los nombres de los jugadores en la partida)
    public Partida(String nombreJugadorBlanco, String nombreJugadorNegro) {
        this.nombreJugadorBlanco = nombreJugadorBlanco;
        this.nombreJugadorNegro = nombreJugadorNegro;
        //Para la pantalla
        setTitle("Vampire Wargame Chess");
        setSize(950, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(950, 680));
        setLayout(null);
        //Matriz de objetos Pieza
        tableroLogico = new Pieza[SIZE][SIZE];

        //Metodos que se cargan por predeterminado
        cargarIconosDePiezas();//Carga de los iconos
        iniciarComponentes();//Inicia los componentes graficos 
        colocarPiezasIniciales(); //Coloca las piezas
        ruleta = new Ruleta(this);//Crea un nuevo dialog Ruleta
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                iniciarPrimerTurnoConSorteo();//Listener para la ventana lo que hace que inicie este metodo justo cuando se llama la ventana partida
            }
        });

 
    }
    
    private void cargarIconosDePiezas() {//El metodo para cargar las imagenes como iconos para las piezas
        int iconSize = TILE_SIZE - 10;//Tamaño de los iconos de las piezas

        //asignacion de valores para cada uno de los iconos con el metodo cargarYredimensionarIcono; este devuelve un icon
        muerteIcon = cargarYRedimensionarIcono("Muerte.png", iconSize);
        vampiroIcon = cargarYRedimensionarIcono("Vampiro.png", iconSize);
        loboIcon = cargarYRedimensionarIcono("Lobo.png", iconSize);
        zombieIcon = cargarYRedimensionarIcono("Zombie.png", iconSize); 
        
        muerteClaraIcon = cargarYRedimensionarIcono("Muerte_Blanco.png", iconSize);
        vampiroClaraIcon = cargarYRedimensionarIcono("Vampiro_Blanco.png", iconSize);
        loboClaraIcon = cargarYRedimensionarIcono("Lobo_Blanco.png", iconSize);
        zombieClaraIcon = cargarYRedimensionarIcono("Zombie_Blanco.png", iconSize); 
    }

    //Metodo par redimensionar, tiene como parametros un string con el nombre del archivo y su tamaño, por predeterminado es iconSize
    private ImageIcon cargarYRedimensionarIcono(String fileName, int size) {
        ImageIcon originalIcon = null;//Inicia originalIcon como null
        try {
            URL url = Partida.class.getResource("/" + fileName);//Busca el archivo desde la raiz
            if (url != null) {//Si lo encuentra
                originalIcon = new ImageIcon(url);//Asigna esa ubicacion al icono
            } else {
                originalIcon = new ImageIcon(fileName);//Si no la encuantra entonces le asigna el String del nombre
            }

            if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {//If por si no la encuentra
                 throw new Exception("Error al cargar la imagen: " + fileName);//Tira el exception para ese error
            }

            Image img = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);//Objeto imagen que recoge parametros para una imagen
            return new ImageIcon(img);//retorna un nuevo incono con los parametros del objeto img
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo cargar el ícono de la pieza " + fileName + ". " + e.getMessage());//Caulquier otr excepcion, tira el error.
            return new ImageIcon();//Devuelve un imageicon vacio
        }
    }

    //Metodo para colocar las piezas en el orden correcto Lobo-Vampiro-Muerte-Muerte-Vampiro-Lobo
    private void colocarPiezasIniciales() {
        tableroLogico = new Pieza[SIZE][SIZE];//Se instancia la matriz de objetos pieza

        // JUGADOR ROJO(NEGRO) (6 piezas: 2 Muerte, 2 Vampiro, 2 Lobo)
        // Fila 0 (arriba)
        tableroLogico[0][0] = new PiezaLobo(Color.RED);
        tableroLogico[0][1] = new PiezaVampiro(Color.RED);
        tableroLogico[0][2] = new PiezaMuerte(Color.RED);
        tableroLogico[0][3] = new PiezaMuerte(Color.RED);
        tableroLogico[0][4] = new PiezaVampiro(Color.RED);
        tableroLogico[0][5] = new PiezaLobo(Color.RED);
        
        // 🔵 JUGADOR AZUL(BLANCO)
        // Fila 5 (Abajo)
        tableroLogico[5][0] = new PiezaLobo(Color.BLUE);
        tableroLogico[5][1] = new PiezaVampiro(Color.BLUE);
        tableroLogico[5][2] = new PiezaMuerte(Color.BLUE);
        tableroLogico[5][3] = new PiezaMuerte(Color.BLUE);
        tableroLogico[5][4] = new PiezaVampiro(Color.BLUE);
        tableroLogico[5][5] = new PiezaLobo(Color.BLUE);
        
        //Metodo para actualizar el Tablero
        actualizarTableroGUI();
    }
    
    //Funcion para conseguir un objeto ImageIcon, tiene un parametro de objeto Pieza
    private ImageIcon getIconoPieza(Pieza pieza) {
        if (pieza.color.equals(Color.RED)) {//Si el color de la pieza obtenida es ihual a rojo(Negro)
            switch (pieza.getTipo()) { //Switch para obtener pieza y retornar dependiendo de cual sea el objeto que seleccione
                case "Muerte": return muerteIcon;
                case "Vampiro": return vampiroIcon;
                case "Lobo": return loboIcon;
                case "Zombie": return zombieIcon; 
            }
        } else if (pieza.color.equals(Color.BLUE)) {//Lo mismo pero si es el azul(Blanco)
            switch (pieza.getTipo()) {
                case "Muerte": return muerteClaraIcon;
                case "Vampiro": return vampiroClaraIcon;
                case "Lobo": return loboClaraIcon;
                case "Zombie": return zombieClaraIcon; // 🧟‍♀️
            }
        }
        return new ImageIcon();//Retorna el objeto icono
    }

    //Metodo para actualizar tablero
    private void actualizarTableroGUI() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton casilla = casillas[row][col];
                Pieza pieza = tableroLogico[row][col];
                casilla.setIcon(null);//Vacia la casilla en la que estaba la pieza

                if (pieza != null) {//Si el objeto pieza es vacio entonces obtiene la icono
                    ImageIcon icon = getIconoPieza(pieza);
                    casilla.setIcon(icon);
                }
            }
        }
    }
    
    private void iniciarComponentes() {
        colocarPanel();
        colocarBotonRendicion(); 
        colocarBotonHabilidad();
        colocarPanelesPiezasPerdidas();
        colocarAreaMensajes();
        colocarFondo();  
        colocarTablero();  

        
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, 950, 680);
        panel.setLayout(null);
        add(panel);
    }
    
    private void colocarPanelesPiezasPerdidas() {
        // Panel izquierdo (jugador azul / blancas)
        panelPiezasAzulesPerdidas = new JPanel();
        panelPiezasAzulesPerdidas.setLayout(new GridLayout(6, 1, 5, 5));
        panelPiezasAzulesPerdidas.setBackground(new Color(30, 30, 60));
        panelPiezasAzulesPerdidas.setBounds(30, 130, 100, 450);
        panel.add(panelPiezasAzulesPerdidas);

        // Panel derecho (jugador rojo / negras)
        panelPiezasRojasPerdidas = new JPanel();
        panelPiezasRojasPerdidas.setLayout(new GridLayout(6, 1, 5, 5));
        panelPiezasRojasPerdidas.setBackground(new Color(60, 20, 20));
        panelPiezasRojasPerdidas.setBounds(820, 130, 100, 450);
        panel.add(panelPiezasRojasPerdidas);
    }


    private void colocarTablero() {
        int boardSize = TILE_SIZE * SIZE;
        int posX = (950 - boardSize) / 2;
        int posY = (800 - boardSize) / 2 - 60;


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

    private void colocarFondo() {   
        ImageIcon iconFondo = new ImageIcon("Fondo4.jpg"); 
        JLabel fondo = new JLabel(new ImageIcon(iconFondo.getImage().getScaledInstance(950, 800, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 950, 800);
        panel.add(fondo);
        panel.setComponentZOrder(fondo, panel.getComponentCount() - 1);
    }
    
    private void colocarBotonHabilidad() {
        ImageIcon iconBtnEspecial= new ImageIcon("BtnEspecial.png");
        btnHabilidadEspecial= new JButton(iconBtnEspecial);//Creamos boton para iniciar sesion
        btnHabilidadEspecial.setBounds(270, 18, 200, 80);//Le asignamos sus posisicion y dimension
        btnHabilidadEspecial.setIcon(new ImageIcon(iconBtnEspecial.getImage().getScaledInstance(270, 190, Image.SCALE_SMOOTH)));
        btnHabilidadEspecial.setBorder(null);
        btnHabilidadEspecial.setContentAreaFilled(false);
        btnHabilidadEspecial.setFocusPainted(false);
        btnHabilidadEspecial.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHabilidadEspecial.addActionListener(e -> activarHabilidadEspecial());
        btnHabilidadEspecial.setEnabled(false);
        panel.add(btnHabilidadEspecial);
    }
    
    private void colocarBotonRendicion() {       
        ImageIcon iconBtnRendirse= new ImageIcon("BtnRendirse.png");
        btnRendirse= new JButton(iconBtnRendirse);//Creamos boton para iniciar sesion
        btnRendirse.setBounds(480, 20, 200, 80);//Le asignamos sus posisicion y dimension
        btnRendirse.setIcon(new ImageIcon(iconBtnRendirse.getImage().getScaledInstance(270, 220, Image.SCALE_SMOOTH)));
        btnRendirse.setBorder(null);
        btnRendirse.setContentAreaFilled(false);
        btnRendirse.setFocusPainted(false);
        btnRendirse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRendirse.addActionListener(e -> rendirse());
        panel.add(btnRendirse);
    }

    private void colocarAreaMensajes() {
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaMensajes.setForeground(Color.YELLOW);
        areaMensajes.setBackground(new Color(60, 20, 20));
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaMensajes);
        scroll.setBounds(0, 0, 230, 120);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
            "MENSAJES",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Perpetua Titling MT", Font.BOLD, 13),
            Color.BLACK
            ));

        panel.add(scroll);
    }
    

    
    private void registrarMensaje(String mensaje) {
        areaMensajes.append(mensaje + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }


    

    // --- LÓGICA DE MOVIMIENTO Y COMBATE (ACTUALIZADA) ---
    private void manejarClicCasilla(int targetRow, int targetCol) {
        if (modoInvocacion && casillasInvocacion != null) {
            for (Point p : casillasInvocacion) {
                if (p.x == targetRow && p.y == targetCol) {
                    Pieza pieza = tableroLogico[selectedRow][selectedCol];
                    colocarZombie(pieza, targetRow, targetCol);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Selecciona una casilla verde válida para colocar el zombie.");
            return;
        }
        
        // 🩸 Si estamos en modo drenaje del Vampiro
        if (modoDrenaje && casillasDrenaje != null) {
            for (Point p : casillasDrenaje) {
                if (p.x == targetRow && p.y == targetCol) {
                    Pieza vampiro = tableroLogico[selectedRow][selectedCol];
                    Pieza enemigo = tableroLogico[p.x][p.y];

                    vampiro.usarHabilidadEspecial(enemigo);
                    JOptionPane.showMessageDialog(this,
                        "🩸 El Vampiro drenó vida de " + enemigo.getTipo() + "!");

                    deseleccionarPieza();
                    limpiarResaltados();
                    modoDrenaje = false;
                    casillasDrenaje = null;

                    actualizarTableroGUI();
                    verificarFinDeJuego();
                    cambiarTurno();
                    return;
                }   
            }

            JOptionPane.showMessageDialog(this, "Selecciona una casilla morada válida para drenar.");
            return;
        }

        
        Pieza piezaEnDestino = tableroLogico[targetRow][targetCol];
        JButton targetCasilla = casillas[targetRow][targetCol];
    
        // 1. Si NO hay pieza seleccionada, intentar seleccionar una
        if (casillaSeleccionada == null) {
            if (piezaEnDestino != null) {
                // 🛑 RESTRICCIÓN DE TURNO: Solo se puede seleccionar la pieza si es tu turno
                if (piezaEnDestino.color.equals(turnoActual)) {
                
                    // 🛑 RESTRICCIÓN DE RULETA: Solo permite seleccionar la pieza sorteada
                    if (!piezaEnDestino.getTipo().equals(piezaPermitida)) {
                        JOptionPane.showMessageDialog(this, "¡Debes seleccionar una pieza de tipo " + piezaPermitida + "!", "Pieza Restringida", JOptionPane.WARNING_MESSAGE);
                        return; // No permite la selección
                    }

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
                
                } else if (targetColor.equals(COLOR_ATAQUE_VALIDO)|| (piezaOrigen.getTipo().equals("Muerte") && targetCasilla.getBackground().equals(new Color(180, 0, 180, 120)))) {
                    // 🔮 Determinar daño según tipo de ataque
                    int daño = piezaOrigen.getAtaque();
                    
                    if (piezaOrigen.getTipo().equals("Muerte") && targetCasilla.getBackground().equals(new Color(180, 0, 180, 120))) {
                        daño = 2; // daño reducido
                    }

                    realizarAtaquePersonalizado(piezaOrigen, piezaEnDestino, targetRow, targetCol, daño);
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
        registrarMensaje("Seleccionada: " + tableroLogico[row][col].getTipo() + " en (" + row + ", " + col + ")");
        
        btnHabilidadEspecial.setEnabled(tableroLogico[row][col].puedeUsarHabilidadEspecial());

    }   


    private void deseleccionarPieza() {
        // Usamos el nuevo método de limpieza que hace todo:
        limpiarResaltados(); 
    
        casillaSeleccionada = null;
        selectedRow = -1;
        selectedCol = -1;
        registrarMensaje("Selección cancelada.");   
        btnHabilidadEspecial.setEnabled(false);

    }
    
    
    private void realizarMovimiento(int targetRow, int targetCol, Pieza piezaOrigen) {
        // 1. Mueve la pieza en el modelo lógico
        tableroLogico[targetRow][targetCol] = piezaOrigen;
        tableroLogico[selectedRow][selectedCol] = null;
        
        // 2. Deselecciona y actualiza la GUI
        deseleccionarPieza();
        actualizarTableroGUI();
        registrarMensaje("Movimiento completado a: (" + targetRow + ", " + targetCol + ")");
    }
    
    private void realizarAtaque(Pieza atacante, Pieza defensor, int targetRow, int targetCol) {
        int dr = Math.abs(selectedRow - targetRow);
        int dc = Math.abs(selectedCol - targetCol);

        int daño = atacante.getAtaque();

        // 💀 Si la pieza es Muerte y el ataque es a distancia (2 casillas)
        if (atacante.getTipo().equals("Muerte") && (dr == 2 || dc == 2)) {
            daño = 2; // Daño reducido por ataque mágico largo
            registrarMensaje("⚡ Ataque mágico a distancia (daño reducido a 2).");
        }

        // Aplica el daño
        defensor.recibirDaño(daño);

        registrarMensaje(atacante.getTipo() + " (" + (atacante.color.equals(Color.RED) ? "NEGRO" : "BLANCO") + ") ataca a " + defensor.getTipo() + " (Daño: " + daño + ")");
        registrarMensaje("Defensor vida/solidez restante: " + defensor.getVida() + "/" + defensor.getSolidez());

        // Si el defensor muere, el atacante ocupa su lugar
        if (!defensor.estaViva()) {
            System.out.println(defensor.getTipo() + " ha sido derrotado!");
            tableroLogico[targetRow][targetCol] = atacante;
            tableroLogico[selectedRow][selectedCol] = null;
        } else {
            registrarMensaje("El defensor sobrevivió al ataque.");
        }

        deseleccionarPieza();
        actualizarTableroGUI();
        verificarFinDeJuego();
    }
    
    private void realizarAtaquePersonalizado(Pieza atacante, Pieza defensor, int targetRow, int targetCol, int daño) {
        defensor.recibirDaño(daño);

        registrarMensaje(atacante.getTipo() + " realiza ataque (" + daño + " daño) sobre " + defensor.getTipo());

        if (!defensor.estaViva()) {
            registrarMensaje(defensor.getTipo() + " ha sido derrotado.");
            tableroLogico[targetRow][targetCol] = atacante;
            tableroLogico[selectedRow][selectedCol] = null;
            registrarPiezaPerdida(defensor);
        }

        deseleccionarPieza();
        actualizarTableroGUI();
        verificarFinDeJuego();
    }

    
    private Color obtenerColorBaseCasilla(int row, int col) {
        Color colorClaro = new Color(200, 180, 150); 
        Color colorOscuro = new Color(60, 30, 30);
        return ((row + col) % 2 == 0) ? colorClaro : colorOscuro;
    }
    
        // --- LÓGICA DE VALIDACIÓN DE RUTA ---
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
                    int dr = Math.abs(startRow - targetRow);
                    int dc = Math.abs(startCol - targetCol);

                    // 🩸 Cuerpo a cuerpo (todas las piezas)
                    if (dr <= 1 && dc <= 1) {
                        targetCasilla.setBackground(COLOR_ATAQUE_VALIDO);
                    }
                    // 💀 Ataque mágico a distancia (solo la Muerte)
                    else if (piezaOrigen.getTipo().equals("Muerte") && (dr == 2 || dc == 2 || dr == dc && dr == 2)) {
                    // color morado semitransparente
                    targetCasilla.setBackground(new Color(180, 0, 180, 120));
                    }
                }
            }
        }
    }
    
    private void activarHabilidadEspecial() {
        if (casillaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una pieza primero.");
            return;
        }

        Pieza pieza = tableroLogico[selectedRow][selectedCol];

        if (!pieza.color.equals(turnoActual)) {
            JOptionPane.showMessageDialog(this, "No puedes usar habilidades con piezas enemigas.");
            return;
        }

        if (!pieza.puedeUsarHabilidadEspecial()) {
            JOptionPane.showMessageDialog(this, pieza.getTipo() + " no tiene habilidad especial.");
            return;
        }

        // 🔹 Si la pieza es una Muerte, intenta invocar un zombie
        if (pieza instanceof Datos.PiezaMuerte) {
            invocarZombie(pieza);
            return;
        }

        // 🔹 Si es Vampiro: mostrar posibles enemigos para drenar
        if (pieza instanceof Datos.PiezaVampiro) {
            mostrarOpcionesDrenaje(pieza);
            return;
        }
        JOptionPane.showMessageDialog(this, "No hay enemigos en rango para usar la habilidad.");
    }
    
    private void invocarZombie(Pieza muerte) {
        java.util.List<Point> opciones = new java.util.ArrayList<>();

        for (int r = selectedRow - 1; r <= selectedRow + 1; r++) {
            for (int c = selectedCol - 1; c <= selectedCol + 1; c++) {
                if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && tableroLogico[r][c] == null) {
                    opciones.add(new Point(r, c));
                }
            }
        }

        if (opciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay espacio libre para invocar un Zombie.");
            return;
        }

        // 🔹 Limpiar antes de resaltar (por si se usa de nuevo en el mismo turno)
        limpiarResaltados();

        // 🔹 Resalta las casillas disponibles
        for (Point p : opciones) {
            casillas[p.x][p.y].setBackground(new Color(100, 220, 100, 180));
        }

        JOptionPane.showMessageDialog(this,
            "Selecciona una casilla verde para invocar el Zombie.",
            "Invocar Zombie", JOptionPane.INFORMATION_MESSAGE);

        // 🔹 Activar modo invocación
        modoInvocacion = true;
        casillasInvocacion = opciones;
    }


    private void colocarZombie(Pieza muerte, int fila, int col) {
        limpiarResaltados();

        tableroLogico[fila][col] = new Datos.PiezaZombie(muerte.color);
        actualizarTableroGUI();

        JOptionPane.showMessageDialog(this,
            "☠️ La Muerte ha invocado un Zombie en (" + fila + ", " + col + ")");
        
        registrarMensaje("☠️ La Muerte ha invocado un Zombie en (" + fila + ", " + col + ")");

        deseleccionarPieza();
        modoInvocacion = false;
        casillasInvocacion = null;
        cambiarTurno();
    }

    
    private int contarPiezas(Color colorJugador) {
        int contador = 0;
        for (int fila = 0; fila < SIZE; fila++) {
            for (int col = 0; col < SIZE; col++) {
                Pieza p = tableroLogico[fila][col];
                if (p != null && p.color.equals(colorJugador)) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    private void mostrarOpcionesDrenaje(Pieza vampiro) {
        java.util.List<Point> opciones = new java.util.ArrayList<>();

        // Buscar enemigos adyacentes
        for (int r = selectedRow - 1; r <= selectedRow + 1; r++) {
            for (int c = selectedCol - 1; c <= selectedCol + 1; c++) {
                if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                    Pieza enemigo = tableroLogico[r][c];
                    if (enemigo != null && !enemigo.color.equals(vampiro.color)) {
                        opciones.add(new Point(r, c));
                    }
                }
            }
        }

        if (opciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay enemigos cerca para drenar.");
            return;
        }

        // 🔮 Resaltar enemigos con color morado
        limpiarResaltados();
        for (Point p : opciones) {
            casillas[p.x][p.y].setBackground(new Color(180, 0, 180, 150));
        }

        JOptionPane.showMessageDialog(this,
            "Selecciona una casilla morada para drenar vida.",
            "Drenar Sangre", JOptionPane.INFORMATION_MESSAGE);

        modoDrenaje = true;
        casillasDrenaje = opciones;
    }

    
    private int calcularGirosDisponibles(Color colorJugador) {
        int piezasIniciales = 6;
        int piezasActuales = contarPiezas(colorJugador);
        int piezasPerdidas = piezasIniciales - piezasActuales;

        // 1 giro mínimo, y +1 por cada 2 piezas perdidas
        int giros = 1 + (piezasPerdidas / 2);
        return Math.min(giros, 3); // Máximo 3 giros
    }
    
    // --- En Partida.java: Métodos ---

    private void iniciarPrimerTurnoConSorteo() {
        registrarMensaje("\n--- Es el turno del jugador BLANCO ---");
    
        // El método girar() BLOQUEA la ejecución hasta que el JDialog se cierra
        piezaPermitida = ruleta.girar(); 
    
        registrarMensaje("Pieza obligatoria para mover: " + piezaPermitida + "\n");
    }

    private void cambiarTurno() {
        // Cambiar turno
        turnoActual = turnoActual.equals(Color.RED) ? Color.BLUE : Color.RED;
        registrarMensaje("\n--- Es el turno del jugador " + (turnoActual.equals(Color.RED) ? "NEGRO" : "BLANCO") + " ---");

        // Calcular giros disponibles
        int girosDisponibles = calcularGirosDisponibles(turnoActual);

        // Mostrar al jugador
        registrarMensaje("El jugador tiene " + girosDisponibles + " oportunidad(es) de giro de ruleta.");

        // Intentar girar ruleta según las oportunidades disponibles
        boolean piezaValidaEncontrada = false;

        for (int intento = 1; intento <= girosDisponibles; intento++) {
            registrarMensaje("Intento #" + intento);
            String piezaSorteada = ruleta.girar();

            if (tienePiezaDisponible(turnoActual, piezaSorteada)) {
                piezaPermitida = piezaSorteada;
                piezaValidaEncontrada = true;
                break;
            } else {
                registrarMensaje("⚠️ El jugador no tiene piezas del tipo " + piezaSorteada + ". Se vuelve a girar...");
            }
        }

        if (!piezaValidaEncontrada) {
            // Si después de todos los giros no hay pieza válida, pierde el turno
            piezaPermitida = null;
            JOptionPane.showMessageDialog(this,
                    "El jugador " + (turnoActual.equals(Color.RED) ? "NEGRO" : "BLANCO")
                    + " no tiene piezas sorteadas disponibles. Pierde el turno.",
                    "Turno perdido", JOptionPane.INFORMATION_MESSAGE);

            cambiarTurno(); // Pasa directamente al otro jugador
        } else {
            registrarMensaje("Pieza obligatoria para mover: " + piezaPermitida);
        }
    }
    
    private boolean tienePiezaDisponible(Color colorJugador, String tipo) {
        for (int fila = 0; fila < SIZE; fila++) {
            for (int col = 0; col < SIZE; col++) {
                Pieza p = tableroLogico[fila][col];
                if (p != null && p.color.equals(colorJugador) && p.getTipo().equals(tipo)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void registrarPiezaPerdida(Pieza piezaPerdida) {
        JLabel icono = new JLabel();
        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setIcon(getIconoPieza(piezaPerdida));

        if (piezaPerdida.color.equals(Color.RED)) {
            panelPiezasRojasPerdidas.add(icono);
        } else if (piezaPerdida.color.equals(Color.BLUE)) {
            panelPiezasAzulesPerdidas.add(icono);
        }

        // Refrescar interfaz
        panelPiezasRojasPerdidas.revalidate();
        panelPiezasRojasPerdidas.repaint();
        panelPiezasAzulesPerdidas.revalidate();
        panelPiezasAzulesPerdidas.repaint();
    }

    
    private void verificarFinDeJuego() {
        int piezasRojas = contarPiezas(Color.RED);
        int piezasAzules = contarPiezas(Color.BLUE);

        if (piezasRojas == 0 || piezasAzules == 0) {
            String ganador;

            if (piezasRojas == 0 && piezasAzules == 0) {
                ganador = "Empate — ambos jugadores perdieron todas sus piezas.";
            } else if (piezasRojas == 0) {
                ganador = "¡El jugador BLANCO ha ganado!";
            } else {
                ganador = "¡El jugador NEGRO ha ganado!";
            }

            // Mostrar mensaje final
            JOptionPane.showMessageDialog(this, ganador, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);

            // Preguntar si se desea reiniciar o salir
            int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas jugar otra partida?", "Juego terminado", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                reiniciarPartida();
            } else {
                System.exit(0);
            }
        }
    }
    
    private void reiniciarPartida() {
        registrarMensaje("\n🔁 Reiniciando partida...");
        
        panelPiezasRojasPerdidas.removeAll();
        panelPiezasAzulesPerdidas.removeAll();
        panelPiezasRojasPerdidas.revalidate();
        panelPiezasRojasPerdidas.repaint();
        panelPiezasAzulesPerdidas.revalidate();
        panelPiezasAzulesPerdidas.repaint();


        // Reinicia la lógica del tablero
        tableroLogico = new Pieza[SIZE][SIZE];
        colocarPiezasIniciales();

        // Reinicia variables de control
        turnoActual = Color.BLUE;
        piezaPermitida = null;

        // Recalcular giros
        girosDisponiblesRojo = 1;
        girosDisponiblesAzul = 1;

        actualizarTableroGUI();
        repaint();

        // Inicia de nuevo el sorteo
        iniciarPrimerTurnoConSorteo();
    }

    private void rendirse() {
        String jugador = (turnoActual.equals(Color.RED)) ? "NEGRO" : "BLANCO";
        String ganador = (turnoActual.equals(Color.RED)) ? "BLANCO" : "NEGRO";

        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas rendirte?\nEl jugador " + ganador + " será declarado ganador.", "Confirmar Rendición", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "El jugador " + jugador + " se ha rendido.\n¡El jugador " + ganador + " gana la partida!","Fin del juego", JOptionPane.INFORMATION_MESSAGE);

            int nuevaPartida = JOptionPane.showConfirmDialog(this, "¿Deseas iniciar una nueva partida?", "Reiniciar", JOptionPane.YES_NO_OPTION);

            if (nuevaPartida == JOptionPane.YES_OPTION) {
                reiniciarPartida();
            } else {
                this.setVisible(false);
            }
        }
    }

}