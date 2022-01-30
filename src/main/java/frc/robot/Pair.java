package frc.robot;

public class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 t1, T2 t2) {
        this.first = t1;
        this.second = t2;
    }

    public T1 getFirst() {
        return this.first;
    }

    public void setFirst(T1 t1) {
        this.first = t1;
    }

    public T2 getSecond() {
        return this.second;
    }

    public void setSecond(T2 t2) {
        this.second = t2;
    }
};