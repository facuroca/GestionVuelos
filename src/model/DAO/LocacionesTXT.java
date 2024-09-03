package model.DAO;

import java.util.List;
import model.Locaciones;
import java.io.File;
import java.util.Scanner;


public class LocacionesTXT {

    private final static String locacionesPath = "src/model/DAO/locaciones.txt";

    //ancho fijo
    //idPais(2) nombrePais(17) idCiudad(2) nombreCiudad(17)
    //01Argentina        01Buenos Aires     
    public static List<Locaciones> leerLocaciones(List<Locaciones> locaciones) {
        File in = null;
        Scanner locacionesScanner = null;
        int index = 0;
        try {
            in = new File(locacionesPath);
            locacionesScanner = new Scanner(in);
            while (locacionesScanner.hasNextLine()) {
                Locaciones l = locaciones.get(index);
                String linea = locacionesScanner.nextLine();
                l.setIdPais(ValidacionArchivos.aInt(linea.substring(0, 2).trim()));
                l.setNombrePais(linea.substring(2, 19).trim());
                l.setIdCiudad(ValidacionArchivos.aInt(linea.substring(19, 21).trim()));
                l.setNombreCiudad(linea.substring(21, 38).trim());
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locacionesScanner.close();
        }
        return locaciones;
    }
    
    public static String getLocacionesPath() {
        return locacionesPath;
    }
}
