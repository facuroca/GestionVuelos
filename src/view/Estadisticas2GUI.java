package view;


// Cuatro datos de aquellas entidades, donde ningún valor de un atributo del objeto asociado por cardinalidad múltiple coincida con el dato 
// ingresado como argumento de la aplicación. Guardar esta información en un archivo acumulativo, con formato JSON aplicando modelo de Streamming,
//  con la fecha del día como propiedad principal.

import javax.swing.*;

import controller.TripulantesController;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.List;

import model.Tripulantes;

public class Estadisticas2GUI implements ActionListener{



    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int WIDTH_TEXT = 14;

    private JFrame ventana = new JFrame("Estadísticas 2");
    private FlowLayout layout = new FlowLayout();

    private final String item2 = "Cuatro datos de aquellas entidades, donde ningún valor de un atributo del objeto asociado por cardinalidad múltiple coincida con el dato ingresado como argumento de la aplicación." +
    " Guardar esta información en un archivo acumulativo, con formato JSON aplicando modelo de Streaming, con la fecha del día como propiedad principal.";
    private JLabel lblItem2 = new JLabel("Item 2");
    private JTextArea txtItem2 = new JTextArea(item2);
    private JButton btnItem2 = new JButton("Mostrar resultados");

    private JLabel lblTripulantes = new JLabel("Seleccione un tripulante para ver los datos del mismo");
    private JLabel lblTripulantesMatched = new JLabel("Dnis de los tripulantes que cumplen con los requisitos:");
    private JComboBox<String> cbTripulantesMatched;
    
    private JLabel lblNombreTripulante = new JLabel("Nombre");
    private JTextField txtNombreTripulante = new JTextField(WIDTH_TEXT);
    private JLabel lblApellidoTripulante = new JLabel("Apellido");
    private JTextField txtApellidoTripulante = new JTextField(WIDTH_TEXT);
    private JLabel lblRolTripulante = new JLabel("Rol");
    private JTextField txtRolTripulante = new JTextField(WIDTH_TEXT);
    private JLabel lblSueldoTripulante = new JLabel("Sueldo");
    private JTextField txtSueldoTripulante = new JTextField(WIDTH_TEXT);

    private TripulantesController tripulantesController;
    private List<Tripulantes> tripulantesMatched;
    

    public Estadisticas2GUI(TripulantesController tripulantesController, String arg) {
        this.tripulantesController = tripulantesController;

        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        ventana.add(lblItem2);
        txtItem2.setEditable(false);
        txtItem2.setLineWrap(true);
        txtItem2.setWrapStyleWord(true);
        txtItem2.setRows(4);
        txtItem2.setColumns(50);
        txtItem2.setOpaque(true);
        txtItem2.setFont(txtItem2.getFont().deriveFont(Font.BOLD));
        ventana.add(txtItem2);
        ventana.add(btnItem2);

        ventana.add(lblTripulantes);
        ventana.add(lblTripulantesMatched);
        this.tripulantesMatched = tripulantesController.item2CalculoTripulantes(arg);
        final String[] tripulanteDniStrings = new String[tripulantesMatched.size()];
        int index = 0;
        for (Tripulantes t : tripulantesMatched) {
            tripulanteDniStrings[index] = String.valueOf(t.getDniTripulante());
            index++;
        }
        cbTripulantesMatched = new JComboBox<>(tripulanteDniStrings);
        ventana.add(cbTripulantesMatched);

        lblTripulantes.setVisible(false);
        lblTripulantesMatched.setVisible(false);
        cbTripulantesMatched.setVisible(false);

        ventana.add(lblNombreTripulante);
        ventana.add(txtNombreTripulante);
        ventana.add(lblApellidoTripulante);
        ventana.add(txtApellidoTripulante);
        ventana.add(lblRolTripulante);
        ventana.add(txtRolTripulante);
        ventana.add(lblSueldoTripulante);
        ventana.add(txtSueldoTripulante);
        lblNombreTripulante.setVisible(false);
        txtNombreTripulante.setVisible(false);
        lblApellidoTripulante.setVisible(false);
        txtApellidoTripulante.setVisible(false);
        lblRolTripulante.setVisible(false);
        txtRolTripulante.setVisible(false);
        lblSueldoTripulante.setVisible(false);
        txtSueldoTripulante.setVisible(false);

        btnItem2.addActionListener(this);
        cbTripulantesMatched.addActionListener(this);

        ventana.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnItem2) {
            lblTripulantes.setVisible(true);
            lblTripulantesMatched.setVisible(true);
            cbTripulantesMatched.setSelectedItem(null);
            cbTripulantesMatched.setVisible(true);
        } else if (source == cbTripulantesMatched) {
            for(Tripulantes t : tripulantesController.getTripulacion()) {
                if (cbTripulantesMatched.getSelectedItem() != null && t.getDniTripulante() == Integer.parseInt((String) cbTripulantesMatched.getSelectedItem())) {
                    mostrarDatosTripulante(t);
                    break;
                }
            }
        }
    }

    public void mostrarDatosTripulante(Tripulantes t) {
        txtNombreTripulante.setText(t.getNombre());
        txtApellidoTripulante.setText(t.getApellido());
        txtRolTripulante.setText(t.getRol());
        txtSueldoTripulante.setText("$" + String.format("%.2f", t.getSueldo()));
        lblNombreTripulante.setVisible(true);
        txtNombreTripulante.setVisible(true);
        lblApellidoTripulante.setVisible(true);
        txtApellidoTripulante.setVisible(true);
        lblRolTripulante.setVisible(true);
        txtRolTripulante.setVisible(true);
        lblSueldoTripulante.setVisible(true);
        txtSueldoTripulante.setVisible(true);
        txtNombreTripulante.setEditable(false);
        txtApellidoTripulante.setEditable(false);
        txtRolTripulante.setEditable(false);
        txtSueldoTripulante.setEditable(false);
        txtNombreTripulante.setFont(txtNombreTripulante.getFont().deriveFont(Font.BOLD));
        txtApellidoTripulante.setFont(txtApellidoTripulante.getFont().deriveFont(Font.BOLD));
        txtRolTripulante.setFont(txtRolTripulante.getFont().deriveFont(Font.BOLD));
        txtSueldoTripulante.setFont(txtSueldoTripulante.getFont().deriveFont(Font.BOLD));
    }
    
}
