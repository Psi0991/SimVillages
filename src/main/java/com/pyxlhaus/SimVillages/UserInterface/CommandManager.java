package com.pyxlhaus.SimVillages.UserInterface;

import com.google.common.primitives.UnsignedInteger;
import com.pyxlhaus.SimVillages.Buildings.BuildingTemplate;
import com.pyxlhaus.SimVillages.Localization;
import com.pyxlhaus.SimVillages.SVLogger;
import com.pyxlhaus.SimVillages.SimVillages;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

/**
 * Created by Psi on 1/13/2018.
 */
public class CommandManager implements Listener, CommandExecutor {
    private SimVillages plugin;
    private SVLogger logger;
    private ActionManager action_manager;
    private Localization text;
    private String Sno_permission;
    private String Snot_player;

    public CommandManager(SimVillages plugin, SVLogger logger, Localization text, ActionManager action_manager){
        this.plugin = plugin;
        this.text = text;
        this.logger = logger;
        this.action_manager = action_manager;
        this.plugin.getCommand("simvillages").setExecutor(this);
        this.Sno_permission = text.get_text(Localization.Text.SV_PREFIX) +
                text.get_text(Localization.Text.NO_PERMISSION);
        this.Snot_player = text.get_text(Localization.Text.NOT_PLAYER);
        logger.log("Command Manager initialized.", SVLogger.DEBUG);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        String command = cmd.getName();

        if(command.equalsIgnoreCase("simvillages")){
            if(args.length == 0){
                this.help(sender, "");
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("help")){
                    this.help(sender, "");
                }
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("help")){
                    this.help(sender, args[1]);
                }
            }
            if(args.length == 3){
                if(args[0].equalsIgnoreCase("help")){
                    this.help(sender, args[1]);
                }
            }
            if(args.length == 5){
                if(args[0].equalsIgnoreCase("help")){
                    this.help(sender, args[1]);
                }
                if(args[0].equalsIgnoreCase("newTemplate")){
                    this.new_template(sender, args[1], args[2], args[3], args[4]);
                }
            }

            return true;
        }
        else{
            return false;
        }
    }

    public void help(CommandSender sender, String command){
        if(sender.hasPermission("sv.general.help")) {
            String message = "";
            if(command == "") {
                message = this.action_manager.get_general_help_text();
            }
            else{
                message = this.action_manager.get_help_not_found_text(command);
            }

            sender.sendMessage(message);
        }
        else {
            sender.sendMessage(Sno_permission);
        }
    }

    public void new_template(CommandSender sender, String Sdimensions, String Sbuilding_stage, String building_name,
                             String Sbuilding_type){
        if(sender.hasPermission("sv.template.new")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                String[] dimension_components = Sdimensions.split(",");
                double x = Double.valueOf(dimension_components[0]);
                double y = Double.valueOf(dimension_components[1]);
                double z = Double.valueOf(dimension_components[2]);
                Vector dimensions = new Vector(x, y, z);
                UnsignedInteger building_stage = UnsignedInteger.valueOf(Sbuilding_stage);
                BuildingTemplate.BuildingType building_type = BuildingTemplate.BuildingType.valueOf(Sbuilding_type);
                BuildingTemplate new_template = new BuildingTemplate(dimensions, player, building_stage, building_name,
                        building_type);
                String message = this.action_manager.create_new_template(player, new_template);
                sender.sendMessage(message);
            }
            else {
                logger.log(Snot_player, SVLogger.WARNING);
            }
        }
        else{
            sender.sendMessage(Sno_permission);
        }

    }

}
