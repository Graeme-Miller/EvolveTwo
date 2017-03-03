package com.mayorgraeme.animal;

import com.mayorgraeme.biome.Inhabitant;
import com.mayorgraeme.util.Coordinate;

/**
 * Created by graememiller on 19/02/2017.
 */
public class InhabitantCoordinates {


    private final int x,y;
    private Inhabitant inhabitant;
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

    public Inhabitant getInhabitant() {
        return inhabitant;
    }

    public void setInhabitant(Inhabitant inhabitant) {
        this.inhabitant = inhabitant;
    }

    @Override
    public String toString() {
        return "InhabitantCoordinates{" +
                "x=" + x +
                ", y=" + y +
                ", inhabitant=" + inhabitant +
                '}';
    }
}
