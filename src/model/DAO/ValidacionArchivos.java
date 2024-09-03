package model.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ValidacionArchivos {
    
    public static int aInt(String s) {
		int result = -1;
		try {
			result = Integer.parseInt(s);
			if (result < 0) {
				return -1;
			}
		} catch (NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static double esDouble(String s) {
		double result = -1.0;
		try {
			result = Double.parseDouble(s);
			if (result < 0.0) {
				return -1.0;
			}
		} catch (NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static long esLong(String s) {
		long result = -1;
		try {
			result = Long.parseLong(s);
			if (result < 0) {
				return -1;
			}
		} catch (NumberFormatException e) {
			return result;
		}
		return result;
	}

	public static char esChar(char opcA, char opcB, char opcC, String s) {
		char result = '\0';
		try {
			result = s.charAt(0);

			if (result != opcA && result != opcB && result != opcC) {
				return '\0';
			}
		} catch (IndexOutOfBoundsException e) {
			return result;
		}
		return result;
	}

    public static char esCharTipos(char opcA, char opcB, String s) {
		char result = '\0';
		try {
			result = s.charAt(0);

			if (result != opcA && result != opcB) {
				return '\0';
			}
		} catch (IndexOutOfBoundsException e) {
			return result;
		}
		return result;
	}

	public static Calendar parseCalendar(String fechaStr) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			calendar.setTime(sdf.parse(fechaStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

    public static boolean esBoolean(String s) {
        boolean result = false;
        try {
            result = Boolean.parseBoolean(s);
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }

	public static String calendarToString(Calendar d) {
        if(d.get(Calendar.MONTH) < 10) {
            return d.get(Calendar.DAY_OF_MONTH) + "/0" + (d.get(Calendar.MONTH)+1) + "/" + d.get(Calendar.YEAR);
        } else {
            return d.get(Calendar.DAY_OF_MONTH) + "/" + (d.get(Calendar.MONTH)+1) + "/" + d.get(Calendar.YEAR);
        }
    }
    
}
