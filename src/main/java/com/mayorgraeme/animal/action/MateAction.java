package com.mayorgraeme.animal.action;

import java.util.Optional;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.moment.Mean;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.animal.AnimalBuilder;
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

//        System.out.println("Mate action");

        if(animal.getMaturityAge() > animal.getAge()) {
//            System.out.println("Mate action: too young. maturity age: "+animal.getMaturityAge() + " age: "+ animal.getAge());
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

        for (int i = 0; i < animal.getLitterSize(); i++) {
            biome.addAnimal(childBuilder.buildAnimal());
        }

        return true;
    }

    public boolean performMatingAction(Animal animal, Biome biome){
//        System.out.println("Mate action: trying to mate");

        Optional<Animal> animalOptional = biome.getAnimals().stream()
                .filter(filterAnimal -> {
                    return  !animal.isPregnant() &&
                            !filterAnimal.isPregnant() &&
                            filterAnimal.getAge() > filterAnimal.getMaturityAge() &&
                            filterAnimal.getSex() != animal.getSex() &&
                            filterAnimal.getSpeciesId().equals(animal.getSpeciesId());
                }).findFirst();


//        System.out.println("Mate action: found mate? "+animalOptional);
        if(!animalOptional.isPresent()){
//            System.out.println("Mate action: no mate found returning");
            return false;
        }

        Animal mate = animalOptional.get();
        if(mate.getSex() == Sex.FEMALE){
//            System.out.println("Mate action: impregnating");
            mate.setPregnant(true);
            mate.setPregnancyCountdown(mate.getGestationSpeed());
            mate.setChildBuilder(createBabyFactory(animal, mate));
        } else {
//            System.out.println("Mate action: being impregnated");
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

        AnimalBuilder builder = new AnimalBuilder();
        builder.setGeneticMutationPercentage(randomiseAnimalPercentage(parentOne.getGeneticMutationPercentage(), parentTwo.getGeneticMutationPercentage(), parentOne.getGeneticMutationPercentage()));
        builder.setSex(sex);
        builder.setDiet(parentOne.getDiet());
        builder.setActions(parentOne.getActions());
        builder.setMoveSpeedPercentage(randomiseAnimalPercentage(parentOne.getMoveSpeedPercentage(), parentTwo.getMoveSpeedPercentage(), builder.getGeneticMutationPercentage()));
        builder.setSpeciesId(parentOne.getSpeciesId());
        builder.setPregnant(false);
        builder.setPregnancyCountdown(0);
        builder.setMaturityAgePercentage(randomiseAnimalPercentage(parentOne.getMaturityAgePercentage(), parentTwo.getMaturityAgePercentage(), builder.getGeneticMutationPercentage()));
        builder.setGestationSpeedPercentage(randomiseAnimalPercentage(parentOne.getGestationSpeedPercentage(), parentTwo.getGestationSpeedPercentage(), builder.getGeneticMutationPercentage()));
        builder.setLitterSizePercentage(randomiseAnimalPercentage(parentOne.getLitterSizePercentage(), parentTwo.getLitterSizePercentage(), builder.getGeneticMutationPercentage()));
        builder.setMaxAgePercentage(randomiseAnimalPercentage(parentOne.getMaxAgePercentage(), parentTwo.getMaxAgePercentage(), builder.getGeneticMutationPercentage()));
        builder.setAge(0);
        builder.setHunger(100);
        builder.setMetabolismPercentage(randomiseAnimalPercentage(parentOne.getMetabolismPercentage(), parentTwo.getMetabolismPercentage(), builder.getGeneticMutationPercentage()));
        builder.setHungerLimitToEatPercentage(randomiseAnimalPercentage(parentOne.getHungerLimitToEatPercentage(), parentTwo.getHungerLimitToEatPercentage(), builder.getGeneticMutationPercentage()));

        return builder;
    }

    private int randomiseAnimalPercentage(int valueOne, int valueTwo, int geneticMutationPercentage){

        Mean mean = new Mean();
        mean.increment(valueOne);
        mean.increment(valueTwo);

        int result = Math.round((long)mean.getResult());

        if (RandomUtil.shouldPeformAction(geneticMutationPercentage)) {
            result += random.nextInt(6);
        }

        return Math.min(Math.max(0, result), 100); //bound between
    }
}
