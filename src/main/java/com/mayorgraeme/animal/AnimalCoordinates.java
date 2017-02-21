package com.mayorgraeme.animal;

/**
 * Created by graememiller on 19/02/2017.
 */
public class AnimalCoordinates {


    private int x,y;
    private Animal animal;

    public AnimalCoordinates(Animal animal, int x, int y) {
        this.x = x;
        this.y = y;
        this.animal = animal;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}
