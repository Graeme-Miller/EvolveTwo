package com.mayorgraeme.biome;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mayorgraeme.animal.Animal;

/**
 * Created by graememiller on 19/02/2017.
 */
public class StandardBiome implements Biome {

    private Set<Animal> animals;
    private Set<Vegetation> vegetation;

    private int vegetationSpawnRate;
    private int vegetationMaxAge;
    private int vegetationNutrition;

    public StandardBiome(int vegetationSpawnRate, int vegetationMaxAge, int vegetationNutrition) {
        this.vegetationSpawnRate = vegetationSpawnRate;
        this.vegetationMaxAge = vegetationMaxAge;
        this.vegetationNutrition = vegetationNutrition;

        animals = new HashSet<>();
        vegetation = new HashSet<>();
    }

    public void process() {
        //Vegetation process old
        Iterator<Vegetation> vegetationIterator = vegetation.iterator();
        Vegetation vegetation;
        while (vegetationIterator.hasNext()) {
            vegetation = vegetationIterator.next();

            if(vegetation.getAge() > vegetationMaxAge) {
                vegetationIterator.remove();
            }

            vegetation.setAge(vegetation.getAge()+1);
        }

        //TODO: need new vegetation spawn code

        //Animal Process
        HashSet<Animal> animalsCopy = new HashSet<>(animals);
        for (Animal animal : animalsCopy) {
            if(!animals.contains(animal)) //this is here incase the animal get's eaten before it's turn
                continue;

            animal.getActions().stream().filter(action -> action.perform(animal, this)).findFirst();
        }
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    @Override
    public Set<Vegetation> getVegetation() {
        return vegetation;
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    @Override
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    @Override
    public void removeVegetation(Vegetation vegetation) {
        this.vegetation.remove(vegetation);
    }

    @Override
    public void addVegetation(Vegetation vegetation) {
        this.vegetation.add(vegetation);
    }

    @Override
    public void setConfigurationVegetationSpawnRate(int vegetationSpawnRate) {
        this.vegetationSpawnRate = vegetationSpawnRate;
    }

    @Override
    public void setConfigurationVegetationMaxAge(int vegetationMaxAge) {
        this.vegetationMaxAge = vegetationMaxAge;
    }

    @Override
    public void setConfigurationVegetationNutrition(int vegetationNutrition) {
        this.vegetationNutrition = vegetationNutrition;
    }
}
