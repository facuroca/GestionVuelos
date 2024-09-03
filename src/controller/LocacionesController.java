package controller;

import model.Locaciones;
import java.util.ArrayList;
import java.util.List;
import model.DAO.LocacionesTXT;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;



public class LocacionesController {
    private List<Locaciones> locaciones = new ArrayList<Locaciones>();

    public LocacionesController() {
        try {
            long lineasLocaciones = Files.lines(Paths.get("src/model/DAO/locaciones.txt")).count();
            for (int i = 0; i < lineasLocaciones; i++) {
                locaciones.add(new Locaciones());
            }
            locaciones = LocacionesTXT.leerLocaciones(locaciones);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Locaciones> getLocaciones() {
        return locaciones;
    }

    public Set<String> getPaisesSet() {
        Set<String> locacionesSet = new HashSet<String>();
        for (Locaciones locacion : locaciones) {
            locacionesSet.add(locacion.getNombrePais());
        }
        return locacionesSet;
    }

    public Set<String> getCiudadesSet() {
        Set<String> locacionesSet = new HashSet<String>();
        for (Locaciones locacion : locaciones) {
            locacionesSet.add(locacion.getNombreCiudad());
        }
        return locacionesSet;
    }
}
