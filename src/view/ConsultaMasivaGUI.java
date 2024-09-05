package view;

// Esta pantalla contará con dos cuadros de texto, a modo de filtro, donde el usuario podrá completar la información a visualizar.
// El sistema mostrará en una grilla de consulta de registros, con no menos de 6 columnas, todos aquellos que coincidan total o parcialmente con el 
// o los criterios de búsquedas ingresados. En caso, que el usuario no ingrese ningún filtro, se mostrarán la totalidad de los registros.
// Mostrar dichos registros ordenados en forma ascendente, según algún criterio que considere oportuno. La pantalla deberá incluir la cantidad total
// de registros visualizados y la cantidad total de registros existentes.

import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.VuelosController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultaMasivaGUI implements ActionListener {
    private static final int WIDTH = 800;
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

    private List<String[]> allRecords; // This should be replaced with your actual data source

    private VuelosController vuelosController;
    
    public ConsultaMasivaGUI(VuelosController vuelosController) {
        this.vuelosController = vuelosController;
        ventana.setSize(800, 600);
        ventana.setLayout(layout);


        // Initialize table
        String[] columnNames = {"ID Vuelo", "Tipo de Vuelo", "Aerolinea", "Estado", "Duracion", "Avion", "Tiene escalas?", "Origen", "Destino"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblRecordsTable = new JTable(tableModel);

        // Layout setup
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

        // Load all records (this should be replaced with your actual data loading logic)
        loadAllRecords();

        // Add action listener to the search button
        searchButton.addActionListener(this);

        // Display all records initially
        displayRecords(allRecords);

        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

    }

    private void loadAllRecords() {
        // This method should load all records from your data source
        allRecords = new ArrayList<>();
        // Example data
        allRecords.add(new String[]{"A1", "B1", "C1", "D1", "E1", "F1"});
        allRecords.add(new String[]{"A2", "B2", "C2", "D2", "E2", "F2"});
        // Add more records as needed
        totalRecordsLabel.setText("Total de registros: " + allRecords.size());
    }

    private void filterRecords() {
        String filter1 = filterField1.getText().toLowerCase();
        String filter2 = filterField2.getText().toLowerCase();

        List<String[]> filteredRecords = new ArrayList<>();
        for (String[] record : allRecords) {
            boolean matchesFilter1 = filter1.isEmpty() || record[0].toLowerCase().contains(filter1);
            boolean matchesFilter2 = filter2.isEmpty() || record[1].toLowerCase().contains(filter2);
            if (matchesFilter1 && matchesFilter2) {
                filteredRecords.add(record);
            }
        }

        // Sort records (example: sort by the first column)
        filteredRecords.sort((r1, r2) -> r1[0].compareTo(r2[0]));

        displayRecords(filteredRecords);
    }

    private void displayRecords(List<String[]> records) {
        tableModel.setRowCount(0); // Clear existing rows
        for (String[] record : records) {
            tableModel.addRow(record);
        }
        displayedRecordsLabel.setText("Registros visualizados: " + records.size());
    }

}