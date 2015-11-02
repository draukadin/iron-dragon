package org.pphi.hexagon.util;

public final class CoordinateUtil {

    private CoordinateUtil() {}

    public static int solveForY(int x, int z) {
        return solve(x, z);
    }

    public static int solveForZ(int x, int y) {
        return solve(x, y);
    }

    public static int solveForX(int y, int z) {
        return solve(y, z);
    }

    /**
     * A hexagon cube coordinate system has the following constraint which ensures each hexagon has a unique
     * coordinate: x + y + z = 0
     *
     * x + y + z = 0 -> x = -y + -z -> -1 * (y + z)
     * x + y + z = 0 -> y = -x + -z -> -1 * (x + z)
     * x + Y + z = 0 -> z = -x + -y -> -1 * (x + y)
     */
    private static int solve(int first, int second) {
        return -1 * (first + second);
    }
}
