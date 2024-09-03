import controller.AvionesController;
import controller.LocacionesController;
import controller.TripulantesController;
import controller.VuelosController;
import view.MainGUI;

public class Principal {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Debe ingresar un argumento");
            return;
        }
        LocacionesController locacionesController = new LocacionesController();
        TripulantesController tripulantesController = new TripulantesController();
        AvionesController avionesController = new AvionesController();
        VuelosController vuelosController = new VuelosController();


        MainGUI gui = new MainGUI(avionesController, locacionesController, tripulantesController, vuelosController, args[0]);
    }
    
}
