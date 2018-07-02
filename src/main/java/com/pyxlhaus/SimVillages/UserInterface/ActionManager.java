package com.pyxlhaus.SimVillages.UserInterface;

import com.pyxlhaus.SimVillages.Localization;
import com.pyxlhaus.SimVillages.SVLogger;
import com.pyxlhaus.SimVillages.SimVillages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Psi on 1/13/2018.
 * Has logic for all possible actions within SimVillages.
 */
public class ActionManager {
    private SimVillages plugin;
    private SVLogger logger;
    private Localization text;
    private String ENDL;
    private HashMap<String, Block> player_pos1;
    private HashMap<String, Block> player_pos2;

    public ActionManager(SimVillages plugin, SVLogger logger, Localization text){
        this.plugin = plugin;
        this.logger = logger;
        this.text = text;
        this.ENDL = "\n";
        this.player_pos1 = new HashMap();
        this.player_pos2 = new HashMap();
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

    public String get_help_not_found_text(String command){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        response += this.text.get_text(Localization.Text.HELP_NOT_FOUND);
        response += ChatColor.BLUE + command + ENDL;
        response += this.text.get_text(Localization.Text.GENERAL_HELP) + ENDL;

        return response;
    }

    public String select_pos_1(Player player, Block selected_block){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        UUID player_uuid = player.getUniqueId();
        this.player_pos1.put(player_uuid.toString(), selected_block);
        logger.log("Position 1 for " + player.getDisplayName() + "[" + player_uuid.toString() + "] set at " +
                selected_block.getLocation().toString() + ".", SVLogger.INFO);
        response += this.text.get_text(Localization.Text.POS1_SET) + selected_block.getLocation().toString();
        return response;
    }

    public String select_pos_2(Player player, Block selected_block){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        UUID player_uuid = player.getUniqueId();
        this.player_pos2.put(player_uuid.toString(), selected_block);
        logger.log("Position 2 for " + player.getDisplayName() + "[" + player_uuid.toString() + "] set at " +
                selected_block.getLocation().toString() + ".", SVLogger.INFO);
        response += this.text.get_text(Localization.Text.POS2_SET) + selected_block.getLocation().toString();
        return response;
    }

    public String save_template(Player player){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        String player_uuid = player.getUniqueId().toString();
        if (player_pos1.get(player_uuid) != null) {
            Location pos_1_location = player_pos1.get(player_uuid).getLocation();
        }
        else{
            response += this.text.get_text(Localization.Text.POS1_NOT_SET) + ENDL;
        }
        if (player_pos2.get(player_uuid) != null) {
            Location pos_2_location = player_pos2.get(player_uuid).getLocation();
        }
        else{
            response += this.text.get_text(Localization.Text.POS2_NOT_SET) + ENDL;
        }

        return response;
    }
}
