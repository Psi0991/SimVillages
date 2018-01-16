package com.pyxlhaus.SimVillages.UserInterface;

import com.pyxlhaus.SimVillages.Localization;
import com.pyxlhaus.SimVillages.SVLogger;
import com.pyxlhaus.SimVillages.SimVillages;
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

    public CommandManager(SimVillages plugin, SVLogger logger, Localization text){
        this.plugin = plugin;
        this.text = text;
        this.logger = logger;
        this.action_manager = new ActionManager(plugin, logger, text);
        this.plugin.getCommand("simvillages").setExecutor(this);
        this.Sno_permission = text.get_text(Localization.Text.SV_PREFIX) + text.get_text(Localization.Text.NO_PERMISSION);
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

            return true;
        }
        else{
            return false;
        }
    }

    public void help(CommandSender sender, String command){
        if(sender.hasPermission("sv.help")) {
            String message = "";
            if(command == "") {
                message = this.action_manager.get_general_help_text();
            }
            else{
                message = this.action_manager.get__help_not_found_text(command);
            }

            sender.sendMessage(message);
        }
        else {
            sender.sendMessage(Sno_permission);
        }
    }


}
