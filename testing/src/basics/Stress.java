package basics;

import processing.core.PApplet;

import frames.core.Graph;
import frames.processing.Scene;

import stress.core.StressManager;

import java.awt.*;


public class Stress extends PApplet {
    public Scene scene;

    public StressManager stressManager;

    public void settings() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width - 400;
        this.height = screenSize.height - 200;

        size(width, height, P3D);
    }

    public void setup() {
        scene = new Scene(this);
        scene.setType(Graph.Type.ORTHOGRAPHIC);
        scene.setFieldOfView(PI/3);
        scene.setRightHanded();
        scene.setRadius(25);
        scene.fitBall();

        stressManager = new StressManager(this, scene);
        stressManager.initManager();
    }

    public void draw() {
        background(127);
        scene.drawAxes(scene.radius() / 3);
        scene.cast();
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"basics.Stress"});
    }
}
