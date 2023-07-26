package com.example.deltaproject2;

import android.graphics.Rect;

public class let {
    private Character letter;
    private int x;
    private int y;
    Rect bounds;
    boolean isfloating;
    boolean Touched;

    public boolean isTouched() {
        return Touched;
    }

    public void setTouched(boolean touched) {
        Touched = touched;
    }

    public Rect getBounds() {
        return bounds;
    }

    public void setBounds(Rect bounds) {
        this.bounds = bounds;
    }

    public boolean isIsfloating() {
        return isfloating;
    }

    public void setIsfloating(boolean isfloating) {
        this.isfloating = isfloating;
    }

    public let(Character letter, int x, int y) {
        this.letter = letter;
        this.x = x;
        this.y = y;
    }
    public let(Character letter,  int y) {
        this.letter = letter;
        this.y = y;
    }
    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
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
}
