package com.mayorgraeme.animal;

import com.mayorgraeme.biome.Inhabitant;

/**
 * Created by graememiller on 19/02/2017.
 */
public class InhabitantCoordinates {


    private final int x,y;
    private Inhabitant animal;

    public InhabitantCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Inhabitant getAnimal() {
        return animal;
    }

    public void setAnimal(Inhabitant animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "InhabitantCoordinates{" +
                "x=" + x +
                ", y=" + y +
                ", animal=" + animal +
                '}';
    }
}
