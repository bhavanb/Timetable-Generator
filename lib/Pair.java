package lib;

/**
 * A class to store any two values as one object
 * Used here to store the indexes of an occurrence of a subject in the
 * timetable, which is a 2D array
 */
public class Pair<T, U> {
    public T x;
    public U y;

    public Pair(T X, U Y) {
        this.x = X;
        this.y = Y;
    }
}
