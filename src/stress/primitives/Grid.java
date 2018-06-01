package stress.primitives;

import processing.core.PApplet;
import processing.core.PGraphics;

import frames.primitives.Vector;
import frames.processing.Scene;
import frames.processing.Shape;

public class Grid extends Shape {
    Scene _scene;
    PApplet _pApplet;

    Vector _i;
    Vector _j;

    String _bubbleText;
    int _bubbleSize;
    int _bubbleTextColor;

    int _gridColor;
    int _gridStroke;

    public Grid(Scene scene, Vector i, Vector j, String bubbleText) {
        this(scene, i, j, bubbleText, 40, scene.pApplet().color(239, 127, 26), 3, scene.pApplet().color(0));
    }

    Grid(Scene scene, Vector i, Vector j, String bubbleText,
         int bubbleSize, int ejeColor, int ejeStroke, int bubbleTextColor) {
        super(scene);

        setScene(scene);

        setI(i);
        setJ(j);

        setBubbleText(bubbleText);
        setBubbleSize(bubbleSize);
        setBubbleTextColor(bubbleTextColor);

        setGridColor(ejeColor);
        setGridStroke(ejeStroke);

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

    int gridColor() {
        return _gridColor;
    }

    void setGridColor(int gridColor) {
        _gridColor = gridColor;
    }

    int gridStroke() {
        return _gridStroke;
    }

    void setGridStroke(int gridStroke) {
        _gridStroke = gridStroke;
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
        pGraphics.stroke(gridColor());
        pGraphics.strokeWeight(gridStroke());
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
        pGraphics.stroke(gridColor());
        pGraphics.strokeWeight(gridStroke());
//            pGraphics.fill(pApplet().red(gridColor()), pApplet().green(gridColor()), pApplet().blue(gridColor()),
//                    63);
//            pGraphics.ellipse(center.x(), center.y(), bubbleSize(), bubbleSize());
//            pGraphics.popStyle();
//
//            pGraphics.pushStyle();
//            pGraphics.textAlign(PApplet.CENTER, PApplet.CENTER);
//            pGraphics.textSize(0.5f * bubbleSize());  // problemas
//            pGraphics.fill(bubbleTextColor());
//            pGraphics.text(bubbleText(), center.x(), center.y());  // problemas
//            pGraphics.popStyle();
        scene().endScreenDrawing();
    }
}
