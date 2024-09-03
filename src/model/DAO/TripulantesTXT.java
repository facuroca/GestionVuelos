package model.DAO;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import model.Tripulantes;


public class TripulantesTXT {

    private final static String tripulantesPath = "src/model/DAO/tripulantes.txt";

    public static List<Tripulantes> leerTripulantes(List<Tripulantes> tripulantes) {
        File in = null;
        Scanner tripulantesScanner = null;

        try {
            in = new File(tripulantesPath);
            tripulantesScanner = new Scanner(in);
            int index = 0;
            while (tripulantesScanner.hasNext()) {
                String linea = tripulantesScanner.nextLine();
                String[] parsed = linea.split(";");
                Tripulantes t = tripulantes.get(index);
                t.setDniTripulante(ValidacionArchivos.aInt(parsed[0].trim()));
                t.setNombre(parsed[1].trim());
                t.setApellido(parsed[2].trim());
                t.setRol(parsed[3].trim());
                t.setEdad(ValidacionArchivos.aInt(parsed[4].trim()));
                t.setAntiguedad(ValidacionArchivos.aInt(parsed[5].trim()));
                t.setSueldo(ValidacionArchivos.esDouble(parsed[6].trim()));
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripulantes;
    }
    
}
