package com.mayorgraeme;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.animal.action.Action;
import com.mayorgraeme.animal.action.AgeAction;
import com.mayorgraeme.animal.action.HungerAction;
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

        Biome biome = new StandardBiome(30, 30, 100, 10 ,50);

        UUID speciesUUID = UUID.randomUUID();

        List<Action> actions = new ArrayList<>();
        actions.add(new AgeAction());
        actions.add(new HungerAction());
        actions.add(new MateAction());
        actions.add(new RandomMove());



        for (int x = 0; x < 20; x++) {
            Sex sex = Sex.MALE;
            if(x%2 == 0)
                sex = Sex.FEMALE;

            Animal herbivoreANimal = new Animal(sex, Diet.HERBIVORE, actions, 100, speciesUUID, false, 0, 50, 50, 50, 60, 0, 100, 50 ,50);
            biome.addAnimal(herbivoreANimal, new Coordinate(x, 0));

            Animal carnivoreAninal = new Animal(sex, Diet.CARNIVORE, actions, 100, speciesUUID, false, 0, 50, 50, 10, 100, 0, 100, 50 ,15);
            biome.addAnimal(carnivoreAninal, new Coordinate(x, 1));
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
