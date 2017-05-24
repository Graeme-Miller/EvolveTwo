package com.mayorgraeme.biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.util.Coordinate;
import com.mayorgraeme.util.MatrixSubSpliterator;
import com.mayorgraeme.util.RandomUtil;

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
                grid[coordinate.getX()][coordinate.getY()].setVegetation(null);
            }

            vegetation.setAge(vegetation.getAge()+1);
        }

        //TODO: the below means we always create vegetation even if there is none left- maybe they should be treated like animals?
        //Vegetation process new
        for (int i = 0; i < vegetationSpawnRate; i++) {
            InhabitantCoordinates emptySpace = getEmptySpaceVegetation();
            if(emptySpace != null) {
                Vegetation vegetationNew = new Vegetation(0, vegetationNutrition);
                addVegetation(vegetationNew, emptySpace.getCoordinate());
            }
        }

        //Animal Process
        HashSet<Animal> animalsCopy = new HashSet<>(animalCoordinate.keySet());
        for (Animal animal : animalsCopy) {
            if(!animalCoordinate.keySet().contains(animal))
                continue;

            animal.getActions().stream().filter(action -> action.perform(animal, this)).findFirst();
        }
    }

    public InhabitantCoordinates[][] getInhabitantMap() {
        return grid;
    }

    @Override
    public void moveAnimal(Animal animal, Coordinate to) {
        checkIfSpaceOccupiedByAnimalAndThrowException(to);

        removeAnimal(animal);
        addAnimal(animal, to);
    }

    @Override
    public void removeAnimal(Animal animal) {
        Coordinate coordinate = animalCoordinate.get(animal);
        if(coordinate == null)
            throw new IllegalArgumentException("Tried to remove animal, but did not have coordinates. Animal "+animal);

        animalCoordinate.remove(animal);
        grid[coordinate.getX()][coordinate.getY()].setAnimal(null);

    }

    @Override
    public void addAnimal(Animal animal, Coordinate coordinate) {
        checkIfSpaceOccupiedByAnimalAndThrowException(coordinate);


        animalCoordinate.put(animal,coordinate);
        grid[coordinate.getX()][coordinate.getY()].setAnimal(animal);
    }

    @Override
    public void addAnimal(Animal animal) {
        InhabitantCoordinates emptySpace = getEmptySpaceAnimal();

        if(emptySpace == null)
            throw new IllegalStateException("Trying to add animal but there is no empty space");

        addAnimal(animal, emptySpace.getCoordinate());
    }

    @Override
    public void removeVegetation(Vegetation vegetation) {
        Coordinate coordinate = vegetationCoordinate.get(vegetation);
        if(coordinate == null)
            throw new IllegalArgumentException("Tried to remove vegetation, but did not have coordinates. Animal "+vegetation);

        vegetationCoordinate.remove(vegetation);
        grid[coordinate.getX()][coordinate.getY()].setVegetation(null);
    }

    @Override
    public void addVegetation(Vegetation vegetation, Coordinate coordinate) {
        checkIfSpaceOccupiedByVegetationAndThrowException(coordinate);
        vegetationCoordinate.put(vegetation,coordinate);

        grid[coordinate.getX()][coordinate.getY()].setVegetation(vegetation);
    }

    @Override
    public Stream<InhabitantCoordinates> getInhabitantCoordinatesStream(Animal animal, int size) {
        Coordinate coordinate = animalCoordinate.get(animal);

        return getInhabitantCoordinatesStream(coordinate, size);
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

    @Override
    public Stream<InhabitantCoordinates> getInhabitantCoordinatesStream(Coordinate coordinate, int size) {
        int minX = Math.max(0, coordinate.getX()-size);
        int maxX = Math.min(maximumX-1, coordinate.getX()+size);
        int minY = Math.max(0, coordinate.getY()-size);
        int maxY = Math.min(maximumY-1, coordinate.getY()+size);

        MatrixSubSpliterator<InhabitantCoordinates> spliterator = new MatrixSubSpliterator<>(grid, minX, maxX, minY, maxY);

        return StreamSupport.stream(spliterator, false);
    }

    private void checkIfSpaceOccupiedByAnimalAndThrowException(Coordinate coordinate){
        InhabitantCoordinates inhabitantCoordinates = grid[coordinate.getX()][coordinate.getY()];
        if(inhabitantCoordinates.getAnimal() != null)
            throw new IllegalArgumentException("Tried to add animal to occupied space. Coordinate "+ coordinate+" occupying inhabitant "+inhabitantCoordinates);
    }

    private void checkIfSpaceOccupiedByVegetationAndThrowException(Coordinate coordinate){
        InhabitantCoordinates inhabitantCoordinates = grid[coordinate.getX()][coordinate.getY()];
        if(inhabitantCoordinates.getVegetation() != null)
            throw new IllegalArgumentException("Tried to add vegetation to occupied space. Coordinate "+ coordinate+" occupying inhabitant "+inhabitantCoordinates);
    }


    private InhabitantCoordinates getEmptySpaceAnimal() {
        Stream<InhabitantCoordinates> inhabitantCoordinatesStream = getInhabitantCoordinatesStream(new Coordinate(0, 0), maximumX * maximumY);
        List<InhabitantCoordinates> inhabitantCoordinatesList = inhabitantCoordinatesStream.filter(inhabitantCoordinates -> inhabitantCoordinates.getAnimal() == null).collect(Collectors.toList());

        if(!inhabitantCoordinatesList.isEmpty()) {
            return RandomUtil.getRandomFromList(inhabitantCoordinatesList);
        } else {
            return null;
        }
    }

    private InhabitantCoordinates getEmptySpaceVegetation() {
        Stream<InhabitantCoordinates> inhabitantCoordinatesStream = getInhabitantCoordinatesStream(new Coordinate(0, 0), maximumX * maximumY);
        List<InhabitantCoordinates> inhabitantCoordinatesList = inhabitantCoordinatesStream.filter(inhabitantCoordinates -> inhabitantCoordinates.getVegetation() == null).collect(Collectors.toList());

        if(!inhabitantCoordinatesList.isEmpty()) {
            return RandomUtil.getRandomFromList(inhabitantCoordinatesList);
        } else {
            return null;
        }
    }
}
