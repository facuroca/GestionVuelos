package model;

import java.util.Calendar;
import java.util.List;

public class Internacionales extends Vuelos{

    private Locaciones paisOrigen;
    private Locaciones paisDestino;
    private boolean requiereVisa;
    private String zonaHorariaDestino;

    public Internacionales() {
    }

    public Internacionales(int idVuelo, char tipoVuelo, String aerolinea, Calendar fechaSistema, char estado, double duracion, Aviones avion,
                            List<Tripulantes> tripulacion, boolean tieneEscalas, boolean permiteMascotas, Locaciones paisOrigen, Locaciones paisDestino, boolean requiereVisa, String zonaHorariaDestino) {
        super(idVuelo, tipoVuelo, aerolinea, fechaSistema, estado, duracion, avion, tripulacion, tieneEscalas, permiteMascotas);
        this.paisOrigen = paisOrigen;
        this.paisDestino = paisDestino;
        this.requiereVisa = requiereVisa;
        this.zonaHorariaDestino = zonaHorariaDestino;
    }

    public Locaciones getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(Locaciones paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public Locaciones getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(Locaciones paisDestino) {
        this.paisDestino = paisDestino;
    }

    public boolean isRequiereVisa() {
        return requiereVisa;
    }

    public void setRequiereVisa(boolean requiereVisa) {
        this.requiereVisa = requiereVisa;
    }

    public String getZonaHorariaDestino() {
        return zonaHorariaDestino;
    }

    public void setZonaHorariaDestino(String zonaHorariaDestino) {
        this.zonaHorariaDestino = zonaHorariaDestino;
    }
}