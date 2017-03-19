package com.mayorgraeme.animal;

/**
 * Created by graememiller on 05/03/2017.
 */
public class PercentageToValue {

    private static int MOVE_SPEED_MIN = 5;
    private static int MOVE_SPEED_MAX = 15;

    private static int MATURITY_AGE_MIN = 10;
    private static int MATURITY_AGE_MAX = 100;

    private static int GESTATION_SPEED_MIN = 20;
    private static int GESTATION_SPEED_MAX = 80;

    private static int LITTER_SIZE_MIN = 1;
    private static int LITTER_SIZE_MAX = 10;

    private static int MAX_AGE_MIN = 50;
    private static int MAX_AGE_MAX = 300;

    private static int METABOLISM_MIN = 1;
    private static int METABOLISM_MAX = 15;

    private static int HUNGER_LIMIT_MIN = 5;
    private static int HUNGER_LIMIT_MAX = 50;

    private static int GENETIC_MUTATION_MIN = 20;
    private static int GENETIC_MUTATION_MAX = 50;

    private static int conversion(int percent, int min, int max) {
        double percentAsDouble = (double)percent/100d;
        return (int) Math.round((percentAsDouble * (max-min)) + min);
    }

    public static int moveSpeedConversion(int moveSpeedPercentage){
        return conversion(moveSpeedPercentage, MOVE_SPEED_MIN, MOVE_SPEED_MAX);
    }

    public static int maturityAgeConversion(int maturityAgePercentage){
        return conversion(maturityAgePercentage, MATURITY_AGE_MIN, MATURITY_AGE_MAX);
    }
    public static int gestationSpeedConversion(int gestationSpeedPercentage){
        return conversion(gestationSpeedPercentage, GESTATION_SPEED_MIN, GESTATION_SPEED_MAX);
    }
    public static int litterSizeConversion(int litterSizePercentage){
        return conversion(litterSizePercentage, LITTER_SIZE_MIN, LITTER_SIZE_MAX);
    }
    public static int maxAgeConversion(int maxAgePercentage){
        return conversion(maxAgePercentage, MAX_AGE_MIN, MAX_AGE_MAX);
    }
    public static int metabolismConversion(int metabolismPercentage){
        return conversion(metabolismPercentage, METABOLISM_MIN, METABOLISM_MAX);
    }
    public static int hungerLimitToEatConversion(int hungerLimitToEatPercentage){
        return conversion(hungerLimitToEatPercentage, HUNGER_LIMIT_MIN, HUNGER_LIMIT_MAX);
    }

    public static int geneticMutationPercentageConversion(int geneticMutationPercentage) {
        return conversion(geneticMutationPercentage, GENETIC_MUTATION_MIN, GENETIC_MUTATION_MAX);
    }
}
