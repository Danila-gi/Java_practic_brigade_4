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
}