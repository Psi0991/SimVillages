package com.pyxlhaus.SimVillages;

import com.pyxlhaus.SimVillages.UserInterface.ActionManager;
import com.pyxlhaus.SimVillages.UserInterface.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by Psi on 1/11/2018.
 */
public class SimVillages extends JavaPlugin {

    private SVLogger logger;
    private Localization text;
    private CommandManager command_manager;
    private ActionManager action_manager;

    @Override
    public void onEnable() {
        this.logger = new SVLogger(this);
        this.text = new Localization("ENGLISH", logger);
        //text.test_all_text();
        this.action_manager = new ActionManager(this, logger, text);
        this.command_manager = new CommandManager(this, logger, text, action_manager);
        logger.log("SimVillages has been enabled.", SVLogger.DEBUG);
    }

    @Override
    public void onDisable() {
        logger.log("SimVillages has been disabled.", SVLogger.DEBUG);
    }
}
