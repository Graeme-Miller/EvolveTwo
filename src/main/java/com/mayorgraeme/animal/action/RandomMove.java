package com.mayorgraeme.animal.action;

import static com.mayorgraeme.util.RandomUtil.getRandomFromList;

import java.util.ArrayList;
import java.util.List;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.util.Coordinate;
import com.mayorgraeme.biome.Biome;

/**
 * Created by graememiller on 19/02/2017.
 */
public class RandomMove implements Action {

    public RandomMove() {
    }

    @Override
    public boolean perform(Animal animal, Biome biome) {
        List<Coordinate> coordinateList = new ArrayList<>();
        biome.loopOverEmpty(animal, animal.getMoveSpeed(), coordinate -> {
            coordinateList.add(coordinate);
            return false;
        } );

        if(coordinateList.isEmpty())
            return false;

        Coordinate randomFromList = getRandomFromList(coordinateList);
        biome.moveAnimal(animal, randomFromList);

        return true;

    }
}
