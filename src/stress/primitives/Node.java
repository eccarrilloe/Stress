package stress.primitives;

import processing.core.PApplet;

import frames.core.Frame;
import frames.primitives.Vector;
import frames.processing.Scene;
import processing.core.PGraphics;

public class Node extends Frame {
    private Scene _scene;

    private String _label;

    private float _nodeSize;
    private int _nodeColor;

    private int _labelColor;
    private int _labelSize;

    private boolean _drawLabel;

    public Node(Scene scene, Vector i, String label) {
        this(scene, i, label, 7.5f, scene.pApplet().color(128, 0, 255), scene.pApplet().color(0),
                14, true);  // ver como solucionar scene.pApplet().color
    }

    public Node(Scene scene, Vector i, String label, float nodeSize, int nodeColor, int labelColor, int labelSize,
         boolean drawLabel) {
        super(scene);

        setScene(scene);
        setPosition(i);

        setLabel(label);

        setNodeSize(nodeSize);
        setNodeColor(nodeColor);

        setLabelColor(labelColor);
        setLabelSize(labelSize);

        setDrawLabel(drawLabel);
    }

    Scene scene() {
        return _scene;
    }

    void setScene(Scene scene) {
        _scene = scene;
    }

    String label() {
        return _label;
    }

    void setLabel(String label) {
        _label = label;
    }

    float nodeSize() {
        return _nodeSize;
    }

    void setNodeSize(float nodeSize) {
        _nodeSize = nodeSize;
    }

    int nodeColor() {
        return _nodeColor;
    }

    void setNodeColor(int nodeColor) {
        _nodeColor = nodeColor;
    }

    int labelColor() {
        return _labelColor;
    }

    void setLabelColor(int labelColor) {
        _labelColor = labelColor;
    }

    float labelSize() {
        return _labelSize;
    }

    void setLabelSize(int labelSize) {
        _labelSize = labelSize;
    }

    boolean drawLabel() {
        return _drawLabel;
    }

    void setDrawLabel(boolean drawLabel) {
        _drawLabel = drawLabel;
    }

    private PApplet pApplet() {
        return scene().pApplet();
    }

    private PGraphics frontBuffer() {
        return scene().frontBuffer();
    }

    @Override
    public void visit() {
        int nodeColorTracked = pApplet().color(255 - pApplet().red(nodeColor()), 255 - pApplet().green(nodeColor()),
                255 - pApplet().blue(nodeColor()));
        scene().beginScreenDrawing();
        frontBuffer().pushStyle();
        frontBuffer().noStroke();
        if (!scene().isTrackedFrame(this)) {
            frontBuffer().fill(nodeColor());
        } else {
            frontBuffer().fill(nodeColorTracked);
        }

        Vector iCoordinates = scene().screenLocation(position());
        frontBuffer().ellipse(iCoordinates.x(), iCoordinates.y(), nodeSize(), nodeSize());
        frontBuffer().popStyle();
        scene().endScreenDrawing();

        if (drawLabel()) {
            Vector iScreen = scene().screenLocation(position());

            scene().beginScreenDrawing();
            frontBuffer().pushStyle();
            frontBuffer().textAlign(PApplet.CENTER, PApplet.CENTER);
            frontBuffer().textSize(labelSize());  // problemas
            frontBuffer().fill(labelColor());
            frontBuffer().text(label(), iScreen.x() + labelSize(), iScreen.y() - labelSize());  // problemas
            frontBuffer().popStyle();
            scene().endScreenDrawing();
        }
    }
}
