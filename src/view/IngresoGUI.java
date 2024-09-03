package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import javax.swing.DefaultListModel;

import javax.swing.JOptionPane;



import controller.AvionesController;
import controller.LocacionesController;
import controller.TripulantesController;
import controller.VuelosController;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import model.Aviones;
import model.Tripulantes;
import model.Vuelos;
import model.Locaciones;


public class IngresoGUI implements ActionListener, ListSelectionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 850;
    private static final int WIDTHField = 15;

    private JFrame ventana = new JFrame("Ingreso de datos");
    private FlowLayout layout = new FlowLayout();

    private JLabel lblIdVuelo = new JLabel("ID Vuelo");
    private JTextField txtIdVuelo = new JTextField(WIDTHField);

    private JPanel panelTipoVuelo = new JPanel();
    private JLabel lblTipoVuelo = new JLabel("Tipo de vuelo");
    private ButtonGroup bgTipoVuelo = new ButtonGroup();
    private JRadioButton rbInternacional = new JRadioButton("Internacional");
    private JRadioButton rbNacional = new JRadioButton("Nacional");

    private JLabel lblAerolinea = new JLabel("Aerolínea");
    private JTextField txtAerolinea = new JTextField(WIDTHField);

    private String[] estadosPosibles = {"Programado", "Retrasado", "Cancelado"};
    private JLabel lblEstado = new JLabel("Estado");
    private JComboBox<String> cbEstado = new JComboBox<String>(estadosPosibles);

    private JLabel lblDuracion = new JLabel("Duración");
    private JTextField txtDuracion = new JTextField(7);
    private JLabel lblDuracionFormato = new JLabel("hs.");

    private JLabel lblAvion = new JLabel("Avión");
    private JComboBox<String> cbAvion = new JComboBox<String>(); // Modelos de aviones

    private JLabel lblTripulacion = new JLabel("Tripulación");
    private JList<String> lstTripulacion; // Dnis de tripulantes
    private JScrollPane spTripulacion;

    private JLabel lblTieneEscalas = new JLabel("Tiene escalas");
    private JCheckBox chkTieneEscalas = new JCheckBox();

    private JLabel lblPermiteMascotas = new JLabel("Permite mascotas");
    private JCheckBox chkPermiteMascotas = new JCheckBox();

    private JLabel lblPaisOrigen = new JLabel("País de origen");
    private JComboBox<String> cbPaisOrigen = new JComboBox<String>(); // nombres de paises

    private JPanel panelPaisFixed = new JPanel();
    private JLabel lblPaisFixedO = new JLabel("País de origen");
    private JTextField txtPaisFixedO = new JTextField("Argentina");
    private JLabel lblPaisFixedD = new JLabel("País de destino");
    private JTextField txtPaisFixedD = new JTextField("Argentina");

    private JLabel lblPaisDestino = new JLabel("País de destino");
    private JComboBox<String> cbPaisDestino = new JComboBox<String>(); // nombres de paises - dependiente de pais origen

    private JPanel panelRequiereVisa = new JPanel();
    private JLabel lblRequiereVisa = new JLabel("Requiere visa?");
    private ButtonGroup bgRequiereVisa = new ButtonGroup();
    private JRadioButton rbRequiereVisa = new JRadioButton("Sí");
    private JRadioButton rbNoRequiereVisa = new JRadioButton("No");

    private String[] zonasHorarias = { "GMT+12", "GMT+11", "GMT+10", "GMT+9", "GMT+8", "GMT+7", "GMT+6", "GMT+5", "GMT+4", "GMT+3", "GMT+2", "GMT+1",
     "GMT-0", "GMT-1", "GMT-2", "GMT-3", "GMT-4", "GMT-5", "GMT-6", "GMT-7", "GMT-8", "GMT-9", "GMT-10", "GMT-11", "GMT-12"};
    private JLabel lblZonaHorariaDestino = new JLabel("Zona horaria destino");
    private JComboBox<String> cbZonaHorariaDestino = new JComboBox<String>(zonasHorarias); // nombres de zonas horarias

    private JLabel lblCiudadOrigen = new JLabel("Ciudad de origen");
    private JComboBox<String> cbCiudadOrigen = new JComboBox<String>(); // nombres de ciudades - dependiente de pais origen

    private JLabel lblCiudadDestino = new JLabel("Ciudad de destino");
    private JComboBox<String> cbCiudadDestino = new JComboBox<String>(); // nombres de ciudades - dependiente de pais destino y ciudad origen

    private JPanel panelEscalas = new JPanel();
    private JLabel lblEscala1 = new JLabel("Tiempo de espera en ");
    private JTextField txtEscala1 = new JTextField(WIDTHField);
    private JLabel lblEscala2 = new JLabel("Tiempo de espera en ");
    private JTextField txtEscala2 = new JTextField(WIDTHField);
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JLabel lblEscalas = new JLabel("Escalas");
    private JList<String> lstEscalas;
    private JScrollPane spEscalas;

    private JButton btnAceptar = new JButton("Aceptar");
    private JButton btnCancelar = new JButton("Cancelar");

    private JPanel panelLeyenda = new JPanel();
    private JLabel lblLeyenda1 = new JLabel("Bienvenido al sistema de gestion de vuelos.");
    private JLabel lblLeyenda2 = new JLabel("Tenga en cuenta que los vuelos nacionales solo seran en Argentina");
    private JLabel lblLeyenda3 = new JLabel("y los vuelos internacionales pueden requerir visa para el ingreso a destino.");
    private JLabel lblLeyenda4 = new JLabel("Es importante que complete todos los campos obligatorios para poder continuar con el ingreso de datos.");
    private JLabel lblLeyenda5 = new JLabel("Recuerde que el límite de tripulantes es de 5 y el de escalas es de 2.");
    private JLabel lblLeyenda6 = new JLabel("Para seleccionar múltiples elementos, mantenga presionada la tecla Ctrl.");
    private JLabel lblLeyenda7 = new JLabel("Cualquier inconveniente, por favor, comuniquese con el administrador del sistema. Gracias por utilizar nuestro sistema.");

    private LocacionesController locacionesController;
    private VuelosController vuelosController;


    public IngresoGUI(AvionesController avionesController, LocacionesController locacionesController, TripulantesController tripulantesController,
     VuelosController vuelosController) {
        this.vuelosController = vuelosController;
        this.locacionesController = locacionesController;

        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        ventana.add(lblIdVuelo);
        ventana.add(txtIdVuelo);

        bgTipoVuelo.add(rbInternacional);
        bgTipoVuelo.add(rbNacional);

        panelTipoVuelo.add(lblTipoVuelo);
        panelTipoVuelo.add(rbInternacional);
        panelTipoVuelo.add(rbNacional);
        panelTipoVuelo.setBackground(Color.LIGHT_GRAY);
        ventana.add(panelTipoVuelo);

        ventana.add(lblAerolinea);
        ventana.add(txtAerolinea);

        ventana.add(lblEstado);
        ventana.add(cbEstado);

        ventana.add(lblDuracion);
        ventana.add(txtDuracion);
        ventana.add(lblDuracionFormato);

        ventana.add(lblAvion);
        for (Aviones avion : avionesController.getAviones()) {
            cbAvion.addItem(avion.getModelo());
        }
        ventana.add(cbAvion);

        ventana.add(lblTripulacion);
        final String[] tripulanteStrings = new String[tripulantesController.getTripulacion().size()];
        int index = 0;
        for (Tripulantes t : tripulantesController.getTripulacion()) {
            tripulanteStrings[index] = String.valueOf(t.getDniTripulante());
            index++;
        }
        lstTripulacion = new JList<String>(tripulanteStrings);
        lstTripulacion.setVisibleRowCount(5);
        lstTripulacion.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstTripulacion.addListSelectionListener(this);
        spTripulacion = new JScrollPane(lstTripulacion);
        ventana.add(spTripulacion);
        

        ventana.add(lblTieneEscalas);
        ventana.add(chkTieneEscalas);

        ventana.add(lblPermiteMascotas);
        chkPermiteMascotas.setSelected(true);
        ventana.add(chkPermiteMascotas);

        ventana.add(lblPaisOrigen);
        lblPaisOrigen.setVisible(false);
        for(String p : locacionesController.getPaisesSet()) {
                cbPaisOrigen.addItem(p);
        }
        ventana.add(cbPaisOrigen);
        cbPaisOrigen.setVisible(false);
        cbPaisOrigen.setSelectedItem(null);

        txtPaisFixedO.setEditable(false);
        txtPaisFixedD.setEditable(false);
        panelPaisFixed.add(lblPaisFixedO);
        panelPaisFixed.add(txtPaisFixedO);
        panelPaisFixed.add(lblPaisFixedD);
        panelPaisFixed.add(txtPaisFixedD);
        panelPaisFixed.setBackground(Color.YELLOW);
        ventana.add(panelPaisFixed);
        panelPaisFixed.setVisible(false);

        ventana.add(lblPaisDestino);
        lblPaisDestino.setVisible(false);
        ventana.add(cbPaisDestino);
        cbPaisDestino.setVisible(false);
        cbPaisDestino.setSelectedItem(null);

        bgRequiereVisa.add(rbRequiereVisa);
        bgRequiereVisa.add(rbNoRequiereVisa);

        panelRequiereVisa.add(lblRequiereVisa);
        panelRequiereVisa.add(rbRequiereVisa);
        panelRequiereVisa.add(rbNoRequiereVisa);
        panelRequiereVisa.setBackground(Color.GREEN);
        ventana.add(panelRequiereVisa);
        panelRequiereVisa.setVisible(false);

        ventana.add(lblZonaHorariaDestino);
        lblZonaHorariaDestino.setVisible(false);
        ventana.add(cbZonaHorariaDestino);
        cbZonaHorariaDestino.setVisible(false);

        ventana.add(lblCiudadOrigen);
        lblCiudadOrigen.setVisible(false);
        ventana.add(cbCiudadOrigen);
        cbCiudadOrigen.setVisible(false);
        for(Locaciones l : this.locacionesController.getLocaciones()) {
            if(l.getNombrePais().equals(txtPaisFixedD.getText())) {
                cbCiudadOrigen.addItem(l.getNombreCiudad());
            }
        }

        cbCiudadOrigen.setSelectedItem(null);

        ventana.add(lblCiudadDestino);
        lblCiudadDestino.setVisible(false);
        ventana.add(cbCiudadDestino);
        cbCiudadDestino.setVisible(false);
        cbCiudadDestino.setSelectedItem(null);

        lblEscalas.setVisible(false);
        ventana.add(lblEscalas);
        lstEscalas = new JList<String>(model);
        lstEscalas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstEscalas.setVisibleRowCount(5);
        lstEscalas.addListSelectionListener(this);
        spEscalas = new JScrollPane(lstEscalas);
        spEscalas.setVisible(false);
        ventana.add(spEscalas);
        panelEscalas.setLayout(new GridLayout(4, 1, 5, 5));
        ventana.add(panelEscalas);
        
        

        

        ventana.add(btnAceptar);
        ventana.add(btnCancelar);

        panelLeyenda.add(lblLeyenda1);
        panelLeyenda.add(lblLeyenda2);
        panelLeyenda.add(lblLeyenda3);
        panelLeyenda.add(lblLeyenda4);
        panelLeyenda.add(lblLeyenda5);
        panelLeyenda.add(lblLeyenda6);
        panelLeyenda.add(lblLeyenda7);
        panelLeyenda.setPreferredSize(new Dimension(700, 140));
        ventana.add(panelLeyenda);
        panelLeyenda.setBackground(Color.LIGHT_GRAY);
        panelLeyenda.setOpaque(true);

        btnAceptar.setActionCommand("Validar_Datos_Numerico");
        btnAceptar.setActionCommand("Validar_Datos_Completos");
        btnAceptar.addActionListener(this);
        btnCancelar.addActionListener(this);

        rbInternacional.addActionListener(this);
        rbNacional.addActionListener(this);

        chkTieneEscalas.addActionListener(this);
        
        cbPaisOrigen.addActionListener(this);
        cbPaisDestino.addActionListener(this);

        cbCiudadOrigen.addActionListener(this);
        cbCiudadDestino.addActionListener(this);


        ventana.setVisible(true);




    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String idVuelo = txtIdVuelo.getText();
        char tipoVuelo = rbInternacional.isSelected() ? 'I' : 'N';
        String aerolinea = txtAerolinea.getText();
        Calendar fechaSistema = Calendar.getInstance();
        String estado = cbEstado.getSelectedItem() != null ? cbEstado.getSelectedItem().toString() : null;
        String duracion = txtDuracion.getText();
        String modeloAvion = cbAvion.getSelectedItem() != null ? cbAvion.getSelectedItem().toString() : null;
        Object[] tripulantes = lstTripulacion.getSelectedValuesList().toArray();
        boolean tieneEscalas = chkTieneEscalas.isSelected();
        boolean permiteMascotas = chkPermiteMascotas.isSelected();
        Object[] escalas = lstEscalas.getSelectedValuesList().toArray();
        String escala1Duracion = txtEscala1.getText();
        String escala2Duracion = txtEscala2.getText();
        String paisOrigen = cbPaisOrigen.getSelectedItem() != null ? cbPaisOrigen.getSelectedItem().toString() : null;
        String paisDestino = cbPaisDestino.getSelectedItem() != null ? cbPaisDestino.getSelectedItem().toString() : null;
        String paisFixed = txtPaisFixedO.getText();
        boolean requiereVisa = rbRequiereVisa.isSelected();
        String zonaHorariaDestino = cbZonaHorariaDestino.getSelectedItem() != null ? cbZonaHorariaDestino.getSelectedItem().toString() : null;
        String ciudadOrigen = cbCiudadOrigen.getSelectedItem() != null ? cbCiudadOrigen.getSelectedItem().toString() : null;
        String ciudadDestino = cbCiudadDestino.getSelectedItem() != null ? cbCiudadDestino.getSelectedItem().toString() : null;





        if (source == btnAceptar) {
            if(e.getActionCommand().equals("Validar_Datos_Numerico") || e.getActionCommand().equals("Validar_Datos_Completos")) {
                if(idVuelo.isEmpty() || tipoVuelo == 0 || aerolinea.isEmpty() || estado == null || duracion.isEmpty() || modeloAvion == null || tripulantes.length == 0  || (tipoVuelo == 'I' && (bgRequiereVisa.isSelected(null) || zonaHorariaDestino == null || paisOrigen == null || paisDestino == null)) || (tipoVuelo == 'N' && (ciudadOrigen == null || ciudadDestino == null)) || (tieneEscalas && escalas.length == 0) || (tieneEscalas && escalas.length == 1 && escala1Duracion.isEmpty()) || (tieneEscalas && escalas.length == 2 && escala2Duracion.isEmpty()) ) {
                    JOptionPane.showMessageDialog(null, "Debe completar todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if(!txtIdVuelo.getText().matches("^[0-9]{1,9}$")) { 
                    JOptionPane.showMessageDialog(null, "El ID de vuelo debe ser un número entero positivo maximo 9 digitos", "Error", JOptionPane.ERROR_MESSAGE);
                    txtIdVuelo.setText("");
                    return;
                } else if(!txtDuracion.getText().matches("^[0-9]{1,15}.?[0-9]*$")) {
                    JOptionPane.showMessageDialog(null, "La duración del vuelo debe ser un número entero positivo", "Error", JOptionPane.ERROR_MESSAGE);
                    txtDuracion.setText("");
                    return;
                } else if (txtEscala1.getText().length() > 0 && !txtEscala1.getText().matches("^[0-9]{1,3}$")) {
                    JOptionPane.showMessageDialog(null, "El tiempo de espera en la escala 1 debe ser un número entero positivo maximo 3 digitos", "Error", JOptionPane.ERROR_MESSAGE);
                    txtEscala1.setText("");
                    return;
                } else if (txtEscala2.getText().length() > 0 && !txtEscala2.getText().matches("^[0-9]{1,3}$")) {
                    JOptionPane.showMessageDialog(null, "El tiempo de espera en la escala 2 debe ser un número entero positivo maximo 3 digitos", "Error", JOptionPane.ERROR_MESSAGE);
                    txtEscala2.setText("");
                    return;
                } else if (tripulantes.length != 5) {
                    JOptionPane.showMessageDialog(null, "El vuelo debe tener 5 tripulantes", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                int ingresoConfirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea ingresar los datos?", "Confirmar ingreso de datos", JOptionPane.YES_NO_OPTION);
                if(ingresoConfirm == JOptionPane.YES_OPTION) {
                    try {
                        final int idVueloInt = Integer.parseInt(idVuelo);
                        final double duracionDouble = Double.parseDouble(duracion);
                        final char estadoChar = estado.charAt(0);
                        final int escala1Int = !escala1Duracion.isEmpty() ? Integer.parseInt(escala1Duracion) : 0;
                        final int escala2Int = !escala2Duracion.isEmpty() ? Integer.parseInt(escala2Duracion) : 0;
                        for (Vuelos v : vuelosController.getVuelos()) {
                            if(v.getIdVuelo() == idVueloInt) {
                                throw new idVueloExistenteExcep("El ID de vuelo ya existe");
                            }
                        }
                        if(tipoVuelo == 'I') {
                            vuelosController.agregarVueloInternacional(idVueloInt, tipoVuelo, aerolinea, fechaSistema, estadoChar, duracionDouble, modeloAvion, tripulantes, tieneEscalas, escalas, permiteMascotas, paisOrigen, paisDestino, requiereVisa, zonaHorariaDestino, escala1Int, escala2Int);
                        } else if (tipoVuelo == 'N') {
                            vuelosController.agregarVueloNacional(idVueloInt, tipoVuelo, aerolinea, fechaSistema, estadoChar, duracionDouble, modeloAvion, tripulantes, tieneEscalas, escalas, permiteMascotas, paisFixed, ciudadOrigen, ciudadDestino, escala1Int, escala2Int);
                        }
                        JOptionPane.showMessageDialog(null, "Datos ingresados correctamente", "Ingreso de datos", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    } catch (idVueloExistenteExcep ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        } else if (source == rbInternacional) {
            model.removeAllElements();
            panelPaisFixed.setVisible(false);

            lblPaisOrigen.setVisible(true);
            cbPaisOrigen.setVisible(true);

            lblPaisDestino.setVisible(true);
            cbPaisDestino.setVisible(true);

            panelRequiereVisa.setVisible(true);

            lblZonaHorariaDestino.setVisible(true);
            cbZonaHorariaDestino.setVisible(true);

            lblCiudadOrigen.setVisible(false);
            cbCiudadOrigen.setVisible(false);

            lblCiudadDestino.setVisible(false);
            cbCiudadDestino.setVisible(false);


        } else if (source == rbNacional) {
             model.removeAllElements();
            panelPaisFixed.setVisible(true);

            lblPaisOrigen.setVisible(false);
            cbPaisOrigen.setVisible(false);

            lblPaisDestino.setVisible(false);
            cbPaisDestino.setVisible(false);

            panelRequiereVisa.setVisible(false);

            lblZonaHorariaDestino.setVisible(false);
            cbZonaHorariaDestino.setVisible(false);

            lblCiudadOrigen.setVisible(true);
            cbCiudadOrigen.setVisible(true);


            lblCiudadDestino.setVisible(true);
            cbCiudadDestino.setVisible(true);

        } else if (source == cbPaisOrigen) {
            model.removeAllElements();
            for(String p : locacionesController.getPaisesSet()) {
                if( (cbPaisOrigen.getSelectedItem() != null && cbPaisOrigen.getSelectedItem().equals(p) ) || (cbPaisDestino.getSelectedItem() != null && cbPaisDestino.getSelectedItem().equals(p) )) {
                    continue;
                } else {
                    model.addElement(p);
                }
            }
            cbPaisDestino.removeAllItems();
            for (String p : this.locacionesController.getPaisesSet()) {
                if(cbPaisOrigen.getSelectedItem() != null && cbPaisOrigen.getSelectedItem().equals(p) ) {
                    continue;
                } else {
                    cbPaisDestino.addItem(p);
                }
            }
        } else if(source == cbPaisDestino) {
            model.removeAllElements();
            for (String p : this.locacionesController.getPaisesSet()) {
                if(cbPaisOrigen.getSelectedItem() != null && cbPaisDestino.getSelectedItem() != null && (cbPaisDestino.getSelectedItem().equals(p) || cbPaisOrigen.getSelectedItem().equals(p))) {
                    model.removeElement(p);
                    continue;
                } else {
                    model.addElement(p);
                }
            }
        } else if (source == cbCiudadOrigen) {
            model.removeAllElements();
            for (String c : locacionesController.getCiudadesSet()) {
                if((cbCiudadOrigen.getSelectedItem() != null && cbCiudadOrigen.getSelectedItem().equals(c)) || (cbCiudadDestino.getSelectedItem() != null && cbCiudadDestino.getSelectedItem().equals(c) )) {
                    continue;
                } else {
                    model.addElement(c);
                }
            }
            cbCiudadDestino.removeAllItems();
            for (Locaciones l : this.locacionesController.getLocaciones()) {
                if(cbCiudadOrigen.getSelectedItem() != null && cbCiudadOrigen.getSelectedItem().equals(l.getNombreCiudad())) {
                    continue;
                } else if (l.getNombrePais().equals("Argentina")) {
                    cbCiudadDestino.addItem(l.getNombreCiudad()); 
                }
            }
        } else if (source == cbCiudadDestino) {
            model.removeAllElements();
            for (Locaciones l : this.locacionesController.getLocaciones()) {
                if(cbCiudadOrigen.getSelectedItem() != null && cbCiudadDestino.getSelectedItem() != null && ( cbCiudadDestino.getSelectedItem().equals(l.getNombreCiudad()) || cbCiudadOrigen.getSelectedItem().equals(l.getNombreCiudad())) ) {
                    model.removeElement(l.getNombreCiudad());
                    continue;
                } else if (l.getNombrePais().equals("Argentina")) {
                    model.addElement(l.getNombreCiudad());
                }
            }
        } else if (source == btnCancelar) {
            int cancelOption = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el ingreso de datos?", "Cancelar ingreso de datos", JOptionPane.YES_NO_OPTION);
            if(cancelOption == JOptionPane.YES_OPTION) {
                clearFields();
            }
        } else if (source == chkTieneEscalas) {
            if(chkTieneEscalas.isSelected()) {
                lblEscalas.setVisible(true);
                spEscalas.setVisible(true);
                panelEscalas.setVisible(true);
            } else {
                lblEscalas.setVisible(false);
                spEscalas.setVisible(false);
                panelEscalas.setVisible(false);
            }
        }
    }


    public void clearFields() {
        txtIdVuelo.setText("");
        txtAerolinea.setText("");
        cbEstado.setSelectedItem(null);
        txtDuracion.setText("");
        cbAvion.setSelectedItem(null);
        chkTieneEscalas.setSelected(false);
        chkPermiteMascotas.setSelected(true);
        cbPaisOrigen.setSelectedItem(null);
        cbPaisDestino.setSelectedItem(null);
        bgRequiereVisa.clearSelection();
        cbZonaHorariaDestino.setSelectedItem(null);
        cbCiudadOrigen.setSelectedItem(null);
        cbCiudadDestino.setSelectedItem(null);
        lstEscalas.clearSelection();
        bgTipoVuelo.clearSelection();
        panelPaisFixed.setVisible(false);
        lblPaisOrigen.setVisible(false);
        cbPaisOrigen.setVisible(false);
        lblPaisDestino.setVisible(false);
        cbPaisDestino.setVisible(false);
        panelRequiereVisa.setVisible(false);
        lblZonaHorariaDestino.setVisible(false);
        cbZonaHorariaDestino.setVisible(false);
        lblCiudadOrigen.setVisible(false);
        cbCiudadOrigen.setVisible(false);
        lblCiudadDestino.setVisible(false);
        cbCiudadDestino.setVisible(false);
        lblEscalas.setVisible(false);
        spEscalas.setVisible(false);
        panelEscalas.setVisible(false);
        txtEscala1.setText("");
        txtEscala2.setText("");
        lstTripulacion.clearSelection();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Object source = e.getSource();
        final int MAX_SELECTIONS = Vuelos.getTotalTripulantes();
        if (!e.getValueIsAdjusting()) {
            if(source == lstTripulacion) {
                int[] selectedIndices = lstTripulacion.getSelectedIndices();
                if (selectedIndices.length > MAX_SELECTIONS) {
                    for (int i = MAX_SELECTIONS; i < selectedIndices.length; i++) {
                        lstTripulacion.removeSelectionInterval(selectedIndices[i], selectedIndices[i]);
                    }
                }
            } else if (source == lstEscalas) {
                int[] selectedIndices = lstEscalas.getSelectedIndices();
                List<String> escalasNames = lstEscalas.getSelectedValuesList();
                final int MAX_ESCALAS = 2;
                panelEscalas.removeAll();
                if(selectedIndices.length == 1) {
                    panelEscalas.add(lblEscala1);
                    panelEscalas.add(txtEscala1);
                    panelEscalas.setVisible(true);
                    lblEscala1.setText("Tiempo de espera en " + escalasNames.get(0));
                } else if(selectedIndices.length == 2) {
                    panelEscalas.add(lblEscala1);
                    panelEscalas.add(txtEscala1);
                    panelEscalas.add(lblEscala2);
                    panelEscalas.add(txtEscala2);
                    panelEscalas.setVisible(true);
                    lblEscala1.setText("Tiempo de espera en " + escalasNames.get(0));
                    lblEscala2.setText("Tiempo de espera en " + escalasNames.get(1));
                } else if (selectedIndices.length > MAX_ESCALAS) {
                    for (int i = MAX_ESCALAS; i < selectedIndices.length; i++) {
                        lstEscalas.removeSelectionInterval(selectedIndices[i], selectedIndices[i]);
                    }
                }
                panelEscalas.revalidate();
                panelEscalas.repaint();
            }
        }
        
    }
	
	
}
