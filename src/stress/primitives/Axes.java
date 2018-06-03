package stress.primitives;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;

import frames.primitives.Vector;
import frames.processing.Scene;

import stress.primitives.Axe;


public class Axes {
    private PApplet _pApplet;
    private PGraphics _frontBuffer;
    private Scene _scene;

    private ArrayList<Axe> _axes;
    private ArrayList<Point> _points;

//    int _actualIndexNivelZ;
//    ArrayList<Float> _nivelesZ;

    public Axes(Scene scene) {
        setScene(scene);

        setAxes(new ArrayList());
        setPoints(new ArrayList());

//        _actualIndexNivelZ = 0;

//        _nivelesZ = new ArrayList();
//        _nivelesZ.add(0f);
    }

    public Scene scene() {
        return _scene;
    }

    public void setScene(Scene scene) {
        _scene = scene;
    }

    public ArrayList<Axe> axes() {
        return _axes;
    }

    public void setAxes(ArrayList<Axe> axes) {
        _axes = axes;
    }

    public ArrayList<Point> points() {
        return _points;
    }

    public void setPoints(ArrayList<Point> points) {
        _points = points;
    }

//    public int actualIndexNivelZ() {
//        return _actualIndexNivelZ;
//    }
//
//    public void setActualIndexNivelZ(int index) {
//        if ((0 <= index) && (index < nivelesZ().size())) {
//            _actualIndexNivelZ = index;
//            for (Punto punto : puntos()) {
//                punto.setNivelZ(nivelesZ().get(actualIndexNivelZ()));
//            }
//            for (Eje eje : ejes()) {
//                eje.setNivelZ(nivelesZ().get(actualIndexNivelZ()));
//            }
//        }
//    }
//
//    public ArrayList<Float> nivelesZ() {
//        return _nivelesZ;
//    }
//
//    public void addNivelZ(float nivelZ) {
//        nivelesZ().add(nivelZ);
//    }

    public void addAxe(Vector i, Vector j, String bubbleTexto) {
        addAxe(new Axe(scene(), i, j, bubbleTexto));
    }

    public void addAxe(Axe axe) {
        _axes.add(axe);
        addAxes();
        moveCenterScene();
        // scene().setAnchor(new Vector(25, 25, 0));
    }

    public void addAxes() {
        int axeSize = axes().size();

        if (ejesSize > 1) {
            Eje lastEje = ejes().get(ejesSize - 1);
            Vector intersection;

            for (int i = 0; i < ejesSize - 1; i++) {
                intersection = intersectionBetweenEjes(ejes.ejes().get(i), lastEje);
                if (intersection != null) {
                    _puntos.add(new Punto(scene(), intersection));
                }
            }
        }
    }

//    void moveCenterScene() {
//        Vector center = new Vector();
//
//        for (int i = 0; i < puntos().size(); i++) {
//            center.add(puntos().get(i).position());
//        }
//        center.divide(puntos().size());
//
//        scene().setCenter(center);
//    }
//
//    /**
//     * Add a point
//     */
//    void addPunto(Vector i) {
//        puntos().add(new Punto(scene(), i));
//    }
//
//    /**
//     * Get vector intersection between two ejes
//     */
//    Vector intersectionBetweenEjes(Eje eje1, Eje eje2) {
//        Vector intersection;
//
//        float dx1 = eje1.j().x() - eje1.i().x();
//        float dx2 = eje2.j().x() - eje2.i().x();
//
//        float dy1 = eje1.j().y() - eje1.i().y();
//        float dy2 = eje2.j().y() - eje2.i().y();
//
//        float dx12 = eje2.i().x() - eje1.i().x();
//        float dy12 = eje2.i().y() - eje1.i().y();
//
//        float alpha = (dx2 * dy1 - dx1 * dy2);
//
//        if (1e-5 < abs(alpha)) {
//            alpha = 1 / alpha;
//
//            float t1 = alpha * (dx2 * dy12 - dy2 * dx12);
//            float t2 = alpha * (dx1 * dy12 - dy1 * dx12);
//
//            if ((0 <= t1) && (t1 <= 1) && (0 <= t2) && (t2 <= 1)) {
//                Vector i = new Vector(eje1.i().x(), eje1.i().y(), eje1.i().z());
//                Vector j = new Vector(eje1.j().x(), eje1.j().y(), eje1.j().z());
//                i.multiply(1 - t1);
//                j.multiply(t1);
//                intersection = Vector.add(i, j);
//            } else {
//                intersection = null;  // the lines don't cross between them
//            }
//        } else {
//            intersection = null;  // the line are parallels
//        }
//
//        return intersection;
//    }
//}


}
