package view;

// Valor total de algún atributo numérico de alguna entidad hija donde todas sus casillas de verificación estén activas 
//y haya sido ingresada durante los últimos 6 meses.

import javax.swing.*;

import controller.VuelosController;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;



public class Estadisticas1GUI implements ActionListener {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JFrame ventana = new JFrame("Estadísticas 1");
    private FlowLayout layout = new FlowLayout();
    
    private final String item1 = "Valor total de algún atributo numérico de alguna entidad hija donde todas sus casillas de verificación estén activas y haya sido ingresada durante los últimos 6 meses.";
    private JLabel lblItem1 = new JLabel("Item 1");
    private JTextArea txtItem1 = new JTextArea(item1);
    private JButton btnItem1 = new JButton("Ejecutar item 1");
    private JLabel lblResultText = new JLabel();

    private VuelosController vuelosController;

    public Estadisticas1GUI(VuelosController vuelosController) {
        this.vuelosController = vuelosController;
        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        ventana.add(lblItem1);
        txtItem1.setEditable(false);
        txtItem1.setLineWrap(true);
        txtItem1.setWrapStyleWord(true);
        txtItem1.setRows(3);
        txtItem1.setColumns(40);
        txtItem1.setOpaque(true);
        txtItem1.setFont(txtItem1.getFont().deriveFont(Font.BOLD));
        ventana.add(txtItem1);
        ventana.add(btnItem1);
        ventana.add(lblResultText);
        lblResultText.setVisible(false);

        btnItem1.addActionListener(this);

        ventana.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnItem1) {
            final double duracionTotal = vuelosController.item1CalculoVuelos();
            lblResultText.setText("La duración total de los vuelos que cumplen con los requisitos es: " + String.format("%.2f", duracionTotal) + " horas.");
            lblResultText.setVisible(true);
            lblResultText.setFont(lblResultText.getFont().deriveFont(Font.BOLD));
        }

    }



    
}
