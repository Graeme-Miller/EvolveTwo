package com.mayorgraeme.biome;

import static com.mayorgraeme.animal.util.RandomUtil.getRandomFromList;
import static com.mayorgraeme.animal.util.RandomUtil.shouldPeformAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.util.Coordinate;

/**
 * Created by graememiller on 19/02/2017.
 */
public class StandardBiome implements Biome {

    private Map<Animal, Coordinate> animalCoordinate;
    private Map<Vegetation, Coordinate> vegetationCoordinate;
    private Inhabitant[][] grid;
    private int maximumX;
    private int maximumY;

    private int vegetationSpawnRate;
    private int vegetationMaxAge;
    private int vegetationNutrition;

    public StandardBiome(int maximumX, int maximumY, int vegetationSpawnRate, int vegetationMaxAge, int vegetationNutrition) {
        this.maximumX = maximumX;
        this.maximumY = maximumY;

        this.vegetationSpawnRate = vegetationSpawnRate;
        this.vegetationMaxAge = vegetationMaxAge;
        this.vegetationNutrition = vegetationNutrition;

        grid = new Inhabitant[maximumX][];
        for (int x = 0; x < maximumX; x++) {
            grid[x] = new Inhabitant[maximumY];
        }

        animalCoordinate = new HashMap<>();
        vegetationCoordinate = new HashMap<>();
    }

    public void process() {
        //Vegetation process old
        Iterator<Vegetation> vegetationIterator = vegetationCoordinate.keySet().iterator();
        Vegetation vegetation;
        while (vegetationIterator.hasNext()) {
            vegetation = vegetationIterator.next();

            if(vegetation.getAge() > vegetationMaxAge) {
                Coordinate coordinate = vegetationCoordinate.get(vegetation);
                vegetationIterator.remove();
                grid[coordinate.getX()][coordinate.getY()] = null;
            }

            vegetation.setAge(vegetation.getAge()+1);
        }

        //Vegetation process new
        if(shouldPeformAction(vegetationSpawnRate)) {
            List<Coordinate> coordinateList = new ArrayList<>();
            loopOverEmpty(new Coordinate(0,0), maximumX*maximumY, coordinate -> {
                coordinateList.add(coordinate);
                return false;
            } );

            Coordinate randomFromList = getRandomFromList(coordinateList);
            if(randomFromList != null) {
                Vegetation vegetationNew = new Vegetation(0, vegetationNutrition);
                grid[randomFromList.getX()][randomFromList.getY()] = vegetationNew;
                vegetationCoordinate.put(vegetationNew, randomFromList);
            }
        }

        //Animal Process
        HashSet<Animal> animalsCopy = new HashSet<>(animalCoordinate.keySet());
        for (Animal animal : animalsCopy) {
            if(!animalCoordinate.keySet().contains(animal))
                continue;

            animal.getActions().stream().filter(action -> {
                return action.perform(animal, this);
            }).findFirst();
        }
    }

    public Inhabitant[][] getInhabitantMap() {
        return grid;
    }

    @Override
    public void moveAnimal(Animal animal, Coordinate to) {
        checkIfSpaceOccupiedAndThrowException(to);

        removeAnimal(animal);
        addAnimal(animal, to);
    }

    @Override
    public void removeAnimal(Animal animal) {
        Coordinate coordinate = animalCoordinate.get(animal);
        if(coordinate == null)
            throw new IllegalArgumentException("Tried to remove animal, but did not have coordinates. Animal "+animal);

        animalCoordinate.remove(animal);
        grid[coordinate.getX()][coordinate.getY()] = null;

    }

    @Override
    public void addAnimal(Animal animal, Coordinate coordinate) {
        checkIfSpaceOccupiedAndThrowException(coordinate);


        animalCoordinate.put(animal,coordinate);
        grid[coordinate.getX()][coordinate.getY()] = animal;
    }

    public void loopOverEmpty(Coordinate coordinate, int size, Function<Coordinate, Boolean> function) {
        if(coordinate == null)
            throw new IllegalArgumentException("loopOverEmpty but coord is null");


        int minX = Math.max(0, coordinate.getX()-size);
        int maxX = Math.min(maximumX, coordinate.getX()+size);
        int minY = Math.max(0, coordinate.getY()-size);
        int maxY = Math.min(maximumY, coordinate.getY()+size);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if( grid[x][y] == null ){
                    Boolean stop = function.apply(new Coordinate(x, y));
                    if(stop)
                        return;
                }
            }
        }
    }

    @Override
    public void loopOverEmpty(Animal animal, int size, Function<Coordinate, Boolean> function) {
        Coordinate coordinate = animalCoordinate.get(animal);
        if(coordinate == null)
            throw new IllegalArgumentException("loopOverEmpty but coord is null");

        loopOverEmpty(coordinate, size, function);
    }

    @Override
    public void loopOverInhabitant(Animal animal, int size, Function<Inhabitant, Boolean> function) {
        Coordinate coordinate = animalCoordinate.get(animal);
        if(coordinate == null)
            throw new IllegalArgumentException("loopOverEmpty but coord is null");

        int minX = Math.max(0, coordinate.getX()-size);
        int maxX = Math.min(maximumX, coordinate.getX()+size);
        int minY = Math.max(0, coordinate.getY()-size);
        int maxY = Math.min(maximumY, coordinate.getY()+size);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Inhabitant gridInhabitant = grid[x][y];

                Boolean stop = function.apply(gridInhabitant);
                if(stop)
                    return;
            }
        }
    }


    private void checkIfSpaceOccupiedAndThrowException(Coordinate coordinate){
        Inhabitant occupyingInhabitant = grid[coordinate.getX()][coordinate.getY()];
        if(occupyingInhabitant != null)
            throw new IllegalArgumentException("Tried to add animal to occupied space. Coordinate "+ coordinate+" occupying inhabitant "+occupyingInhabitant);
    }


}
