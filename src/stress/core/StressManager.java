package stress.core;

import frames.primitives.Vector;
import frames.processing.Scene;
import processing.core.PApplet;
import processing.core.PShape;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import stress.constants.Status;

public class StressManager {

    private PApplet parent;
    private Scene scene;
    private PShape section;

    private FileManager fileManager;
    private CommandManager commandManager;
    private Status status;

    private Boolean showCoordinates;
    private Boolean showLevel;
    private Boolean showTrackedObject;
    private Boolean showAddNode;
    private Boolean showAddPortico;
    private Boolean showDrawLevels;

    private String textCoordinates;
    private String textLevel;
    private String textTrackedObject;

    private Vector mouseCoordinates;
    private Vector coordinates;
    private Vector i;
    private Vector j;

    private Axes axes;
    private Nodes nodes;
    private Porticos porticos;

    public StressManager(PApplet parent, Scene scene) {
        this.parent = parent;
        this.scene = scene;

        this.fileManager = new FileManager(this);
        this.commandManager = new CommandManager(this);
        this.status = Status.CANVAS;

        this.showCoordinates = true;
        this.showLevel = true;
        this.showTrackedObject = false;
        this.showAddNode = false;
        this.showAddPortico = false;
        this.showDrawLevels = false;

        this.textCoordinates = "";
        this.textLevel = "";
        this.textTrackedObject = "";

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
        if (showDrawLevels) {
            drawLevels();
        }
        if (showTrackedObject) {
            drawTrackedObject();
        }
    }

    public void runCommand() {

    }

    public void initManager() {
        initializeAxes();

        nodes = new Nodes(scene);

        porticos = new Porticos(scene);
    }

    public void initializeAxes() {
        axes = new Axes(scene);

        int dx = 5;
        int dy = 5;
        int length = dx * dy;

        String[] xBubbleText = {"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            axes.addAxis(new Vector(-dx, dy * i), new Vector(length + dy, dy * i), xBubbleText[i]);
        }

        String[] yBubbleText = {"1", "2", "3", "4", "5"};
        for (int i = 0; i < 5; i++) {
            axes.addAxis(new Vector(dx * i, length), new Vector(dx * i, -dy), yBubbleText[i]);
        }

        axes.addLevel( 0f);
        axes.addLevel( 5f);
        axes.addLevel(10f);
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

            this.textCoordinates = coordinates;
        }
    }

    public void drawLevels() {
        String level;

        scene.beginScreenDrawing();
        parent.pushStyle();

        int offsetX = (int) parent.textWidth(textCoordinates);
        int offsetY = 10;

        if (status == Status.COMMAND) {
            offsetY = 40;
        }

        parent.textAlign(parent.RIGHT, parent.BOTTOM);
        parent.textSize(14);

        parent.fill(0);

        level = "Nivel Z: " + parent.nf(axes.levels().get(axes.actualIndexLevel()), 0, 3);

        parent.text(level, parent.width - offsetX, parent.height - offsetY);

        scene.endScreenDrawing();
        parent.popStyle();

        textLevel = level;
    }

    public void drawTrackedObject() {
        String trackedObject = "";

        scene.beginScreenDrawing();
        parent.pushStyle();

        int offsetX = parent.max((int) parent.textWidth(textLevel), (int) parent.textWidth(textCoordinates));
        int offsetY = 10;

        if (status == Status.COMMAND) {
            offsetY = 40;
        }

        parent.textAlign(parent.RIGHT, parent.BOTTOM);
        parent.textSize(14);

        parent.fill(0);

        if (!(scene.trackedFrame() == null)) {
            trackedObject = scene.trackedFrame().getClass().getSimpleName();
        }

        parent.text(trackedObject, parent.width - offsetX, parent.height - offsetY);

        scene.endScreenDrawing();
        parent.popStyle();

        textTrackedObject = trackedObject + textLevel;
    }

    public void addNode() {
        nodes.add(mouseCoordinates);
    }

    public void addPortico() {
        porticos.add(section, i, j);
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
            } else {
                switch(event.getKey()) {
                    case 'c':
                        showCoordinates = !showCoordinates;
                        break;
                    case 'a':
                        porticos.setDrawLocalAxes(!porticos.drawLocalAxes());
                        break;
                    case 'e':
                        showLevel = !showLevel;
                        break;
                    case 'f':
                        showTrackedObject = !showTrackedObject;
                        break;
                    case 'l':
                        nodes.setDrawLabels(!nodes.drawLabels());
                        porticos.setDrawLabels(!porticos.drawLabels());
                        break;
                    case 'n':
                        showAddNode = !showAddNode;
                        break;
                    case 'p':
                        showAddPortico = !showAddPortico;
                        break;
                    case 'x':
                        porticos.setShowExtrude(!porticos.showExtrude());
                        break;
                    case '+':
                        axes.setActualIndexLevel(axes.actualIndexLevel() < axes.levels().size() - 1 ?
                                axes.actualIndexLevel() + 1 : 0);
                        break;
                    case '-':
                        axes.setActualIndexLevel(0 < axes.actualIndexLevel() ?
                                axes.actualIndexLevel() - 1 : axes.levels().size() - 1);
                        break;
                    case 'z':
                        showDrawLevels = !showDrawLevels;
                        break;
                }
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
        } else if (mouseEvent.getAction() == MouseEvent.CLICK) {
            if (showAddNode && mouseEvent.getButton() == parent.LEFT && mouseEvent.getCount() == 1) {
                this.addNode();
                this.showAddNode = false;
            }

            if (showAddPortico && mouseEvent.getAction() == parent.LEFT && mouseEvent.getCount() == 1) {
                if (i == null) {
                    i = mouseCoordinates;
                    return;
                } else {
                    j = mouseCoordinates;
                }

                porticos.add(section, i, j);
                i = null;
                j = null;
                showAddPortico = false;
            }
        } else if (mouseEvent.getAction() == MouseEvent.WHEEL) {
            scene.setTrackedFrame(scene.eye());
            scene.scale(-20 * mouseEvent.getCount());
        } else if (mouseEvent.getAction() == MouseEvent.DRAG) {
            if (mouseEvent.getButton() == parent.LEFT) {

            } else if (mouseEvent.getButton() == parent.CENTER) {
                scene.mousePan();
            } else if (mouseEvent.getButton() == parent.RIGHT) {
                scene.mouseCAD(new Vector(0, 0, 1));
            }
        }
    }

}
