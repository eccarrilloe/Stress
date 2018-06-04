package basics;

import processing.core.PApplet;
import processing.event.MouseEvent;

import frames.core.Graph;
import frames.primitives.Vector;
import frames.processing.Scene;

// import stress.primitives.Point;
// import stress.primitives.Axis;
import stress.core.Axes;
import stress.primitives.Node;

public class Stress extends PApplet {
    Scene scene;

    Axes axes;

    Node node;

    boolean showCoordinates;

    Vector _worldCoordinatesMouse = new Vector();
    String _coordinates;


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

        if (showCoordinates) showCoordinates();
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

    private Vector worldCoordinatesMouse() {
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

        setCoordinates(coordinates);
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
//            case 'e':
//                nodos.setDrawEtiqueta(!nodos.drawEtiqueta());
//                break;
//            case 'n':
//                addNodo = !addNodo;
//                if (addNodo) {
//                    println("add nodo");
//                } else {
//                    println("cancel");
//                }
//                break;
//            case '+':
//                indexNivelZ = indexNivelZ < ejes.nivelesZ().size() - 1 ? indexNivelZ + 1 : 0;
//                ejes.setActualIndexNivelZ(indexNivelZ);
//                break;
//            case '-':
//                indexNivelZ = 0 < indexNivelZ ? indexNivelZ - 1 : ejes.nivelesZ().size() - 1;
//                ejes.setActualIndexNivelZ(indexNivelZ);
//                break;
//            case 'z':
//                drawNivelesZ = !drawNivelesZ;
//                break;
        }
    }


    public static void main(String args[]) {
        PApplet.main(new String[]{"basics.Stress"});
    }
}
