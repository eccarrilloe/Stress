package stress.core;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;

import frames.processing.Scene;
import frames.primitives.Vector;

import stress.primitives.Portico;

public class Porticos {
    public Scene _scene;

    public ArrayList<Portico> _porticos;

    public boolean _drawLabels;

    public Porticos(Scene scene) {
        setScene(scene);

        setPorticos(new ArrayList<>());

        setDrawLabels(false);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    public ArrayList<Portico> porticos() {
        return _porticos;
    }

    public void setPorticos(ArrayList<Portico> porticos) {
        _porticos = porticos;
    }

    public boolean drawLabels() {
        return _drawLabels;
    }

    public void setDrawLabels(boolean drawLabel) {
        for (Portico portico : porticos()) {
            portico.setDrawLabel(drawLabel);
        }
        _drawLabels = drawLabel;
    }

    public void add(PShape section, Vector i, Vector j) {
        add(new Portico(scene(), section, i, j, Integer.toString(porticos().size() + 1)));
    }

    public void add(Portico portico) {
        porticos().add(portico);
        PApplet.println(porticos().size());
    }
}
