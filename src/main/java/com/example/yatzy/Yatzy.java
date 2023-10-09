package com.example.yatzy;

import java.util.Arrays;

public class Yatzy {

    private final int[] dice;

    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        this.dice = new int[]{d1, d2, d3, d4, d5};
        Arrays.sort(this.dice);
    }

    public int chance() {
        return Arrays.stream(dice).sum();
    }

    public int yatzy() {
        int[] counts = new int[6];
        for (int die : dice) {
            counts[die - 1]++;
        }
        return Arrays.stream(counts).anyMatch(count -> count == 5) ? 50 : 0;
    }

    public int ones() {
        return countOccurrences(1);
    }

    public int twos() {
        return countOccurrences(2) * 2;
    }

    public int threes() {
        return countOccurrences(3) * 3;
    }

    public int fours() {
        return countOccurrences(4) * 4;
    }

    public int fives() {
        return countOccurrences(5) * 5;
    }

    public int sixes() {
        return countOccurrences(6) * 6;
    }

    public int scorePair() {
        return findNOfAKind(2) * 2;
    }

    public int twoPair() {
        int pair1 = findNOfAKind(2);
        int pair2 = findNOfAKind(2);
        return (pair1 > 0 && pair2 > 0 && pair1 != pair2) ? (pair1 + pair2) * 2 : 0;
    }

    public int fourOfAKind() {
        return findNOfAKind(4) * 4;
    }

    public int threeOfAKind() {
        return findNOfAKind(3) * 3;
    }

    public int smallStraight() {
        return Arrays.equals(dice, new int[]{1, 2, 3, 4, 5}) ? 15 : 0;
    }

    public int largeStraight() {
        return Arrays.equals(dice, new int[]{2, 3, 4, 5, 6}) ? 20 : 0;
    }

    public int fullHouse() {
        int[] counts = new int[6];
        for (int die : dice) {
            counts[die - 1]++;
        }
        boolean has2 = false;
        boolean has3 = false;

        for (int count : counts) {
            if (count == 2) {
                has2 = true;
            }
            if (count == 3) {
                has3 = true;
            }
        }

        return has2 && has3 ? chance() : 0;
    }

    private int countOccurrences(int value) {
        return (int) Arrays.stream(dice).filter(die -> die == value).count();
    }

    private int findNOfAKind(int n) {
        int[] counts = new int[6];
        for (int die : dice) {
            counts[die - 1]++;
        }
        for (int i = counts.length - 1; i >= 0; i--) {
            if (counts[i] >= n) {
                return (i + 1);
            }
        }
        return 0;
    }
}
