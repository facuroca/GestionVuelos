package model;

import java.util.Calendar;
import java.util.List;

public class Nacionales extends Vuelos{

    private Locaciones ciudadOrigen;
    private Locaciones ciudadDestino;

    public Nacionales() {
    }

    public Nacionales(int idVuelo, char tipoVuelo, String aerolinea, Calendar fechaSistema, char estado, double duracion, Aviones avion,
     List<Tripulantes> tripulacion, boolean tieneEscalas, boolean permiteMascotas, Locaciones ciudadOrigen, Locaciones ciudadDestino) {
        super(idVuelo, tipoVuelo, aerolinea, fechaSistema, estado, duracion, avion, tripulacion, tieneEscalas, permiteMascotas);
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
    }

    public Locaciones getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Locaciones ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Locaciones getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Locaciones ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

}