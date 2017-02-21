package com.mayorgraeme.animal.action;

import com.mayorgraeme.animal.Animal;
import com.mayorgraeme.biome.Biome;

/**
 * Created by graememiller on 19/02/2017.
 */
public interface Action {
    boolean perform(Animal animal, Biome biome);
}
