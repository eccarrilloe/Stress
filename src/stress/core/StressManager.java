package stress.core;

import frames.primitives.Vector;
import frames.processing.Scene;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import stress.constants.Status;

public class StressManager {

    private PApplet parent;
    private Scene scene;

    private FileManager fileManager;
    private CommandManager commandManager;
    private Status status;
    private Boolean showCoordinates;
    private Vector mouseCoordinates;

    public StressManager(PApplet parent, Scene scene) {
        this.parent = parent;
        this.scene = scene;

        this.fileManager = new FileManager(this);
        this.commandManager = new CommandManager(this);
        this.status = Status.CANVAS;
        this.showCoordinates = true;

        this.parent.registerMethod("draw", this);
        this.parent.registerMethod("keyEvent", this);
        this.parent.registerMethod("mouseEvent", this);
    }

    public void draw() {
        if (status == Status.COMMAND) {
            drawCommandLine();
        }
        if (showCoordinates) {
            drawCoordinates();
        }
    }

    public void runCommand() {

    }

    public void drawCoordinates() {
        if (mouseCoordinates != null) {
            String coordinates;
            Integer offsetY = 10;
            Integer offsetX = 10;

            if (status == Status.COMMAND) {
                offsetY = 40;
            }

            scene.beginScreenDrawing();
            parent.pushStyle();

            parent.textAlign(parent.RIGHT, parent.BOTTOM);
            parent.textSize(14);

            parent.fill(0);
            parent.text(")", parent.width - offsetX, parent.height - offsetY);
            coordinates = ")";

            parent.fill(0, 0, 255);
            parent.text(parent.nf(mouseCoordinates.z(), 0, 3), parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
            coordinates = parent.nf(mouseCoordinates.z(), 0, 3) + coordinates;

            parent.fill(0);
            parent.text(" ,", parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
            coordinates = " ," + coordinates;

            parent.fill(0, 255, 0);
            parent.text(parent.nf(mouseCoordinates.y(), 0, 3), parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
            coordinates = parent.nf(mouseCoordinates.y(), 0, 3) + coordinates;

            parent.fill(0);
            parent.text(" ,", parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
            coordinates = " ," + coordinates;

            parent.fill(255, 0, 0);
            parent.text(parent.nf(mouseCoordinates.x(), 0, 3), parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
            coordinates = parent.nf(mouseCoordinates.x(), 0, 3) + coordinates;

            parent.fill(0);
            parent.text("(", parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
            coordinates = "(" + coordinates;

            parent.popStyle();
            scene.endScreenDrawing();
        }
    }

    public void drawCommandLine() {
        scene.beginScreenDrawing();
        parent.pushStyle();
        parent.fill(32);
        parent.noStroke();
        parent.rect(0, scene.height() - 30, scene.width(), scene.height());
        parent.fill(255);
        parent.text(String.format(">> %s", commandManager.command), 5, scene.height() - 10);
        parent.popStyle();
        scene.endScreenDrawing();
    }

    public void keyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.PRESS) {
            System.out.println(event.getKeyCode());
            if (event.getKey() == parent.ENTER) {
                if (status == Status.CANVAS) {
                    status = Status.COMMAND;
                } else if (status == Status.COMMAND) {
                    status = Status.CANVAS;
                    runCommand();
                    this.commandManager.command = "";
                }
            } else if (status == Status.COMMAND) {
                if (event.getKeyCode() >= 32 && event.getKeyCode() < 126){
                    this.commandManager.command += event.getKey();
                } else if (event.getKeyCode() == 8 && this.commandManager.command.length() > 0) {
                    this.commandManager.command = this.commandManager.command.substring(0, this.commandManager.command.length() -1);
                }
            } else if (event.getKey() == 'C') {
                showCoordinates = !showCoordinates;
            }
        }
    }

    public void mouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getAction() == MouseEvent.MOVE) {
            if (scene.trackedFrame() == null) {
                mouseCoordinates = scene.location(new Vector(parent.mouseX, parent.mouseY));
            } else if (!scene.trackedFrame().getClass().getName().equals("stress.primitives.Axis")) {
                mouseCoordinates = scene.trackedFrame().position();
            }
        }
    }

}
