package com.pyxlhaus.SimVillages.UserInterface;

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
                if(args[0].equalsIgnoreCase("set")){
                    this.set_pos(sender, "");
                }
                if(args[0].equalsIgnoreCase("save")){
                    this.save_template(sender);
                }
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("help")){
                    this.help(sender, args[1]);
                }
                if(args[0].equalsIgnoreCase("set")){
                    this.set_pos(sender, args[1]);
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

    public void set_pos(CommandSender sender, String command){
        if(sender.hasPermission("sv.template.setpos")){
            if(sender instanceof Player){
                String message = "";
                Player player = (Player) sender;

                if(command.equalsIgnoreCase("pos1")){
                    message = this.action_manager.select_pos_1(player);
                }
                else if(command.equalsIgnoreCase("pos2")){
                    message = this.action_manager.select_pos_2(player);
                }
                else {
                    this.help(sender, "set");
                }
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

    public void save_template(CommandSender sender){
        if(sender.hasPermission("sv.template.save")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                String message = this.action_manager.save_template(player);
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
