package model.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.PrintWriter;


import controller.AvionesController;
import controller.TripulantesController;
import model.Vuelos;
import model.Aviones;
import model.Internacionales;
import model.Nacionales;
import model.Tripulantes;
import model.Locaciones;
import controller.LocacionesController;




public class VuelosTXT {

    private static final String vuelosPath = "src/model/DAO/vuelos.txt";
    
    public static List<Character> crearObjetosVuelos() {
		File in = null;
		Scanner vuelosScanner = null;
		String linea;
        String[] parsed;
		final List<Character> tiposVuelos = new ArrayList<Character>();

		try {
            in = new File(vuelosPath);
            vuelosScanner = new Scanner(in);
			while (vuelosScanner.hasNext()) {
				linea = vuelosScanner.nextLine();
                parsed = linea.split(";");
				tiposVuelos.add(ValidacionArchivos.esCharTipos('N', 'I', parsed[1].trim()));
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
                vuelosScanner.close();
        }
        return tiposVuelos;
	}

    //idVuelo;tipoVuelo;aerolinea;fechaVuelo;estado;duracion;idAvion;dniTripulante,dniTripulante,masDnis;tieneEscalas;permiteMascotas;
    //if(tieneEscalas) origen-destino-espera,origen-destino-espera,origen-destino-espera;
    //paisOrigen;paisDestino;requiereVisa;zonaHorariaDestino OR ciudadOrigen;ciudadDestino

    public static List<Vuelos> leerVuelos(List<Vuelos> vuelos, AvionesController avionesController, TripulantesController tripulacionController,
     LocacionesController locacionesController) {
        File in = null;
        Scanner vuelosScanner = null;
        int index = 0;
        try {
            in = new File(vuelosPath);
            vuelosScanner = new Scanner(in);
            while (vuelosScanner.hasNext()) {
                String linea = vuelosScanner.nextLine();
                String[] parsed = linea.split(";");
                Vuelos v = vuelos.get(index);

                v.setIdVuelo(ValidacionArchivos.aInt(parsed[0].trim()));
                v.setTipoVuelo(ValidacionArchivos.esCharTipos('N', 'I', parsed[1].trim()));
                v.setAerolinea(parsed[2].trim());
                v.setFechaSistema(ValidacionArchivos.parseCalendar(parsed[3].trim()));
                v.setEstado(ValidacionArchivos.esChar('P', 'R', 'C', parsed[4].trim()));
                v.setDuracion(ValidacionArchivos.esDouble(parsed[5].trim()));
                final String modeloAvion = parsed[6].trim();
                boolean flagAviones = false;
                for (Aviones av : avionesController.getAviones()) {
                    if (av.getModelo().equals(modeloAvion)) {
                        v.setAvion(av);
                        flagAviones = true;
                        break;
                    }
                }
                if (!flagAviones) {
                    throw new AvionInexistenteExcep("El avion con el modelo " + modeloAvion + " no existe");
                }
                
                final List<String> tripulantes = Arrays.asList(parsed[7].trim().split(","));
                final List<Integer> idsTripulantes = tripulantes.stream().map(Integer::parseInt).collect(Collectors.toList());
                final List<Tripulantes> tripulantesVuelo = new ArrayList<Tripulantes>();
                for (Tripulantes t : tripulacionController.getTripulacion()) {
                    if (idsTripulantes.contains(t.getDniTripulante())) {
                        tripulantesVuelo.add(t);
                    }
                }
                v.setTripulacion(tripulantesVuelo);

                v.setTieneEscalas(ValidacionArchivos.esBoolean(parsed[8].trim()));
                v.setPermiteMascotas(ValidacionArchivos.esBoolean(parsed[9].trim()));

                if(v.isTieneEscalas()) {
                    final List<String> escalas = Arrays.asList(parsed[10].trim().split(","));
                    int indexEscalas = 0;
                    for (String e : escalas) { 
                        String[] escala = e.split("-");
                        v.setEscala(indexEscalas, escala[0], escala[1], ValidacionArchivos.aInt(escala[2]));
                        indexEscalas++;
                    }
                    // final List<Escalas> escalasVuelo = escalas.stream().map(e -> {
                    //     String[] escala = e.split("-");
                    //     return new Escalas(escala[0], escala[1], ValidacionArchivos.aInt(escala[2]));
                    // }).collect(Collectors.toList());
                    if (v instanceof Internacionales) {
                        final String paisOrigen = parsed[11].trim();
                        final String paisDestino = parsed[12].trim();
                        for(Locaciones l : locacionesController.getLocaciones()) {
                            if (l.getNombrePais().equals(paisOrigen)) {
                                ((Internacionales)v).setPaisOrigen(l);
                            }
                            if (l.getNombrePais().equals(paisDestino)) {
                                ((Internacionales)v).setPaisDestino(l);
                            }
                        }
                        ((Internacionales)v).setRequiereVisa(ValidacionArchivos.esBoolean(parsed[13].trim()));
                        ((Internacionales)v).setZonaHorariaDestino(parsed[14].trim());
                    } else if (v instanceof Nacionales) {
                        final String ciudadOrigen = parsed[11].trim();
                        final String ciudadDestino = parsed[12].trim();
                        for(Locaciones l : locacionesController.getLocaciones()) {
                            if (l.getNombreCiudad().equals(ciudadOrigen)) {
                                ((Nacionales)v).setCiudadOrigen(l);
                            }
                            if (l.getNombreCiudad().equals(ciudadDestino)) {
                                ((Nacionales)v).setCiudadDestino(l);
                            }
                        }
                    }
                } else {
                    if (v instanceof Internacionales) {
                        final String paisOrigen = parsed[10].trim();
                        final String paisDestino = parsed[11].trim();
                        for(Locaciones l : locacionesController.getLocaciones()) {
                            if (l.getNombrePais().equals(paisOrigen)) {
                                ((Internacionales)v).setPaisOrigen(l);
                            }
                            if (l.getNombrePais().equals(paisDestino)) {
                                ((Internacionales)v).setPaisDestino(l);
                            }
                        }
                        ((Internacionales)v).setRequiereVisa(ValidacionArchivos.esBoolean(parsed[12].trim()));
                        ((Internacionales)v).setZonaHorariaDestino(parsed[13].trim());
                    } else if (v instanceof Nacionales) {
                        final String ciudadOrigen = parsed[10].trim();
                        final String ciudadDestino = parsed[11].trim();
                        for(Locaciones l : locacionesController.getLocaciones()) {
                            if (l.getNombreCiudad().equals(ciudadOrigen)) {
                                ((Nacionales)v).setCiudadOrigen(l);
                            }
                            if (l.getNombreCiudad().equals(ciudadDestino)) {
                                ((Nacionales)v).setCiudadDestino(l);
                            }
                        }
                    }
                }
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            vuelosScanner.close();
        }
        return vuelos;
    }

// 1;I;Aerolineas Argentinas;29/08/2024;P;13.5;Boeing 777;30456789,31234567,28765432,27890123,29876543,32345678,33123456,26789012,25678901,34567890,35678901,36789012;true;true;Argentina-Inglaterra-2,Inglaterra-Japon-1;Argentina;Japon;false;GMT-5
// 2;N;Latam;29/08/2024;P;3.5;Airbus A320;30456789,31234567,28765432,27890123,29876543,32345678,33123456,26789012,25678901,34567890,35678901,36789012;true;false;Buenos Aires-Rosario-1,Rosario-Cordoba-1;Buenos Aires;Cordoba
// 3;N;Latam;29/08/2024;P;2;Airbus A320;30456789,31234567,28765432,27890123,29876543,32345678,33123456,26789012,25678901,34567890,35678901,36789012;false;false;Buenos Aires;Rosario


    public static void escribirVuelo(Vuelos last) {
        FileWriter fw = null;
        PrintWriter out = null;

        try {
            fw = new FileWriter(vuelosPath, true);
            out = new PrintWriter(fw);
            String tripulacion = last.getTripulacion().stream().map(t -> String.valueOf(t.getDniTripulante())).collect(Collectors.joining(","));
            String escalas = last.getEscalas().stream()
            .filter(e -> e.getOrigen() != "null" && e.getDestino() != "null")
            .map(e -> e.getOrigen() + "-" + e.getDestino() + "-" + e.getEspera())
            .collect(Collectors.joining(","));
            out.println();

            out.print(last.getIdVuelo() + ";" + last.getTipoVuelo() + ";" + last.getAerolinea() + ";" + ValidacionArchivos.calendarToString(last.getFechaSistema()) +
             ";" + last.getEstado() + ";" + last.getDuracion() + ";" + last.getAvion().getModelo() +
              ";" + tripulacion + ";" + last.isTieneEscalas() + ";" + last.isPermiteMascotas());
            if(last instanceof Internacionales && last.isTieneEscalas()) {
             out.print(";" + escalas + ";" + ((Internacionales)last).getPaisOrigen().getNombrePais() + ";" + 
             ((Internacionales)last).getPaisDestino().getNombrePais() + ";" + ((Internacionales)last).isRequiereVisa() + ";" 
             + ((Internacionales)last).getZonaHorariaDestino());
            } else if(last instanceof Internacionales && !last.isTieneEscalas()) {
                out.print(";" + ((Internacionales)last).getPaisOrigen().getNombrePais() + ";" + ((Internacionales)last).getPaisDestino().getNombrePais() +
                 ";" +((Internacionales)last).isRequiereVisa() + ";" + ((Internacionales)last).getZonaHorariaDestino());
            } else if (last instanceof Nacionales && last.isTieneEscalas()) {
                out.print(";" + escalas + ";" + ((Nacionales)last).getCiudadOrigen().getNombreCiudad() + ";" +
                 ((Nacionales)last).getCiudadDestino().getNombreCiudad());
            } else if (last instanceof Nacionales && !last.isTieneEscalas()) {
                out.print(";" + ((Nacionales)last).getCiudadOrigen().getNombreCiudad() + ";" + ((Nacionales)last).getCiudadDestino().getNombreCiudad());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static void escribirVuelosActualizados(List<Vuelos> vuelos) {
        FileWriter fw = null;
        PrintWriter out = null;

        try {
            fw = new FileWriter(vuelosPath);
            out = new PrintWriter(fw);
            for (int i = 0 ; i < vuelos.size() ; i++) {
                Vuelos v = vuelos.get(i);
                String tripulacion = v.getTripulacion().stream().map(t -> String.valueOf(t.getDniTripulante())).collect(Collectors.joining(","));
                String escalas = v.getEscalas().stream()
                .filter(e -> e.getOrigen() != "null" && e.getDestino() != "null")
                .map(e -> e.getOrigen() + "-" + e.getDestino() + "-" + e.getEspera())
                .collect(Collectors.joining(","));

                out.print(v.getIdVuelo() + ";" + v.getTipoVuelo() + ";" + v.getAerolinea() + ";" + ValidacionArchivos.calendarToString(v.getFechaSistema()) +
                 ";" + v.getEstado() + ";" + v.getDuracion() + ";" + v.getAvion().getModelo() +
                  ";" + tripulacion + ";" + v.isTieneEscalas() + ";" + v.isPermiteMascotas());
                if(v instanceof Internacionales && v.isTieneEscalas()) {
                 out.print(";" + escalas + ";" + ((Internacionales)v).getPaisOrigen().getNombrePais() + ";" + 
                 ((Internacionales)v).getPaisDestino().getNombrePais() + ";" + ((Internacionales)v).isRequiereVisa() + ";" 
                 + ((Internacionales)v).getZonaHorariaDestino());
                } else if(v instanceof Internacionales && !v.isTieneEscalas()) {
                    out.print(";" + ((Internacionales)v).getPaisOrigen().getNombrePais() + ";" + ((Internacionales)v).getPaisDestino().getNombrePais() +
                     ";" +((Internacionales)v).isRequiereVisa() + ";" + ((Internacionales)v).getZonaHorariaDestino());
                } else if (v instanceof Nacionales && v.isTieneEscalas()) {
                    out.print(";" + escalas + ";" + ((Nacionales)v).getCiudadOrigen().getNombreCiudad() + ";" +
                     ((Nacionales)v).getCiudadDestino().getNombreCiudad());
                } else if (v instanceof Nacionales && !v.isTieneEscalas()) {
                    out.print(";" + ((Nacionales)v).getCiudadOrigen().getNombreCiudad() + ";" + ((Nacionales)v).getCiudadDestino().getNombreCiudad());
                }
                if(i != vuelos.size() - 1) {
                out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("entre por txt");
            e.printStackTrace();
        } finally {
            out.close();
        }

    }





}
