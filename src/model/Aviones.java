package model;

public class Aviones {

    private int idAvion;
    private String modelo;
    private int capacidad;
    private int antiguedad;
    private int capacidadTanque;

    public Aviones() {
    }

    public Aviones(int idAvion, String modelo, int capacidad, int antiguedad, int capacidadTanque) {
        this.idAvion = idAvion;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.antiguedad = antiguedad;
        this.capacidadTanque = capacidadTanque;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public int getCapacidadTanque() {
        return capacidadTanque;
    }

    public void setCapacidadTanque(int capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

}