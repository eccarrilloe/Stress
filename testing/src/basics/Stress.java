package basics;

import processing.core.PApplet;
import processing.event.MouseEvent;

import frames.core.Graph;
import frames.primitives.Vector;
import frames.processing.Scene;

//import stress.primitives.Point;
//import stress.primitives.Axis;
import stress.primitives.Axes;
import stress.primitives.Node;

public class Stress extends PApplet {
    Scene scene;

    Axes axes;

    Node node;

    Vector _worldCoordinatesMouse = new Vector();

    public void settings() {
        size(600, 400, P3D);
    }

    public void setup() {
        scene = new Scene(this);
        scene.setType(Graph.Type.ORTHOGRAPHIC);
        scene.setFieldOfView(PI/3);
        scene.setRightHanded();
        scene.setRadius(25);
        scene.fitBall();

        // Axes
        axes = new Axes(scene);
        int dx = 5;
        int dy = 5;
        int length = dx * dy;
        String[] xBubbleText = {"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            axes.addAxis(new Vector(-dx, dy * i), new Vector(length + dy, dy * i), xBubbleText[i]);
        }
        String[] yBubbleText = {"1", "2", "3", "4", "5"};
        for (int i = 0; i < 5; i++) {
            axes.addAxis(new Vector(dx * i, length), new Vector(dx * i, -dy), yBubbleText[i]);
        }

        // Node
        node = new Node(scene, new Vector(10, 10, 0), "holi");
    }

    public void draw() {
        background(127);
        scene.drawAxes(scene.radius() / 3);
        scene.cast();

        println(worldCoordinatesMouse());
    }

//    Point trackedPoint() {
//        return _trackedPoint;
//    }

//    public Axis trackedAxis() {
//        return _trackedAxis;
//    }

//    void setTrackedPoint() {
//        _trackedPoint = null;
//    }

//    public void setTrackedAxis(Axis trackedAxis) {
//        _trackedAxis = trackedAxis;
//    }

//    public void mouseClicked() {
//        scene.focus();
//    }

    public void mouseDragged() {
        if (mouseButton == LEFT) ;
        if (mouseButton == CENTER) scene.mousePan();
        if (mouseButton == RIGHT) scene.mouseCAD(new Vector(0, 0, 1));
    }

    Vector worldCoordinatesMouse() {
        return _worldCoordinatesMouse;
    }

    void setWorldCoordinatesMouse() {
        if (scene.trackedFrame() == null) {
            _worldCoordinatesMouse = scene.location(new Vector(mouseX, mouseY));
        } else {
            if (!scene.trackedFrame().getClass().getName().equals("stress.primitives.Axis")) {
                _worldCoordinatesMouse = scene.trackedFrame().position();
            }
        }
    }

    public void mouseMoved() {
        setWorldCoordinatesMouse();
//        setTrackedPoint();
//        if (scene.track(mouseX, mouseY, axis)) scene.setTrackedFrame(axis);
    }

    public void mouseWheel(MouseEvent event) {
        scene.setTrackedFrame(scene.eye());  // revisar una mejor implementacion
        scene.scale(-20 * event.getCount());
        setWorldCoordinatesMouse();
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"basics.Stress"});
    }
}
