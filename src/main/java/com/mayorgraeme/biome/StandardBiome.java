package com.mayorgraeme.biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.util.Coordinate;

/**
 * Created by graememiller on 19/02/2017.
 */
public class StandardBiome implements Biome {

    private Map<Animal, Coordinate> animalCoordinate;
    private Animal[][] grid;
    private int maximumX;
    private int maximumY;

    public StandardBiome(int maximumX, int maximumY) {
        this.maximumX = maximumX;
        this.maximumY = maximumY;

        grid = new Animal[maximumX][];
        for (int x = 0; x < maximumX; x++) {
            grid[x] = new Animal[maximumY];
        }

        animalCoordinate = new HashMap<>();
    }

    public void process() {
        HashSet<Animal> animalsCopy = new HashSet<>(animalCoordinate.keySet());
        for (Animal animal : animalsCopy) {
            if(!animalCoordinate.keySet().contains(animal))
                continue;

            animal.getActions().stream().filter(action -> {
                return action.perform(animal, this);
            }).findFirst();
        }


//        animalCoordinate.keySet().forEach( animal -> {
//            System.out.println("Processing "+animal);
//            animal.getActions().stream().filter(action -> {
//                return action.perform(animal, this);
//            }).findFirst();
//        });
    }

    public Animal[][] getAnimalMap() {
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

    @Override
    public void loopOverEmpty(Animal animal, int size, Function<Coordinate, Boolean> function) {
        Coordinate coordinate = animalCoordinate.get(animal);
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
    public void loopOverAnimal(Animal animal, int size, Function<Animal, Boolean> function) {
        Coordinate coordinate = animalCoordinate.get(animal);
        if(coordinate == null)
            throw new IllegalArgumentException("loopOverEmpty but coord is null");

        int minX = Math.max(0, coordinate.getX()-size);
        int maxX = Math.min(maximumX, coordinate.getX()+size);
        int minY = Math.max(0, coordinate.getY()-size);
        int maxY = Math.min(maximumY, coordinate.getY()+size);

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Animal gridAnimal = grid[x][y];

                Boolean stop = function.apply(gridAnimal);
                if(stop)
                    return;
            }
        }
    }


    private void checkIfSpaceOccupiedAndThrowException(Coordinate coordinate){
        Animal occupyingAnimal = grid[coordinate.getX()][coordinate.getY()];
        if(occupyingAnimal != null)
            throw new IllegalArgumentException("Tried to add animal to occupied space. Coordinate "+ coordinate+" occupying animal "+occupyingAnimal);
    }
}
