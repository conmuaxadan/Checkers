package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.*;

import java.util.List;

public abstract class Checker {
    protected String checkerType;
    protected Position position;
    protected String iconLink;
    protected boolean isAttacken;

    public Checker(String checkerType, Position position) {
        this.checkerType = checkerType;
        this.position = position;
        isAttacken = false;
    }

    public void moveCheckerToAnotherPos(Checker checker, Position pos) {

    }
    public boolean isValid(Position newPos, CheckerBoard board){
        for (Position pos: this.getValidPositions(board)
             ) {
            if (pos.getX() == newPos.getX() && pos.getY() == newPos.getY())
                return true;
        }
        return false;
    }

    public boolean isAttackPos(Position newPos, CheckerBoard board) {
        for (Position pos: this.getValidPositions(board)
        ) {
            if (pos.getX() == newPos.getX() && pos.getY() == newPos.getY() && pos.isAttackPos())
                return true;
        }
        return false;
    }
    public abstract List<Position> getValidPositions(CheckerBoard board);

    public String getCheckerType() {
        return checkerType;
    }

    public void setCheckerType(String checkerType) {
        this.checkerType = checkerType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public boolean isAttacken() {
        return isAttacken;
    }

    public void setAttacken(boolean attacken) {
        isAttacken = attacken;
    }

}
