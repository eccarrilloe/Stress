package stress.primitives;

import frames.processing.Scene;
import frames.processing.Shape;
import frames.primitives.Vector;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Point extends Shape {
    private PApplet _pApplet;
    private PGraphics _frontBuffer;
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
        setPApplet(scene().pApplet());
        setFrontBuffer(scene().frontBuffer());

        setPosition(i);

        setCrossSize(20);
        setCrossColor(pApplet().color(53, 56, 57));
        setCrossWeightStroke(1.5f);

        setPrecision(Precision.FIXED);
//        setHighlighting(Highlighting.FRONT);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        this._scene = scene;
    }

    public PApplet pApplet() {
        return _pApplet;
    }

    public void setPApplet(PApplet pApplet) {
        this._pApplet = pApplet;
    }

    public PGraphics frontBuffer() {
        return _frontBuffer;
    }

    public void setFrontBuffer(PGraphics frontBuffer) {
        this._frontBuffer = frontBuffer;
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

    @Override
    public void setGraphics(PGraphics pGraphics) {
        Vector center = scene().screenLocation(position());

        pGraphics.pushStyle();
        pGraphics.colorMode(PApplet.RGB, 255);

        if (scene().isTrackedFrame(this)) {
            pGraphics.stroke(255 - pApplet().red(crossColor()),
                    255 - pApplet().green(crossColor()), 255 - pApplet().blue(crossColor()));
        } else {
            pGraphics.stroke(pApplet().red(crossColor()), pApplet().green(crossColor()), pApplet().blue(crossColor()));
        }

        pGraphics.strokeWeight(crossWeightStroke());
        pGraphics.noFill();

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
