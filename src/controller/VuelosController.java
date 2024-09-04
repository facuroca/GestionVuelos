package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import model.Locaciones;
import model.Vuelos;
import model.DAO.ValidacionArchivos;
import model.DAO.VuelosTXT;
import model.Internacionales;
import model.Nacionales;
import model.Tripulantes;
import model.Aviones;



public class VuelosController {

    private List<Vuelos> vuelos = new ArrayList<Vuelos>();
    private List<Character> tiposVuelos = new ArrayList<Character>();
    private AvionesController avionesController = new AvionesController();
    private TripulantesController tripulacionController = new TripulantesController();
    private LocacionesController locacionesController = new LocacionesController();


    public VuelosController() {
        tiposVuelos = VuelosTXT.crearObjetosVuelos();
        for (int i = 0; i < tiposVuelos.size(); i++) {
            if('N' == tiposVuelos.get(i)) {
                vuelos.add(new Nacionales());
            } else if ('I' == tiposVuelos.get(i)) {
                vuelos.add(new Internacionales());
            }
        }
        vuelos = VuelosTXT.leerVuelos(vuelos, avionesController, tripulacionController, locacionesController);
    }

    public List<Vuelos> getVuelos() {
        return vuelos;
    }

    public List<Vuelos> getVuelosSortedById() {
        List<Vuelos> vuelosSorted = new ArrayList<Vuelos>(vuelos);
        vuelosSorted = vuelos;
        vuelosSorted.sort((v1, v2) -> v1.getIdVuelo() - v2.getIdVuelo());
        return vuelosSorted;
    }

    public void agregarVueloInternacional(int idVueloInt, char tipoVuelo, String aerolinea, Calendar fechaSistema,
            char estado, double duracionDouble, String modeloAvion, Object[] tripulantes, boolean tieneEscalas,
            Object[] escalas, boolean permiteMascotas, String paisOrigen, String paisDestino, boolean requiereVisa,
            String zonaHorariaDestino, int escala1Int, int escala2Int) {
                Aviones avionAux = null;
                List<Tripulantes> tripulacionInternacional = new ArrayList<Tripulantes>();
                for(Object t : tripulantes) {
                        Integer d = Integer.parseInt(t.toString());
                        for (Tripulantes tripulante : tripulacionController.getTripulacion()) {
                            if (tripulante.getDniTripulante() == d) {
                                tripulacionInternacional.add(tripulante);
                            }
                        }
                }
                for (Aviones a : avionesController.getAviones()) {
                    if (a.getModelo().equals(modeloAvion)) {
                        avionAux = a;
                        break;
                    }
                }
                Locaciones lAuxOrigen = null;
                Locaciones lAuxDestino = null;
                for(Locaciones l : locacionesController.getLocaciones()) {
                    if (l.getNombrePais().equals(paisOrigen)) {
                        lAuxOrigen = l;
                    } else if (l.getNombrePais().equals(paisDestino)) {
                        lAuxDestino = l;
                    }
                }
                vuelos.add(new Internacionales(idVueloInt, tipoVuelo, aerolinea, fechaSistema, estado, duracionDouble,
                 avionAux, tripulacionInternacional, tieneEscalas, permiteMascotas,
                  lAuxOrigen, lAuxDestino, requiereVisa, zonaHorariaDestino));
                if(tieneEscalas && escalas.length == 1) {
                    List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                    vuelos.getLast().setEscala(0, lAuxOrigen.getNombrePais(), escalasStrings.get(0), escala1Int);
                    vuelos.getLast().setEscala(1, escalasStrings.get(0), lAuxDestino.getNombrePais(), 0);
                    vuelos.getLast().setEscala(2, "null", "null", 0);
                } else if (tieneEscalas && escalas.length == 2) {
                    List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                    vuelos.getLast().setEscala(0, lAuxOrigen.getNombrePais(), escalasStrings.get(0), escala1Int);
                    vuelos.getLast().setEscala(1, escalasStrings.get(0), escalasStrings.get(1), escala2Int);
                    vuelos.getLast().setEscala(2, escalasStrings.get(1), lAuxDestino.getNombrePais(), 0);
                }
                VuelosTXT.escribirVuelo(vuelos.getLast());
    }

    public void agregarVueloNacional(int idVueloInt, char tipoVuelo, String aerolinea, Calendar fechaSistema,
            char estado, double duracionDouble, String modeloAvion, Object[] tripulantes, boolean tieneEscalas,
            Object[] escalas, boolean permiteMascotas, String paisFixed, String ciudadOrigen, String ciudadDestino, int escala1Int, int escala2Int) {
                Aviones avionAux = null;
                List<Tripulantes> tripulacionNacional = new ArrayList<Tripulantes>();
                for(Object t : tripulantes) {
                    Integer d = Integer.parseInt(t.toString());
                    for (Tripulantes tripulante : tripulacionController.getTripulacion()) {
                        if (tripulante.getDniTripulante() == d) {
                            tripulacionNacional.add(tripulante);
                        }
                    }
            }
                for (Aviones a : avionesController.getAviones()) {
                    if (a.getModelo().equals(modeloAvion)) {
                        avionAux = a;
                        break;
                    }
                }
                Locaciones lAuxOrigen = null;
                Locaciones lAuxDestino = null;
                for(Locaciones l : locacionesController.getLocaciones()) {
                    if (l.getNombrePais().equals(paisFixed) && l.getNombreCiudad().equals(ciudadOrigen)) {
                        lAuxOrigen = l;
                    } else if (l.getNombrePais().equals(paisFixed) && l.getNombreCiudad().equals(ciudadDestino)) {
                        lAuxDestino = l;
                    }
                }
                vuelos.add(new Nacionales(idVueloInt, tipoVuelo, aerolinea, fechaSistema, estado, duracionDouble,
                 avionAux, tripulacionNacional, tieneEscalas, permiteMascotas,
                  lAuxOrigen, lAuxDestino));
                if(tieneEscalas && escalas.length == 1) {
                    List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                    vuelos.getLast().setEscala(0, lAuxOrigen.getNombreCiudad(), escalasStrings.get(0), escala1Int);
                    vuelos.getLast().setEscala(1, escalasStrings.get(0), lAuxDestino.getNombreCiudad(), 0);
                    vuelos.getLast().setEscala(2, "null", "null", 0);
                } else if (tieneEscalas && escalas.length == 2) {
                    List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                    vuelos.getLast().setEscala(0, lAuxOrigen.getNombreCiudad(), escalasStrings.get(0), escala1Int);
                    vuelos.getLast().setEscala(1, escalasStrings.get(0), escalasStrings.get(1), escala2Int);
                    vuelos.getLast().setEscala(2, escalasStrings.get(1), lAuxDestino.getNombreCiudad(), 0);
                }
                VuelosTXT.escribirVuelo(vuelos.getLast());
    }

    public List<Vuelos> searchByCriteria(String criteria, String searchValue) throws VueloNotFoundExcep {
        List <Vuelos> vuelosMatched = new ArrayList<Vuelos>();
        switch (criteria) {
            case "IDVUELO":
                for (Vuelos v : vuelos) {
                    if (v.getIdVuelo() == Integer.parseInt(searchValue)) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese ID");
                }
                break;
            case "TIPOVUELO":
                for (Vuelos v : vuelos) {
                    if (v.getTipoVuelo() == searchValue.charAt(0)) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese tipo de vuelo, recuerde ingresar I para internacionales y N para nacionales");
                }
                break;
            case "AEROLINEA":
                for (Vuelos v : vuelos) {
                    if (v.getAerolinea().equalsIgnoreCase(searchValue)) {
                        vuelosMatched.add(v);
                    } 
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con esa aerolinea");
                }
                break;
            case "FECHADECARGA":
                for (Vuelos v : vuelos) {
                    if (v.getFechaSistema().equals(ValidacionArchivos.parseCalendar(searchValue))) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con esa fecha de carga");
                }
                break;
            case "ESTADO":
                for (Vuelos v : vuelos) {
                    if (v.getEstado() == searchValue.charAt(0)) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese estado");
                }
                break;
            case "DURACION":
                for (Vuelos v : vuelos) {
                    if (v.getDuracion() == Double.parseDouble(searchValue)) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con esa duracion");
                }  
                break;
            case "AVION":
                for (Vuelos v : vuelos) {
                    if (v.getAvion().getModelo().equals(searchValue)) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese avion");
                }        
                break;
            case "TRIPULANTE":
                for (Vuelos v : vuelos) {
                    for (Tripulantes t : v.getTripulacion()) {
                        if (t.getDniTripulante() == Integer.parseInt(searchValue)) {
                            vuelosMatched.add(v);
                            break;
                        }
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese tripulante");
                }
                break;
            case "TIENE ESCALAS?":
                final boolean escalas = searchValue.equalsIgnoreCase("SI") ? true : false;
                for (Vuelos v : vuelos) {
                    if (v.isTieneEscalas() == escalas) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con esa cantidad de escalas");
                }
                break;
            case "PERMITE MASCOTAS?":
            final boolean mascotas = searchValue.equalsIgnoreCase("SI") ? true : false;
                for (Vuelos v : vuelos) {
                    if (v.isPermiteMascotas() == mascotas) {
                        vuelosMatched.add(v);
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese requerimiento de mascotas");
                }
                break;
            case "ESCALA":
                for (Vuelos v : vuelos) {
                    for (int i = 0; i < v.getEscalas().size(); i++) {
                        if (v.getEscalas().get(i).getOrigen().equals(searchValue) || v.getEscalas().get(i).getDestino().equals(searchValue)) {
                            vuelosMatched.add(v);
                            break;
                        }
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con esa escala");
                }
                break;
            case "ORIGEN":
                for (Vuelos v : vuelos) {
                    if(v instanceof Internacionales) {
                        if (((Internacionales) v).getPaisOrigen().getNombrePais().equals(searchValue)) {
                            vuelosMatched.add(v);
                        }
                    } else if (v instanceof Nacionales) {
                        if (((Nacionales) v).getCiudadOrigen().getNombreCiudad().equals(searchValue)) {
                            vuelosMatched.add(v);
                        }
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese origen");
                }
                break;
            case "DESTINO":
                for (Vuelos v : vuelos) {
                    if(v instanceof Internacionales) {
                        if (((Internacionales) v).getPaisDestino().getNombrePais().equals(searchValue)) {
                            vuelosMatched.add(v);
                        }
                    } else if (v instanceof Nacionales) {
                        if (((Nacionales) v).getCiudadDestino().getNombreCiudad().equals(searchValue)) {
                            vuelosMatched.add(v);
                        }
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese destino");
                }
                break;
            case "REQUIERE VISA?":
            final boolean visa = searchValue.equalsIgnoreCase("SI") ? true : false;
                for (Vuelos v : vuelos) {
                    if(v instanceof Internacionales) {
                        if (((Internacionales) v).isRequiereVisa() == visa) {
                            vuelosMatched.add(v);
                        }
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con ese requerimiento de visa");
                }
                break;
            case "ZONA HORARIA":
                for (Vuelos v : vuelos) {
                    if(v instanceof Internacionales) {
                        if (((Internacionales) v).getZonaHorariaDestino().equals(searchValue)) {
                            vuelosMatched.add(v);
                        }
                    }
                }
                if(vuelosMatched.size() == 0) {
                    throw new VueloNotFoundExcep("No se encontraron vuelos con esa zona horaria de destino");
                }
                break;
            default:
                throw new IllegalArgumentException("Criterio de busqueda invalido");
        }
        return vuelosMatched;
    } 

    public double item1CalculoVuelos() {
        double result = 0;
        Calendar ultimos6Meses = Calendar.getInstance();
        ultimos6Meses.add(Calendar.MONTH, -6); 
        for (Vuelos v : vuelos) {
            if (v instanceof Internacionales) {
                if (v.isPermiteMascotas() && v.isTieneEscalas() && ((Internacionales)v).isRequiereVisa() && v.getFechaSistema() != null &&
                 v.getFechaSistema().after(ultimos6Meses)) {
                    result += v.getDuracion();
                }
            } else if (v instanceof Nacionales) {
                if (v.isPermiteMascotas() && v.isTieneEscalas() && v.getFechaSistema() != null &&
                 v.getFechaSistema().after(ultimos6Meses)) {
                    result += v.getDuracion();
                }
            }
        }
        return result;
    }

    public int item3CalculoVuelos(double random) {
        int contador = 0;
        for (Vuelos v : vuelos) {
            double sumaDuracionId = v.getDuracion() + v.getIdVuelo();
            if ( sumaDuracionId > random) {
                contador++;
            }
        }
        return contador;
    }

    public Vuelos getVueloById(int selectedId) {
        Vuelos vueloById = null;
        for (Vuelos v : vuelos) {
            if (v.getIdVuelo() == selectedId) {
                vueloById = v;
                break;
            }
        }
        return vueloById;
    }

    public void actualizarListadoVuelos(int idVueloInt, char tipoVuelo, String aerolinea, Calendar fechaSistema,
            char estadoChar, double duracionDouble, String modeloAvion, Object[] tripulantes, boolean tieneEscalas,
            boolean permiteMascotas, String paisOrigen, String paisDestino, boolean requiereVisa,
            String zonaHorariaDestino, String ciudadOrigen, String ciudadDestino, Object[] escalas, int escala1Int,
            int escala2Int) {
                Aviones avionAux = null;
                Locaciones lAuxOrigen = null;
                Locaciones lAuxDestino = null;
                for(Vuelos v : vuelos) {
                    if(v.getIdVuelo() == idVueloInt) {
                        v.setAerolinea(aerolinea);
                        v.setEstado(estadoChar);
                        v.setDuracion(duracionDouble);
                        for (Aviones a : avionesController.getAviones()) {
                            if (a.getModelo().equals(modeloAvion)) {
                                avionAux = a;
                                break;
                            }
                        }
                        v.setAvion(avionAux);
                        List<Tripulantes> tripulacion = new ArrayList<Tripulantes>();
                        for(Object t : tripulantes) {
                            Integer d = Integer.parseInt(t.toString());
                            for (Tripulantes tripulante : tripulacionController.getTripulacion()) {
                                if (tripulante.getDniTripulante() == d) {
                                    tripulacion.add(tripulante);
                                }
                            }
                        }
                        v.setTripulacion(tripulacion);
                        v.setTieneEscalas(tieneEscalas);
                        v.setPermiteMascotas(permiteMascotas);
                        if(v instanceof Nacionales) {
                            if(tieneEscalas && escalas.length == 1) {
                                List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                                v.setEscala(0, ciudadOrigen, escalasStrings.get(0), escala1Int);
                                v.setEscala(1, escalasStrings.get(0), ciudadDestino, 0);
                                v.setEscala(2, "null", "null", 0);
                            } else if (tieneEscalas && escalas.length == 2) {
                                List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                                v.setEscala(0, ciudadOrigen, escalasStrings.get(0), escala1Int);
                                v.setEscala(1, escalasStrings.get(0), escalasStrings.get(1), escala2Int);
                                v.setEscala(2, escalasStrings.get(1), ciudadDestino, 0);
                            }
                            for(Locaciones l : locacionesController.getLocaciones()) {
                                if (l.getNombrePais().equals("Argentina") && l.getNombreCiudad().equals(ciudadOrigen)) {
                                    lAuxOrigen = l;
                                } else if (l.getNombrePais().equals("Argentina") && l.getNombreCiudad().equals(ciudadDestino)) {
                                    lAuxDestino = l;
                                }
                            }
                            ((Nacionales) v).setCiudadOrigen(lAuxOrigen);
                            ((Nacionales) v).setCiudadDestino(lAuxDestino);                         
                        } else if (v instanceof Internacionales) {
                            for(Locaciones l : locacionesController.getLocaciones()) {
                                if (l.getNombrePais().equals(paisOrigen)) {
                                    lAuxOrigen = l;
                                } else if (l.getNombrePais().equals(paisDestino)) {
                                    lAuxDestino = l;
                                }
                            }
                            ((Internacionales)v).setPaisOrigen(lAuxOrigen);
                            ((Internacionales)v).setPaisDestino(lAuxDestino);
                            ((Internacionales)v).setRequiereVisa(requiereVisa);
                            ((Internacionales)v).setZonaHorariaDestino(zonaHorariaDestino);
                            if(tieneEscalas && escalas.length == 1) {
                                List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                                v.setEscala(0, lAuxOrigen.getNombrePais(), escalasStrings.get(0), escala1Int);
                                v.setEscala(1, escalasStrings.get(0), lAuxDestino.getNombrePais(), 0);
                                v.setEscala(2, "null", "null", 0);
                            } else if (tieneEscalas && escalas.length == 2) {
                                List<String> escalasStrings = Arrays.stream(escalas).filter(e -> e instanceof String).map(e -> (String) e).collect(Collectors.toList());
                                v.setEscala(0, lAuxOrigen.getNombrePais(), escalasStrings.get(0), escala1Int);
                                v.setEscala(1, escalasStrings.get(0), escalasStrings.get(1), escala2Int);
                                v.setEscala(2, escalasStrings.get(1), lAuxDestino.getNombrePais(), 0);
                            }
                        }
                    }
                }
        VuelosTXT.escribirVuelosActualizados(vuelos);
    }
}
