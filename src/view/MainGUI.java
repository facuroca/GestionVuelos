package view;

import javax.swing.*;
import java.awt.Image;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.AvionesController;
import controller.LocacionesController;
import controller.TripulantesController;
import controller.VuelosController;

public class MainGUI implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    private JFrame ventana = new JFrame("Sistema de gestión de vuelos");
    private FlowLayout layout = new FlowLayout();
    private JMenuBar menuBar = new JMenuBar();

    private JMenu operacionesMenu = new JMenu("Operaciones");
    private JMenuItem ingresoItem = new JMenuItem("Ingreso");
    private JMenuItem consultaItem = new JMenuItem("Consulta y actualización");
    private JMenuItem consultaMasivaItem = new JMenuItem("Consulta masiva");

    private JMenu estadisticasMenu = new JMenu("Estadísticas");
    private JMenuItem estadisticasItem1 = new JMenuItem("Calcular item 1");
    private JMenuItem estadisticasItem2 = new JMenuItem("Calcular item 2");
    private JMenuItem estadisticasItem3 = new JMenuItem("Calcular item 3");

    private JMenu sistemaMenu = new JMenu("Sistema");
    private JMenuItem acercaDeItem = new JMenuItem("Acerca de...");

    private JMenu salirMenu = new JMenu("Salir");
    private JMenuItem salirItem = new JMenuItem("Salir");

    private ImageIcon image = new ImageIcon("src/airport.png");
    private Image resizedImage = ((ImageIcon) image).getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
    private ImageIcon resizedIcon = new ImageIcon(resizedImage);
    private JLabel imageLabel = new JLabel(resizedIcon); 


    private AvionesController avionesController;
    private LocacionesController locacionesController;
    private TripulantesController tripulantesController;
    private VuelosController vuelosController;
    private String arg;


    public MainGUI(AvionesController avionesController, LocacionesController locacionesController, TripulantesController tripulantesController, VuelosController vuelosController, String arg) {
        this.avionesController = avionesController;
        this.locacionesController = locacionesController;
        this.tripulantesController = tripulantesController;
        this.vuelosController = vuelosController;
        this.arg = arg;
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        menuBar.add(operacionesMenu);
        operacionesMenu.add(ingresoItem);
        operacionesMenu.add(consultaItem);
        operacionesMenu.add(consultaMasivaItem);

        menuBar.add(estadisticasMenu);
        estadisticasMenu.add(estadisticasItem1);
        estadisticasMenu.add(estadisticasItem2);
        estadisticasMenu.add(estadisticasItem3);

        menuBar.add(sistemaMenu);
        sistemaMenu.add(acercaDeItem);
        menuBar.add(salirMenu);
        salirMenu.add(salirItem);

        ventana.add(imageLabel);

        ingresoItem.addActionListener(this);
        consultaItem.addActionListener(this);
        estadisticasItem1.addActionListener(this);
        estadisticasItem2.addActionListener(this);
        estadisticasItem3.addActionListener(this);
        acercaDeItem.addActionListener(this);
        salirItem.addActionListener(this);
        

        ventana.setJMenuBar(menuBar);
        ventana.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == salirItem) {
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        } 
    } else if (source == acercaDeItem) {
        JOptionPane.showMessageDialog(null, "Sistema de gestión de vuelos\nVersion: 4.0\nAño: 2024\nAutor: Facundo Roca", "Acerca de...", JOptionPane.INFORMATION_MESSAGE);
    } else if (source == ingresoItem) {
        new IngresoGUI(avionesController, locacionesController, tripulantesController, vuelosController);
    } else if (source == consultaItem) {
        new ConsultaActualizacionGUI(vuelosController, avionesController, tripulantesController, locacionesController);
    } else if (source == estadisticasItem1) {
        new Estadisticas1GUI(vuelosController);
    } else if (source == estadisticasItem2) {
        new Estadisticas2GUI(tripulantesController, arg);
    } else if (source == estadisticasItem3) {
        new Estadisticas3GUI(vuelosController);
    }
    }
}