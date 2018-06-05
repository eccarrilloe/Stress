package stress.core;

import java.util.ArrayList;

import frames.processing.Scene;

import stress.primitives.Node;

public class Nodes {

    Scene _scene;

    ArrayList<Node> _nodes;

    boolean _drawLabel;

    public Nodes(Scene scene) {
        setScene(scene);

        setNodes(new ArrayList<>());

//        setDrawLabel(true);
    }
//
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


    public boolean drawLabel() {
        return _drawLabel;
    }

    void setDrawLabel(boolean drawLabel) {
//        for (Nodo nodo : nodos()) {
//            nodo.setDrawEtiqueta(drawEtiqueta);
//        }
//        _drawEtiqueta = drawEtiqueta;
//    }
//
//    void add(Vector i) {
//        add(new Nodo(scene(), i, Integer.toString(nodos().size() + 1)));
//    }
//
//    void add(Nodo nodo) {
//        nodos().add(nodo);
//    }
}



}
