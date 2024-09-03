package model.DAO;

public class AvionInexistenteExcep extends Exception {
    public AvionInexistenteExcep() {
        super();
    }

    public AvionInexistenteExcep(String mensaje) {
        super(mensaje);
    }
}