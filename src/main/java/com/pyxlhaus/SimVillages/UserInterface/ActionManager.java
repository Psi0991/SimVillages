package com.pyxlhaus.SimVillages.UserInterface;

import com.pyxlhaus.SimVillages.Buildings.BuildingTemplate;
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


    public static CardinalDirection get(Player player) {
        float yaw = player.getLocation().getYaw();
        if (yaw < 0) {
            yaw += 360;
        }
        if (yaw >= 315 || yaw < 45) {
            return CardinalDirection.SOUTH;
        } else if (yaw < 135) {
            return CardinalDirection.WEST;
        } else if (yaw < 225) {
            return CardinalDirection.NORTH;
        } else if (yaw < 315) {
            return CardinalDirection.EAST;
        }
        return CardinalDirection.NORTH;
    }

    public String create_new_template(Player player, BuildingTemplate template){
        String response = this.text.get_text(Localization.Text.SV_PREFIX);
        Vector dimensions = template.getDimensions();
        int width = (int)dimensions.getX();
        int height = (int)dimensions.getY();
        int depth = (int)dimensions.getZ();
        CardinalDirection direction = get(player);
        if (direction == CardinalDirection.NORTH){      //facing to -z

        }
        if (direction == CardinalDirection.SOUTH){      //facing to +z

        }
        if (direction == CardinalDirection.WEST){      //facing to -x

        }
        if (direction == CardinalDirection.EAST){      //facing to -x

        }

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

    public String save_template(final Player player){
        boolean fail = false;
        World pos1_world = player.getLocation().getWorld();
        World pos2_world = player.getLocation().getWorld();
        Vector pos_1_location = player.getLocation().toVector();
        Vector pos_2_location = player.getLocation().toVector();
        String response = "";
        String player_uuid = player.getUniqueId().toString();
        if (player_pos1.get(player_uuid) != null) {
            pos_1_location = player_pos1.get(player_uuid).getLocation().toVector();
            pos1_world = player_pos1.get(player_uuid).getWorld();
        }
        else{
            response += this.text.get_text(Localization.Text.SV_PREFIX);
            response += this.text.get_text(Localization.Text.POS1_NOT_SET) + ENDL;
            fail = true;
        }
        if (player_pos2.get(player_uuid) != null) {
            pos_2_location = player_pos2.get(player_uuid).getLocation().toVector();
            pos2_world = player_pos2.get(player_uuid).getWorld();
        }
        else{
            response += this.text.get_text(Localization.Text.SV_PREFIX);
            response += this.text.get_text(Localization.Text.POS2_NOT_SET);
            fail = true;
        }
        if (!fail && (pos1_world != pos2_world)){
            response += this.text.get_text(Localization.Text.SV_PREFIX);
            response += this.text.get_text(Localization.Text.POS_WORLD_NO_MATCH);
        }
        else{
            if (!fail){
                final World template_world = pos1_world;
                response += this.text.get_text(Localization.Text.SV_PREFIX);
                final int min_x;
                final int max_x;
                final int min_y;
                final int max_y;
                final int min_z;
                final int max_z;
                double x_distance = (new Vector(pos_1_location.getX(), 0, 0).distance(new Vector(pos_2_location.getX(), 0, 0))) + 1d;
                double y_distance = (new Vector(0, pos_1_location.getY(), 0).distance(new Vector(0, pos_2_location.getY(), 0))) + 1d;
                double z_distance = (new Vector(0, 0, pos_1_location.getZ()).distance(new Vector(0, 0, pos_2_location.getZ()))) + 1d;

                if (pos_1_location.getX() < pos_2_location.getX()){
                    min_x = (int)pos_1_location.getX();
                    max_x = (int)pos_2_location.getX();
                }
                else{
                    min_x = (int)pos_2_location.getX();
                    max_x = (int)pos_1_location.getX();
                }
                if (pos_1_location.getY() < pos_2_location.getY()){
                    min_y = (int)pos_1_location.getY();
                    max_y = (int)pos_2_location.getY();
                }
                else{
                    min_y = (int)pos_2_location.getY();
                    max_y = (int)pos_1_location.getY();
                }
                if (pos_1_location.getZ() < pos_2_location.getZ()){
                    min_z = (int)pos_1_location.getZ();
                    max_z = (int)pos_2_location.getZ();
                }
                else{
                    min_z = (int)pos_2_location.getZ();
                    max_z = (int)pos_1_location.getZ();
                }

                final int block_count = (int)x_distance * (int)y_distance * (int)z_distance;
                Vector dimensions = new Vector(x_distance, y_distance, z_distance);

                response += text.get_text(Localization.Text.SCANNING_TEMPLATE);
                logger.log(player.getDisplayName() + "[" + player_uuid.toString() + "] is scanning " +
                        String.valueOf(block_count) + " blocks.", SVLogger.INFO);
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    HashMap<Vector, Block> temp_template = new HashMap();
                    Vector block_position = new Vector(0d, 0d, 0d);
                    for (int y = min_y; y <= max_y; y++){
                        block_position.setY(y - min_y);
                        for (int x = min_x; x <= max_x; x++){
                            block_position.setX(x - min_x);
                            for (int z = min_z; z <= max_z; z++){
                                block_position.setZ(z - min_z);
                                Location block_scan_location = new Location(template_world, (double)x, (double)y,
                                        (double)z);
                                Block scan_block = block_scan_location.getBlock();
                                temp_template.put(block_position.clone(), scan_block);
                            }
                        }
                    }
                    logger.log("Template Size: " + String.valueOf(temp_template.size()), SVLogger.INFO);
                    String message = text.get_text(Localization.Text.SV_PREFIX);
                    message += text.get_text(Localization.Text.SCAN_COMPLETED);

                    player.sendMessage(message);
                });
            }
        }
        return response;
    }
}
