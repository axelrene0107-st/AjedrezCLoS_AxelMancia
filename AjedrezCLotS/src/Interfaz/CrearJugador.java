//Clase JFrame para la ventana de creacion del jugador
package Interfaz;

import Datos.Jugador;
import Datos.Jugadores;
import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author axelr
 */
public class CrearJugador extends JFrame implements ActionListener {
    public JPanel panelCJ;//Creamos el panel
    private Jugadores listaJugadores;
    public JTextField nombreCuenta;
    public JPasswordField contrasena;
    
    public CrearJugador(){
        listaJugadores= new Jugadores();
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Creación de Jugador");
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
        panelCJ= new JPanel();//Creo el panel para la ventana de menu
        panelCJ.setLayout(null);//Desactivamos el diseño por defecto
        panelCJ.setBackground(Color.white);//Le asignamos un color
        this.getContentPane().add(panelCJ);//se agrega el panel a la ventana 
        
    } 
        
        
    private void colocarLabels(){//Metodo para añadir 
        ImageIcon iconLogo= new ImageIcon("Logo.png");   
        JLabel logo= new JLabel();//Agregamos un label para el fondo
        logo.setBounds(117, 0, 250, 170);
        logo.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(250, 170, Image.SCALE_SMOOTH)));
        panelCJ.add(logo);//Label del fondo
        
        JLabel etiquetaNombre= new JLabel("Nombre:");
        Font fuente= new Font("Perpetua Titling MT", 1, 12);
        etiquetaNombre.setFont(fuente);
        etiquetaNombre.setBounds(105, 228, 70, 50);
        etiquetaNombre.setForeground(Color.YELLOW);
        panelCJ.add(etiquetaNombre);
        
        JLabel etiquetaContrasena= new JLabel("Password:");
        Font fuente2= new Font("Perpetua Titling MT", 1, 9);
        etiquetaContrasena.setFont(fuente2);
        etiquetaContrasena.setBounds(107, 260, 70, 50);
        etiquetaContrasena.setForeground(Color.YELLOW);
        panelCJ.add(etiquetaContrasena);
        
        ImageIcon iconEspacio= new ImageIcon("txtEspacio.png");   
        JLabel espacio= new JLabel();//Agregamos un label para el fondo
        espacio.setBounds(0, 90, 480, 350);
        espacio.setIcon(new ImageIcon(iconEspacio.getImage().getScaledInstance(480, 350, Image.SCALE_SMOOTH)));
        panelCJ.add(espacio);
        
        ImageIcon iconFondo= new ImageIcon("fondo2.jpg");   
        JLabel fondo= new JLabel();//Agregamos un label para el fondo
        fondo.setBounds(0, 0, 500, 500);
        fondo.setIcon(new ImageIcon(iconFondo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
        panelCJ.add(fondo);//Label del fondo  

        
    }
    
     private void colocarBotones(){
        ImageIcon iconCrear= new ImageIcon("BtnCrear.png");
        JButton btnCrear= new JButton(iconCrear);//Creamos boton para iniciar sesion
        btnCrear.setBounds(300, 400, 150, 40);//Le asignamos sus posisicion y dimension
        btnCrear.setIcon(new ImageIcon(iconCrear.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panelCJ.add(btnCrear);
        ActionListener accion= new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(contrasena.getPassword().length!=5){
                    JOptionPane.showMessageDialog(null, "La contraseña debe ser de 5 caracteres.", "Error con Contraseña", JOptionPane.WARNING_MESSAGE);
                    }else if(nombreCuenta.getText().length()<7 || nombreCuenta.getText().length()>10){
                        JOptionPane.showMessageDialog(null, "El nombre debe tener entre 7-10 letras", "Error con Nombre", JOptionPane.WARNING_MESSAGE);
                        }else if(listaJugadores.revisarNombres(nombreCuenta.getText())==true){//Revisa si el nombre escrito ya existe
                            JOptionPane.showMessageDialog(null, "Ese nombre ya esta en uso.", "Error con Nombre", JOptionPane.WARNING_MESSAGE);
                            }else{
                            Jugador player= new Jugador(nombreCuenta.getText(), contrasena.getPassword());
                            listaJugadores.guardarJugadores(player);
                            JOptionPane.showMessageDialog(null, "La cuenta se ha creado con exito.", "Cuenta Creada", JOptionPane.INFORMATION_MESSAGE);
                            CrearJugador.this.setVisible(false);
                            }               
            }                
        };
        btnCrear.addActionListener(accion);
        
        //Creacion del boton salir
        ImageIcon iconSalir= new ImageIcon("btnSalir.png");
        JButton btnSalir= new JButton();//Creamos boton para iniciar sesion
        btnSalir.setBounds(140, 400, 150, 40);//Le asignamos sus posicion y dimensiones
        btnSalir.setIcon(new ImageIcon(iconSalir.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        panelCJ.add(btnSalir);
        ActionListener accion2= new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearJugador.this.setVisible(false);
            }
        };       
        btnSalir.addActionListener(accion2);
        
     }

    
    
    private void colocarJTextField(){
        nombreCuenta= new JTextField(10);
        Font fuenteTexto= new Font("Perpetua Titling MT", 1, 9);
        nombreCuenta.setFont(fuenteTexto);
        nombreCuenta.setForeground(Color.YELLOW);
        nombreCuenta.setBackground(Color.DARK_GRAY);
        nombreCuenta.setText("Ingrese el nombre de la cuenta....");
        nombreCuenta.setBounds(170, 240, 200, 20);
        panelCJ.add(nombreCuenta);
    }
    
    public void colocarPassword(){
        contrasena= new JPasswordField(5);
        contrasena.setBounds(170, 270, 200, 20);
        contrasena.setForeground(Color.YELLOW);
        contrasena.setBackground(Color.DARK_GRAY);
        panelCJ.add(contrasena);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public Jugadores getListaJugadores() {
        return listaJugadores;
    }
}
