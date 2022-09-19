package com.example.minesweeper;

public class Block {
    private int value;
    private boolean isRevealed;
    private boolean isFlagged;

    public Block() {
        this.value = 0;
        this.isRevealed = false;
        this.isFlagged = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        value = val;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isBomb(){
        if (this.getValue() == -1){
            return true;
        }
        return false;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
