package windyday.utils;

/*
 * Random number generators.
 */

public class Random {

    public static int getInt(int upperBound) {
        int randomInt = (int) (Math.random() * upperBound) + 1;
        return randomInt;
    }

    private Random() {
    }
}
