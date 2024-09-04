package model;


public class Escalas {

    private String origen;
    private String destino;
    private int espera;

    public Escalas() {
    }

    public Escalas(String origen, String destino, int espera) {
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

    public int getEspera() {
        return espera;
    }

    public void setEspera(int espera) {
        this.espera = espera;
    }


}