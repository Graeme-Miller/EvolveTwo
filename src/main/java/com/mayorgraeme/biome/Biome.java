package com.mayorgraeme.biome;

import java.util.function.Function;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.util.Coordinate;

/**
 * Created by graememiller on 19/02/2017.
 */
public interface Biome {
    void process();
    Animal[][] getAnimalMap();
    void moveAnimal(Animal animal, Coordinate to);
    void removeAnimal(Animal animal);
    void addAnimal(Animal animal, Coordinate coordinate);
    void loopOverEmpty(Animal animal, int size, Function<Coordinate, Boolean> function);
    void loopOverAnimal(Animal animal, int size, Function<Animal, Boolean> function);
}