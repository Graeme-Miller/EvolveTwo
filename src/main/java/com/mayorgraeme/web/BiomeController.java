package com.mayorgraeme.web;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.biome.InhabitantCoordinates;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.animal.action.Action;
import com.mayorgraeme.animal.action.AgeAction;
import com.mayorgraeme.animal.action.HungerAction;
import com.mayorgraeme.animal.action.MateAction;
import com.mayorgraeme.animal.action.RandomMove;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.biome.StandardBiome;

/**
 * Created by graememiller on 11/03/2017.
 */

@RestController
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

                synchronized (biome) {
                    biome.process();
                }
            }
        }

    }

    @PostConstruct
    public void init() {
        biome  = new StandardBiome(xSize, ySize, vegetationSpawnRate, vegetationMaxAge ,vegetationNutrition);
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


    @RequestMapping(path = "/vegetationSpawnRate", method = RequestMethod.PUT)
    public void setConfigurationVegetationSpawnRate(int vegetationSpawnRate){
        this.vegetationSpawnRate = vegetationSpawnRate;
        biome.setConfigurationVegetationSpawnRate(vegetationSpawnRate);
    }

    @RequestMapping(path = "/vegetationMaxAge", method = RequestMethod.PUT)
    public void setConfigurationVegetationMaxAge(int vegetationMaxAge){
        this.vegetationMaxAge = vegetationMaxAge;
        biome.setConfigurationVegetationMaxAge(vegetationMaxAge);
    }

    @RequestMapping(path = "/vegetationNutrition", method = RequestMethod.PUT)
    public void setConfigurationVegetationNutrition(int vegetationNutrition){
        this.vegetationNutrition = vegetationNutrition;
        biome.setConfigurationVegetationNutrition(vegetationNutrition);
    }

    @RequestMapping(path = "/animal", method = RequestMethod.PUT)
    public void animal(
            Sex sex,
            Diet diet,
            String speciesId,
            int moveSpeedPercentage,
            int maturityAgePercentage,
            int gestationSpeedPercentage,
            int litterSizePercentage,
            int maxAgePercentage,
            int metabolismPercentage,
            int hungerLimitToEatPercentage,
            int geneticMutationPercentage) {

        List<Action> actions = new ArrayList<>();
        actions.add(new AgeAction());
        actions.add(new HungerAction());
        actions.add(new MateAction());
        actions.add(new RandomMove());

        Animal animal = new Animal(sex, diet, actions, moveSpeedPercentage, speciesId, false, 0, maturityAgePercentage, gestationSpeedPercentage, litterSizePercentage, maxAgePercentage, 0, 100, metabolismPercentage, hungerLimitToEatPercentage, null, geneticMutationPercentage);
        synchronized(biome) {
            biome.addAnimal(animal);
        }
    }

}
