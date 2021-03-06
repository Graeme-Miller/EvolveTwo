package com.mayorgraeme.animal;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mayorgraeme.animal.action.Action;

/**
 * Created by graememiller on 21/02/2017.
 */
public class Animal {

    private Sex sex;
    private Diet diet;

    @JsonIgnore
    private List<Action> actions;

    @JsonIgnore
    private AnimalBuilder childBuilder;

    private UUID id;
    private String speciesId;

    private int moveSpeedPercentage;
    private int moveSpeed;

    private boolean isPregnant;
    private int pregnancyCountdown;
    private int maturityAgePercentage;
    private int maturityAge;

    private int gestationSpeedPercentage;
    private int litterSizePercentage;
    private int gestationSpeed;
    private int litterSize;

    private int maxAgePercentage;
    private int maxAge;
    private int age;

    private int hunger;
    private int metabolismPercentage;
    private int metabolism;
    private int hungerLimitToEatPercentage;
    private int hungerLimitToEat;

    private int geneticMutation;
    private int geneticMutationPercentage;

    public Animal(Sex sex,
                       Diet diet,
                       List<Action> actions,
                       int moveSpeedPercentage,
                       String speciesId,
                       boolean isPregnant,
                       int pregnancyCountdown,
                       int maturityAgePercentage,
                       int gestationSpeedPercentage,
                       int litterSizePercentage,
                       int maxAgePercentage,
                       int age,
                       int hunger,
                       int metabolismPercentage,
                       int hungerLimitToEatPercentage,
                       AnimalBuilder childBuilder,
                       int geneticMutationPercentage) {
        this.sex = sex;
        this.diet = diet;
        this.actions = actions;
        this.moveSpeedPercentage = moveSpeedPercentage;
        this.id = UUID.randomUUID();
        this.speciesId = speciesId;
        this.isPregnant = isPregnant;
        this.pregnancyCountdown = pregnancyCountdown;
        this.maturityAgePercentage = maturityAgePercentage;
        this.gestationSpeedPercentage = gestationSpeedPercentage;
        this.litterSizePercentage = litterSizePercentage;
        this.maxAgePercentage = maxAgePercentage;
        this.age = age;
        this.hunger = hunger;
        this.metabolismPercentage = metabolismPercentage;
        this.hungerLimitToEatPercentage = hungerLimitToEatPercentage;
        this.childBuilder = childBuilder;
        this.geneticMutationPercentage = geneticMutationPercentage;
        
         moveSpeed = PercentageToValue.moveSpeedConversion(moveSpeedPercentage);
         maturityAge = PercentageToValue.maturityAgeConversion(maturityAgePercentage);
         gestationSpeed = PercentageToValue.gestationSpeedConversion(gestationSpeedPercentage);
         litterSize = PercentageToValue.litterSizeConversion(litterSizePercentage);
         maxAge = PercentageToValue.maxAgeConversion(maxAgePercentage);
         metabolism = PercentageToValue.metabolismConversion(metabolismPercentage);
         hungerLimitToEat = PercentageToValue.hungerLimitToEatConversion(hungerLimitToEatPercentage);
        geneticMutation = PercentageToValue.geneticMutationPercentageConversion(geneticMutationPercentage);
    }

    
    public Sex getSex() {
        return sex;
    }
    
    public Diet getDiet() {
        return diet;
    }
    
    public int getPregnancyCountdown() {
        return pregnancyCountdown;
    }
    
    public void setPregnancyCountdown(int pregnancyCountdown) {
        this.pregnancyCountdown = pregnancyCountdown;
    }
    
    public List<Action> getActions() {
        return actions;
    }

    public int getMoveSpeedPercentage() {
        return moveSpeedPercentage;
    }

    
    public UUID getId() {
        return id;
    }

    
    public String getSpeciesId() {
        return speciesId;
    }

    
    public boolean isPregnant() {
        return isPregnant;
    }

    
    public void setPregnant(boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public int getMaturityAgePercentage() {
        return maturityAgePercentage;
    }

    public int getGestationSpeedPercentage() {
        return gestationSpeedPercentage;
    }

    public int getLitterSizePercentage() {
        return litterSizePercentage;
    }

    public int getMaxAgePercentage() {
        return maxAgePercentage;
    }

    
    public int getAge() {
        return age;
    }

    
    public void setAge(int age) {
        this.age = age;
    }

    
    public int getHunger() {
        return hunger;
    }

    
    public void setHunger(int hunger) {
        this.hunger = Math.min(hunger, 100); //never more full than 100%
    }

    public int getMetabolismPercentage() {
        return metabolismPercentage;
    }

    public int getHungerLimitToEatPercentage() {
        return hungerLimitToEatPercentage;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getMaturityAge() {
        return maturityAge;
    }

    public int getGestationSpeed() {
        return gestationSpeed;
    }

    public int getLitterSize() {
        return litterSize;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getMetabolism() {
        return metabolism;
    }

    public int getHungerLimitToEat() {
        return hungerLimitToEat;
    }

    public int getGeneticMutation() {
        return geneticMutation;
    }

    public int getGeneticMutationPercentage() {
        return geneticMutationPercentage;
    }

    public AnimalBuilder getChildBuilder() {
        return childBuilder;
    }

    public void setChildBuilder(AnimalBuilder childBuilder) {
        this.childBuilder = childBuilder;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal that = (Animal) o;

        return id.equals(that.id);
    }

    
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Animal{" +
                "sex=" + sex +
                ", diet=" + diet +
                ", actions=" + actions +
                ", id=" + id +
                ", speciesId=" + speciesId +
                ", moveSpeedPercentage=" + moveSpeedPercentage +
                ", moveSpeed=" + moveSpeed +
                ", isPregnant=" + isPregnant +
                ", pregnancyCountdown=" + pregnancyCountdown +
                ", maturityAgePercentage=" + maturityAgePercentage +
                ", maturityAge=" + maturityAge +
                ", gestationSpeedPercentage=" + gestationSpeedPercentage +
                ", litterSizePercentage=" + litterSizePercentage +
                ", gestationSpeed=" + gestationSpeed +
                ", litterSize=" + litterSize +
                ", maxAgePercentage=" + maxAgePercentage +
                ", maxAge=" + maxAge +
                ", age=" + age +
                ", hunger=" + hunger +
                ", metabolismPercentage=" + metabolismPercentage +
                ", metabolism=" + metabolism +
                ", hungerLimitToEatPercentage=" + hungerLimitToEatPercentage +
                ", hungerLimitToEat=" + hungerLimitToEat +
                '}';
    }
}
