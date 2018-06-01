package stress.primitives;

import processing.core.PApplet;
import processing.core.PGraphics;

import frames.primitives.Vector;
import frames.processing.Scene;
import frames.processing.Shape;

public class Axe extends Shape {
    Scene _scene;
    PApplet _pApplet;

    Vector _i;
    Vector _j;

    String _bubbleText;
    int _bubbleSize;
    int _bubbleTextColor;

    int _axeColor;
    int _axeStroke;

    public Axe(Scene scene, Vector i, Vector j, String bubbleText) {
        this(scene, i, j, bubbleText, 40, scene.pApplet().color(239, 127, 26), 3, scene.pApplet().color(0));
    }

    Axe(Scene scene, Vector i, Vector j, String bubbleText,
         int bubbleSize, int axeColor, int axeStroke, int bubbleTextColor) {
        super(scene);

        setScene(scene);
        setPApplet(scene().pApplet());

        setI(i);
        setJ(j);

        setBubbleText(bubbleText);
        setBubbleSize(bubbleSize);
        setBubbleTextColor(bubbleTextColor);

        setAxeColor(axeColor);
        setAxeStroke(axeStroke);

        setPrecision(Precision.FIXED);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    public PApplet pApplet() {
        return _pApplet;
    }

    public void setPApplet(PApplet pApplet) {
        _pApplet = pApplet;
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

    int axeColor() {
        return _axeColor;
    }

    void setAxeColor(int axeColor) {
        _axeColor = axeColor;
    }

    int axeStroke() {
        return _axeStroke;
    }

    void setAxeStroke(int axeStroke) {
        _axeStroke = axeStroke;
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
        pGraphics.pushStyle();
        pGraphics.stroke(axeColor());
        pGraphics.strokeWeight(axeStroke());
        pGraphics.line(i().x(), i().y(), j().x(), j().y());
        pGraphics.popStyle();

        Vector iScreen = scene().screenLocation(i());
        Vector jScreen = scene().screenLocation(j());
        Vector parallelDirection = Vector.subtract(jScreen, iScreen);
        parallelDirection.normalize();

        Vector center = Vector.add(iScreen,
                Vector.multiply(parallelDirection, -bubbleSize() / 2));

        scene().beginScreenDrawing();
        pGraphics.pushStyle();
        pGraphics.stroke(axeColor());
        pGraphics.strokeWeight(axeStroke());
        pGraphics.fill(pApplet().red(axeColor()), pApplet().green(axeColor()), pApplet().blue(axeColor()), 63);
        pGraphics.ellipse(center.x(), center.y(), bubbleSize(), bubbleSize());
        pGraphics.popStyle();

        pGraphics.pushStyle();
        pGraphics.textAlign(PApplet.CENTER, PApplet.CENTER);
        pGraphics.textSize(0.5f * bubbleSize());  // problemas
        pGraphics.fill(bubbleTextColor());
        pGraphics.text(bubbleText(), center.x(), center.y());  // problemas
        pGraphics.popStyle();
        scene().endScreenDrawing();
    }
}
