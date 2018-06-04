package stress.primitives;

import frames.processing.Scene;
import frames.processing.Shape;
import frames.primitives.Vector;

import processing.core.PApplet;
import processing.core.PGraphics;
/*
Creo que no tiene sentido extender de Shape si punto se dibuja en pantalla y no se le puede hacer tracked
 */
public class Point extends Shape {
    private Scene _scene;

    private float _crossSize;
    private int _crossColor;
    private float _crossWeightStroke;

    // private boolean _highlight;

    public Point(Scene scene) {
        this(scene, new Vector());
    }

    public Point(Scene scene, Vector i) {
        super(scene);

        setScene(scene);

        setPosition(i);

        setCrossSize(20);
        setCrossColor(pApplet().color(53, 56, 57));  // encontrar una mejor solución a pApplet().color()
        setCrossWeightStroke(1.5f);

         setPrecision(Precision.FIXED);  // es como si fuera un Frame en vez de un Shape ?
//        setHighlighting(Highlighting.FRONT);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        this._scene = scene;
    }


    public float crossSize() {
        return _crossSize;
    }

    public void setCrossSize(float crossSize) {
        this._crossSize = crossSize;
    }

    public int crossColor() {
        return _crossColor;
    }

    public void setCrossColor(int crossColor) {
        this._crossColor = crossColor;
    }

    public float crossWeightStroke() {
        return _crossWeightStroke;
    }

    public void setCrossWeightStroke(float crossWeightStroke) {
        this._crossWeightStroke = crossWeightStroke;
    }

    public PApplet pApplet() {
        return scene().pApplet();
    }

    @Override
    public void setGraphics(PGraphics pGraphics) {
        Vector center = scene().screenLocation(position());

        pGraphics.pushStyle();
        pGraphics.colorMode(PApplet.RGB, 255);

        if (!scene().isTrackedFrame(this)) {
            pGraphics.stroke(pGraphics.red(crossColor()), pGraphics.green(crossColor()), pGraphics.blue(crossColor()));
        } else {
            pGraphics.stroke(255 - pGraphics.red(crossColor()),
                    255 - pGraphics.green(crossColor()), 255 - pGraphics.blue(crossColor()));
        }

        pGraphics.strokeWeight(crossWeightStroke());
        pGraphics.noFill();
        /*
        Se necesita pGraphics.beginShape()... pGraphics.vertex() para que se seleccione con precision ?
         */
        scene().beginScreenDrawing();

        pGraphics.ellipse(center.x(), center.y(),
                3 * crossSize() / 8, 3 * crossSize() / 8);

        pGraphics.line(center.x() - crossSize() / 2, center.y(),
                center.x() + crossSize() / 2, center.y());
        pGraphics.line(center.x(), center.y() - crossSize() / 2,
                center.x(), center.y() + crossSize() / 2);

        scene().endScreenDrawing();

        pGraphics.popStyle();
    }
}
