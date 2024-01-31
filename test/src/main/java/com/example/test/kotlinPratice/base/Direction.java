package com.example.test.kotlinPratice.base;

public enum Direction {
    NORTH(1),
    SOUTH(2),
    WEST(3),
    EAST(4);

    int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
class testDireciton{
    public static void main(String[] args) {
        Direction west = Direction.WEST;
        System.out.println(west.getDirection());
    }
}
