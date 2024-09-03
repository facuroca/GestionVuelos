package controller;

import java.util.ArrayList;
import java.util.List;
import model.Aviones;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.DAO.AvionesTXT;


public class AvionesController {

    private List<Aviones> aviones = new ArrayList<Aviones>();

    public AvionesController() {
        try {
            long lineasAviones = Files.lines(Paths.get("src/model/DAO/aviones.txt")).count();
            for (int i = 0; i < lineasAviones; i++) {
                aviones.add(new Aviones());
            }
            aviones = AvionesTXT.leerAviones(aviones);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Aviones> getAviones() {
        return aviones;
    }
    
}
