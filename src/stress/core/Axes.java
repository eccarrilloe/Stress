package stress.core;

import java.util.ArrayList;

import processing.core.PApplet;

import frames.primitives.Vector;
import frames.processing.Scene;

import stress.primitives.Axis;
import stress.primitives.Point;


public class Axes {
    private Scene _scene;

    private ArrayList<Axis> _axis;
    private ArrayList<Point> _points;

    int _actualIndexLevel;
    ArrayList<Float> _levels;

    public Axes(Scene scene) {
        setScene(scene);

        setAxis(new ArrayList<>());
        setPoints(new ArrayList<>());
        setLevels(new ArrayList<>());

        addLevel(0);
        setActualIndexLevel(0);

        setLevels(new ArrayList<>());
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    public ArrayList<Axis> axis() {
        return _axis;
    }

    public void setAxis(ArrayList<Axis> axis) {
        _axis = axis;
    }

    public ArrayList<Point> points() {
        return _points;
    }

    public void setPoints(ArrayList<Point> points) {
        _points = points;
    }

    public int actualIndexLevel() {
        return _actualIndexLevel;
    }

    public void setActualIndexLevel(int indexLevel) {
        _actualIndexLevel = indexLevel;

        if ((0 <= indexLevel) && (indexLevel < levels().size())) {
            for (Point point : points()) {
                point.setLevel(levels().get(actualIndexLevel()));
            }
            for (Axis axis : axis()) {
                axis.setLevel(levels().get(actualIndexLevel()));
            }
        }
    }

    public ArrayList<Float> levels() {
        return _levels;
    }

    public void setLevels(ArrayList<Float> levels) {
        _levels = levels;
    }

    public void addLevel(float level) {
        levels().add(level);
    }

    public void addAxis(Vector i, Vector j, String bubbleTexto) {
        addAxis(new Axis(scene(), i, j, bubbleTexto));
    }

    public void addAxis(Axis axis) {
        axis().add(axis);
        modifyAxes();
        modifyBallScene();
    }

    private void modifyAxes() {
        int axisSize = axis().size();

        if (axisSize > 1) {
            Axis lastAxis = axis().get(axisSize - 1);
            Vector intersection;

            for (int i = 0; i < axisSize - 1; i++) {
                intersection = intersectionBetweenAxis(axis().get(i), lastAxis);
                if (intersection != null) {
                    points().add(new Point(scene(), intersection));
                }
            }
        }
    }

    private void modifyBallScene() {
        if (points().size() > 0) {
            Vector center = new Vector();

            for (int i = 0; i < points().size(); i++) {
                center.add(points().get(i).position());
            }
            center.divide(points().size());

            scene().setCenter(center);
            scene().setAnchor(center);
            scene().setRadius(1.5f * center.magnitude());
        }
    }

//    /**
//     * Add a point
//     */
//    void addPunto(Vector i) {
//        puntos().add(new Punto(scene(), i));
//    }

    /**
     * Get vector intersection between two ejes
     * Se debe mejorar la precisión del algoritmo evitando al máximo operaciones intermedias
     * y errores por truncamiento
     */
    private Vector intersectionBetweenAxis(Axis axis1, Axis axis2) {
        Vector intersection;

        float dx1 = axis1.j().x() - axis1.i().x();
        float dx2 = axis2.j().x() - axis2.i().x();

        float dy1 = axis1.j().y() - axis1.i().y();
        float dy2 = axis2.j().y() - axis2.i().y();

        float dx12 = axis2.i().x() - axis1.i().x();
        float dy12 = axis2.i().y() - axis1.i().y();

        float alpha = (dx2 * dy1 - dx1 * dy2);

        if (1e-5 < PApplet.abs(alpha)) {
            alpha = 1 / alpha;

            float t1 = alpha * (dx2 * dy12 - dy2 * dx12);
            float t2 = alpha * (dx1 * dy12 - dy1 * dx12);

            if ((0 <= t1) && (t1 <= 1) && (0 <= t2) && (t2 <= 1)) {
                Vector i = new Vector(axis1.i().x(), axis1.i().y(), axis1.i().z());
                Vector j = new Vector(axis1.j().x(), axis1.j().y(), axis1.j().z());
                i.multiply(1 - t1);
                j.multiply(t1);
                intersection = Vector.add(i, j);
            } else {
                intersection = null;  // the lines don't cross between them
            }
        } else {
            intersection = null;  // the line are parallels
        }

        return intersection;
    }
}
