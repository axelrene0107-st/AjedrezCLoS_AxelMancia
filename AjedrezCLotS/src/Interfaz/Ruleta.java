package Interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

public class Ruleta extends JDialog implements ActionListener {

    private static final String[] TIPOS_PIEZA = {"Lobo", "Vampiro", "Muerte"};
    private String piezaSorteada = null;
    private JLabel labelGif;
    private Timer timer;

    private static final int TIEMPO_RESULTADO_MS = 3000; // El tiempo que dura el gif 

    public Ruleta(JFrame parent) {
        super(parent, "Resultado del Sorteo", true);

        setSize(250, 300);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setResizable(true);
        setLocationRelativeTo(parent);
        
        
        //Panel de la ruleta
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(30, 0, 0, 180)); 
        this.add(contentPanel, BorderLayout.CENTER);

        labelGif = new JLabel();
        labelGif.setHorizontalAlignment(SwingConstants.CENTER);
        labelGif.setOpaque(false); 
        contentPanel.add(labelGif, BorderLayout.CENTER);

        timer = new Timer(TIEMPO_RESULTADO_MS, this);
        timer.setRepeats(false);
    }

    public String girar() {
        // Sorteo inmediato
        Random random = new Random();
        int index = random.nextInt(TIPOS_PIEZA.length);
        piezaSorteada = TIPOS_PIEZA[index];

        // Mostrar el gif 
        String resultadoImg = piezaSorteada+"_Resultado.gif";
        cargarImagen(resultadoImg);

        // Inicia el timer
        timer.start();

        
        setVisible(true);

        return piezaSorteada;
    }

    // Metodo para cargar el gif
    private void cargarImagen(String fileName) {
        labelGif.setIcon(null);
        labelGif.setText(null);

        try {
            ImageIcon icon = null;

            // Busca el metodo desde la raiz
            java.net.URL url = getClass().getResource("/" + fileName);
            if (url != null) {
                icon = new ImageIcon(url);
            }
            
            //Tuve problemas cargando el gif entonces pase los gifs a la carpeta source, con este metodo los busca ahi
            if (icon == null || icon.getIconWidth() == -1) {
                java.io.File file = new java.io.File("src/" + fileName);
                if (file.exists()) {
                    icon = new ImageIcon(file.getAbsolutePath());
                }
            }

            if (icon == null || icon.getIconWidth() == -1) {
                throw new Exception("No se pudo cargar el GIF: " + fileName);
            }

            int size = 280;
            Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT);
            labelGif.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            labelGif.setText("ERROR: " + fileName);
            System.err.println("Error al cargar GIF: " + e.getMessage());
        }
    }

    //Metodo de cierre del JDialog
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}