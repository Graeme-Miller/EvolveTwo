package com.mayorgraeme.animal;

import java.util.List;
import java.util.UUID;

import com.mayorgraeme.animal.action.Action;
import com.mayorgraeme.biome.Inhabitant;

/**
 * Created by graememiller on 19/02/2017.
 */
public interface Animal extends Inhabitant {

    List<Action> getActions();

    int getMoveSpeed();

    UUID getId();

    UUID getSpeciesId();

    boolean isPregnant();

    void setPregnant(boolean isPregnant);

    int getMaturityAge();

    int getGestationSpeed();

    int getLitterSize();

    int getMaxAge();

    int getAge();

    void setAge(int age);

    Sex getSex();

    Diet getDiet();

    int getPregnancyCountdown();

    void setPregnancyCountdown(int pregnancyCountdown);
}
