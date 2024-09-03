package model;

public class Locaciones {
    
    private int idPais;
    private String nombrePais;
    private int idCiudad;
    private String nombreCiudad;

    public Locaciones() {
    }

    public Locaciones(int idPais, String nombrePais, int idCiudad, String nombreCiudad) {
        this.idPais = idPais;
        this.nombrePais = nombrePais;
        this.idCiudad = idCiudad;
        this.nombreCiudad = nombreCiudad;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
}
