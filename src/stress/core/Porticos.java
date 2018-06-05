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

    public boolean _showExtrude;

    public boolean _drawLabels;
    public boolean _drawLocalAxes;

    public Porticos(Scene scene) {
        setScene(scene);

        setPorticos(new ArrayList<>());

        setShowExtrude(false);
        setDrawLabels(false);
        setDrawLocalAxes(false);
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

    public boolean showExtrude() {
        return _showExtrude;
    }

    public void setShowExtrude(boolean showExtrude) {
        for (Portico portico : porticos()) {
            portico.setShowExtrude(showExtrude);
        }
        _showExtrude = showExtrude;
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

    public boolean drawLocalAxes() {
        return _drawLocalAxes;
    }

    public void setDrawLocalAxes(boolean drawLocalAxes) {
        for (Portico portico: porticos()) {
            portico.setDrawLocalAxes(drawLocalAxes);
        }
        _drawLocalAxes = drawLocalAxes;
    }

    public void add(PShape section, Vector i, Vector j) {
        add(new Portico(scene(), section, i, j, Integer.toString(porticos().size() + 1)));
    }

    public void add(Portico portico) {
        porticos().add(portico);
        System.out.println(porticos().get(0));
    }
}
