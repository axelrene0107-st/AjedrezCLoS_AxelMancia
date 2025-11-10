package Interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

public class Ruleta extends JDialog implements ActionListener {

    // Tipos de pieza: asegúrate de que coincidan con tus archivos GIF
    private static final String[] TIPOS_PIEZA = {"Lobo", "Vampiro", "Muerte"};
    private String piezaSorteada = null;
    private JLabel labelGif;
    private Timer timer;

    // Tiempo total de visualización del resultado (antes de cerrar el diálogo)
    private static final int TIEMPO_RESULTADO_MS = 3400; // 1.5 segundos

    public Ruleta(JFrame parent) {
        super(parent, "Resultado del Sorteo", true); // Diálogo modal: bloquea la Partida

        setSize(250, 300);
        setUndecorated(true); // Elimina la barra de título para un look más limpio
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(30, 0, 0, 180)); 
        this.add(contentPanel, BorderLayout.CENTER);

        labelGif = new JLabel();
        labelGif.setHorizontalAlignment(SwingConstants.CENTER);
        labelGif.setOpaque(false); // 🔹 Esto lo hace transparente
        contentPanel.add(labelGif, BorderLayout.CENTER);

        // El Timer controla cuánto tiempo se muestra el resultado
        timer = new Timer(TIEMPO_RESULTADO_MS, this);
        timer.setRepeats(false);
    }

    public String girar() {
        // 1. Lógica de sorteo inmediato
        Random random = new Random();
        int index = random.nextInt(TIPOS_PIEZA.length);
        piezaSorteada = TIPOS_PIEZA[index];

        // 2. Muestra el GIF/imagen de la pieza ganadora
        String resultadoImg = piezaSorteada+"_Resultado.gif";
        cargarImagen(resultadoImg);

        // 3. Inicia el Timer y muestra el diálogo modal
        timer.start();

        // IMPORTANTE: setVisible(true) bloquea la ejecución hasta que dispose() es llamado
        setVisible(true);

        return piezaSorteada;
    }

    // 🔹 Carga el GIF igual que las imágenes del proyecto (desde la raíz del src/)
    private void cargarImagen(String fileName) {
        labelGif.setIcon(null);
        labelGif.setText(null);

        try {
            ImageIcon icon = null;

            // 1️⃣ Intenta cargar desde el classpath (build/classes)
            java.net.URL url = getClass().getResource("/" + fileName);
            if (url != null) {
                icon = new ImageIcon(url);
            }

            // 2️⃣ Si no se encontró, intenta desde la carpeta src (NetBeans)
            if (icon == null || icon.getIconWidth() == -1) {
                java.io.File file = new java.io.File("src/" + fileName);
                if (file.exists()) {
                    icon = new ImageIcon(file.getAbsolutePath());
                }
            }

            // 3️⃣ Verifica la carga
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

       // 🔹 Se ejecuta cuando el Timer termina (después de 1.5 segundos)
    @Override
    public void actionPerformed(ActionEvent e) {
        // Cierra el JDialog. Esto desbloquea la ejecución en el método girar() de Partida.java
        dispose();
    }
}