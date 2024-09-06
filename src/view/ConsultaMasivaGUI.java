package view;

// Esta pantalla contará con dos cuadros de texto, a modo de filtro, donde el usuario podrá completar la información a visualizar.
// El sistema mostrará en una grilla de consulta de registros, con no menos de 6 columnas, todos aquellos que coincidan total o parcialmente con el 
// o los criterios de búsquedas ingresados. En caso, que el usuario no ingrese ningún filtro, se mostrarán la totalidad de los registros.
// Mostrar dichos registros ordenados en forma ascendente, según algún criterio que considere oportuno. La pantalla deberá incluir la cantidad total
// de registros visualizados y la cantidad total de registros existentes.


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.VuelosController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultaMasivaGUI implements ActionListener {
    private static final int WIDTH = 950;
    private static final int HEIGHT = 600;
    private static final int TEXTFIELD_WIDTH = 12;


    private JFrame ventana = new JFrame("Consulta Masiva");
    private BorderLayout layout = new BorderLayout();

    private JPanel filterPanel = new JPanel();
    private JLabel lblFilter1 = new JLabel("Filtro 1:");
    private JTextField filterField1 = new JTextField(TEXTFIELD_WIDTH);
    private JLabel lblFilter2 = new JLabel("Filtro 2:");
    private JTextField filterField2 = new JTextField(TEXTFIELD_WIDTH);
    private JButton searchButton = new JButton("Buscar");

    private JPanel infoPanel = new JPanel();
    private JLabel totalRecordsLabel = new JLabel("Total de registros: 0");
    private JLabel displayedRecordsLabel = new JLabel("Registros visualizados: 0");

    private JScrollPane spRecordsTable;
    private DefaultTableModel tableModel;
    private JTable tblRecordsTable;

    private List<String[]> allRecords;

    private VuelosController vuelosController;
    
    public ConsultaMasivaGUI(VuelosController vuelosController) {
        this.vuelosController = vuelosController;
        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        String[] columnNames = {"ID Vuelo", "Tipo de Vuelo", "Aerolinea", "Estado", "Duracion", "Avion", "Tiene escalas?", "Permite mascotas?", "Origen", "Destino"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblRecordsTable = new JTable(tableModel);

        filterPanel.add(lblFilter1);
        filterPanel.add(filterField1);
        filterPanel.add(lblFilter2);
        filterPanel.add(filterField2);
        filterPanel.add(searchButton);

        infoPanel.add(totalRecordsLabel);
        infoPanel.add(displayedRecordsLabel);

        spRecordsTable = new JScrollPane(tblRecordsTable);
        ventana.add(filterPanel, BorderLayout.NORTH);
        ventana.add(spRecordsTable, BorderLayout.CENTER);
        ventana.add(infoPanel, BorderLayout.SOUTH);

        loadAllRecords();

        searchButton.addActionListener(this);

        displayRecords(allRecords);

        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == searchButton) {
            filterRecords();
        }
    }

    private void loadAllRecords() {
        allRecords = vuelosController.getVuelosString();
        totalRecordsLabel.setText("Total de registros: " + allRecords.size());
    }

    private void filterRecords() {
        String filter1 = filterField1.getText().toLowerCase();
        String filter2 = filterField2.getText().toLowerCase();

        loadAllRecords();

        List<String[]> filteredRecords = new ArrayList<>();

        for (String[] record : allRecords) {
            boolean matchesFilter1 = filter1.isEmpty() || record[0].toLowerCase().contains(filter1) || record[1].toLowerCase().contains(filter1) ||
             record[2].toLowerCase().contains(filter1) || record[3].toLowerCase().contains(filter1) || record[4].toLowerCase().contains(filter1) ||
              record[5].toLowerCase().contains(filter1) || record[6].toLowerCase().equals(filter1) || record[7].toLowerCase().equals(filter1) ||
               record[8].toLowerCase().contains(filter1) || record[9].toLowerCase().contains(filter1);

            boolean matchesFilter2 = filter2.isEmpty() || record[1].toLowerCase().contains(filter2) || record[2].toLowerCase().contains(filter2) ||
             record[3].toLowerCase().contains(filter2) || record[4].toLowerCase().contains(filter2) || record[5].toLowerCase().contains(filter2) ||
              record[6].toLowerCase().equals(filter2) || record[7].toLowerCase().equals(filter2) || record[8].toLowerCase().contains(filter2) ||
               record[9].toLowerCase().contains(filter2);

            if (matchesFilter1 && matchesFilter2) {
                filteredRecords.add(record);
            }
        }
        displayRecords(filteredRecords);
    }

    private void displayRecords(List<String[]> records) {
        tableModel.setRowCount(0);
        for (String[] record : records) {
            tableModel.addRow(record);
        }
        displayedRecordsLabel.setText("Registros visualizados: " + records.size());
    }

}