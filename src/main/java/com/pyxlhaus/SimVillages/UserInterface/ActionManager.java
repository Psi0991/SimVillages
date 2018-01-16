package com.pyxlhaus.SimVillages.UserInterface;

import com.pyxlhaus.SimVillages.Localization;
import com.pyxlhaus.SimVillages.SVLogger;
import com.pyxlhaus.SimVillages.SimVillages;
import org.bukkit.ChatColor;

/**
 * Created by Psi on 1/13/2018.
 * Has logic for all possible actions within SimVillages.
 */
public class ActionManager {
    private SimVillages plugin;
    private SVLogger logger;
    private Localization text;
    private String ENDL;

    ActionManager(SimVillages plugin, SVLogger logger, Localization text){
        this.plugin = plugin;
        this.logger = logger;
        this.text = text;
        this.ENDL = "\n";
        logger.log("Action Manager initialized.", SVLogger.DEBUG);
    }

    public String get_general_help_text(){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        response += this.text.get_text(Localization.Text.GENERAL_HELP) + ENDL;
        response += this.text.get_text(Localization.Text.SV_HINT_1) + ENDL;
        response += this.text.get_text(Localization.Text.SV_HINT_2) + ENDL;
        response += this.text.get_text(Localization.Text.SV_HINT_3);

        return response;
    }

    public String get__help_not_found_text(String command){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        response += this.text.get_text(Localization.Text.HELP_NOT_FOUND);
        response += ChatColor.BLUE + command + ENDL;
        response += this.text.get_text(Localization.Text.GENERAL_HELP) + ENDL;

        return response;
    }
}
