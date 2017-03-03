package com.mayorgraeme.display;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.InhabitantCoordinates;
import com.mayorgraeme.biome.Inhabitant;

import com.google.common.base.Strings;

/**
 * Created by graememiller on 21/02/2017.
 */
public class TextDisplay implements Display {

    @Override
    public void display(InhabitantCoordinates[][] inhabitantArray) {
        int length = inhabitantArray.length;

        System.out.println(Strings.repeat("-", length*3));

        for (InhabitantCoordinates[] inhabitants : inhabitantArray) {
            for (InhabitantCoordinates inhabitant : inhabitants) {
                if(inhabitant.getInhabitant() == null) {
                    System.out.print("  |");
                } else {
                    System.out.print(inhabitant.getInhabitant().getIdentifier() + "|");
                }

            }
            System.out.println();
        }

        System.out.println(Strings.repeat("-", length*3));
    }
}
