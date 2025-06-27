package org.practice.application.model;

public class Edge {
    private final Vertex source;
    private final Vertex target;
    public boolean isBridge;
    public Edge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
        this.isBridge = false;
    }
}
