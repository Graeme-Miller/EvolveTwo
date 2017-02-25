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

    public BasicAnimal(Sex sex, Diet diet, List<Action> actions, int moveSpeed, UUID id, UUID speciesId) {
        this.sex = sex;
        this.diet = diet;
        this.actions = actions;
        this.moveSpeed = moveSpeed;
        this.id = id;
        this.speciesId = speciesId;
    }

    public Sex getSex() {
        return sex;
    }

    public Diet getDiet() {
        return diet;
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
