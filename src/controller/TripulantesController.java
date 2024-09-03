package controller;

import java.util.ArrayList;
import java.util.List;
import model.Tripulantes;
import model.DAO.Estadisticas2JSON;
import model.DAO.TripulantesTXT;

import java.nio.file.Files;
import java.nio.file.Paths;



public class TripulantesController {
    private List<Tripulantes> tripulacion = new ArrayList<Tripulantes>();

    public TripulantesController() {
        try {
            long lineasTripulantes = Files.lines(Paths.get("src/model/DAO/tripulantes.txt")).count();
            for (int i = 0; i < lineasTripulantes; i++) {
                tripulacion.add(new Tripulantes());
            }
            tripulacion = TripulantesTXT.leerTripulantes(tripulacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Tripulantes> getTripulacion() {
        return tripulacion;
    }

        public List<Tripulantes> item2CalculoTripulantes(String arg) {
        List<Tripulantes> tripulantesMatched = new ArrayList<Tripulantes>();
        for (Tripulantes t : tripulacion) {
            try {
                if (!t.getNombre().equals(arg) && !t.getApellido().equals(arg) &&
                    t.getDniTripulante() != Integer.parseInt(arg) && !t.getRol().equals(arg) && 
                    t.getEdad() != Integer.parseInt(arg) && t.getAntiguedad() != Double.parseDouble(arg) && 
                    t.getSueldo() != Integer.parseInt(arg)) {
                    tripulantesMatched.add(t);
                }
            } catch (NumberFormatException e) {
                if (!t.getNombre().equals(arg) && !t.getApellido().equals(arg) && !t.getRol().equals(arg)) {
                    tripulantesMatched.add(t);
                }
            }
        }
        Estadisticas2JSON.escribirEstadisticas2Stream(tripulantesMatched);
        return tripulantesMatched;
    }
    
}
