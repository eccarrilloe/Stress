package stress.primitives;

import frames.primitives.Quaternion;
import processing.core.PApplet;
import processing.core.PGraphics;

import frames.core.Frame;
import frames.primitives.Vector;
import frames.processing.Scene;

public class Axis extends Frame {
    private Scene _scene;

    private Vector _i;
    private Vector _j;

    private String _bubbleText;
    private int _bubbleSize;
    private int _bubbleTextColor;

    private int _AxisColor;
    private float _axisStroke;

    public Axis(Scene scene, Vector i, Vector j, String bubbleText) {
        this(scene, i, j, bubbleText, 30, scene.pApplet().color(0), scene.pApplet().color(239, 127, 26),
                2.5f);
    }
    /*
    No se puede hacer axis con linea, precisión no funciona
     */
    public Axis(Scene scene, Vector i, Vector j, String bubbleText, int bubbleSize, int bubbleTextColor, int axisColor,
         float axisStroke) {
        super(scene);

        setScene(scene);

        setI(i);
        setJ(j);

        setBubbleText(bubbleText);
        setBubbleSize(bubbleSize);
        setBubbleTextColor(bubbleTextColor);

        setAxisColor(axisColor);
        setAxisStroke(axisStroke);

        setPosition(i());
        setRotation(new Quaternion(new Vector(1, 0, 0), ij()));
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
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

    public String bubbleText() {
        return _bubbleText;
    }

    public void setBubbleText(String bubbleText) {
        _bubbleText = bubbleText;
    }

    public int bubbleSize() {
        return _bubbleSize;
    }

    public void setBubbleSize(int bubbleSize) {
        _bubbleSize = bubbleSize;
    }

    public int bubbleTextColor() {
        return _bubbleTextColor;
    }

    public void setBubbleTextColor(int bubbleTextColor) {
        _bubbleTextColor = bubbleTextColor;
    }

    public int axisColor() {
        return _AxisColor;
    }

    public void setAxisColor(int AxisColor) {
        _AxisColor = AxisColor;
    }

    public float axisStroke() {
        return _axisStroke;
    }

    public void setAxisStroke(float axisStroke) {
        _axisStroke = axisStroke;
    }

    public PApplet pApplet() {
        return scene().pApplet();
    }

    public PGraphics frontBuffer() {
        return scene().frontBuffer();
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
    public void visit() {
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
        Vector center = Vector.add(iScreen, Vector.multiply(parallelDirection, -bubbleSize() / 2));

        int axisColorTracked = pApplet().color(255 - pApplet().red(axisColor()), 255 - pApplet().green(axisColor()),
                255 - pApplet().blue(axisColor()));
        // line
        frontBuffer().pushStyle();
        if (!scene().isTrackedFrame(this)) {
            frontBuffer().stroke(axisColor());
        } else {
            frontBuffer().stroke(axisColorTracked);
        }
        frontBuffer().strokeWeight(axisStroke());
        frontBuffer().beginShape(pApplet().LINES);
        frontBuffer().vertex(0, 0, 0);
        frontBuffer().vertex(ij.magnitude(), 0, 0);
        frontBuffer().endShape();
        frontBuffer().popStyle();

        // circle,circumference & text
        scene().beginScreenDrawing();
        // circle
        frontBuffer().pushStyle();
        if (!scene().isTrackedFrame(this)) {
            frontBuffer().fill(pApplet().red(axisColor()), pApplet().green(axisColor()), pApplet().blue(axisColor()),
                    63);
        } else {
            frontBuffer().fill(pApplet().red(axisColorTracked), pApplet().green(axisColorTracked),
                    pApplet().blue(axisColorTracked), 63);
        }
        frontBuffer().noStroke();
        float px, py;
        int detail = 20;
        float degrees = 360 / detail;
        frontBuffer().beginShape(PApplet.TRIANGLE_FAN);
        frontBuffer().vertex(center.x(), center.y());  // no sirve picking in frontbuffer
        for (float i = detail; i > -1; i--) {
            px = center.x() + (float) Math.cos(PApplet.radians(i * degrees)) * bubbleSize() / 2;
            py = center.y() + (float) Math.sin(PApplet.radians(i * degrees)) * bubbleSize() / 2;
            frontBuffer().vertex(px, py, 0);
        }
        frontBuffer().endShape();
        frontBuffer().popStyle();

        // circumference
        frontBuffer().pushStyle();
        if (!scene().isTrackedFrame(this)) {
            frontBuffer().stroke(axisColor());
        } else {
            frontBuffer().stroke(axisColorTracked);
        }
        frontBuffer().strokeWeight(axisStroke());
        frontBuffer().noFill();
        frontBuffer().ellipse(center.x(), center.y(), bubbleSize(), bubbleSize());  // para stroke alrededor circulo
        frontBuffer().popStyle();

        // text
        frontBuffer().pushStyle();
        frontBuffer().textAlign(PApplet.CENTER, PApplet.CENTER);
        frontBuffer().textSize(0.5f * bubbleSize());  // problemas
        frontBuffer().fill(bubbleTextColor());
        frontBuffer().text(bubbleText(), center.x(), center.y());  // problemas
        frontBuffer().popStyle();

        scene().endScreenDrawing();
    }
}

//package stress.primitives;
//
//        import frames.primitives.Quaternion;
//        import processing.core.PApplet;
//        import processing.core.PGraphics;
//
//        import frames.primitives.Vector;
//        import frames.processing.Scene;
//        import frames.processing.Shape;
//
//public class Axis extends Shape {
//    Scene _scene;
//
//    Vector _i;
//    Vector _j;
//
//    String _bubbleText;
//    int _bubbleSize;
//    int _bubbleTextColor;
//
//    int _AxisColor;
//    float _axisRadius;
//
//    public Axis(Scene scene, Vector i, Vector j, String bubbleText) {
//        this(scene, i, j, bubbleText, 30, scene.pApplet().color(0), scene.pApplet().color(239, 127, 26),
//                0.1f);
//    }
//    /*
//    No se puede hacer axis con linea, precisión no funciona
//     */
//    Axis(Scene scene, Vector i, Vector j, String bubbleText, int bubbleSize, int bubbleTextColor, int axisColor,
//         float axisRadius) {
//        super(scene);
//
//        setScene(scene);
//
//        setI(i);
//        setJ(j);
//
//        setBubbleText(bubbleText);
//        setBubbleSize(bubbleSize);
//        setBubbleTextColor(bubbleTextColor);
//
//        setAxisColor(axisColor);
//        setAxisRadius(axisRadius);
//
//        setPosition(i());
//        setRotation(new Quaternion(new Vector(1, 0, 0), ij()));
//        setHighlighting(Highlighting.NONE);
//    }
//
//    public Scene scene() {
//        return _scene;
//    }
//
//    public void setScene(Scene scene) {
//        _scene = scene;
//    }
//
//    Vector i() {
//        return _i;
//    }
//
//    void setI(Vector i) {
//        _i = i;
//    }
//
//    Vector j() {
//        return _j;
//    }
//
//    void setJ(Vector j) {
//        _j = j;
//    }
//
//    String bubbleText() {
//        return _bubbleText;
//    }
//
//    void setBubbleText(String bubbleText) {
//        _bubbleText = bubbleText;
//    }
//
//    int bubbleSize() {
//        return _bubbleSize;
//    }
//
//    void setBubbleSize(int bubbleSize) {
//        _bubbleSize = bubbleSize;
//    }
//
//    int bubbleTextColor() {
//        return _bubbleTextColor;
//    }
//
//    void setBubbleTextColor(int bubbleTextColor) {
//        _bubbleTextColor = bubbleTextColor;
//    }
//
//    int axisColor() {
//        return _AxisColor;
//    }
//
//    void setAxisColor(int AxisColor) {
//        _AxisColor = AxisColor;
//    }
//
//    float axisRadius() {
//        return _axisRadius;
//    }
//
//    void setAxisRadius(float axisRadius) {
//        _axisRadius = axisRadius;
//    }
//
//    public PApplet pApplet() {
//        return scene().pApplet();
//    }
//
//    public Vector ij() {
//        return Vector.subtract(j(), i());
//    }
//
////        float nivelZ() {
////            return position().z();
////        }
////
////        public void setNivelZ(float nivelZ) {
////            setI(new Vector(i().x(), i().y(), nivelZ));
////            setJ(new Vector(j().x(), j().y(), nivelZ));
////            setPosition(new Vector(position().x(), position().y(), nivelZ));
////        }
//
//    @Override
//    public void setGraphics(PGraphics pGraphics) {
//        /*
//        Una posible solucion al picking es setear con sphere y cone el backBuffer y setear con ellipse y line
//        el frontBuffer, pero no pude hacerlo.
//
//        Pintar en pantalla no permite hacer el picking
//         */
//        Vector ij = Vector.subtract(j(), i());
//
//        Vector iScreen = scene().screenLocation(i());
//        Vector jScreen = scene().screenLocation(j());
//        Vector parallelDirection = Vector.subtract(jScreen, iScreen);
//        parallelDirection.normalize();
//        Vector center = Vector.add(iScreen,
//                Vector.multiply(parallelDirection, -bubbleSize() / 2));
//
//        int axisColorTracked = pApplet().color(255 - pApplet().red(axisColor()), 255 - pApplet().green(axisColor()),
//                255 - pApplet().blue(axisColor()));
//
//        pGraphics.pushStyle();
//        pGraphics.noStroke();
//        if (!scene().isTrackedFrame(this)) {
//            pGraphics.fill(axisColor());
//        } else {
//            pGraphics.fill(axisColorTracked);
//        }
//        pGraphics.pushMatrix();
//        pGraphics.rotateY(PApplet.radians(90));
//        Scene.drawCylinder(pGraphics, 0.1f, ij.magnitude());
//        /*
//        No he podido implementar la linea. Problemas con Precision
//        pGraphics.beginShape(pApplet().LINES);
//        pGraphics.vertex(0, 0, 0);
//        pGraphics.vertex(ij.magnitude(), 0, 0);
//        pGraphics.endShape();
//         */
//        pGraphics.popMatrix();
//        pGraphics.popStyle();
//
//        scene().beginScreenDrawing();
//        pGraphics.pushStyle();
//        pGraphics.noStroke();
//        pGraphics.strokeWeight(20 * axisRadius());  // falta implementar
//
//        if (!scene().isTrackedFrame(this)) {
//            pGraphics.fill(pApplet().red(axisColor()), pApplet().green(axisColor()), pApplet().blue(axisColor()),
//                    63);
//        } else {
//            pGraphics.fill(pApplet().red(axisColorTracked), pApplet().green(axisColorTracked),
//                    pApplet().blue(axisColorTracked), 63);
//        }
//
//        float px, py;
//        int detail = 20;
//        float degrees = 360 / detail;
//
//        pGraphics.beginShape(PApplet.TRIANGLE_FAN);
//        pGraphics.vertex(center.x(), center.y());  // no sirve picking in frontbuffer
//        for (float i = detail; i > -1; i--) {
//            px = center.x() + (float) Math.cos(PApplet.radians(i * degrees)) * bubbleSize() / 2;
//            py = center.y() + (float) Math.sin(PApplet.radians(i * degrees)) * bubbleSize() / 2;
//            pGraphics.vertex(px, py, 0);
//        }
//        if (!scene().isTrackedFrame(this)) {
//            pGraphics.stroke(axisColor());
//        } else {
//            pGraphics.stroke(axisColorTracked);
//        }
//
//        pGraphics.ellipse(center.x(), center.y(), bubbleSize(), bubbleSize());  // para stroke alrededor circulo
//        pGraphics.endShape();
//        pGraphics.endShape();
//        pGraphics.popStyle();
//
//        pGraphics.pushStyle();
//        pGraphics.textAlign(PApplet.CENTER, PApplet.CENTER);
//        pGraphics.textSize(0.5f * bubbleSize());  // problemas
//        pGraphics.fill(bubbleTextColor());
//        pGraphics.beginShape();
//        pGraphics.text(bubbleText(), center.x(), center.y());  // problemas
//        pGraphics.endShape();
//        pGraphics.popStyle();
//        scene().endScreenDrawing();
//    }
//}

