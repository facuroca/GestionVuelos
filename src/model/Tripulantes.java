package model;

public class Tripulantes {

    private int dniTripulante;
    private String nombre;
    private String apellido;
    private String rol;
    private int edad;
    private int antiguedad;
    private double sueldo;

    public Tripulantes() {
    }

    public Tripulantes(int dniTripulante, String nombre, String apellido, String rol, int edad, int antiguedad, double sueldo) {
        this.dniTripulante = dniTripulante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.edad = edad;
        this.antiguedad = antiguedad;
        this.sueldo = sueldo;
    }

    public int getDniTripulante() {
        return dniTripulante;
    }

    public void setDniTripulante(int dniTripulante) {
        this.dniTripulante = dniTripulante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }



}