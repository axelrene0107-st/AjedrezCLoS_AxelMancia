//Clase JFrame para la ventana de creacion del jugador
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
    
    public CrearJugador(Jugadores listaCompartida){
        this.listaJugadores = listaCompartida;
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
        panelCJ.setBackground(Color.BLACK);//Le asignamos un color
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
        btnCrear.setBorder(null);
        btnCrear.setContentAreaFilled(false);
        btnCrear.setFocusPainted(false);
        btnCrear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelCJ.add(btnCrear);
        
        btnCrear.addActionListener(e -> crearJugador());
        
        //Creacion del boton salir
        ImageIcon iconSalir= new ImageIcon("btnSalir.png");
        JButton btnSalir= new JButton();//Creamos boton para iniciar sesion
        btnSalir.setBounds(140, 400, 150, 40);//Le asignamos sus posicion y dimensiones
        btnSalir.setIcon(new ImageIcon(iconSalir.getImage().getScaledInstance(220, 130, Image.SCALE_SMOOTH)));
        btnSalir.setBorder(null);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelCJ.add(btnSalir);
        btnSalir.addActionListener(e -> setVisible(false));
        
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
    
     private void crearJugador() {
        String nombre = nombreCuenta.getText().trim();
        char[] pass = contrasena.getPassword();

        if (pass.length != 5) {
            JOptionPane.showMessageDialog(this, "La contraseña debe ser de 5 caracteres.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nombre.length() < 7 || nombre.length() > 10) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener entre 7 y 10 letras.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (listaJugadores.revisarNombres(nombre)) {
            JOptionPane.showMessageDialog(this, "Ese nombre ya está en uso.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✅ Crear y guardar el jugador
        Jugador nuevo = new Jugador(nombre, pass);
        listaJugadores.guardarJugadores(nuevo);

        JOptionPane.showMessageDialog(this, "La cuenta se ha creado con éxito.", "Cuenta Creada", JOptionPane.INFORMATION_MESSAGE);
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public Jugadores getListaJugadores() {
        return listaJugadores;
    }
}
