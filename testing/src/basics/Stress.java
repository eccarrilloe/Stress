package basics;

import processing.core.PApplet;
import processing.event.MouseEvent;

import frames.core.Graph;
import frames.primitives.Vector;
import frames.processing.Scene;

import stress.primitives.Point;
import stress.primitives.Grid;

public class Stress extends PApplet {
    Scene scene;

    Point _trackedPoint;
    Grid grid;

    public void settings() {
        size(600, 400, P3D);
    }

    public void setup() {
        scene = new Scene(this);
        scene.setType(Graph.Type.ORTHOGRAPHIC);
        scene.setFieldOfView(PI/3);
        scene.fitBall();
        scene.setRightHanded();

        grid = new Grid(scene, new Vector(10, 0, 0), new Vector(10, 10, 0), "A");
    }

    public void draw() {
        background(127);
        scene.drawAxes();
        scene.cast();
    }

    Point trackedPoint() {
        return _trackedPoint;
    }

    void setTrackedPoint() {
        _trackedPoint = null;
    }

    public void mouseDragged() {
        if (mouseButton == LEFT) ;
        if (mouseButton == CENTER) scene.mousePan();
        if (mouseButton == RIGHT) scene.mouseCAD(new Vector(0, 0, 1));
    }

    public void mouseMoved() {
        setTrackedPoint();
    }

    public void mouseWheel(MouseEvent event) {
        scene.setTrackedFrame(scene.eye());  // revisar una mejor implementacion
        scene.scale(-20 * event.getCount());
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"basics.Stress"});
    }
}
