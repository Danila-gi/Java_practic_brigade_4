package org.practice.application.model;

public class Edge {
    private final Vertex from;
    private final Vertex to;
    public boolean isBridge;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
        this.isBridge = false;
    }

    public int getFromId() {
        return this.from.getId();
    }

    public int getToId() {
        return this.to.getId();
    }

    public Vertex getFirstVertex() {
        return this.from;
    }

    public Vertex getSecondVertex() {
        return this.to;
    }
}
