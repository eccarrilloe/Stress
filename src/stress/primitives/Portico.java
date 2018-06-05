package stress.primitives;

import processing.core.PGraphics;
import processing.core.PApplet;
import processing.core.PShape;

import frames.core.Frame;
import frames.primitives.Vector;
import frames.primitives.Quaternion;
import frames.processing.Scene;
import frames.processing.Shape;
import processing.core.PVector;

public class Portico extends Shape{
    private Scene _scene;

    private PShape _section;
    private Vector _i;
    private Vector _j;
    private String _label;

    private boolean _drawLocalAxes;
    private boolean _showExtrude;
    private int _porticoColor;
    private int _labelColor;
    private int _labelSize;
    private boolean _drawLabel;

    public Portico(Scene scene, PShape section, Vector i, Vector j, String label) {
        this(scene, section, i, j, label, false, false, scene.pApplet().color(63, 0, 255), scene.pApplet().color(0),
                14, false);  // ver como solucionar scene.pApplet().color
    }

    public Portico(Scene scene, PShape section, Vector i, Vector j, String label, boolean drawLocalAxes,
                   boolean showExtrude, int porticoColor, int labelColor, int labelSize, boolean drawLabel) {
        super(scene);

        setScene(scene);

        setSection(section);
        setI(i);
        setJ(j);
        setLabel(label);

        setDrawLocalAxes(drawLocalAxes);
        setShowExtrude(showExtrude);
        setPorticoColor(porticoColor);
        setLabelColor(labelColor);
        setLabelSize(labelSize);
        setDrawLabel(drawLabel);

        setPosition(i);
        setRotation(new Quaternion(new Vector(1, 0, 0), localVector()));

        setPrecision(Precision.EXACT);
        setHighlighting(Highlighting.NONE);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    public PShape section() {
        return _section;
    }

    public void setSection(PShape section) {
        _section = section;
    }

    public Vector i() {
        return _i;
    }

    public void setI(Vector i) {
        _i = i;
    }

    public Vector j() {
        return _j;
    }

    public void setJ(Vector j) {
        _j = j;
    }

    public String label() {
        return _label;
    }

    void setLabel(String label) {
        _label = label;
    }

    public boolean drawLocalAxes() {
        return _drawLocalAxes;
    }

    public void setDrawLocalAxes(boolean drawLocalAxes) {
        _drawLocalAxes = drawLocalAxes;
    }

    public boolean showExtrude() {
        return _showExtrude;
    }

    public void setShowExtrude(boolean showExtrude) {
        _showExtrude = showExtrude;
    }

    public int porticoColor() {
        return _porticoColor;
    }

    public void setPorticoColor(int porticoColor) {
        _porticoColor = porticoColor;
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

    public boolean drawLabel() {
        return _drawLabel;
    }

    public void setDrawLabel(boolean drawLabel) {
        _drawLabel = drawLabel;
    }

    public PGraphics frontBuffer() {
        return scene().frontBuffer();
    }

    public Vector localVector() {
        return Vector.subtract(j(), i());
    }

    public float deltaXLocal() {
        return localVector().magnitude();
    }

    public PShape extrude() {
        PVector n;
        PShape extrude;

        extrude = frontBuffer().createShape();
        extrude.beginShape(PGraphics.QUAD_STRIP);
        for (int i = 0; i < section().getVertexCount(); i++) {
            n = section().getVertex(i);

            extrude.vertex(n.x, n.y, 0);
            extrude.vertex(n.x, n.y, deltaXLocal());
        }
        n = section().getVertex(section().getVertexCount() - 1);

        extrude.vertex(n.x, n.y, 0);
        extrude.vertex(n.x, n.y, deltaXLocal());

        n = section().getVertex(0);

        extrude.vertex(n.x, n.y, 0);
        extrude.vertex(n.x, n.y, deltaXLocal());

        extrude.endShape();

        return extrude;
    }

    public PApplet pApplet() {
        return scene().pApplet();
    }

//    private PGraphics frontBuffer() {
//        return scene().frontBuffer();
//    }

    @Override
    public void setGraphics(PGraphics pGraphics) {
        if (drawLocalAxes()) {
            pGraphics.pushMatrix();
            pGraphics.translate(deltaXLocal() / 2, 0, 0);
            scene().drawAxes(pGraphics, scene().radius() / 6);
            pGraphics.popMatrix();
        }

        pGraphics.pushMatrix();
        pGraphics.rotateY(PApplet.radians(90));

        if (!showExtrude()) {
            pGraphics.pushStyle();
            pGraphics.noStroke();

            if (!scene().isTrackedFrame(this)) {
                pGraphics.fill(porticoColor());
            } else {
                pGraphics.fill(255 - pApplet().red(porticoColor()), 255 - pApplet().green(porticoColor()),
                        255 - pApplet().blue(porticoColor()));
            }

            Scene.drawCylinder(pGraphics, 0.1f, deltaXLocal());

            pGraphics.popStyle();
        } else {
            pGraphics.pushStyle();

            if (!scene().isTrackedFrame(this)) {
                pGraphics.stroke(porticoColor());
                pGraphics.fill(pApplet().red(porticoColor()), pApplet().green(porticoColor()),
                        pApplet().blue(porticoColor()), 63);
            } else {
                pGraphics.stroke(255 - pApplet().red(porticoColor()), 255 - pApplet().green(porticoColor()),
                        255 - pApplet().blue(porticoColor()));
                pGraphics.fill(255 - pApplet().red(porticoColor()), 255 - pApplet().green(porticoColor()),
                        255 - pApplet().blue(porticoColor()), 63);
            }

            pGraphics.rotateZ(PApplet.radians(90));
            pGraphics.shape(extrude());

            pGraphics.pushMatrix();
            pGraphics.shape(section());
            pGraphics.popMatrix();

//            pGraphics.pushMatrix();
//            pGraphics.translate(deltaXLocal(), 0, 0);
//            pGraphics.shape(section());
//            pGraphics.popMatrix();
//
            pGraphics.popStyle();
        }

        pGraphics.popMatrix();
//

//
//            pGraphics.pushMatrix();
//            pGraphics.rotateY(PApplet.radians(90));
//            // pGraphics.rotateZ(PApplet.radians(90)); //
//            pGraphics.shape(section());
//            pGraphics.popMatrix();
//        } else {
//            pGraphics.pushStyle();
//            pGraphics.stroke(porticoColor());
//            pGraphics.strokeWeight(5);
//            pGraphics.line(0, 0, 0, deltaXLocal(), 0, 0);
//            pGraphics.popStyle();
//        }
//        pGraphics.endDraw();
    }
}
