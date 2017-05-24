package com.mayorgraeme.animal.action;

import static com.mayorgraeme.util.RandomUtil.getRandomFromList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.biome.InhabitantCoordinates;
import com.mayorgraeme.biome.Biome;

/**
 * Created by graememiller on 19/02/2017.
 */
public class RandomMove implements Action {

    public RandomMove() {
    }

    @Override
    public boolean perform(Animal animal, Biome biome) {
        Stream<InhabitantCoordinates> inhabitantCoordinatesStream = biome.getInhabitantCoordinatesStream(animal, animal.getMoveSpeedPercentage());
        List<InhabitantCoordinates> coordinateList  = inhabitantCoordinatesStream.filter(inhabitantCoordinates -> inhabitantCoordinates.getAnimal() == null).collect(Collectors.toList());

        if(coordinateList.isEmpty())
            return false;

        InhabitantCoordinates randomFromList = getRandomFromList(coordinateList);
        biome.moveAnimal(animal, randomFromList.getCoordinate());

        return true;

    }
}
