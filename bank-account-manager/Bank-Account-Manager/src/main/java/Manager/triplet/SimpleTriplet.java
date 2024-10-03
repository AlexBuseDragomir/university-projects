package manager.triplet;

public class SimpleTriplet<U, V, T> {

    private U first;
    private V second;
    private T third;

    public SimpleTriplet() {
    }

    public SimpleTriplet(SimpleTriplet<U, V, T> simpleTriplet) {
        this.first = simpleTriplet.getFirst();
        this.second = simpleTriplet.getSecond();
        this.third = simpleTriplet.getThird();
    }

    public SimpleTriplet(U first, V second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public U getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }

    public T getThird() {
        return this.third;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public void setThird(T third) {
        this.third = third;
    }
}
