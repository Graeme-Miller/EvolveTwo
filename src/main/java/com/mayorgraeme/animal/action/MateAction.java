package com.mayorgraeme.animal.action;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.AnimalBuilder;
import com.mayorgraeme.animal.Diet;
import com.mayorgraeme.animal.InhabitantCoordinates;
import com.mayorgraeme.animal.Sex;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.util.RandomUtil;

/**
 * Created by graememiller on 25/02/2017.
 */
public class MateAction implements Action {

    private static Random random = new Random();

    @Override
    public boolean perform(Animal animal, Biome biome) {

        System.out.println("Mate action");

        if(animal.getMaturityAge() > animal.getAge()) {
            System.out.println("Mate action: too young. maturity age: "+animal.getMaturityAge() + " age: "+ animal.getAge());
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
        AnimalBuilder childBuilder = animal.getChildBuilder();
        animal.setChildBuilder(null);

        biome.getInhabitantCoordinatesStream(animal, animal.getMoveSpeed())
                .filter(inhabitantCoordinates -> inhabitantCoordinates.getInhabitant() == null)
                .limit(animal.getLitterSize()).forEach(inhabitantCoordinates -> {

            biome.addAnimal(childBuilder.buildAnimal(), inhabitantCoordinates.getCoordinate());
        });

        return true;
    }

    public boolean performMatingAction(Animal animal, Biome biome){
        System.out.println("Mate action: trying to mate");

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


        System.out.println("Mate action: found mate? "+inhabitantCoordinatesOptional);
        if(!inhabitantCoordinatesOptional.isPresent()){
            System.out.println("Mate action: no mate found returning");
            return false;
        }

        Animal mate = (Animal)inhabitantCoordinatesOptional.get().getInhabitant();
        if(mate.getSex() == Sex.FEMALE){
            System.out.println("Mate action: impregnating");
            mate.setPregnant(true);
            mate.setPregnancyCountdown(mate.getGestationSpeed());
            mate.setChildBuilder(createBabyFactory(animal, mate));
        } else {
            System.out.println("Mate action: being impregnated");
            animal.setPregnant(true);
            animal.setPregnancyCountdown(animal.getGestationSpeed());
            animal.setChildBuilder(createBabyFactory(animal, mate));
        }

        return true;
    }



    public AnimalBuilder createBabyFactory(Animal parentOne, Animal parentTwo){
        Sex sex;
        if (RandomUtil.shouldPeformAction(50)) {
            sex = Sex.MALE;
        } else {
            sex = Sex.FEMALE;
        }

        Animal parentToInheritFrom;
        if (RandomUtil.shouldPeformAction(50)) {
            parentToInheritFrom = parentOne;
        } else {
            parentToInheritFrom = parentTwo;
        }

        AnimalBuilder builder = new AnimalBuilder();
        builder.setSex(sex);
        builder.setDiet(parentToInheritFrom.getDiet());
        builder.setActions(parentToInheritFrom.getActions());
        builder.setMoveSpeedPercentage(randomiseAnimalPercentage(parentToInheritFrom.getMoveSpeedPercentage()));
        builder.setSpeciesId(parentToInheritFrom.getSpeciesId());
        builder.setPregnant(false);
        builder.setPregnancyCountdown(0);
        builder.setMaturityAgePercentage(randomiseAnimalPercentage(parentToInheritFrom.getMaturityAgePercentage()));
        builder.setGestationSpeedPercentage(randomiseAnimalPercentage(parentToInheritFrom.getGestationSpeedPercentage()));
        builder.setLitterSizePercentage(randomiseAnimalPercentage(parentToInheritFrom.getLitterSizePercentage()));
        builder.setMaxAgePercentage(randomiseAnimalPercentage(parentToInheritFrom.getMaxAgePercentage()));
        builder.setAge(0);
        builder.setHunger(100);
        builder.setMetabolismPercentage(randomiseAnimalPercentage(parentToInheritFrom.getMetabolismPercentage()));
        builder.setHungerLimitToEatPercentage(randomiseAnimalPercentage(parentToInheritFrom.getHungerLimitToEatPercentage()));

        return builder;
    }

    private int randomiseAnimalPercentage(int percentage){
        int newPercentage;
        if (RandomUtil.shouldPeformAction(50)) {
            newPercentage = percentage + random.nextInt(6);
        } else {
            newPercentage = percentage - random.nextInt(6);
        }

        return Math.min(Math.max(0, newPercentage), 100); //bound between
    }
}
