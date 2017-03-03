package com.mayorgraeme;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mayorgraeme.animal.BasicAnimal;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.animal.action.Action;
import com.mayorgraeme.animal.action.AgeAction;
import com.mayorgraeme.animal.action.MateAction;
import com.mayorgraeme.animal.action.RandomMove;
import com.mayorgraeme.util.Coordinate;
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

        Biome biome = new StandardBiome(30, 30, 50, 10 ,10);

        UUID speciesUUID = UUID.randomUUID();

        List<Action> actions = new ArrayList<>();
        actions.add(new AgeAction());
        actions.add(new MateAction());
        actions.add(new RandomMove());



        for (int x = 0; x < 5; x++) {
            BasicAnimal basicAnimal = new BasicAnimal(Sex.FEMALE, Diet.HERBIVORE, actions, 3, speciesUUID, false, 0, 5, 5, 20, 30, 0);
            biome.addAnimal(basicAnimal, new Coordinate(x, 0));
        }

        for (int x = 5; x < 10; x++) {
            BasicAnimal basicAnimal = new BasicAnimal(Sex.MALE, Diet.HERBIVORE, actions, 3, speciesUUID, false, 0, 5, 5, 20, 30, 0);
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
