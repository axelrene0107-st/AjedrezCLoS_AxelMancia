
package Interfaz;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author axelr
 */
public class Menu extends JFrame{
    public JPanel panel;//Creamos un objeto panel para añadirle elementos
    CrearJugador ventanaCJ;//JFrame de CrearJugador
    LogIn ventanaLI;//JFrame de LogIn
    
    public Menu(){//Constructor del frame de menu
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
        panel.setBackground(Color.white);//Le asignamos un color
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
    }
    
    private void colocarBotones(){
        ImageIcon iconLogIn= new ImageIcon("BtnLogIn.png");
        JButton btnLogIn= new JButton(iconLogIn);//Creamos boton para iniciar sesion
        btnLogIn.setBounds(170, 250, 150, 40);//Le asignamos sus dimensiones y posicion
        btnLogIn.setIcon(new ImageIcon(iconLogIn.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panel.add(btnLogIn);
        //Accion del boton de log in
        ActionListener accion1;
        accion1 = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { 
                ventanaLI= new LogIn(ventanaCJ.getListaJugadores());
                ventanaLI.setVisible(true);
            }
        };
        btnLogIn.addActionListener(accion1);
        
        //Creacion de boton Crear Jugador
        ImageIcon iconCrearJugador= new ImageIcon("BtnCrearJug.png");
        JButton btnCrearJugador= new JButton();//Creamos boton para iniciar sesion
        btnCrearJugador.setBounds(170, 300, 150, 40);//Le asignamos sus dimensiones y posicion
        btnCrearJugador.setIcon(new ImageIcon(iconCrearJugador.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panel.add(btnCrearJugador);
        ventanaCJ= new CrearJugador();
        //Accion del boton crear Jugador
        ActionListener accion2;
        accion2 = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {                             
                ventanaCJ.nombreCuenta.setText("");
                ventanaCJ.contrasena.setText("");
                ventanaCJ.setVisible(true);
            }
        };
        btnCrearJugador.addActionListener(accion2);
        
        //Creacion del boton salir
        ImageIcon iconSalir= new ImageIcon("btnSalir.png");
        JButton btnSalir= new JButton();//Creamos boton para iniciar sesion
        btnSalir.setBounds(170, 350, 150, 40);//Le asignamos sus dimensiones y posicion
        btnSalir.setIcon(new ImageIcon(iconSalir.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panel.add(btnSalir);
        ActionListener accion3;
        accion3 = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        btnSalir.addActionListener(accion3);
    }

    public CrearJugador getVentanaCJ() {
        return ventanaCJ;
    }      
}
