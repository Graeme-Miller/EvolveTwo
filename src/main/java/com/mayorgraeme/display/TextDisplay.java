package com.mayorgraeme.display;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.biome.Inhabitant;

import com.google.common.base.Strings;

/**
 * Created by graememiller on 21/02/2017.
 */
public class TextDisplay implements Display {

    @Override
    public void display(Inhabitant[][] inhabitantArray) {
        int length = inhabitantArray.length;

        System.out.println(Strings.repeat("-", length*3));

        for (Inhabitant[] inhabitants : inhabitantArray) {
            for (Inhabitant inhabitant : inhabitants) {
                if(inhabitant == null) {
                    System.out.print("  |");
                } else {
                    System.out.print(inhabitant.getIdentifier() + "|");
                }

            }
            System.out.println();
        }

        System.out.println(Strings.repeat("-", length*3));
    }
}
