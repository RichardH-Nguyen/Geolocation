package com.Richard;

import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String key = null;
        Scanner stringScanner = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader("GeoKey.txt"))){
            key = reader.readLine();
            System.out.println(key);

            GeoApiContext context = new GeoApiContext().setApiKey(key);

            System.out.println("What city?");
            String location = stringScanner.nextLine();

            //
            GeocodingResult[] resultLatLng = GeocodingApi.geocode(context, location).await();

            GeocodingResult point = resultLatLng[0];



            LatLng mctcLatLng = new LatLng(point.geometry.location.lat, point.geometry.location.lng);

            ElevationResult[] results = ElevationApi.getByPoints(context, mctcLatLng).await();

            if (results.length >= 1){
                ElevationResult mctcElevation = results[0];
                System.out.println(String.format("The elevation of " + location + " above sea level is %.2f meters", mctcElevation.elevation));
            }else {
                System.out.println("No results");
            }

        }catch (Exception ioe){
            System.out.println("? " + ioe);
            System.exit(-1);
        }


    }
}
