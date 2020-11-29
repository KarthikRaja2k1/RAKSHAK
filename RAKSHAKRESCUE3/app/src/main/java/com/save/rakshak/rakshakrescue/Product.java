package com.save.rakshak.rakshakrescue;

/**
 * Created by Anand on 11/28/2017.
 */
public class Product {
    private int id;
    private double Lattitude;
    private double Longitude;


    public Product(int id, double Lattitude, double Longitude) {
        this.id = id;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
    }

    public int getId() {
        return id;
    }

    public double getLattitude() {
        return Lattitude;
    }

    public double getLongitude() {
        return Longitude;
    }


}
