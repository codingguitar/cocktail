package com.vgg.array;

/**
 *  circular array with a fixed capacity.
 *
 *  use generics just for kicks
 */
public class CircularArray<T> {

    private int size = 0;
    private int capacity;
    private int startIdx = 0;
    private int endIdx = 0;
    private T[] circBuffer;

    private enum Index{
        START,
        END
    }
    public CircularArray(){
        this(16);
    }

    public CircularArray(int capacity){
        this.capacity = capacity;
        circBuffer = (T[]) new Object[capacity];
    }

    public void addToFront(T element){
        startIdx = getFixedIndex(startIdx - 1);
        circBuffer[startIdx] = element;
        if (size < capacity){
            size++;
        }
        //adjust end index if size == capacity
        if (size == capacity){
            adjustIndex(Index.END);
        }
    }

    public void addToBack(T element){
        circBuffer[endIdx] = element;
        endIdx = getFixedIndex(endIdx + 1);
        if (size < capacity){
            size++;
        }
        else if (size == capacity){
            adjustIndex(Index.START);
        }
    }

    public void removeFront(){
        if (size > 0) {
            circBuffer[startIdx] = null;
            startIdx = getFixedIndex(startIdx + 1);
            size--;
        }
    }

    public void removeBack(){
        if (size > 0){
            endIdx = getFixedIndex(endIdx - 1);
            circBuffer[endIdx] = null;
            size--;
        }
    }

    public boolean set(T element, int idx){
        boolean isSet = false;
        if (idx >=0 && idx < size){
            int actualIdx = getFixedIndex(startIdx + idx);
            circBuffer[actualIdx] = element;
            isSet = true;
        }
        return isSet;
    }

    public T get(int idx) {
        T element = null;
        if (idx >= 0 && idx < size) {
            int actualIdx = getFixedIndex(startIdx + idx);
            element = circBuffer[actualIdx];
        }
        return element;
    }

    private void adjustIndex(Index type) {
        switch (type){
            case START: startIdx = getFixedIndex(startIdx + 1);
                        break;
            case END :  endIdx = getFixedIndex(endIdx - 1);
        }
    }


    private int getFixedIndex(int idx){
        return (idx + capacity) % capacity;
    }

}
