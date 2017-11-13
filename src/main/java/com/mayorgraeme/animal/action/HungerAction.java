package com.mayorgraeme.animal.action;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.biome.Biome;
import com.mayorgraeme.biome.Vegetation;
import com.mayorgraeme.util.RandomUtil;

/**
 * Created by graememiller on 04/03/2017.
 */
public class HungerAction implements Action {

    @Override
    public boolean perform(Animal animal, Biome biome) {
        //Check if dead from hunger
        int newHunger = animal.getHunger() - animal.getMetabolism();
        if(newHunger <= 0) {
            biome.removeAnimal(animal);
            System.out.println("Animal died due to hunger"+animal);
            return true;
        }
        animal.setHunger(newHunger);

        //Check if hungry enough to eat
        if(animal.getHunger() > animal.getHungerLimitToEat() ) {
            return false;
        }

        boolean success = false;
        switch (animal.getDiet()) {
            case HERBIVORE: success = herbivore(animal, biome); break;
            case CARNIVORE: success = carnivore(animal, biome); break;
            default: throw new IllegalStateException("Haven't covered all cases");
        }

        return success;
    }

    public boolean herbivore(Animal animal, Biome biome) {
        //TODO: This will mean we always eat from top right. Maybe randomise?
        Optional<Vegetation> vegetationOptional = biome.getVegetation().stream().findFirst();

        if(!vegetationOptional.isPresent())
            return false;

        Vegetation vegetation = vegetationOptional.get();
        biome.removeVegetation(vegetation);

        animal.setHunger(animal.getHunger() + vegetation.getNutrition());

        return true;
    }

    public boolean carnivore(Animal animal, Biome biome) {
        List<Animal> dinnerOptions =
                biome.getAnimals().stream().filter(animalInhabitant -> !animal.getSpeciesId().equals(animalInhabitant.getSpeciesId())
        ).collect(Collectors.toList());

        System.out.println("Carnivore list size: "+dinnerOptions.size());
        if(dinnerOptions.isEmpty())
            return false;
        //TODO taking random from list, should consider:
        // weakest from list
        // a limit for how much defense they have
        // a function to try and balance cost to benefit using properties of animal
        Animal randomFromList = (Animal)RandomUtil.getRandomFromList(dinnerOptions);
        biome.removeAnimal(randomFromList);

        animal.setHunger(animal.getHunger() + 100); //TODO should the amount of hunger gained be a function of how big the eaten animal is
        return true;
    }
}
