package com.mayorgraeme.biome;

import static com.mayorgraeme.util.RandomUtil.getRandomFromList;
import static com.mayorgraeme.util.RandomUtil.shouldPeformAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.InhabitantCoordinates;
import com.mayorgraeme.util.Coordinate;
import com.mayorgraeme.util.MatrixSubSpliterator;

/**
 * Created by graememiller on 19/02/2017.
 */
public class StandardBiome implements Biome {

    private Map<Animal, Coordinate> animalCoordinate;
    private Map<Vegetation, Coordinate> vegetationCoordinate;
    private InhabitantCoordinates[][] grid;
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

        grid = new InhabitantCoordinates[maximumX][];
        for (int x = 0; x < maximumX; x++) {
            grid[x] = new InhabitantCoordinates[maximumY];

            for (int y = 0; y < maximumY; y++) {
                grid[x][y] = new InhabitantCoordinates(x, y);
            }
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

            Stream<InhabitantCoordinates> inhabitantCoordinatesStream = getInhabitantCoordinatesStream(new Coordinate(0, 0), maximumX * maximumY);
            Optional<InhabitantCoordinates> inhabitantOptional = inhabitantCoordinatesStream.filter(inhabitantCoordinates -> inhabitantCoordinates.getAnimal() == null).findFirst();

            if (inhabitantOptional.isPresent()) {
                Vegetation vegetationNew = new Vegetation(0, vegetationNutrition);
                grid[inhabitantOptional.get().getX()][inhabitantOptional.get().getY()].setAnimal(vegetationNew);
                vegetationCoordinate.put(vegetationNew, new Coordinate(inhabitantOptional.get().getX(), inhabitantOptional.get().getY()));
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

    public InhabitantCoordinates[][] getInhabitantMap() {
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
        grid[coordinate.getX()][coordinate.getY()].setAnimal(animal);
    }

    @Override
    public Stream<InhabitantCoordinates> getInhabitantCoordinatesStream(Animal animal, int size) {
        Coordinate coordinate = animalCoordinate.get(animal);

        return getInhabitantCoordinatesStream(coordinate, size);
    }

    @Override
    public Stream<InhabitantCoordinates> getInhabitantCoordinatesStream(Coordinate coordinate, int size) {
        int minX = Math.max(0, coordinate.getX()-size);
        int maxX = Math.min(maximumX, coordinate.getX()+size);
        int minY = Math.max(0, coordinate.getY()-size);
        int maxY = Math.min(maximumY, coordinate.getY()+size);

        MatrixSubSpliterator<InhabitantCoordinates> spliterator = new MatrixSubSpliterator<>(grid, minX, maxX, minY, maxY);

        return StreamSupport.stream(spliterator, false);
    }

    private void checkIfSpaceOccupiedAndThrowException(Coordinate coordinate){
        InhabitantCoordinates inhabitantCoordinates = grid[coordinate.getX()][coordinate.getY()];
        if(inhabitantCoordinates.getAnimal() != null)
            throw new IllegalArgumentException("Tried to add animal to occupied space. Coordinate "+ coordinate+" occupying inhabitant "+inhabitantCoordinates);
    }


}
