package com.mayorgraeme.util;

import java.util.List;
import java.util.Random;

/**
 * Created by graememiller on 21/02/2017.
 */
public class RandomUtil {

    private static Random random = new Random();


    public static <G> G getRandomFromList(List<G> list) {
        if(list.isEmpty()){
            return null;
        }

        Random rnd = new Random();
        int i = rnd.nextInt(list.size());
        return list.get(i);
    }

    public static boolean shouldPeformAction(int probability) {
        int randomInt = random.nextInt(100);
        return randomInt < probability;
    }
}
