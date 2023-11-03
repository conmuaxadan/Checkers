package com.dangnha.checkers.model;

public class Position {
    private int x;
    private int y;
    private boolean attackPos;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        attackPos = false;
    }

    public Position(int x, int y, boolean attackPos) {
        this.x = x;
        this.y = y;
        this.attackPos = attackPos;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAttackPos() {
        return attackPos;
    }

    public void setAttackPos(boolean attackPos) {
        this.attackPos = attackPos;
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y + "\t" + isAttackPos();
    }
}
