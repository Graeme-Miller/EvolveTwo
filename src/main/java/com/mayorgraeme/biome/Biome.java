package com.mayorgraeme.biome;

import java.util.stream.Stream;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.util.Coordinate;

/**
 * Created by graememiller on 19/02/2017.
 */
public interface Biome {
    void process();
    InhabitantCoordinates[][] getInhabitantMap();
    void moveAnimal(Animal animal, Coordinate to);
    void removeAnimal(Animal animal);
    void addAnimal(Animal animal, Coordinate coordinate);
    void addAnimal(Animal animal);
    void removeVegetation(Vegetation vegetation);
    void addVegetation(Vegetation vegetation, Coordinate coordinate);
    Stream<InhabitantCoordinates> getInhabitantCoordinatesStream(Coordinate coordinate, int size);
    Stream<InhabitantCoordinates> getInhabitantCoordinatesStream(Animal animal, int size);

    void setConfigurationVegetationSpawnRate(int vegetationSpawnRate);
    void setConfigurationVegetationMaxAge(int vegetationMaxAge);
    void setConfigurationVegetationNutrition(int vegetationNutrition);
}
