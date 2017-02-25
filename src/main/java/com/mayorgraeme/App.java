package com.mayorgraeme;

import java.util.Collections;
import java.util.UUID;

import com.mayorgraeme.animal.BasicAnimal;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.animal.action.RandomMove;
import com.mayorgraeme.animal.util.Coordinate;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.biome.StandardBiome;
import com.mayorgraeme.display.Display;
import com.mayorgraeme.display.TextDisplay;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        System.out.println( "Hello World!" );

        Display textDisplay = new TextDisplay();

        Biome biome = new StandardBiome(30, 30, 10, 10 ,10);

        UUID speciesUUID = UUID.randomUUID();
        for (int x = 0; x < 10; x++) {
            BasicAnimal basicAnimal = new BasicAnimal(Sex.FEMALE, Diet.HERBIVORE, Collections.singletonList(new RandomMove()), 3, UUID.randomUUID(), speciesUUID);
            biome.addAnimal(basicAnimal, new Coordinate(x, 0));
        }

        while(true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            biome.process();
            textDisplay.display(biome.getInhabitantMap());
        }

    }
}
