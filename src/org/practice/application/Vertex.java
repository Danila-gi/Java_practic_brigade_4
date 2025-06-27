package org.practice.application;

public class Vertex {
    private int number;
    private boolean checked_in_first_dfs;
    private boolean checked_in_second_dfs;
    private int color;

    public Vertex(int number) {
        this.number = number;
        this.checked_in_first_dfs = false;
        this.checked_in_second_dfs = false;
        this.color = -1;
    }

    public int getNumber() {
        return this.number;
    }

    public int getColor() {
        return this.color;
    }

    public boolean getStateFisrtDFS() {
        return this.checked_in_first_dfs;
    }

    public boolean getStateSecondDFS() {
        return this.checked_in_second_dfs;
    }

    public void firstDFS() {
        this.checked_in_first_dfs = true;
    }

    public void secondDFS() {
        this.checked_in_second_dfs = true;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
