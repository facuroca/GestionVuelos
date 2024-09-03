package view;

// Esta pantalla contará con dos cuadros de texto, a modo de filtro, donde el usuario podrá completar la información a visualizar.
// El sistema mostrará en una grilla de consulta de registros, con no menos de 6 columnas, todos aquellos que coincidan total o parcialmente con el 
// o los criterios de búsquedas ingresados. En caso, que el usuario no ingrese ningún filtro, se mostrarán la totalidad de los registros.
// Mostrar dichos registros ordenados en forma ascendente, según algún criterio que considere oportuno. La pantalla deberá incluir la cantidad total
//  de registros visualizados y la cantidad total de registros existentes.

import java.awt.FlowLayout;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConsultaMasivaGUI implements ActionListener{
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JFrame ventana = new JFrame("Consulta Masiva");
    private FlowLayout layout = new FlowLayout();

    

    public ConsultaMasivaGUI() {
        ventana.setSize(WIDTH, HEIGHT);
        ventana.setLayout(layout);

        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }




    
}
