package br.com.petfy.ms_clinica.util;

public class Haversine {

    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * Calcula a distância em quilômetros entre dois pontos de lat/lon.
     */
    public static double calculateDistanceInKm(double lat1, double lon1, double lat2, double lon2) {
        // Converte graus para radianos
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferença das longitudes e latitudes
        double dLon = lon2Rad - lon1Rad;
        double dLat = lat2Rad - lat1Rad;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

}
