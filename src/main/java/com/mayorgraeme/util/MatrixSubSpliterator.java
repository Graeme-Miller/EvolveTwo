package com.mayorgraeme.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by graememiller on 01/03/2017.
 */
public class MatrixSubSpliterator<A> implements Spliterator<A> {

    private A[][] array;
    private int minX, maxX, minY, maxY;
    private int currentX, currentY;

    public MatrixSubSpliterator(A[][] array, int minX, int maxX, int minY, int maxY) {
        this.array = array;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.currentX = minX;
        this.currentY = minY;
    }

    @Override
    public boolean tryAdvance(Consumer<? super A> action) {
        if(currentX == maxX && currentY == maxY) {
            return false;
        }

        A inhabitant = array[currentX][currentY];
        action.accept(inhabitant);

        currentY++;

        if(currentY > maxY) {
            currentY = minY;
            currentX++;
        }

        return true;
    }

    @Override
    public Spliterator<A> trySplit() {
        return null; //TODO implement splitting
    }

    @Override
    public long estimateSize() {
        return (maxX-minX)*(maxY-minY);
    }

    public int characteristics() {
        return  SIZED;
    }
}