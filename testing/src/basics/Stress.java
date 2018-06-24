package basics;

import processing.core.PApplet;/*
import processing.core.PShape;
import processing.event.MouseEvent;*/

import frames.core.Graph;
import frames.processing.Scene;/*
import frames.primitives.Vector;*/
/*
import stress.core.Axes;
import stress.core.Nodes;
import stress.core.Porticos;*/
import stress.core.StressManager;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Stress extends PApplet {
    public Scene scene;

    // public Axes axes;
    // public Nodes nodes;
    // public Porticos porticos;

    // public PShape section;

    // public Vector i;
    // public Vector j;

    // public boolean showCoordinates = true;
    // public boolean showLevel = true;
    // public boolean showTrackedObject = true;

    // public boolean drawLevels = false;

    // public boolean addNode = false;
    // public boolean addPortico = false;

    // public Vector _worldCoordinatesMouse = new Vector();
    // public String _coordinates;
    // public String _level;
    // public String _trackedObject;

    public StressManager stressManager;

    public void settings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  // get screen dimensions
        this.width = screenSize.width - 400;
        this.height = screenSize.height - 200;

        size(width, height, P3D);
    }

    public void setup() {
        scene = new Scene(this);
        scene.setType(Graph.Type.ORTHOGRAPHIC);  // toggle between orthographic and perspective
        scene.setFieldOfView(PI/3);
        scene.setRightHanded();
        scene.setRadius(25);
        scene.fitBall();

        stressManager = new StressManager(this, scene);
        stressManager.initManager();

        // section
        /*section = createShape();
        section.beginShape();
        section.fill(color(0, 0, 255, 63));
        section.stroke(color(0, 0, 255));
        section.vertex( 0.125f,  0.225f);
        section.vertex(-0.125f,  0.225f);
        section.vertex(-0.125f, -0.225f);
        section.vertex( 0.125f, -0.225f);
        section.endShape(CLOSE);*/
    }

    public void draw() {
        background(127);
        scene.drawAxes(scene.radius() / 3); // esto debe estar en el manager
        // scene.cast();  // revisar si esto sigue siendo lo mismo
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"basics.Stress"});
    }
}
