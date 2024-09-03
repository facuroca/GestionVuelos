package view;


// Cantidad de entidades donde el valor de la suma de dos de sus atributos sea menor que el generado al azar.

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import controller.VuelosController;

import java.awt.FlowLayout;
import java.awt.Font;



public class Estadisticas3GUI implements ActionListener{


    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JFrame ventana = new JFrame("Estadísticas 3");
    private FlowLayout layout = new FlowLayout();

    private final String item3 = "Cantidad de entidades donde el valor de la suma de dos de sus atributos sea menor que el generado al azar.";
    private JLabel lblItem3 = new JLabel("Item 3");
    private JTextArea txtItem3 = new JTextArea(item3);

    private JLabel lblResultText = new JLabel();
    private JLabel lblRandomValue = new JLabel();
    private JButton btnItem3 = new JButton("Ejecutar item 3");

    private VuelosController vuelosController;

    public Estadisticas3GUI(VuelosController vuelosController) {
        this.vuelosController = vuelosController;
        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        ventana.add(lblItem3);
        txtItem3.setEditable(false);
        txtItem3.setLineWrap(true);
        txtItem3.setWrapStyleWord(true);
        txtItem3.setRows(2);
        txtItem3.setColumns(30);
        txtItem3.setOpaque(true);
        txtItem3.setFont(txtItem3.getFont().deriveFont(Font.BOLD));
        ventana.add(txtItem3);
        ventana.add(btnItem3);

        ventana.add(lblResultText);
        ventana.add(lblRandomValue);
        lblResultText.setVisible(false);
        lblRandomValue.setVisible(false);

        btnItem3.addActionListener(this);

        ventana.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnItem3) {
            final double randomValue = Math.random() * 100;
            final int response = vuelosController.item3CalculoVuelos(randomValue);
            lblResultText.setVisible(true);
            lblRandomValue.setVisible(true);
            lblResultText.setText("El resultado es: " + response);
            lblRandomValue.setText("El valor utilizado al azar fue: " + String.format("%.2f", randomValue));
            lblResultText.setFont(lblResultText.getFont().deriveFont(Font.BOLD));
            lblRandomValue.setFont(lblRandomValue.getFont().deriveFont(Font.BOLD));
        }
    }
}
