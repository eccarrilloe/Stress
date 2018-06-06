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
    private Boolean showDrawLevel;

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
        this.showDrawLevel = false;

        this.textCoordinates = "";
        this.textLevel = "";
        this.textTrackedObject = "";

        // section
        section = parent.createShape();
        section.beginShape();
        section.fill(parent.color(0, 0, 255, 63));
        section.stroke(parent.color(0, 0, 255));
        section.vertex( 0.125f,  0.225f);
        section.vertex(-0.125f,  0.225f);
        section.vertex(-0.125f, -0.225f);
        section.vertex( 0.125f, -0.225f);
        section.endShape(parent.CLOSE);

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

        if (showDrawLevel) {
            drawLevel();
        }
    }

    public void runCommand() {
        String[] params = this.commandManager.command.split(" ");
        String command = params[0].toUpperCase();
        String i = "";
        String j = "";
        String[] iCoords;
        String[] jCoords;

        switch (command) {
            case "CREATE":
                switch (params[1].toUpperCase()){
                    case "NODE":
                        i = params[2];

                        iCoords = i.substring(1, i.length() - 1).split(",");
                        Vector iVect = new Vector(Integer.valueOf(iCoords[0]), Integer.valueOf(iCoords[1]), Integer.valueOf(iCoords[2]));

                        System.out.println(i);
                        System.out.println(iVect);

                        this.addNode(iVect);
                        break;
                    case "PORTICO":
                        i = params[2];
                        j = params[3];

                        iCoords = i.substring(1, i.length() - 1).split(",");
                        this.i = new Vector(Integer.valueOf(iCoords[0]), Integer.valueOf(iCoords[1]), Integer.valueOf(iCoords[2]));

                        jCoords = j.substring(1, j.length() - 1).split(",");
                        this.j = new Vector(Integer.valueOf(jCoords[0]), Integer.valueOf(jCoords[1]), Integer.valueOf(jCoords[2]));

                        this.addPortico();
                        break;
                }
                break;
            default:
                System.out.println("ERROR: Command not Found");
                break;
        }
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

    public void drawLevels() {
        for (int i = 0; i < axes.levels().size(); i++) {
            parent.pushStyle();

            if (i == axes.actualIndexLevel()) {
                parent.stroke(144, 238, 144);
                parent.fill(144, 238, 144, 15);
            } else {
                parent.stroke(31, 117, 254);
                parent.fill(31, 117, 254, 15);
            }

            parent.pushMatrix();
            parent.translate(0, 0, axes.levels().get(i));
            parent.ellipse(scene.center().x(), scene.center().y(), 2 * scene.radius(), 2 * scene.radius());

            parent.popMatrix();
            parent.popStyle();
        }
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

    public void drawLevel() {
        String level;

        scene.beginScreenDrawing();
        parent.pushStyle();

        int offsetX = (int) (parent.textWidth(textCoordinates) + 10);
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

        int offsetX = (int) (parent.textWidth(textLevel) + parent.textWidth(textCoordinates) + 10);
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

    public void addNode(Vector vect) {
        System.out.println(vect);
        nodes.add(vect);
    }

    public void addPortico() {
        porticos.add(section, i, j);
    }

    public void drawCommandLine() {
        scene.beginScreenDrawing();
        parent.pushStyle();
        parent.fill(32, 32, 32, 200);
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
                        textCoordinates = "";
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
                    case 'q':
                        showDrawLevels = !showDrawLevels;
                        break;
                    case 'z':
                        showDrawLevel = !showDrawLevel;
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
        } else if (mouseEvent.getAction() == MouseEvent.CLICK && mouseEvent.getButton() == parent.LEFT && mouseEvent.getCount() == 1) {
            if (showAddNode) {
                this.addNode();
                this.showAddNode = false;
            } else if (showAddPortico) {
                if (i == null) {
                    i = mouseCoordinates;
                    return;
                } else {
                    j = mouseCoordinates;
                }

                addPortico();

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
