package com.mayorgraeme.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.animal.action.Action;
import com.mayorgraeme.animal.action.AgeAction;
import com.mayorgraeme.animal.action.HungerAction;
import com.mayorgraeme.animal.action.MateAction;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.biome.StandardBiome;
import com.mayorgraeme.biome.Vegetation;

/**
 * Created by graememiller on 11/03/2017.
 */

@RestController
@EnableAutoConfiguration
public class BiomeController {


    @Value("${vegetationSpawnRate}")
    private int vegetationSpawnRate;

    @Value("${vegetationMaxAge}")
    private int vegetationMaxAge;

    @Value("${vegetationNutrition}")
    private int vegetationNutrition;

    @Value("${vegetationMaxCount}")
    private int vegetationMaxCount;

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
        biome  = new StandardBiome(vegetationSpawnRate, vegetationMaxAge ,vegetationNutrition, vegetationMaxCount);
        System.out.println("Init called");
        (new UpdateThread()).start();
    }


    @RequestMapping("/animal")
    @ResponseBody
    public Set<Animal> animals() {
        return biome.getAnimals();
    }

    @RequestMapping("/vegetation")
    @ResponseBody
    public Set<Vegetation> vegetation() {
        return biome.getVegetation();
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

        Animal animal = new Animal(sex, diet, actions, moveSpeedPercentage, speciesId, false, 0, maturityAgePercentage, gestationSpeedPercentage, litterSizePercentage, maxAgePercentage, 0, 100, metabolismPercentage, hungerLimitToEatPercentage, null, geneticMutationPercentage);
        synchronized(biome) {
            biome.addAnimal(animal);
        }
    }

}
