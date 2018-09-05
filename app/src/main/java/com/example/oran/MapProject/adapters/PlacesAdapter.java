package com.example.oran.MapProject.adapters;


public class PlacesAdapter {


    // method to calculate distance between two locations in meters
    public static double distance(double pLAT, double placeLat, double PLng,
                                  double placeLng, double el, double elPlace) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(pLAT - placeLat);
        double lonDistance = Math.toRadians(PLng - placeLng);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(placeLat)) * Math.cos(Math.toRadians(pLAT))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        double height = elPlace - el;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}





