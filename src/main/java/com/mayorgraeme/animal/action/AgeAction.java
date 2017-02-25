package com.mayorgraeme.animal.action;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.biome.Biome;

/**
 * Created by graememiller on 25/02/2017.
 */
public class AgeAction implements  Action {
    @Override
    public boolean perform(Animal animal, Biome biome) {
        if(animal.getAge() > animal.getMaxAge()) {
            biome.removeAnimal(animal);

            return true;
        } else {
            animal.setAge(animal.getAge() + 1);
            return false;
        }
    }
}
