package com.mayorgraeme.animal;

import java.util.List;
import java.util.UUID;

import com.mayorgraeme.animal.action.Action;

/**
 * Created by graememiller on 19/02/2017.
 */
public interface Animal {

    public List<Action> getActions();

    public int getMoveSpeed();

    public UUID getId();

    public UUID getSpeciesId();
}
