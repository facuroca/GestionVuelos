package model;


public class Escalas {

    private String origen;
    private String destino;
    private double espera;

    public Escalas() {
    }

    public Escalas(String origen, String destino, double espera) {
        this.origen = origen;
        this.destino = destino;
        this.espera = espera;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getEspera() {
        return espera;
    }

    public void setEspera(double espera) {
        this.espera = espera;
    }


}