package com.mayorgraeme.animal.action;

import java.util.ArrayList;
import java.util.List;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.BasicAnimal;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.util.Coordinate;
import com.mayorgraeme.util.RandomUtil;
import com.mayorgraeme.biome.Biome;

/**
 * Created by graememiller on 25/02/2017.
 */
public class MateAction implements Action {

    @Override
    public boolean perform(Animal animal, Biome biome) {

        if(animal.getMaturityAge() > animal.getAge()) {
            return false;
        }

        if(animal.isPregnant()) {
            return performPregnancyActions(animal, biome);
        } else {
            return performMatingAction(animal, biome);
        }
    }

    public boolean performPregnancyActions(Animal animal, Biome biome) {
        if(animal.getPregnancyCountdown() != 0) {
            animal.setPregnancyCountdown(animal.getPregnancyCountdown() - 1);
            return false;
        }

        //It's birthin' time
        animal.setPregnant(false);

        List<Coordinate> coordinateList = new ArrayList<>();
        biome.loopOverEmpty(animal, animal.getMoveSpeed(), coordinate -> {
            coordinateList.add(coordinate);
            return false;
        } );


        for (int i = 0; i < animal.getLitterSize(); i++) {

            //No space, wasted pregnancy
            if(coordinateList.isEmpty()) {
                return true;
            }

            Sex sex;
            if(RandomUtil.shouldPeformAction(50)){
                sex = Sex.MALE;
            } else {
                sex = Sex.FEMALE;
            }

            Animal baby = new BasicAnimal(  sex,
                                            animal.getDiet(),
                                            animal.getActions(),
                                            animal.getMoveSpeed(),
                                            animal.getSpeciesId(),
                                            false,
                                            0 ,
                                            animal.getMaturityAge(),
                                            animal.getGestationSpeed(),
                                            animal.getLitterSize(),
                                            animal.getMaxAge(),
                                            0);

            Coordinate coordinate = coordinateList.remove(0);
            biome.addAnimal(baby, coordinate);

        }

        return true;
    }

    public boolean performMatingAction(Animal animal, Biome biome){

        biome.loopOverInhabitant(animal, animal.getMoveSpeed(), inhabitant -> {

            return false;
        }

            if(!(inhabitant instanceof  Animal))
                return false;

            Animal potentialMate = (Animal) inhabitant;
            if(     !animal.isPregnant() &&
                    !potentialMate.isPregnant() &&
                    potentialMate.getAge() > potentialMate.getMaturityAge() &&
                    potentialMate.getSex() != animal.getSex()) {

                if(potentialMate.getSex() == Sex.FEMALE){
                    potentialMate.setPregnant(true);
                    potentialMate.setPregnancyCountdown(potentialMate.getGestationSpeed());
                } else {
                    animal.setPregnant(true);
                    animal.setPregnancyCountdown(potentialMate.getGestationSpeed());
                }

                return true;
            }

            return  false;


        return true;  //TODO: returning false here means that if you can breed, you must, and wont get any other actions
    }

}
