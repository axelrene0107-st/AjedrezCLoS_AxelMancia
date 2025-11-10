
package Interfaz;
import Datos.Jugadores;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author axelr
 */
public class Menu extends JFrame{
    public JPanel panel;//Creamos un objeto panel para añadirle elementos
    CrearJugador ventanaCJ;//JFrame de CrearJugador
    LogIn ventanaLI;//JFrame de LogIn
    private Jugadores listaGlobal = new Jugadores();

    
    public Menu(){//Constructor del frame de menu
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Menu de Usuario");
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(500, 500));       
        iniciarComponentes();
        Jugadores listaGlobal = new Jugadores();
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
        ImageIcon iconLogo= new ImageIcon("Logo.png");   
        JLabel logo= new JLabel();//Agregamos un label para el fondo
        logo.setBounds(70, 0, 350, 270);
        logo.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(350, 270, Image.SCALE_SMOOTH)));
        panel.add(logo);//Label del fondo
        
        ImageIcon iconFondo= new ImageIcon("FondoCastlevania.jpg");   
        JLabel fondo= new JLabel();//Agregamos un label para el fondo
        fondo.setBounds(0, 0, 500, 500);
        fondo.setIcon(new ImageIcon(iconFondo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        panel.add(fondo);//Label del fondo  
        panel.setComponentZOrder(fondo, panel.getComponentCount() - 1);

    }
    
    private void colocarBotones(){
        ImageIcon iconLogIn= new ImageIcon("BtnLogIn.png");
        JButton btnLogIn= new JButton(iconLogIn);//Creamos boton para iniciar sesion
        btnLogIn.setBounds(170, 250, 150, 40);//Le asignamos sus dimensiones y posicion
        btnLogIn.setIcon(new ImageIcon(iconLogIn.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        //Accion del boton de log in
        btnLogIn.addActionListener(e -> {
            if (ventanaCJ != null && ventanaCJ.getListaJugadores() != null) {
                ventanaLI = new LogIn(listaGlobal);
                ventanaLI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Primero debes crear al menos un jugador.",
                        "Sin jugadores registrados",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(btnLogIn);
        
        //Creacion de boton Crear Jugador
        ImageIcon iconCrearJugador= new ImageIcon("BtnCrearJug.png");
        JButton btnCrearJugador= new JButton();//Creamos boton para iniciar sesion
        btnCrearJugador.setBounds(170, 300, 150, 40);//Le asignamos sus dimensiones y posicion
        btnCrearJugador.setIcon(new ImageIcon(iconCrearJugador.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnCrearJugador.addActionListener(e -> {
            if (ventanaCJ == null) {
                ventanaCJ = new CrearJugador(listaGlobal);
            }
            ventanaCJ.setVisible(true);
        });
        panel.add(btnCrearJugador);
        
        //Creacion del boton salir
        ImageIcon iconSalir= new ImageIcon("btnSalir.png");
        JButton btnSalir= new JButton();//Creamos boton para iniciar sesion
        btnSalir.setBounds(170, 350, 150, 40);//Le asignamos sus dimensiones y posicion
        btnSalir.setIcon(new ImageIcon(iconSalir.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnSalir.addActionListener(e -> System.exit(0));
        panel.add(btnSalir);
    }

    public CrearJugador getVentanaCJ() {
        return ventanaCJ;
    }      
}
