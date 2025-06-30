package org.practice.application.model;

public class Vertex {
    private int id;
    private int color;
    private boolean isFirstDfs;
    private boolean isSecondDfs;

    public Vertex(int id) {
        this.id = id;
        this.color = -1;
        this.isFirstDfs = false;
        this.isSecondDfs = false;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return this.color;
    }

    public boolean getStateFisrtDFS() {
        return this.isFirstDfs;
    }

    public boolean getStateSecondDFS() {
        return this.isSecondDfs;
    }

    public void firstDFS() {
        this.isFirstDfs = true;
    }

    public void secondDFS() {
        this.isSecondDfs = true;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void clearVertex(){
        this.color = -1;
        this.isFirstDfs = false;
        this.isSecondDfs = false;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

}