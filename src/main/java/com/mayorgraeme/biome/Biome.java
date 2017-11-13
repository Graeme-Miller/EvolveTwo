package com.mayorgraeme.biome;

import java.util.Set;

import com.mayorgraeme.animal.Animal;

/**
 * Created by graememiller on 19/02/2017.
 */
public interface Biome {
    void process();
    Set<Animal> getAnimals();
    Set<Vegetation> getVegetation();

    void removeAnimal(Animal animal);
    void addAnimal(Animal animal);
    void removeVegetation(Vegetation vegetation);
    void addVegetation(Vegetation vegetation);

    void setConfigurationVegetationSpawnRate(int vegetationSpawnRate);
    void setConfigurationVegetationMaxAge(int vegetationMaxAge);
    void setConfigurationVegetationNutrition(int vegetationNutrition);
}
