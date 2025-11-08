//Jframe de LogIn
package Interfaz;

import Datos.Jugador;
import Datos.Jugadores;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author axelr
 */
public class LogIn extends JFrame implements ActionListener{
public JPanel panelLI;
public Jugadores listaJ;
JTextField nombreCuenta;
JPasswordField contrasena;
    
    public LogIn(Jugadores listaJ){
        this.listaJ=listaJ;
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Inicio de Sesion");
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(500, 500));
        iniciarComponentes();
    }
    
    public void iniciarComponentes(){//Metodo para inicializar componentes del menu 
        colocarPanels();
        colocarBotones();
        colocarJTextField();
        colocarPassword();
        colocarLabels();       
    }
    
    private void colocarPanels(){//Metodo para añadir paneles
        panelLI= new JPanel();//Creo el panel para la ventana de menu
        panelLI.setLayout(null);//Desactivamos el diseño por defecto
        panelLI.setBackground(Color.white);//Le asignamos un color
        this.getContentPane().add(panelLI);//se agrega el panel a la ventana 
        
    } 
            
    private void colocarLabels(){//Metodo para añadir 
        ImageIcon iconLogo= new ImageIcon("Logo.png");   
        JLabel logo= new JLabel();//Agregamos un label para el fondo
        logo.setBounds(117, 0, 250, 170);
        logo.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(250, 170, Image.SCALE_SMOOTH)));
        panelLI.add(logo);//Label del fondo
        
        JLabel etiquetaNombre= new JLabel("Nombre:");
        Font fuente= new Font("Perpetua Titling MT", 1, 12);
        etiquetaNombre.setFont(fuente);
        etiquetaNombre.setBounds(105, 228, 70, 50);
        etiquetaNombre.setForeground(Color.YELLOW);
        panelLI.add(etiquetaNombre);
        
        JLabel etiquetaContrasena= new JLabel("Password:");
        Font fuente2= new Font("Perpetua Titling MT", 1, 9);
        etiquetaContrasena.setFont(fuente2);
        etiquetaContrasena.setBounds(107, 260, 70, 50);
        etiquetaContrasena.setForeground(Color.YELLOW);
        panelLI.add(etiquetaContrasena);
        
        ImageIcon iconEspacio= new ImageIcon("txtEspacio.png");   
        JLabel espacio= new JLabel();//Agregamos un label para el fondo
        espacio.setBounds(0, 90, 480, 350);
        espacio.setIcon(new ImageIcon(iconEspacio.getImage().getScaledInstance(480, 350, Image.SCALE_SMOOTH)));
        panelLI.add(espacio);
        
        ImageIcon iconFondo= new ImageIcon("fondo2.jpg");   
        JLabel fondo= new JLabel();//Agregamos un label para el fondo
        fondo.setBounds(0, 0, 500, 500);
        fondo.setIcon(new ImageIcon(iconFondo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        panelLI.add(fondo);//Label del fondo  

        
    }
    
     private void colocarBotones(){
        //Boton LogIn
        ImageIcon iconLogIn= new ImageIcon("BtnLogIn.png");
        JButton btnLogIn= new JButton(iconLogIn);//Creamos boton para iniciar sesion
        btnLogIn.setBounds(300, 400, 150, 40);//Le asignamos sus posisicion y dimension
        btnLogIn.setIcon(new ImageIcon(iconLogIn.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panelLI.add(btnLogIn);      
        //Accion del boton de log in
        ActionListener accion1= new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal menuPa= new MenuPrincipal();
                LogIn.this.setVisible(false);
                menuPa.setVisible(true);
                if(nombreCuenta.getText().isBlank()==true){
                    JOptionPane.showMessageDialog(null, "Favor escriba un nombre.", "Error con Nombre", JOptionPane.WARNING_MESSAGE);
                }else if(contrasena.getPassword().length==0){
                    JOptionPane.showMessageDialog(null, "Favor escriba la contraseña.", "Error con Contraseña", JOptionPane.WARNING_MESSAGE);
                }else if(listaJ.revisarNombres(nombreCuenta.getText())==false){//Revisa si el nombre escrito ya existe
                    JOptionPane.showMessageDialog(null, "Ese nombre no esta registrado.", "Error con Nombre", JOptionPane.WARNING_MESSAGE);
                }else if(listaJ.revisarPassword(contrasena.getPassword())==false){
                    JOptionPane.showMessageDialog(null, "La contraseña ingresada es incorrecta", "Error con Contrseña", JOptionPane.WARNING_MESSAGE);
                }else{
                    MenuPrincipal menuP= new MenuPrincipal();
                    LogIn.this.setVisible(false);
                    menuP.setVisible(true);
                }
            }  
        };
        btnLogIn.addActionListener(accion1);
        
        //Creacion del boton salir
        ImageIcon iconSalir= new ImageIcon("btnSalir.png");
        JButton btnSalir= new JButton();//Creamos boton para iniciar sesion
        btnSalir.setBounds(140, 400, 150, 40);//Le asignamos sus posicion y dimensiones
        btnSalir.setIcon(new ImageIcon(iconSalir.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panelLI.add(btnSalir);
        //Accion del boton salir
         ActionListener accion2= new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LogIn.this.setVisible(false);
            }
        };       
        btnSalir.addActionListener(accion2);
     }
    
    
    private void colocarJTextField(){
        nombreCuenta= new JTextField(20);
        Font fuenteTexto= new Font("Perpetua Titling MT", 1, 9);
        nombreCuenta.setFont(fuenteTexto);
        nombreCuenta.setForeground(Color.YELLOW);
        nombreCuenta.setBackground(Color.DARK_GRAY);
        nombreCuenta.setText("Ingrese el nombre de la cuenta....");
        nombreCuenta.setBounds(170, 240, 200, 20);
        panelLI.add(nombreCuenta);
    }
    
    public void colocarPassword(){
        contrasena= new JPasswordField(5);
        contrasena.setBounds(170, 270, 200, 20);
        contrasena.setForeground(Color.YELLOW);
        contrasena.setBackground(Color.DARK_GRAY);
        panelLI.add(contrasena);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
