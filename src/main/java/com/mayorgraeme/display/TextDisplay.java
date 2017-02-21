package com.mayorgraeme.display;

import com.mayorgraeme.animal.Animal;

import com.google.common.base.Strings;

/**
 * Created by graememiller on 21/02/2017.
 */
public class TextDisplay implements Display {

    @Override
    public void display(Animal[][] animalArray) {
        int length = animalArray.length;

        System.out.println(Strings.repeat("-", length*3));

        for (Animal[] animals : animalArray) {
            for (Animal animal : animals) {
                if(animal == null) {
                    System.out.print("  |");
                } else {
                    System.out.print(animal.getSpeciesId().toString().substring(0, 2) + "|");
                }

            }
            System.out.println();
        }

        System.out.println(Strings.repeat("-", length*3));
    }
}
