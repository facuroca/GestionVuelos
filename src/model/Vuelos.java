package model;

import java.util.*;

public abstract class Vuelos {
    protected int idVuelo;
    protected char tipoVuelo;
    protected String aerolinea;
    protected Calendar fechaSistema;
    protected char estado;
    protected double duracion;
    protected Aviones avion;
    protected List<Tripulantes> tripulacion = new ArrayList<Tripulantes>();
    protected boolean tieneEscalas;
    protected boolean permiteMascotas;
    protected List<Escalas> escalas = new ArrayList<Escalas>();
    protected final static int MAX_ESCALAS = 3;
    protected final static int TOTAL_TRIPULANTES = 5;

    public Vuelos() {
            for (int i = 0; i < MAX_ESCALAS; i++) {
                this.escalas.add(new Escalas());
            }
    }

    public Vuelos(int idVuelo, char tipoVuelo, String aerolinea, Calendar fechaSistema, char estado, double duracion, Aviones avion, List<Tripulantes> tripulacion, boolean tieneEscalas, boolean permiteMascotas) {
            for (int i = 0; i < MAX_ESCALAS; i++) {
                this.escalas.add(new Escalas());
            }
        this.idVuelo = idVuelo;
        this.tipoVuelo = tipoVuelo;
        this.aerolinea = aerolinea;
        this.fechaSistema = fechaSistema;
        this.estado = estado;
        this.duracion = duracion;
        this.avion = avion;
        this.tripulacion = tripulacion;
        this.tieneEscalas = tieneEscalas;
        this.permiteMascotas = permiteMascotas;
    }
    
    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public char getTipoVuelo() {
        return tipoVuelo;
    }

    public void setTipoVuelo(char tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Calendar getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(Calendar fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Aviones getAvion() {
        return avion;
    }

    public void setAvion(Aviones avion) {
        this.avion = avion;
    }

    public List<Tripulantes> getTripulacion() {
        return tripulacion;
    }

    public void setTripulacion(List<Tripulantes> tripulacion) {
        this.tripulacion = tripulacion;
    }

    public boolean isTieneEscalas() {
        return tieneEscalas;
    }

    public void setTieneEscalas(boolean tieneEscalas) {
        this.tieneEscalas = tieneEscalas;
    }

    public List<Escalas> getEscalas() {
        return escalas;
    }

    public void setEscala(int index, String origen, String destino, double espera) {
        this.escalas.get(index).setOrigen(origen);
        this.escalas.get(index).setDestino(destino);
        this.escalas.get(index).setEspera(espera);
    }

    public void setEscalas(List<Escalas> escalas) {
        this.escalas = escalas;
    }

    public boolean isPermiteMascotas() {
        return permiteMascotas;
    }

    public void setPermiteMascotas(boolean permiteMascotas) {
        this.permiteMascotas = permiteMascotas;
    }

    public static int getMaxEscalas() {
        return MAX_ESCALAS;
    }

    public static int getTotalTripulantes() {
        return TOTAL_TRIPULANTES;
    }

    
}
