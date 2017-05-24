package com.mayorgraeme.biome;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.util.Coordinate;

/**
 * Created by graememiller on 19/02/2017.
 */
public class InhabitantCoordinates {


    private final int x,y;
    private Animal animal;
    private Vegetation vegetation;
    private Coordinate coordinate;

    public InhabitantCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        this.coordinate = new Coordinate(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Vegetation getVegetation() {
        return vegetation;
    }

    public void setVegetation(Vegetation vegetation) {
        this.vegetation = vegetation;
    }

    @Override
    public String toString() {
        return "InhabitantCoordinates{" +
                "x=" + x +
                ", y=" + y +
                ", animal=" + animal +
                ", vegetation=" + vegetation +
                ", coordinate=" + coordinate +
                '}';
    }
}
