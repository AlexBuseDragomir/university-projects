package com.ace.fla.pair;

public class SimplePair<U, V> {

    private U first;
    private V second;

    public SimplePair() {
    }

    public SimplePair(SimplePair<U, V> simplePair) {
        this.first = simplePair.getFirst();
        this.second = simplePair.getSecond();
    }

    public SimplePair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }
}
