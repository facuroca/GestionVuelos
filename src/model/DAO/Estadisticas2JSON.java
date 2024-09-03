package model.DAO;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.File;



import model.Tripulantes;



public class Estadisticas2JSON {

    private final static String estadisticas2Path = "src/model/DAO/estadisticas2.json";

    public static void escribirEstadisticas2Stream(List<Tripulantes> tripulantesMatched) {
        List<JsonObject> existingTripulantes = new ArrayList<JsonObject>();
        Calendar today = Calendar.getInstance();

        File estadisticas2File = new File(estadisticas2Path);
        if(estadisticas2File.exists() && estadisticas2File.length() > 0) {
            try (InputStream is = new FileInputStream(estadisticas2Path)) {
                JsonReader reader = Json.createReader(is);
                JsonObject jsonObject = reader.readObject();
                JsonArray jsonArray = jsonObject.getJsonArray("tripulantesMatched");
                if (jsonArray != null) {
                    for (JsonValue value : jsonArray) {
                        existingTripulantes.add(value.asJsonObject());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Tripulantes t : tripulantesMatched) {
            JsonObject tripulanteJson = Json.createObjectBuilder()
                .add("fechaGeneracion", ValidacionArchivos.calendarToString(today))
                .add("dniTripulante", t.getDniTripulante())
                .add("nombre", t.getNombre())
                .add("apellido", t.getApellido())
                .add("rol", t.getRol())
                .add("edad", t.getEdad())
                .add("antiguedad", t.getAntiguedad())
                .add("sueldo", t.getSueldo())
                .build();
            existingTripulantes.add(tripulanteJson);
        }
        try (OutputStream os = new FileOutputStream(estadisticas2Path);
             JsonGenerator gen = Json.createGenerator(os)) {
            gen.writeStartObject();
            gen.writeStartArray("tripulantesMatched");
            for (JsonObject tripulante : existingTripulantes) {
                gen.write(tripulante);
            }
            gen.writeEnd();
            gen.writeEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
