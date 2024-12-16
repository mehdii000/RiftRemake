package me.mehdidev.rift.handlers;

import java.util.Random;

public class MathHandler {

    private static Random random = new Random();

    public static int random(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must be less than or equal to end");
        }
        return start + random.nextInt(end - start + 1);
    }

}
