package com.mayorgraeme.biome;

/**
 * Created by graememiller on 25/02/2017.
 */
public class Vegetation implements Inhabitant {

    private int age;
    private int nutrition;

    public Vegetation(int age, int nutrition) {
        this.age = age;
        this.nutrition = nutrition;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNutrition() {
        return nutrition;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }

    @Override
    public String toString() {
        return "Vegetation{" +
                "age=" + age +
                ", nutrition=" + nutrition +
                '}';
    }

    @Override
    public String getIdentifier() {
        return "Ve";
    }
}
