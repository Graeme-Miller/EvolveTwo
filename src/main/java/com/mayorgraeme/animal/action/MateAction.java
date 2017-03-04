package com.mayorgraeme.animal.action;

import java.util.Optional;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.BasicAnimal;
import com.mayorgraeme.animal.InhabitantCoordinates;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.util.RandomUtil;

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

        biome.getInhabitantCoordinatesStream(animal, animal.getMoveSpeed())
                .filter(inhabitantCoordinates -> inhabitantCoordinates.getInhabitant() == null)
                .limit(animal.getLitterSize()).forEach(inhabitantCoordinates -> {

            Sex sex;
            if (RandomUtil.shouldPeformAction(50)) {
                sex = Sex.MALE;
            } else {
                sex = Sex.FEMALE;
            }

            Animal baby = new BasicAnimal(sex,
                    animal.getDiet(),
                    animal.getActions(),
                    animal.getMoveSpeed(),
                    animal.getSpeciesId(),
                    false,
                    0,
                    animal.getMaturityAge(),
                    animal.getGestationSpeed(),
                    animal.getLitterSize(),
                    animal.getMaxAge(),
                    0,
                    100,
                    animal.getMetabolism(),
                    animal.getHungerLimitToEat());

            biome.addAnimal(baby, inhabitantCoordinates.getCoordinate());
        });

        return true;
    }

    public boolean performMatingAction(Animal animal, Biome biome){


        Optional<InhabitantCoordinates> inhabitantCoordinatesOptional = biome.getInhabitantCoordinatesStream(animal, animal.getMoveSpeed())
                .filter(inhabitantCoordinates -> {

                    if (inhabitantCoordinates.getInhabitant() == null)
                        return false;
                    if (!(inhabitantCoordinates.getInhabitant() instanceof Animal))
                        return false;

                    Animal filterAnimal = (Animal) inhabitantCoordinates.getInhabitant();
                    return  !animal.isPregnant() &&
                            !filterAnimal.isPregnant() &&
                            filterAnimal.getAge() > filterAnimal.getMaturityAge() &&
                            filterAnimal.getSex() != animal.getSex() &&
                            filterAnimal.getSpeciesId().equals(animal.getSpeciesId());
                }).findFirst();



        if(!inhabitantCoordinatesOptional.isPresent()){
            return false;
        }

        Animal mate = (Animal)inhabitantCoordinatesOptional.get().getInhabitant();
        if(mate.getSex() == Sex.FEMALE){
            mate.setPregnant(true);
            mate.setPregnancyCountdown(mate.getGestationSpeed());
        } else {
            animal.setPregnant(true);
            animal.setPregnancyCountdown(animal.getGestationSpeed());
        }

        return true;
    }

}
