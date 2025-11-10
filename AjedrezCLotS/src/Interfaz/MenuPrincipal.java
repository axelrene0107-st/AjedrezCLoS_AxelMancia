package Interfaz;

import Datos.Jugador;
import Datos.Jugadores;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author axelr
 */
public final class MenuPrincipal extends JFrame{
    public JPanel panel;//Creamos un objeto panel para añadirle elementos
    Partida ventanaJuego;//JFrame de Partida
    MiCuenta ventanaMC;//JFrame de MiCuenta
    private Jugador jugadorActivo; //Objeto Jugador
    private Jugadores listaJugadores;//Objeto Jugadoes


    
    public MenuPrincipal(Jugador jugadorEncontrado, Jugadores listaJugadores){//Constructor del frame de MenuPrincipal
        this.jugadorActivo = jugadorEncontrado; // guarda referencia del jugador
        this.listaJugadores = listaJugadores;
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Menu de Usuario");
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(500, 500));        
        iniciarComponentes();
    }
    
    public void iniciarComponentes(){//Metodo para inicializar componentes del menu       
        colocarPanels();
        colocarBotones();
        colocarLabels();
    }
    
    private void colocarPanels(){//Metodo para añadir paneles
        panel= new JPanel();//Creo el panel para la ventana de menu
        panel.setLayout(null);//Desactivamos el diseño por defecto
        panel.setBackground(Color.BLACK);//Le asignamos un color
        this.getContentPane().add(panel);//se agrega el panel a la ventana de menu
        
    }    
    
    private void colocarLabels(){//Metodo para añadir 
        if (jugadorActivo != null) {
            JLabel lblJugador = new JLabel("Jugador: " + jugadorActivo.getNombre());
            lblJugador.setForeground(Color.YELLOW);
            lblJugador.setFont(new Font("Perpetua Titling MT", Font.BOLD, 14));
            lblJugador.setBounds(20, 10, 400, 30);
            panel.add(lblJugador);
        }
        
        ImageIcon iconLogo= new ImageIcon("Logo.png");   
        JLabel logo= new JLabel();//Agregamos un label para el fondo
        logo.setBounds(70, 0, 350, 270);
        logo.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(350, 270, Image.SCALE_SMOOTH)));
        panel.add(logo);//Label del fondo
        
        ImageIcon iconFondo= new ImageIcon("Fondo3.jpg");   
        JLabel fondo= new JLabel();//Agregamos un label para el fondo
        fondo.setBounds(0, 0, 500, 500);
        fondo.setIcon(new ImageIcon(iconFondo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        panel.add(fondo);//Label del fondo  
    }
    
    private void colocarBotones(){
        ImageIcon iconJugar= new ImageIcon("BtnJugar.png");
        JButton btnJugar= new JButton(iconJugar);//Creamos boton para iniciar la partida
        btnJugar.setBounds(170, 250, 150, 40);//Le asignamos sus dimensiones y posicion
        btnJugar.setIcon(new ImageIcon(iconJugar.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnJugar.setBorder(null);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setFocusPainted(false);
        btnJugar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnJugar.addActionListener(e -> abrirPartida());//Action listener con lambda "e" parametro de entrada y "->" separador y "abrirPartida()" accion que realiza
        panel.add(btnJugar);
        
        //Creacion de boton Cuenta
        ImageIcon iconCuenta= new ImageIcon("BtnCuenta.png");
        JButton btnCuenta= new JButton();//Creamos boton para iniciar sesion
        btnCuenta.setBounds(170, 300, 150, 40);//Le asignamos sus dimensiones y posicion
        btnCuenta.setIcon(new ImageIcon(iconCuenta.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnCuenta.setBorder(null);
        btnCuenta.setContentAreaFilled(false);
        btnCuenta.setFocusPainted(false);
        btnCuenta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCuenta.addActionListener(e -> abrirMiCuenta());
        panel.add(btnCuenta);
        
        //Creacion del boton salir
        ImageIcon iconSalir= new ImageIcon("btnSalir.png");
        JButton btnSalir= new JButton();//Creamos boton para iniciar sesion
        btnSalir.setBounds(170, 350, 150, 40);//Le asignamos sus dimensiones y posicion
        btnSalir.setIcon(new ImageIcon(iconSalir.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnSalir.setBorder(null);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> confirmarSalida());
        panel.add(btnSalir);
    }
    
    //Metodo para abrir una ventana de Seleccion de Jugador
    private void abrirPartida() {
        SeleccionJugador seleccion = new SeleccionJugador(jugadorActivo, listaJugadores);//Le pasas los parametro para que verifique que hay mas de un jugador
        seleccion.setVisible(true);
    }

    //Metodo para abrir la ventana mi cuenta
    private void abrirMiCuenta() {
        if (ventanaMC == null) {
            ventanaMC = new MiCuenta(jugadorActivo);
        }
        ventanaMC.setVisible(true);
    }

    //Metodo para cerrar ventana de menu principal
    private void confirmarSalida() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar la cuenta?", "Cerrar Sesion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            this.setVisible(false);
        }
    }
}
