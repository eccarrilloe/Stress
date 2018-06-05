package stress.core;

import processing.core.PApplet;

public class CommandManager {

    public String command;
    StressManager stressManager;

    public CommandManager(StressManager stressManager) {
        this.stressManager = stressManager;
        this.command = "";
    }
}
