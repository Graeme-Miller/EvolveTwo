package com.mayorgraeme.animal;

import java.util.List;
import java.util.UUID;

import com.mayorgraeme.animal.action.Action;

/**
 * Created by graememiller on 21/02/2017.
 */
public class BasicAnimal implements Animal {

    private Sex sex;
    private Diet diet;
    private List<Action> actions;
    private int moveSpeed;
    private UUID id;
    private UUID speciesId;
    private boolean isPregnant;
    private int pregnancyCountdown;
    private int maturityAge;
    private int gestationSpeed;
    private int litterSize;
    private int maxAge;
    private int age;

    public BasicAnimal(Sex sex, Diet diet, List<Action> actions, int moveSpeed, UUID speciesId, boolean isPregnant, int pregnancyCountdown, int maturityAge, int gestationSpeed, int litterSize, int maxAge, int age) {
        this.sex = sex;
        this.diet = diet;
        this.actions = actions;
        this.moveSpeed = moveSpeed;
        this.id = UUID.randomUUID();
        this.speciesId = speciesId;
        this.isPregnant = isPregnant;
        this.pregnancyCountdown = pregnancyCountdown;
        this.maturityAge = maturityAge;
        this.gestationSpeed = gestationSpeed;
        this.litterSize = litterSize;
        this.maxAge = maxAge;
        this.age = age;
    }

    @Override
    public Sex getSex() {
        return sex;
    }

    @Override
    public Diet getDiet() {
        return diet;
    }

    @Override
    public int getPregnancyCountdown() {
        return pregnancyCountdown;
    }

    @Override
    public void setPregnancyCountdown(int pregnancyCountdown) {
        this.pregnancyCountdown = pregnancyCountdown;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public int getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getSpeciesId() {
        return speciesId;
    }

    @Override
    public boolean isPregnant() {
        return isPregnant;
    }

    @Override
    public void setPregnant(boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    @Override
    public int getMaturityAge() {
        return maturityAge;
    }

    @Override
    public int getGestationSpeed() {
        return gestationSpeed;
    }

    @Override
    public int getLitterSize() {
        return litterSize;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicAnimal that = (BasicAnimal) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String getIdentifier() {
        return getSpeciesId().toString().substring(0, 2);
    }
}
