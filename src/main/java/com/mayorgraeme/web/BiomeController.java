package com.mayorgraeme.web;


import java.util.ArrayList;
import java.util.List;
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



        for (int x = 0; x < 20; x++) {
            Sex sex = Sex.MALE;
            if(x%2 == 0)
                sex = Sex.FEMALE;

            Animal herbivoreANimal = new Animal(sex, Diet.HERBIVORE, actions, 100, speciesUUID, false, 0, 50, 50, 50, 60, 0, 100, 50 ,50);
            biome.addAnimal(herbivoreANimal, new Coordinate(x, 0));

            Animal carnivoreAninal = new Animal(sex, Diet.CARNIVORE, actions, 100, speciesUUID, false, 0, 50, 50, 10, 100, 0, 100, 50 ,15);
            biome.addAnimal(carnivoreAninal, new Coordinate(x, 1));
        }

        (new UpdateThread()).start();
    }


    @RequestMapping("/")
    @ResponseBody
    public InhabitantCoordinates[][] home() {
        return biome.getInhabitantMap();
    }


}