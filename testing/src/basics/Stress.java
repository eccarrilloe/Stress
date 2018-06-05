package basics;

import processing.core.PApplet;
import processing.event.MouseEvent;

import frames.core.Graph;
import frames.primitives.Vector;
import frames.processing.Scene;

import stress.core.Axes;
import stress.core.Nodes;

public class Stress extends PApplet {
    public Scene scene;

    public Axes axes;

    public Nodes nodes;

    public boolean showCoordinates = true;
    public boolean showLevel = true;
    public boolean showTrackedObject = true;

    public boolean drawLevels = false;

    public boolean addNode;
//    public boolean addPortico;

    public Vector _worldCoordinatesMouse = new Vector();
    public String _coordinates;
    public String _level;
    public String _trackedObject;

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
        axes.addLevel( 0f);
        axes.addLevel( 5f);
        axes.addLevel(10f);

        // Nodes
        nodes = new Nodes(scene);
    }

    public void draw() {
        background(127);
        scene.drawAxes(scene.radius() / 3);
        scene.cast();

        if (showCoordinates) showCoordinates();
        if (showLevel) showLevel();
        if (showTrackedObject) showTrackedObject();

        if (drawLevels) drawLevels();
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

    public void addNode() {
        nodes.add(worldCoordinatesMouse());
    }

    public void addPortico() {
        ;
    }

    public Vector worldCoordinatesMouse() {
        return _worldCoordinatesMouse;
    }

    private void setWorldCoordinatesMouse() {
        if (scene.trackedFrame() == null) {
            _worldCoordinatesMouse = scene.location(new Vector(mouseX, mouseY));
        } else {
            if (!scene.trackedFrame().getClass().getName().equals("stress.primitives.Axis")) {
                _worldCoordinatesMouse = scene.trackedFrame().position();
            }
        }
    }

    private String coordinates() {
        return _coordinates;
    }

    private void setCoordinates(String coordinates) {
        _coordinates = coordinates;
    }

    void showCoordinates() {
        String coordinates;

        scene.beginScreenDrawing();
        pushStyle();

        textAlign(RIGHT, BOTTOM);
        textSize(14);

        fill(0);
        text(")", width, height);
        coordinates = ")";

        fill(0, 0, 255);
        text(nf(worldCoordinatesMouse().z(), 0, 3), width - textWidth(coordinates), height);
        coordinates = nf(worldCoordinatesMouse().z(), 0, 3) + coordinates;

        fill(0);
        text(" ,", width - textWidth(coordinates), height);
        coordinates = " ," + coordinates;

        fill(0, 255, 0);
        text(nf(worldCoordinatesMouse().y(), 0, 3), width - textWidth(coordinates), height);
        coordinates = nf(worldCoordinatesMouse().y(), 0, 3) + coordinates;

        fill(0);
        text(" ,", width - textWidth(coordinates), height);
        coordinates = " ," + coordinates;

        fill(255, 0, 0);
        text(nf(worldCoordinatesMouse().x(), 0, 3), width - textWidth(coordinates), height);
        coordinates = nf(worldCoordinatesMouse().x(), 0, 3) + coordinates;

        fill(0);
        text("(", width - textWidth(coordinates), height);
        coordinates = "(" + coordinates;

        scene.endScreenDrawing();
        popStyle();

        setCoordinates("     " + coordinates);
    }

    public String level() {
        return _level;
    }

    public void setLevel(String level) {
        _level = level;
    }

    public void showLevel() {
        String level;

        scene.beginScreenDrawing();
        pushStyle();

        textAlign(RIGHT, BOTTOM);
        textSize(14);

        fill(0);

        level = "Nivel Z: " + nf(axes.levels().get(axes.actualIndexLevel()), 0, 3);

        text(level, width - textWidth(coordinates()), height);

        scene.endScreenDrawing();
        popStyle();

        setLevel("     " + level + coordinates());
    }

    public void drawLevels() {
        for (int i = 0; i < axes.levels().size(); i++) {
            pushStyle();
            if (i == axes.actualIndexLevel()) {
                stroke(144, 238, 144);
                fill(144, 238, 144, 15);
            } else {
                stroke(31, 117, 254);
                fill(31, 117, 254, 15);
            }
            pushMatrix();
            translate(0, 0, axes.levels().get(i));
            ellipse(scene.center().x(), scene.center().y(),
                    2 * scene.radius(), 2 * scene.radius());
            popMatrix();
            popStyle();
        }
    }

    public String trackedObject() {
        return _trackedObject;
    }

    public void setTrackedObject(String trackedObjec) {
        _trackedObject = trackedObjec;
    }

    public void showTrackedObject() {
        String trackedObject;

        scene.beginScreenDrawing();
        pushStyle();

        textAlign(RIGHT, BOTTOM);
        textSize(14);

        fill(0);

        trackedObject = "";

        if (!(scene.trackedFrame() == null)) {
            trackedObject = scene.trackedFrame().getClass().getSimpleName();
        }

        text(trackedObject, width - max(textWidth(level()), textWidth(coordinates())), height);

        scene.endScreenDrawing();
        popStyle();

        setTrackedObject("     " + trackedObject + level());
    }

    public void mouseClicked(MouseEvent event) {
        if (addNode && mouseButton == LEFT && event.getCount() == 1) {
            addNode();
            addNode = false;
            println("done");
        }
//        scene.focus();
    }

    public void mouseDragged() {
        if (mouseButton == LEFT) ;
        if (mouseButton == CENTER) scene.mousePan();
        if (mouseButton == RIGHT) scene.mouseCAD(new Vector(0, 0, 1));
    }

    public void mouseMoved() {
        setWorldCoordinatesMouse();
    }

    public void mouseWheel(MouseEvent event) {
        /*
        No hay implementando un mouseScale, por lo tanto, scene.scale() recae sobre el trackedFrame
        Revisar una mejor implementación.
        Solución: implementar nuestro propio mouse
         */
        scene.setTrackedFrame(scene.eye());  // revisar una mejor implementacion
        scene.scale(-20 * event.getCount());
        setWorldCoordinatesMouse();
    }

    public void keyPressed() {
        switch (key) {
            case 'c':
                println("Las coordenadas deben ser administradas en el stressManager");
                showCoordinates = !showCoordinates;
                if (!showCoordinates) setCoordinates("");
                break;
            case 'e':
                showLevel = !showLevel;
                if(!showLevel) setLevel("");
                break;
            case 'f':
                showTrackedObject = !showTrackedObject;
                if(!showTrackedObject) setTrackedObject("");
            case 'l':
                nodes.setDrawLabels(!nodes.drawLabels());
                break;
            case 'n':
                addNode = !addNode;
                if (addNode) {
                    println("add nodo");
                } else {
                    println("cancel");
                }
                break;
            case '+':
                axes.setActualIndexLevel(axes.actualIndexLevel() < axes.levels().size() - 1 ?
                        axes.actualIndexLevel() + 1 : 0);
                break;
            case '-':
                axes.setActualIndexLevel(0 < axes.actualIndexLevel() ?
                        axes.actualIndexLevel() - 1 : axes.levels().size() - 1);
                break;
            case 'z':
                drawLevels = !drawLevels;
                break;
        }
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"basics.Stress"});
    }
}
