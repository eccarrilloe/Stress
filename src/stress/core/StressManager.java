package stress.core;

import processing.core.PApplet;/*
import processing.core.PShape;
import processing.event.KeyEvent;*/
import processing.event.MouseEvent;

import frames.processing.Scene;
import frames.primitives.Vector;

// import stress.constants.Status;

public class StressManager {
    private PApplet pApplet;
    private Scene scene;
    // private PShape section;

    // private FileManager fileManager;
    // private CommandManager commandManager;

    // private Status status;

    // private Boolean toggleShowCoordinates;
    // private Boolean toggleShowLevel;
    // private Boolean toggleShowTrackedObject;
    // private Boolean showAddNode;  // el nombre de esta variable no es claro
    // private Boolean showAddPortico;  // el nombre de esta variable no es claro
    // private Boolean showDrawLevels;  // no entiendo la diferencia entre esta variable
    // private Boolean showDrawLevel;  // y esta otra

    // private String textCoordinates;
    // private String textLevel;
    // private String textTrackedObject;

    // private Vector mouseCoordinates;
    // private Vector coordinates;
    // private Vector i;
    // private Vector j;

    // private Axes axes;
    // private Nodes nodes;
    // private Porticos porticos;

    private Vector screenCoordinates;
    private boolean drawSelector = false;

    public StressManager(PApplet pApplet, Scene scene) {
        this.pApplet = pApplet;
        this.scene = scene;

//        // this.fileManager = new FileManager(this);
//        // this.commandManager = new CommandManager(this);
//        // this.status = Status.CANVAS;  // know if we are at the canvas, console or menu
//
//        // this.toggleShowCoordinates = true;  // toggle show mouse coordinates
//        // this.toggleShowLevel = true;  // toggle show active level
//        // this.toggleShowTrackedObject = true;  // toggle show tracked object
//        // this.showAddNode = false;  // el nombre de esta variable no es claro
//        // this.showAddPortico = false;  // el nombre de esta variable no es claro
//        // this.showDrawLevels = false;
//        // this.showDrawLevel = false;
//
//        // this.textCoordinates = "";
//        // this.textLevel = "";
//        // this.textTrackedObject = "";
//
//        // section
//        /*section = pApplet.createShape();
//        section.beginShape();
//        section.fill(pApplet.color(0, 0, 255, 63));
//        section.stroke(pApplet.color(0, 0, 255));
//        section.vertex( 0.125f,  0.225f);
//        section.vertex(-0.125f,  0.225f);
//        section.vertex(-0.125f, -0.225f);
//        section.vertex( 0.125f, -0.225f);
//        section.endShape(pApplet.CLOSE);*/
//
         this.pApplet.registerMethod("draw", this);
//        // this.pApplet.registerMethod("keyEvent", this);
          this.pApplet.registerMethod("mouseEvent", this);
    }
//
     public void draw() {
        // if (drawSelector) drawSelector();
        // if (status == Status.COMMAND) {
            // drawCommandLine();
        // }
        // if (toggleShowCoordinates) {
            // drawCoordinates();
        // }
        // if (showDrawLevels) {
            // drawLevels();
        // }
        // if (toggleShowTrackedObject) {
            // drawTrackedObject();
        // }

        // if (showDrawLevel) {
            // drawLevel();
        // }
     }

     public void drawSelector() {
        int colorStroke = 127;
        int colorFill   = 127;
        int colorAlpha  =  63;

        pApplet.pushStyle();

        pApplet.rectMode(pApplet.CORNERS);

        if (pApplet.mouseX >= screenCoordinates.x()) {
            pApplet.stroke(0, colorStroke, 0);
            pApplet.fill(0, colorFill, 0, colorAlpha);
        } else if (pApplet.mouseX < screenCoordinates.x()) {
            pApplet.stroke(colorStroke, 0, 0);
            pApplet.fill(colorFill, 0, 0, colorAlpha);
        }

        scene.beginScreenDrawing();
        pApplet.rect(screenCoordinates.x(), screenCoordinates.y(), pApplet.mouseX, pApplet.mouseY);
        scene.endScreenDrawing();

        pApplet.popStyle();
     }

//    /* runCommand debería estar en commandManageer y desde este llamar las funciones
//    de stressManager
//
//    public void runCommand() {
//        String[] params = this.commandManager.command.split(" ");
//        String command = params[0].toUpperCase();
//        String i = "";
//        String j = "";
//        String[] iCoords;
//        String[] jCoords;
//
//        switch (command) {
//            case "CREATE":
//                switch (params[1].toUpperCase()){
//                    case "NODE":
//                        i = params[2];
//
//                        iCoords = i.substring(1, i.length() - 1).split(",");
//                        Vector iVect = new Vector(Integer.valueOf(iCoords[0]), Integer.valueOf(iCoords[1]), Integer.valueOf(iCoords[2]));
//
//                        System.out.println(i);
//                        System.out.println(iVect);
//
//                        this.addNode(iVect);
//                        break;
//                    case "PORTICO":
//                        i = params[2];
//                        j = params[3];
//
//                        iCoords = i.substring(1, i.length() - 1).split(",");
//                        this.i = new Vector(Integer.valueOf(iCoords[0]), Integer.valueOf(iCoords[1]), Integer.valueOf(iCoords[2]));
//
//                        jCoords = j.substring(1, j.length() - 1).split(",");
//                        this.j = new Vector(Integer.valueOf(jCoords[0]), Integer.valueOf(jCoords[1]), Integer.valueOf(jCoords[2]));
//
//                        this.addPortico();
//                        break;
//                }
//                break;
//            default:
//                System.out.println("ERROR: Command not Found");
//                break;
//        }
//    }
//*/
     public void initManager() {
        // initializeAxes();

        // nodes = new Nodes(scene);

        // porticos = new Porticos(scene);
     }

//    // public void initializeAxes() {
//        // axes = new Axes(scene);
//
//        // int dx = 5;
//        // int dy = 5;
//        // int length = dx * dy;
//
//        // String[] xBubbleText = {"A", "B", "C", "D", "E"};
//        // for (int i = 0; i < 5; i++) {
//            // axes.addAxis(new Vector(-dx, dy * i), new Vector(length + dy, dy * i), xBubbleText[i]);
//        // }
//
//        // String[] yBubbleText = {"1", "2", "3", "4", "5"};
//        // for (int i = 0; i < 5; i++) {
//            // axes.addAxis(new Vector(dx * i, length), new Vector(dx * i, -dy), yBubbleText[i]);
//        // }
//
//        // axes.addLevel( 0f);
//        // axes.addLevel( 5f);
//        // axes.addLevel(10f);
//    // }
//
//    // public void drawLevels() {
//        // for (int i = 0; i < axes.levels().size(); i++) {
//            // parent.pushStyle();
//
//            // if (i == axes.actualIndexLevel()) {
//                // parent.stroke(144, 238, 144);
//                // parent.fill(144, 238, 144, 15);
//            // } else {
//                // parent.stroke(31, 117, 254);
//                // parent.fill(31, 117, 254, 15);
//            // }
//
//            parent.pushMatrix();
//            parent.translate(0, 0, axes.levels().get(i));
//            parent.ellipse(scene.center().x(), scene.center().y(), 2 * scene.radius(), 2 * scene.radius());
//
//            parent.popMatrix();
//            parent.popStyle();
//        }
//    }
//
//    public void drawCoordinates() {
//        if (mouseCoordinates != null) {
//            String coordinates;
//            Integer offsetY = 10;
//            Integer offsetX = 10;
//
//            if (status == Status.COMMAND) {
//                offsetY = 40;
//            }
//
//            scene.beginScreenDrawing();
//            parent.pushStyle();
//
//            parent.textAlign(parent.RIGHT, parent.BOTTOM);
//            parent.textSize(14);
//
//            parent.fill(0);
//            parent.text(")", parent.width - offsetX, parent.height - offsetY);
//            coordinates = ")";
//
//            parent.fill(0, 0, 255);
//            parent.text(parent.nf(mouseCoordinates.z(), 0, 3), parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
//            coordinates = parent.nf(mouseCoordinates.z(), 0, 3) + coordinates;
//
//            parent.fill(0);
//            parent.text(" ,", parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
//            coordinates = " ," + coordinates;
//
//            parent.fill(0, 255, 0);
//            parent.text(parent.nf(mouseCoordinates.y(), 0, 3), parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
//            coordinates = parent.nf(mouseCoordinates.y(), 0, 3) + coordinates;
//
//            parent.fill(0);
//            parent.text(" ,", parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
//            coordinates = " ," + coordinates;
//
//            parent.fill(255, 0, 0);
//            parent.text(parent.nf(mouseCoordinates.x(), 0, 3), parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
//            coordinates = parent.nf(mouseCoordinates.x(), 0, 3) + coordinates;
//
//            parent.fill(0);
//            parent.text("(", parent.width - parent.textWidth(coordinates) - offsetX, parent.height - offsetY);
//            coordinates = "(" + coordinates;
//
//            parent.popStyle();
//            scene.endScreenDrawing();
//
//            this.textCoordinates = coordinates;
//        }
//    }
//
//    public void drawLevel() {
//        String level;
//
//        scene.beginScreenDrawing();
//        parent.pushStyle();
//
//        int offsetX = (int) (parent.textWidth(textCoordinates) + 10);
//        int offsetY = 10;
//
//        if (status == Status.COMMAND) {
//            offsetY = 40;
//        }
//
//        parent.textAlign(parent.RIGHT, parent.BOTTOM);
//        parent.textSize(14);
//
//        parent.fill(0);
//
//        level = "Nivel Z: " + parent.nf(axes.levels().get(axes.actualIndexLevel()), 0, 3);
//
//        parent.text(level, parent.width - offsetX, parent.height - offsetY);
//
//        scene.endScreenDrawing();
//        parent.popStyle();
//
//        textLevel = level;
//    }
//
//    public void drawTrackedObject() {
//        String trackedObject = "";
//
//        scene.beginScreenDrawing();
//        parent.pushStyle();
//
//        int offsetX = (int) (parent.textWidth(textLevel) + parent.textWidth(textCoordinates) + 10);
//        int offsetY = 10;
//
//        if (status == Status.COMMAND) {
//            offsetY = 40;
//        }
//
//        parent.textAlign(parent.RIGHT, parent.BOTTOM);
//        parent.textSize(14);
//
//        parent.fill(0);
//
//        if (!(scene.trackedFrame() == null)) {
//            trackedObject = scene.trackedFrame().getClass().getSimpleName();
//        }
//
//        parent.text(trackedObject, parent.width - offsetX, parent.height - offsetY);
//
//        scene.endScreenDrawing();
//        parent.popStyle();
//
//        textTrackedObject = trackedObject + textLevel;
//    }
//
//    public void addNode() {
//        nodes.add(mouseCoordinates);
//    }
//
//    public void addNode(Vector vect) {
//        System.out.println(vect);
//        nodes.add(vect);
//    }
//
//    public void addPortico() {
//        porticos.add(section, i, j);
//    }
//
//    public void drawCommandLine() {
//        scene.beginScreenDrawing();
//        parent.pushStyle();
//        parent.fill(32, 32, 32, 200);
//        parent.noStroke();
//        parent.rect(0, scene.height() - 30, scene.width(), scene.height());
//        parent.fill(255);
//        parent.text(String.format(">> %s", commandManager.command), 5, scene.height() - 10);
//        parent.popStyle();
//        scene.endScreenDrawing();
//    }
//
//    public void keyEvent(KeyEvent event) {
//        if (event.getAction() == KeyEvent.PRESS) {
//            if (event.getKey() == parent.ENTER) {
//                if (status == Status.CANVAS) {
//                    status = Status.COMMAND;
//                } else if (status == Status.COMMAND) {
//                    status = Status.CANVAS;
//                    runCommand();
//                    this.commandManager.command = "";
//                }
//            } else if (status == Status.COMMAND) {
//                if (event.getKeyCode() >= 32 && event.getKeyCode() < 126){
//                    this.commandManager.command += event.getKey();
//                } else if (event.getKeyCode() == 8 && this.commandManager.command.length() > 0) {
//                    this.commandManager.command = this.commandManager.command.substring(0, this.commandManager.command.length() -1);
//                }
//            } else {
//                switch(event.getKey()) {
//                    case 'c':
//                        textCoordinates = "";
//                        toggleShowCoordinates = !toggleShowCoordinates;
//                        break;
//                    case 'a':
//                        porticos.setDrawLocalAxes(!porticos.drawLocalAxes());
//                        break;
//                    case 'e':
//                        toggleShowLevel = !toggleShowLevel;
//                        break;
//                    case 'f':
//                        toggleShowTrackedObject = !toggleShowTrackedObject;
//                        break;
//                    case 'l':
//                        nodes.setDrawLabels(!nodes.drawLabels());
//                        porticos.setDrawLabels(!porticos.drawLabels());
//                        break;
//                    case 'n':
//                        showAddNode = !showAddNode;
//                        break;
//                    case 'p':
//                        showAddPortico = !showAddPortico;
//                        break;
//                    case 'x':
//                        porticos.setShowExtrude(!porticos.showExtrude());
//                        break;
//                    case '+':
//                        axes.setActualIndexLevel(axes.actualIndexLevel() < axes.levels().size() - 1 ?
//                                axes.actualIndexLevel() + 1 : 0);
//                        break;
//                    case '-':
//                        axes.setActualIndexLevel(0 < axes.actualIndexLevel() ?
//                                axes.actualIndexLevel() - 1 : axes.levels().size() - 1);
//                        break;
//                    case 'q':
//                        showDrawLevels = !showDrawLevels;
//                        break;
//                    case 'z':
//                        showDrawLevel = !showDrawLevel;
//                        break;
//                }
//            }
//        }
//    }
//
    public void mouseEvent(MouseEvent event) {
        if (event.getAction() == MouseEvent.MOVE) {
//            if (scene.trackedFrame() == null) {
//                mouseCoordinates = scene.location(new Vector(parent.mouseX, parent.mouseY));
//            } else if (!scene.trackedFrame().getClass().getName().equals("stress.primitives.Axis")) {
//                mouseCoordinates = scene.trackedFrame().position();
//            }
        } else if (event.getAction() == MouseEvent.DRAG) {
            if (event.getButton() == pApplet.LEFT) {
                drawSelector = true;
                pApplet.println("Hace falta implementar");
            } else if (event.getButton() == pApplet.CENTER) {
                scene.translate();
            } else if (event.getButton() == pApplet.RIGHT) {
                scene.rotateCAD(new Vector(0, 0, 1));
                pApplet.println("Con respecto a que punto está rotando ?");
            }
        } else if (event.getAction() == MouseEvent.RELEASE) {
            this.screenCoordinates = null;
            drawSelector = false;
        } else if (event.getAction() == MouseEvent.CLICK && event.getButton() == pApplet.LEFT && event.getCount() == 1) {
            this.screenCoordinates = new Vector(pApplet.mouseX, pApplet.mouseY);
//            if (showAddNode) {
//                this.addNode();
//                this.showAddNode = false;
//            } else if (showAddPortico) {
//                if (i == null) {
//                    i = mouseCoordinates;
//                    return;
//                } else {
//                    j = mouseCoordinates;
//                }
//
//                addPortico();
//
//                i = null;
//                j = null;
//                showAddPortico = false;
//            }
        } else if (event.getAction() == MouseEvent.WHEEL) {
            // scene.setTrackedFrame(scene.eye());
            scene.scale(-20 * event.getCount());
            pApplet.println("Hacia donde apuntar el wheel ?"  +
                "\n Aumentar la sensibilidad ?");
        }
    }
}
