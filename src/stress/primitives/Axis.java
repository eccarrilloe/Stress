package stress.primitives;

import frames.primitives.Quaternion;
import processing.core.PApplet;
import processing.core.PGraphics;

import frames.primitives.Vector;
import frames.processing.Scene;
import frames.processing.Shape;

public class Axis extends Shape {
    Scene _scene;

    Vector _i;
    Vector _j;

    String _bubbleText;
    int _bubbleSize;
    int _bubbleTextColor;

    int _AxisColor;
    float _axisRadius;

    public Axis(Scene scene, Vector i, Vector j, String bubbleText) {
        this(scene, i, j, bubbleText, 30, scene.pApplet().color(0), scene.pApplet().color(239, 127, 26),
                0.2f);
    }
    /*
    No se puede hacer axis con linea, precisión no funciona
     */
    Axis(Scene scene, Vector i, Vector j, String bubbleText, int bubbleSize, int bubbleTextColor, int axisColor,
         float axisRadius) {
        super(scene);

        setScene(scene);

        setI(i);
        setJ(j);

        setBubbleText(bubbleText);
        setBubbleSize(bubbleSize);
        setBubbleTextColor(bubbleTextColor);

        setAxisColor(axisColor);
        setAxisRadius(axisRadius);

        setPosition(i());
        setRotation(new Quaternion(new Vector(1, 0, 0), ij()));
        setHighlighting(Highlighting.NONE);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    Vector i() {
        return _i;
    }

    void setI(Vector i) {
        _i = i;
    }

    Vector j() {
        return _j;
    }

    void setJ(Vector j) {
        _j = j;
    }

    String bubbleText() {
        return _bubbleText;
    }

    void setBubbleText(String bubbleText) {
        _bubbleText = bubbleText;
    }

    int bubbleSize() {
        return _bubbleSize;
    }

    void setBubbleSize(int bubbleSize) {
        _bubbleSize = bubbleSize;
    }

    int bubbleTextColor() {
        return _bubbleTextColor;
    }

    void setBubbleTextColor(int bubbleTextColor) {
        _bubbleTextColor = bubbleTextColor;
    }

    int axisColor() {
        return _AxisColor;
    }

    void setAxisColor(int AxisColor) {
        _AxisColor = AxisColor;
    }

    float axisRadius() {
        return _axisRadius;
    }

    void setAxisRadius(float axisRadius) {
        _axisRadius = axisRadius;
    }

    public PApplet pApplet() {
        return scene().pApplet();
    }

    public Vector ij() {
        return Vector.subtract(j(), i());
    }

//        float nivelZ() {
//            return position().z();
//        }
//
//        public void setNivelZ(float nivelZ) {
//            setI(new Vector(i().x(), i().y(), nivelZ));
//            setJ(new Vector(j().x(), j().y(), nivelZ));
//            setPosition(new Vector(position().x(), position().y(), nivelZ));
//        }

    @Override
    public void setGraphics(PGraphics pGraphics) {
        /*
        Una posible solucion al picking es setear con sphere y cone el backBuffer y setear con ellipse y line
        el frontBuffer, pero no pude hacerlo.

        Pintar en pantalla no permite hacer el picking
         */
        Vector ij = Vector.subtract(j(), i());

        Vector iScreen = scene().screenLocation(i());
        Vector jScreen = scene().screenLocation(j());
        Vector parallelDirection = Vector.subtract(jScreen, iScreen);
        parallelDirection.normalize();
        Vector center = Vector.add(iScreen,
                Vector.multiply(parallelDirection, -bubbleSize() / 2));

        pGraphics.pushStyle();
        pGraphics.noStroke();
        if (!scene().isTrackedFrame(this)) {
            pGraphics.fill(axisColor());
        } else {
            pGraphics.fill(255 - pApplet().red(axisColor()), 255 - pApplet().green(axisColor()),
                    255 - pApplet().blue(axisColor()));
        }
        pGraphics.pushMatrix();
        pGraphics.rotateY(pApplet().radians(90));
        scene().drawCylinder(pGraphics, 0.1f, ij.magnitude());
        /*
        No he podido implementar la linea. Problemas con Precision
        pGraphics.beginShape(pApplet().LINES);
        pGraphics.vertex(0, 0, 0);
        pGraphics.vertex(ij.magnitude(), 0, 0);
        pGraphics.endShape();
         */
        pGraphics.popMatrix();
        pGraphics.popStyle();

        scene().beginScreenDrawing();
        pGraphics.pushStyle();
        pGraphics.noStroke();
        pGraphics.strokeWeight(10 * axisRadius());  // falta implementar
        pGraphics.fill(pApplet().red(axisColor()), pApplet().green(axisColor()), pApplet().blue(axisColor()), 63);

        float px, py;
        int detail = 20;
        float degrees = 360 / detail;

        pGraphics.beginShape(PApplet.TRIANGLE_FAN);
        pGraphics.vertex(center.x(), center.y());  // no sirve picking in frontbuffer
        for (float i = detail; i > -1; i--) {
            px = center.x() + (float) Math.cos(PApplet.radians(i * degrees)) * bubbleSize() / 2;
            py = center.y() + (float) Math.sin(PApplet.radians(i * degrees)) * bubbleSize() / 2;
            pGraphics.vertex(px, py, 0);
        }
        pGraphics.stroke(axisColor());
        pGraphics.ellipse(center.x(), center.y(), bubbleSize(), bubbleSize());  // para stroke alrededor circulo
        pGraphics.endShape();
        pGraphics.endShape();
        pGraphics.popStyle();

        pGraphics.pushStyle();
        pGraphics.textAlign(PApplet.CENTER, PApplet.CENTER);
        pGraphics.textSize(0.5f * bubbleSize());  // problemas
        pGraphics.fill(bubbleTextColor());
        pGraphics.beginShape();
        pGraphics.text(bubbleText(), center.x(), center.y());  // problemas
        pGraphics.endShape();
        pGraphics.popStyle();
        scene().endScreenDrawing();
    }
}
