package com.esir.sr.sweetsnake.utils;


public class Pair<T, E>
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private T first;
    private E second;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public Pair(final T _first, final E _second) {
        first = _first;
        second = _second;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public T getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    public void setFirst(final T _first) {
        first = _first;
    }

    public void setSecond(final E _second) {
        second = _second;
    }

}