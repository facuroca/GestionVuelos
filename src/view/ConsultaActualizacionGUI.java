package view;

import javax.swing.*;


import controller.VuelosController;
import controller.AvionesController;
import controller.LocacionesController;
import controller.TripulantesController;
import model.Aviones;
import model.Escalas;
import model.Internacionales;
import model.Locaciones;
import model.Nacionales;
import model.Tripulantes;
import model.Vuelos;
import model.DAO.ValidacionArchivos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.Calendar;




// Consulta y Actualización. El sistema deberá incluir una pantalla donde el usuario ingresará el mejor criterio de búsqueda
// para la información a modificar, a eliminar y/o a consultar. Al presionar el botón “Buscar”, el sistema buscará y mostrará 
// el resto de la información existente en formato no editable. O bien, un mensaje de error indicando que la misma no pudo ser encontrada. 
// Al visualizar la información completa de la entidad, el usuario puede seleccionar: Editar o Anular.
// Para el primer caso, “Editar”, los componentes de la pantalla pasarán a modo editable de forma tal que el usuario realice 
// las modificaciones que desea. Y luego, seleccionará Aceptar o Cancelar, de acuerdo a lo deseado.
// Para el segundo caso, “Anular”, el sistema confirmará tal operación y si es avalada por el usuario, se eliminará la información del archivo TXT donde reside.

public class ConsultaActualizacionGUI implements ActionListener, ListSelectionListener {
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 900;
    private final static int TEXTFIELD_WIDTH = 15;
    private final static String TITLE = "Consulta y Actualización";
    private FlowLayout layout = new FlowLayout();
    private JFrame ventana = new JFrame(TITLE);

    // 44384420;I;American;29/08/2024;R;5.0;Airbus A340;27890123,29876543,32345678,33123456,26789012;true;true;
    // Argentina-Inglaterra-1.0,Inglaterra-Espana-4.0,Espana-Japon-0.0;Argentina;Japon;true;GMT+9
    private String[] criterias = {"IDVUELO", "TIPOVUELO", "AEROLINEA", "FECHADECARGA", "ESTADO", "DURACION", "AVION", "TRIPULANTE", "TIENE ESCALAS?", "PERMITE MASCOTAS?",
     "ESCALA", "ORIGEN", "DESTINO", "REQUIERE VISA?", "ZONA HORARIA"};
    private JLabel lblCriteria = new JLabel("Seleccione el criterio de busqueda de vuelos");
    private JComboBox<String> cbCriteria = new JComboBox<String>(criterias);

    private JLabel searchLabel = new JLabel("Ingrese el valor a buscar: ");
    private JTextField searchField = new JTextField(TEXTFIELD_WIDTH);
    private JButton searchButton = new JButton("Buscar");


    private JLabel lblResultIds = new JLabel("ID de los vuelos encontrados");
    private JComboBox<String> cbResultIds = new JComboBox<String>();


    private JLabel lblIdVuelo = new JLabel("ID Vuelo");
    private JTextField txtIdVuelo = new JTextField(9);

    private JLabel lblTipoVuelo = new JLabel("Tipo Vuelo");
    private JTextField txtTipoVuelo = new JTextField(12);

    private JLabel lblAerolinea = new JLabel("Aerolinea");
    private JTextField txtAerolinea = new JTextField(12);

    private JLabel lblFechaDeCarga = new JLabel("Fecha de Carga");
    private JTextField txtFechaDeCarga = new JTextField(11);

    private JLabel lblEstado = new JLabel("Estado");
    private JComboBox<String> cbEstado = new JComboBox<String>(new String[]{"Retrasado", "Cancelado", "Programado"});

    private JLabel lblDuracion = new JLabel("Duracion");
    private JTextField txtDuracion = new JTextField(5);

    private JLabel lblAvion = new JLabel("Avion");
    private JComboBox<String> cbAvion = new JComboBox<String>();

    private JLabel lblTripulacion = new JLabel("Tripulación");
    private JList<String> lstTripulacion;
    private JScrollPane spTripulacion;

    private JLabel lblTieneEscalas = new JLabel("Tiene Escalas?");
    private JCheckBox chkTieneEscalas = new JCheckBox();

    private JLabel lblPermiteMascotas = new JLabel("Permite Mascotas?");
    private JCheckBox chkPermiteMascotas = new JCheckBox();

    private JLabel lblPaisOrigen = new JLabel("Pais origen");
    private JComboBox<String> cbPaisOrigen = new JComboBox<String>();

    private JLabel lblPaisDestino = new JLabel("Pais destino");
    private JComboBox<String> cbPaisDestino = new JComboBox<String>();

    private JLabel lblRequiereVisa = new JLabel("Requiere Visa?");
    private JCheckBox chkRequiereVisa = new JCheckBox();

    private String[] zonasHorarias = { "GMT+12", "GMT+11", "GMT+10", "GMT+9", "GMT+8", "GMT+7", "GMT+6", "GMT+5", "GMT+4", "GMT+3", "GMT+2", "GMT+1",
     "GMT-0", "GMT-1", "GMT-2", "GMT-3", "GMT-4", "GMT-5", "GMT-6", "GMT-7", "GMT-8", "GMT-9", "GMT-10", "GMT-11", "GMT-12"};
    private JLabel lblZonaHorariaDestino = new JLabel("Zona horaria destino");
    private JComboBox<String> cbZonaHorariaDestino = new JComboBox<String>(zonasHorarias);

    private JLabel lblCiudadOrigen = new JLabel("Ciudad origen");
    private JComboBox<String> cbCiudadOrigen = new JComboBox<String>();

    private JLabel lblCiudadDestino = new JLabel("Ciudad destino");
    private JComboBox<String> cbCiudadDestino = new JComboBox<String>();

    private JPanel panelEscalas = new JPanel();
    private JLabel lblEscala1 = new JLabel("Tiempo de espera en ");
    private JTextField txtEscala1 = new JTextField(TEXTFIELD_WIDTH);
    private JLabel lblEscala2 = new JLabel("Tiempo de espera en ");
    private JTextField txtEscala2 = new JTextField(TEXTFIELD_WIDTH);
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JLabel lblEscalas = new JLabel("Escalas");
    private JList<String> lstEscalas;
    private JScrollPane spEscalas;


    
    private JButton showInfo = new JButton("Mostrar info del vuelo seleccionado");
    private JButton editButton = new JButton("Editar");
    private JButton deleteButton = new JButton("Anular/Borrar");

    private JButton acceptButton = new JButton("Aceptar");
    private JButton cancelButton = new JButton("Cancelar");

    private VuelosController vuelosController;
    private LocacionesController locacionesController;
    private TripulantesController tripulantesController;
    private List<Vuelos> vuelosMatched;

    public ConsultaActualizacionGUI(VuelosController vuelosController, AvionesController avionesController, TripulantesController tripulantesController, LocacionesController locacionesController) {
        this.vuelosController = vuelosController;
        this.locacionesController = locacionesController;
        this.tripulantesController = tripulantesController;

        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        ventana.add(lblCriteria);
        ventana.add(cbCriteria);
        cbCriteria.setSelectedItem(null);

        ventana.add(searchLabel);
        ventana.add(searchField);
        ventana.add(searchButton);

        ventana.add(lblResultIds);
        ventana.add(cbResultIds);

        ventana.add(showInfo);

        ventana.add(editButton);
        ventana.add(deleteButton);

        lblIdVuelo.setVisible(false);
        txtIdVuelo.setVisible(false);
        txtIdVuelo.setEditable(false);  //not editable
        ventana.add(lblIdVuelo);
        ventana.add(txtIdVuelo);

        lblTipoVuelo.setVisible(false);
        txtTipoVuelo.setVisible(false);
        txtTipoVuelo.setEditable(false); //not editable
        ventana.add(lblTipoVuelo);
        ventana.add(txtTipoVuelo);

        lblAerolinea.setVisible(false);
        txtAerolinea.setVisible(false);
        txtAerolinea.setEditable(false);
        ventana.add(lblAerolinea);
        ventana.add(txtAerolinea);

        lblFechaDeCarga.setVisible(false);
        txtFechaDeCarga.setVisible(false);
        txtFechaDeCarga.setEditable(false); //not editable
        ventana.add(lblFechaDeCarga);
        ventana.add(txtFechaDeCarga);

        lblEstado.setVisible(false);
        cbEstado.setVisible(false);
        cbEstado.setSelectedItem(null);
        cbEstado.setEditable(false);
        cbEstado.setEnabled(false);
        ventana.add(lblEstado);
        ventana.add(cbEstado);

        lblDuracion.setVisible(false);
        txtDuracion.setVisible(false);
        txtDuracion.setEditable(false);
        ventana.add(lblDuracion);
        ventana.add(txtDuracion);

        lblAvion.setVisible(false);
        for (Aviones avion : avionesController.getAviones()) {
            cbAvion.addItem(avion.getModelo());
        }
        cbAvion.setVisible(false);
        cbAvion.setSelectedItem(null);
        cbAvion.setEditable(false);
        cbAvion.setEnabled(false);
        ventana.add(lblAvion);
        ventana.add(cbAvion);


        lblTripulacion.setVisible(false);
        final String[] tripulanteStrings = new String[tripulantesController.getTripulacion().size()];
        int index = 0;
        for (Tripulantes t : tripulantesController.getTripulacion()) {
            tripulanteStrings[index] = String.valueOf(t.getDniTripulante());
            index++;
        }
        lstTripulacion = new JList<String>(tripulanteStrings);
        lstTripulacion.setVisibleRowCount(5);
        lstTripulacion.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        spTripulacion = new JScrollPane(lstTripulacion);
        spTripulacion.setVisible(false);
        spTripulacion.setEnabled(false);
        lstTripulacion.setEnabled(false);
        ventana.add(lblTripulacion);
        ventana.add(spTripulacion);



        lblTieneEscalas.setVisible(false);
        chkTieneEscalas.setVisible(false);
        chkTieneEscalas.setEnabled(false);
        ventana.add(lblTieneEscalas);
        ventana.add(chkTieneEscalas);
        

        lblPermiteMascotas.setVisible(false);
        chkPermiteMascotas.setVisible(false);
        chkPermiteMascotas.setEnabled(false);
        ventana.add(lblPermiteMascotas);
        ventana.add(chkPermiteMascotas);

        lblPaisOrigen.setVisible(false);
        cbPaisOrigen.setVisible(false);
        cbPaisOrigen.setSelectedItem(null);
        cbPaisOrigen.setEditable(false);
        cbPaisOrigen.setEnabled(false);
        ventana.add(lblPaisOrigen);
        ventana.add(cbPaisOrigen);


        lblPaisDestino.setVisible(false);
        cbPaisDestino.setVisible(false);
        cbPaisDestino.setSelectedItem(null);
        cbPaisDestino.setEditable(false);
        cbPaisDestino.setEnabled(false);
        ventana.add(lblPaisDestino);
        //cargar combo
        

        ventana.add(cbPaisDestino);

        lblRequiereVisa.setVisible(false);
        chkRequiereVisa.setVisible(false);
        chkRequiereVisa.setEnabled(false);
        ventana.add(lblRequiereVisa);
        ventana.add(chkRequiereVisa);


        lblZonaHorariaDestino.setVisible(false);
        cbZonaHorariaDestino.setVisible(false);
        cbZonaHorariaDestino.setSelectedItem(null);
        cbZonaHorariaDestino.setEditable(false);
        cbZonaHorariaDestino.setEnabled(false);
        ventana.add(lblZonaHorariaDestino);
        ventana.add(cbZonaHorariaDestino);

        lblCiudadOrigen.setVisible(false);
        for(Locaciones l : locacionesController.getLocaciones()) {
            if(l.getNombrePais().equals("Argentina")) {
                cbCiudadOrigen.addItem(l.getNombreCiudad());
            }
        }
        cbCiudadOrigen.setVisible(false);
        cbCiudadOrigen.setSelectedItem(null);
        cbCiudadOrigen.setEditable(false);
        cbCiudadOrigen.setEnabled(false);
        ventana.add(lblCiudadOrigen);
        ventana.add(cbCiudadOrigen);


        lblCiudadDestino.setVisible(false);
        cbCiudadDestino.setVisible(false);
        cbCiudadDestino.setSelectedItem(null);
        cbCiudadDestino.setEditable(false);
        cbCiudadDestino.setEnabled(false);
        ventana.add(lblCiudadDestino);
        ventana.add(cbCiudadDestino);

        lblEscalas.setVisible(false);
        lstEscalas = new JList<String>(model);
        lstEscalas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstEscalas.setVisibleRowCount(5);
        spEscalas = new JScrollPane(lstEscalas);
        spEscalas.setVisible(false);
        ventana.add(spEscalas);
        lstEscalas.setEnabled(false);
        spEscalas.setEnabled(false);
        ventana.add(lblEscalas);
        ventana.add(spEscalas);

        panelEscalas.setLayout(new GridLayout(4, 1, 5, 5));
        panelEscalas.setVisible(false);
        panelEscalas.setEnabled(false);
        ventana.add(panelEscalas);



        
        ventana.add(acceptButton);
        acceptButton.setVisible(false);
        ventana.add(cancelButton);
        cancelButton.setVisible(false);


        acceptButton.setActionCommand("Validar_Datos_Completos");
        acceptButton.setActionCommand("Validar_Datos_Numericos");
        

        searchButton.addActionListener(this);

        cbPaisOrigen.addActionListener(this);
        cbPaisDestino.addActionListener(this);

        cbCiudadOrigen.addActionListener(this);
        cbCiudadDestino.addActionListener(this);

        showInfo.addActionListener(this);
        cbResultIds.addActionListener(this);

        lstTripulacion.addListSelectionListener(this);

        chkTieneEscalas.addActionListener(this);

        lstEscalas.addListSelectionListener(this);




        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);

        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String criteria = cbCriteria.getSelectedItem() != null ? cbCriteria.getSelectedItem().toString() : null;
        String searchValue = searchField.getText();
        String selectedId = cbResultIds.getSelectedItem() != null ? (String) cbResultIds.getSelectedItem(): "0";
        Vuelos selectedVuelo = null;

        if (source == searchButton) {
            setComponentsInvisible();
            editButton.setVisible(true);
            try {
                this.vuelosMatched = vuelosController.searchByCriteria(criteria, searchValue);
                cbResultIds.removeAllItems();
                for(Vuelos v : vuelosMatched) {
                    if(v != null) {
                        cbResultIds.addItem(String.valueOf(v.getIdVuelo()));
                    }
                }
                cbResultIds.setSelectedItem(null);

            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else if (source == cbResultIds) {
            setComponentsNonEditable();
            int selectedIdInt = 0;
            if(selectedId != null) {
                selectedIdInt = Integer.parseInt(selectedId);
                selectedVuelo = vuelosController.getVueloById(selectedIdInt);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un vuelo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            txtIdVuelo.setText(String.valueOf(selectedVuelo.getIdVuelo()));

            txtTipoVuelo.setText(selectedVuelo.getTipoVuelo() == 'I' ? "Internacional" : "Nacional");

            txtAerolinea.setText(selectedVuelo.getAerolinea());

            txtFechaDeCarga.setText(ValidacionArchivos.calendarToString(selectedVuelo.getFechaSistema()));

            cbEstado.setSelectedItem(selectedVuelo.getEstado() == 'R' ? "Retrasado" : selectedVuelo.getEstado() == 'C' ? "Cancelado" : "Programado");

            txtDuracion.setText(String.valueOf(selectedVuelo.getDuracion()));

            cbAvion.setSelectedItem(selectedVuelo.getAvion().getModelo());

            
            int[] indices = new int[Vuelos.getTotalTripulantes()];
            int cont = 0;
            for(int i = 0 ; i < tripulantesController.getTripulacion().size() ; i++) {
                for(Tripulantes t : selectedVuelo.getTripulacion()) {
                    if(tripulantesController.getTripulacion().get(i).getDniTripulante() == t.getDniTripulante()) {
                        indices[cont] = i;
                        cont++;
                    }
                }
            }
            lstTripulacion.setSelectedIndices(indices);

            chkTieneEscalas.setSelected(selectedVuelo.isTieneEscalas());

            chkPermiteMascotas.setSelected(selectedVuelo.isPermiteMascotas());

            if (selectedVuelo instanceof Internacionales) {
                Internacionales vueloInternacional = (Internacionales) selectedVuelo;

                if (vueloInternacional.getPaisOrigen() != null) {
                    cbPaisOrigen.removeAllItems();
                    for (String pais : locacionesController.getPaisesSet()) {
                        cbPaisOrigen.addItem(pais);
                    }
                    cbPaisOrigen.setSelectedItem(vueloInternacional.getPaisOrigen().getNombrePais());
                    lblPaisOrigen.setVisible(true);
                    cbPaisOrigen.setVisible(true);
                }
            
                if (vueloInternacional.getPaisDestino() != null) {
                    cbPaisDestino.removeAllItems();
                    for (String p : this.locacionesController.getPaisesSet()) {
                        if(cbPaisOrigen.getSelectedItem() != null && cbPaisOrigen.getSelectedItem().equals(p) ) {
                            continue;
                        } else {
                            cbPaisDestino.addItem(p);
                        }
                    }
                    cbPaisDestino.setSelectedItem(vueloInternacional.getPaisDestino().getNombrePais());
                    lblPaisDestino.setVisible(true);
                    cbPaisDestino.setVisible(true);
                }
            
                chkRequiereVisa.setSelected(vueloInternacional.isRequiereVisa());
            
                cbZonaHorariaDestino.setSelectedItem(vueloInternacional.getZonaHorariaDestino());

                lblRequiereVisa.setVisible(true);
                chkRequiereVisa.setVisible(true);
    
                lblZonaHorariaDestino.setVisible(true);
                cbZonaHorariaDestino.setVisible(true);
            } else if (selectedVuelo instanceof Nacionales) {
                Nacionales vueloNacional = (Nacionales) selectedVuelo;
            
                if (vueloNacional.getCiudadOrigen() != null) {
                    cbCiudadOrigen.setSelectedItem(vueloNacional.getCiudadOrigen().getNombreCiudad());
                    lblCiudadOrigen.setVisible(true);
                    cbCiudadOrigen.setVisible(true);
                }
            
                if (vueloNacional.getCiudadDestino() != null) {
                    cbCiudadDestino.removeAllItems();
                    for (Locaciones l : this.locacionesController.getLocaciones()) {
                        if(cbCiudadOrigen.getSelectedItem() != null && cbCiudadOrigen.getSelectedItem().equals(l.getNombreCiudad())) {
                            continue;
                        } else if (l.getNombrePais().equals("Argentina")) {
                            cbCiudadDestino.addItem(l.getNombreCiudad()); 
                        }
                    }
                    cbCiudadDestino.setSelectedItem(vueloNacional.getCiudadDestino().getNombreCiudad());
                    lblCiudadDestino.setVisible(true);
                    cbCiudadDestino.setVisible(true);
                }
            }
            

            lblIdVuelo.setVisible(true);
            txtIdVuelo.setVisible(true);

            lblTipoVuelo.setVisible(true);
            txtTipoVuelo.setVisible(true);

            lblAerolinea.setVisible(true);
            txtAerolinea.setVisible(true);

            lblFechaDeCarga.setVisible(true);
            txtFechaDeCarga.setVisible(true);

            lblEstado.setVisible(true);
            cbEstado.setVisible(true);

            lblDuracion.setVisible(true);
            txtDuracion.setVisible(true);

            lblAvion.setVisible(true);
            cbAvion.setVisible(true);

            lblTripulacion.setVisible(true);
            lstTripulacion.setVisible(true);
            spTripulacion.setVisible(true);

            lblTieneEscalas.setVisible(true);
            chkTieneEscalas.setVisible(true);

            lblPermiteMascotas.setVisible(true);
            chkPermiteMascotas.setVisible(true);

            if(chkTieneEscalas.isSelected()) {
                lblEscalas.setVisible(true);
                lstEscalas.setVisible(true);
                spEscalas.setVisible(true);
                panelEscalas.removeAll();
                panelEscalas.add(lblEscala1);
                panelEscalas.add(txtEscala1);
                panelEscalas.add(lblEscala2);
                panelEscalas.add(txtEscala2);
                panelEscalas.setVisible(true);
                panelEscalas.revalidate();
                panelEscalas.repaint();
                //TODO FIX THIS
                
                int[] indicesEscalas = new int[selectedVuelo.getEscalas().size()-1];
                int contEscalas = 0;
                String[] escalasNames = new String[selectedVuelo.getEscalas().size()-1];
                if (selectedVuelo instanceof Internacionales && selectedVuelo.getEscalas().size() == 2) {
                    for(Escalas esc : selectedVuelo.getEscalas()) {
                        if(esc.getDestino() != null && esc.getOrigen().equals((String)cbPaisOrigen.getSelectedItem())) {
                            escalasNames[contEscalas] = esc.getDestino();
                            contEscalas++;
                            txtEscala1.setText(String.valueOf(esc.getEspera()));
                        }
                    }
                } else if (selectedVuelo instanceof Internacionales && selectedVuelo.getEscalas().size() == 3) {
                    for(Escalas esc : selectedVuelo.getEscalas()) {
                        if(esc.getDestino() != null && esc.getOrigen().equals((String)cbPaisOrigen.getSelectedItem())) {
                            escalasNames[contEscalas] = esc.getDestino();
                            contEscalas++;
                            txtEscala1.setText(String.valueOf(esc.getEspera()));
                        } else if (esc.getOrigen() != null && esc.getDestino().equals((String) cbPaisDestino.getSelectedItem())) {
                            escalasNames[contEscalas] = esc.getOrigen();
                            contEscalas++;
                        } else if (!esc.getOrigen().equals((String)cbPaisOrigen.getSelectedItem()) && !esc.getDestino().equals((String) cbPaisDestino.getSelectedItem())
                        && !esc.getOrigen().equals((String)cbPaisDestino.getSelectedItem()) && !esc.getDestino().equals((String) cbPaisOrigen.getSelectedItem())) {
                            txtEscala2.setText(String.valueOf(esc.getEspera()));
                        }
                    }
                } else if (selectedVuelo instanceof Nacionales && selectedVuelo.getEscalas().size() == 2) {
                    for(Escalas esc : selectedVuelo.getEscalas()) {
                        if(esc.getDestino() != null && esc.getOrigen().equals((String)cbCiudadOrigen.getSelectedItem())) {
                            escalasNames[contEscalas] = esc.getDestino();
                            contEscalas++;
                            txtEscala1.setText(String.valueOf(esc.getEspera()));
                        }
                    }
                } else if (selectedVuelo instanceof Nacionales && selectedVuelo.getEscalas().size() == 3) {
                    for(Escalas esc : selectedVuelo.getEscalas()) {
                        if(esc.getDestino() != null && esc.getOrigen().equals((String)cbCiudadOrigen.getSelectedItem())) {
                            escalasNames[contEscalas] = esc.getDestino();
                            contEscalas++;
                            txtEscala1.setText(String.valueOf(esc.getEspera()));
                        } else if (esc.getOrigen() != null && esc.getDestino().equals((String) cbCiudadDestino.getSelectedItem())) {
                            escalasNames[contEscalas] = esc.getOrigen();
                            contEscalas++;
                        } else if (!esc.getOrigen().equals((String)cbCiudadOrigen.getSelectedItem()) && !esc.getDestino().equals((String) cbCiudadDestino.getSelectedItem())
                        && !esc.getOrigen().equals((String)cbCiudadDestino.getSelectedItem()) && !esc.getDestino().equals((String) cbCiudadOrigen.getSelectedItem())) {
                            txtEscala2.setText(String.valueOf(esc.getEspera()));
                        }
                    }
                }
                for(int i = 0 ; i < escalasNames.length ; i++) {
                    indicesEscalas[i] = model.indexOf(escalasNames[i]);
                }
                lstEscalas.setSelectedIndices(indicesEscalas);
            } else {
                lblEscalas.setVisible(false);
                lstEscalas.setVisible(false);
                spEscalas.setVisible(false);
                panelEscalas.setVisible(false);
            }
            
            editButton.setVisible(true);

            deleteButton.setVisible(true);

            acceptButton.setVisible(false);

            cancelButton.setVisible(false);



        } else if (source == editButton) {
            if(txtIdVuelo.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Seleccione un vuelo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            setComponentsEditable();
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            acceptButton.setVisible(true);
            cancelButton.setVisible(true);


        } else if (source == deleteButton) {

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

        } else if (source == cbPaisDestino) {
            model.removeAllElements();
            for (String p : this.locacionesController.getPaisesSet()) {
                if(cbPaisOrigen.getSelectedItem() != null && cbPaisDestino.getSelectedItem() != null && (cbPaisDestino.getSelectedItem().equals(p) || cbPaisOrigen.getSelectedItem().equals(p))) {
                    model.removeElement(p);
                    continue;
                } else {
                    model.addElement(p);
                }
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
        } else if (source == acceptButton) {
            String idVuelo = txtIdVuelo.getText();
            String tipoVuelo = txtTipoVuelo.getText();
            String aerolinea = txtAerolinea.getText();
            Calendar fechaSistema = ValidacionArchivos.parseCalendar(txtFechaDeCarga.getText());
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
            boolean requiereVisa = chkRequiereVisa.isSelected();
            String zonaHorariaDestino = cbZonaHorariaDestino.getSelectedItem() != null ? cbZonaHorariaDestino.getSelectedItem().toString() : null;
            String ciudadOrigen = cbCiudadOrigen.getSelectedItem() != null ? cbCiudadOrigen.getSelectedItem().toString() : null;
            String ciudadDestino = cbCiudadDestino.getSelectedItem() != null ? cbCiudadDestino.getSelectedItem().toString() : null;
            if(e.getActionCommand().equals("Validar_Datos_Numericos") || e.getActionCommand().equals("Validar_Datos_Completos")) {
                if(idVuelo.isEmpty() || tipoVuelo.isEmpty() || aerolinea.isEmpty() || estado == null || duracion.isEmpty() || modeloAvion == null || tripulantes.length == 0  || (tipoVuelo.equals("Internacional") && (chkRequiereVisa == null || zonaHorariaDestino == null || paisOrigen == null || paisDestino == null)) || (tipoVuelo.equals("Nacional") && (ciudadOrigen == null || ciudadDestino == null)) || (tieneEscalas && escalas.length == 0) || (tieneEscalas && escalas.length == 1 && escala1Duracion.isEmpty()) || (tieneEscalas && escalas.length == 2 && escala2Duracion.isEmpty()) ) {
                    JOptionPane.showMessageDialog(null, "Debe completar todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
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
                int ingresoConfirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea actualizar el vuelo "+ idVuelo +" con estos datos?", "Confirmar ingreso de datos", JOptionPane.YES_NO_OPTION);
                if(ingresoConfirm == JOptionPane.YES_OPTION) {
                    try {
                        final int idVueloInt = Integer.parseInt(idVuelo);
                        final double duracionDouble = Double.parseDouble(duracion);
                        final char estadoChar = estado.charAt(0);
                        final int escala1Int = !escala1Duracion.isEmpty() ? Integer.parseInt(escala1Duracion) : 0;
                        final int escala2Int = !escala2Duracion.isEmpty() ? Integer.parseInt(escala2Duracion) : 0;
                        vuelosController.actualizarListadoVuelos(idVueloInt, tipoVuelo.charAt(0), aerolinea, fechaSistema, estadoChar, duracionDouble, modeloAvion, tripulantes, tieneEscalas, permiteMascotas, paisOrigen, paisDestino, requiereVisa, zonaHorariaDestino, ciudadOrigen, ciudadDestino, escalas, escala1Int, escala2Int);
                        JOptionPane.showMessageDialog(null, "Datos actualizados correctamente", "Ingreso de datos", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    }
                }
            }

        } else if (source == cancelButton) {
            int cancelOption = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el ingreso de datos?", "Cancelar ingreso de datos", JOptionPane.YES_NO_OPTION);
            if(cancelOption == JOptionPane.YES_OPTION) {
                clearFields();
                setComponentsInvisible();
                editButton.setVisible(true);
            }
        } 
        
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

    public void setComponentsInvisible() {
        lblIdVuelo.setVisible(false);
        txtIdVuelo.setVisible(false);

        lblTipoVuelo.setVisible(false);
        txtTipoVuelo.setVisible(false);

        lblAerolinea.setVisible(false);
        txtAerolinea.setVisible(false);

        lblFechaDeCarga.setVisible(false);
        txtFechaDeCarga.setVisible(false);

        lblEstado.setVisible(false);
        cbEstado.setVisible(false);

        lblDuracion.setVisible(false);
        txtDuracion.setVisible(false);

        lblAvion.setVisible(false);
        cbAvion.setVisible(false);

        lblTripulacion.setVisible(false);
        lstTripulacion.setVisible(false);
        spTripulacion.setVisible(false);

        lblTieneEscalas.setVisible(false);
        chkTieneEscalas.setVisible(false);

        lblPermiteMascotas.setVisible(false);
        chkPermiteMascotas.setVisible(false);

        lblPaisOrigen.setVisible(false);
        cbPaisOrigen.setVisible(false);

        lblPaisDestino.setVisible(false);
        cbPaisDestino.setVisible(false);

        lblRequiereVisa.setVisible(false);
        chkRequiereVisa.setVisible(false);

        lblZonaHorariaDestino.setVisible(false);
        cbZonaHorariaDestino.setVisible(false);

        lblCiudadOrigen.setVisible(false);
        cbCiudadOrigen.setVisible(false);

        lblCiudadDestino.setVisible(false);
        cbCiudadDestino.setVisible(false);

        lblEscalas.setVisible(false);
        lstEscalas.setVisible(false);
        spEscalas.setVisible(false);

        panelEscalas.setVisible(false);

        acceptButton.setVisible(false);

        cancelButton.setVisible(false);
    }

    public void setComponentsEditable() {

        txtAerolinea.setEditable(true);

        cbEstado.setEnabled(true);
        cbEstado.setEditable(true);

        txtDuracion.setEditable(true);

        cbAvion.setEnabled(true);
        cbAvion.setEditable(true);

        lstTripulacion.setEnabled(true);
        spTripulacion.setEnabled(true);

        chkTieneEscalas.setEnabled(true);

        chkPermiteMascotas.setEnabled(true);

        cbPaisOrigen.setEnabled(true);
        cbPaisOrigen.setEditable(true);

        cbPaisDestino.setEnabled(true);
        cbPaisDestino.setEditable(true);

        chkRequiereVisa.setEnabled(true);

        cbZonaHorariaDestino.setEnabled(true);
        cbZonaHorariaDestino.setEditable(true);

        cbCiudadOrigen.setEnabled(true);
        cbCiudadOrigen.setEditable(true);

        cbCiudadDestino.setEnabled(true);
        cbCiudadDestino.setEditable(true);

        lstEscalas.setEnabled(true);

        panelEscalas.setEnabled(true);
        txtEscala1.setEditable(true);
        txtEscala2.setEditable(true);

        acceptButton.setVisible(true);

        cancelButton.setVisible(true);
    }

    public void setComponentsNonEditable() {
        txtAerolinea.setEditable(false);

        cbEstado.setEnabled(false);
        cbEstado.setEditable(false);

        txtDuracion.setEditable(false);

        cbAvion.setEnabled(false);
        cbAvion.setEditable(false);

        lstTripulacion.setEnabled(false);
        spTripulacion.setEnabled(false);

        chkTieneEscalas.setEnabled(false);

        chkPermiteMascotas.setEnabled(false);

        cbPaisOrigen.setEnabled(false);
        cbPaisOrigen.setEditable(false);

        cbPaisDestino.setEnabled(false);
        cbPaisDestino.setEditable(false);

        chkRequiereVisa.setEnabled(false);

        cbZonaHorariaDestino.setEnabled(false);
        cbZonaHorariaDestino.setEditable(false);

        cbCiudadOrigen.setEnabled(false);
        cbCiudadOrigen.setEditable(false);

        cbCiudadDestino.setEnabled(false);
        cbCiudadDestino.setEditable(false);

        lstEscalas.setEnabled(false);

        txtEscala1.setEditable(false);
        txtEscala2.setEditable(false);
        panelEscalas.setEnabled(false);

        acceptButton.setVisible(false);

        cancelButton.setVisible(false);
    }

    private void clearFields() {
        setComponentsNonEditable();
            
        txtIdVuelo.setText("");
        txtTipoVuelo.setText("");
        txtAerolinea.setText("");
        cbEstado.setSelectedItem(null);
        txtDuracion.setText("");
        cbAvion.setSelectedItem(null);
        chkTieneEscalas.setSelected(false);
        chkPermiteMascotas.setSelected(true);
        cbPaisOrigen.setSelectedItem(null);
        cbPaisDestino.setSelectedItem(null);
        chkRequiereVisa.setSelected(false);;
        cbZonaHorariaDestino.setSelectedItem(null);
        cbCiudadOrigen.setSelectedItem(null);
        cbCiudadDestino.setSelectedItem(null);
        lstEscalas.clearSelection();
        lblPaisOrigen.setVisible(false);
        cbPaisOrigen.setVisible(false);
        lblPaisDestino.setVisible(false);
        cbPaisDestino.setVisible(false);
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

}
