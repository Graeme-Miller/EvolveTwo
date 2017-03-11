package com.mayorgraeme.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.animal.InhabitantCoordinates;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.animal.action.Action;
import com.mayorgraeme.animal.action.AgeAction;
import com.mayorgraeme.animal.action.HungerAction;
import com.mayorgraeme.animal.action.MateAction;
import com.mayorgraeme.animal.action.RandomMove;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.biome.StandardBiome;
import com.mayorgraeme.util.Coordinate;

/**
 * Created by graememiller on 11/03/2017.
 */

@Controller
@EnableAutoConfiguration
public class BiomeController {

    @Value("${xSize}")
    private int xSize;

    @Value("${ySize}")
    private int ySize;

    @Value("${vegetationSpawnRate}")
    private int vegetationSpawnRate;

    @Value("${vegetationMaxAge}")
    private int vegetationMaxAge;

    @Value("${vegetationNutrition}")
    private int vegetationNutrition;

    private Biome biome;

    public class UpdateThread extends Thread {

        public void run() {
            while(true) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                biome.process();
            }
        }

    }

    @PostConstruct
    public void init() {
        biome  = new StandardBiome(xSize, ySize, vegetationSpawnRate, vegetationMaxAge ,vegetationNutrition);

        UUID speciesUUID = UUID.randomUUID();

        List<Action> actions = new ArrayList<>();
        actions.add(new AgeAction());
        actions.add(new HungerAction());
        actions.add(new MateAction());
        actions.add(new RandomMove());

        Random random = new Random();

        for (int x = 0; x < 20; x++) {
            Sex sex = Sex.MALE;
            if(x%2 == 0)
                sex = Sex.FEMALE;

            Animal herbivoreAnimal = new Animal(sex, Diet.HERBIVORE, actions, 100, speciesUUID, false, 0, random.nextInt(100)+1, random.nextInt(100)+1, random.nextInt(100)+1, random.nextInt(100)+1, 0, 100, random.nextInt(100)+1 ,random.nextInt(100)+1);
            biome.addAnimal(herbivoreAnimal, new Coordinate(x, 0));

            Animal carnivoreAnimal = new Animal(sex, Diet.CARNIVORE, actions, 100, speciesUUID, false, 0, random.nextInt(100)+1, random.nextInt(100)+1, random.nextInt(100)+1, random.nextInt(100)+1, 0, 100, random.nextInt(100)+1 ,random.nextInt(100)+1);
            biome.addAnimal(carnivoreAnimal, new Coordinate(x, 1));
        }

        (new UpdateThread()).start();
    }


    @RequestMapping("/")
    @ResponseBody
    public List<InhabitantCoordinates> home() {
        InhabitantCoordinates[][] inhabitantMap = biome.getInhabitantMap();

        ArrayList<InhabitantCoordinates> list = new ArrayList(xSize*ySize);
        for (InhabitantCoordinates[] inhabitantCoordinates : inhabitantMap) {
            for (InhabitantCoordinates inhabitantCoordinate : inhabitantCoordinates) {
                list.add(inhabitantCoordinate);
            }
        }

        return list;
    }


}
