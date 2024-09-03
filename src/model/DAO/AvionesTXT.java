package model.DAO;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import model.Aviones;


public class AvionesTXT {

    private final static String avionesPath = "src/model/DAO/aviones.txt";

    public static List<Aviones> leerAviones(List<Aviones> aviones) {
        File in = null;
        Scanner avionesScanner = null;

        try {
            in = new File(avionesPath);
            avionesScanner = new Scanner(in);
            int index = 0;
            while (avionesScanner.hasNext()) {
                String linea = avionesScanner.nextLine();
                String[] parsed = linea.split(";");
                Aviones a = aviones.get(index);
                a.setIdAvion(ValidacionArchivos.aInt(parsed[0].trim()));
                a.setModelo(parsed[1].trim());
                a.setCapacidad(ValidacionArchivos.aInt(parsed[2].trim()));
                a.setAntiguedad(ValidacionArchivos.aInt(parsed[3].trim()));
                a.setCapacidadTanque(ValidacionArchivos.aInt(parsed[4].trim()));
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aviones;
    }
    
}
