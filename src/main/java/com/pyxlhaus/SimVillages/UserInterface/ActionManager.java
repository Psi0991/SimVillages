package com.pyxlhaus.SimVillages.UserInterface;

import com.pyxlhaus.SimVillages.Localization;
import com.pyxlhaus.SimVillages.SVLogger;
import com.pyxlhaus.SimVillages.SimVillages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
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

    public String select_pos_1(Player player){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        UUID player_uuid = player.getUniqueId();
        Block selected_block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        this.player_pos1.put(player_uuid.toString(), selected_block);
        logger.log("Position 1 for " + player.getDisplayName() + "[" + player_uuid.toString() + "] set at " +
                selected_block.getLocation().toString() + ".", SVLogger.INFO);
        response += this.text.get_text(Localization.Text.POS1_SET) + selected_block.getLocation().toString();
        return response;
    }

    public String select_pos_2(Player player){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        UUID player_uuid = player.getUniqueId();
        Block selected_block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        this.player_pos2.put(player_uuid.toString(), selected_block);
        logger.log("Position 2 for " + player.getDisplayName() + "[" + player_uuid.toString() + "] set at " +
                selected_block.getLocation().toString() + ".", SVLogger.INFO);
        response += this.text.get_text(Localization.Text.POS2_SET) + selected_block.getLocation().toString();
        return response;
    }

    public String save_template(Player player){
        boolean fail = false;
        World pos1_world = player.getLocation().getWorld();
        World pos2_world = player.getLocation().getWorld();
        Vector pos_1_location = player.getLocation().toVector();
        Vector pos_2_location = player.getLocation().toVector();
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        String player_uuid = player.getUniqueId().toString();
        if (player_pos1.get(player_uuid) != null) {
            pos_1_location = player_pos1.get(player_uuid).getLocation().toVector();
            pos1_world = player_pos1.get(player_uuid).getWorld();
        }
        else{
            response += this.text.get_text(Localization.Text.POS1_NOT_SET) + ENDL;
            fail = true;
        }
        if (player_pos2.get(player_uuid) != null) {
            pos_2_location = player_pos2.get(player_uuid).getLocation().toVector();
            pos2_world = player_pos2.get(player_uuid).getWorld();
        }
        else{
            response += this.text.get_text(Localization.Text.POS2_NOT_SET);
            fail = true;
        }
        if (!fail && (pos1_world != pos2_world)){
            response += this.text.get_text(Localization.Text.POS_WORLD_NO_MATCH);
        }
        else{
            if (!fail){
                double x_distance = (new Vector(pos_1_location.getX(), 0, 0).distance(new Vector(pos_2_location.getX(), 0, 0))) + 1d;
                double y_distance = (new Vector(0, pos_1_location.getY(), 0).distance(new Vector(0, pos_2_location.getY(), 0))) + 1d;
                double z_distance = (new Vector(0, 0, pos_1_location.getZ()).distance(new Vector(0, 0, pos_2_location.getZ()))) + 1d;

                int block_count = (int)x_distance * (int)y_distance * (int)z_distance;
                Vector dimensions = new Vector(x_distance, y_distance, z_distance);

                response += "Template is being scanned.";
                Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                    @Override
                    public void run() {
                        logger.log("Running Scanning Thread", SVLogger.DEBUG);
                    }
                });
            }
        }
        return response;
    }
}
