package com.mayorgraeme.animal;

import java.util.List;

import com.mayorgraeme.animal.action.Action;

public class AnimalBuilder {
    private Sex sex;
    private Diet diet;

    private List<Action> actions;
    private String speciesId;

    private int moveSpeedPercentage;

    private boolean isPregnant;
    private int pregnancyCountdown;
    private int maturityAgePercentage;
    private int gestationSpeedPercentage;
    private int litterSizePercentage;

    private int maxAgePercentage;
    private int age;

    private int hunger;
    private int metabolismPercentage;
    private int hungerLimitToEatPercentage;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    public int getMoveSpeedPercentage() {
        return moveSpeedPercentage;
    }

    public void setMoveSpeedPercentage(int moveSpeedPercentage) {
        this.moveSpeedPercentage = moveSpeedPercentage;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public int getPregnancyCountdown() {
        return pregnancyCountdown;
    }

    public void setPregnancyCountdown(int pregnancyCountdown) {
        this.pregnancyCountdown = pregnancyCountdown;
    }

    public int getMaturityAgePercentage() {
        return maturityAgePercentage;
    }

    public void setMaturityAgePercentage(int maturityAgePercentage) {
        this.maturityAgePercentage = maturityAgePercentage;
    }

    public int getGestationSpeedPercentage() {
        return gestationSpeedPercentage;
    }

    public void setGestationSpeedPercentage(int gestationSpeedPercentage) {
        this.gestationSpeedPercentage = gestationSpeedPercentage;
    }

    public int getLitterSizePercentage() {
        return litterSizePercentage;
    }

    public void setLitterSizePercentage(int litterSizePercentage) {
        this.litterSizePercentage = litterSizePercentage;
    }

    public int getMaxAgePercentage() {
        return maxAgePercentage;
    }

    public void setMaxAgePercentage(int maxAgePercentage) {
        this.maxAgePercentage = maxAgePercentage;
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
        this.hunger = hunger;
    }

    public int getMetabolismPercentage() {
        return metabolismPercentage;
    }

    public void setMetabolismPercentage(int metabolismPercentage) {
        this.metabolismPercentage = metabolismPercentage;
    }

    public int getHungerLimitToEatPercentage() {
        return hungerLimitToEatPercentage;
    }

    public void setHungerLimitToEatPercentage(int hungerLimitToEatPercentage) {
        this.hungerLimitToEatPercentage = hungerLimitToEatPercentage;
    }

    public Animal buildAnimal() {
        return new Animal(sex,
                diet,
                actions,
                moveSpeedPercentage,
                speciesId,
                isPregnant,
                pregnancyCountdown,
                maturityAgePercentage,
                gestationSpeedPercentage,
                litterSizePercentage,
                maxAgePercentage,
                age,
                hunger,
                metabolismPercentage,
                hungerLimitToEatPercentage,
                null);
    }
}