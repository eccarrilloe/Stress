package stress.core;

import frames.processing.Scene;
import processing.core.PApplet;
import processing.event.KeyEvent;
import stress.primitives.Structure;

import java.util.List;

public class StressManager {

    private PApplet parent;
    private Scene scene;

    private FileManager fileManager;
    private CommandManager commandManager;

    private List<Structure> structures;


    public StressManager(PApplet parent, Scene scene) {
        this.parent = parent;
        this.scene = scene;

        this.fileManager = new FileManager(this);
        this.commandManager = new CommandManager();
        this.parent.registerMethod("draw", this);
        this.parent.registerMethod("mouseEvent", this);
    }

    public void draw() {
        scene.beginScreenDrawing();
        parent.pushStyle();
        parent.fill(200);
        parent.noStroke();
        parent.rect(0, parent.height - 20, parent.width, parent.height);
        parent.text("Just A Test", 5, parent.height - 10);
        parent.popStyle();
        scene.endScreenDrawing();
    }

    public void keyEvent(KeyEvent event) {

    }

    public PApplet getParent() {
        return parent;
    }

    public void setParent(PApplet parent) {
        this.parent = parent;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }
}
