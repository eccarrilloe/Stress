package stress.core;

import java.util.ArrayList;

import frames.processing.Scene;
import frames.primitives.Vector;

import stress.primitives.Node;

public class Nodes {
    Scene _scene;

    ArrayList<Node> _nodes;

    boolean _drawLabels;

    public Nodes(Scene scene) {
        setScene(scene);

        setNodes(new ArrayList<>());

        setDrawLabels(false);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    public ArrayList<Node> nodes() {
        return _nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        _nodes = nodes;
    }


    public boolean drawLabels() {
        return _drawLabels;
    }

    public void setDrawLabels(boolean drawLabel) {
        for (Node node : nodes()) {
            node.setDrawLabel(drawLabel);
        }
        _drawLabels = drawLabel;
    }

    public void add(Vector i) {
        add(new Node(scene(), i, Integer.toString(nodes().size() + 1)));
    }

    public void add(Node node) {
        nodes().add(node);
    }
}
