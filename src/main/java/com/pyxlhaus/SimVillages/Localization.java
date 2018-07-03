package com.pyxlhaus.SimVillages;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Psi on 1/13/2018.
 */
public class Localization {
    private SVLogger logger;
    public enum Languages {
        ENGLISH,
        GERMAN,
    }
    public enum Text {
        SV_PREFIX,          // [SimVillages]
        SV_HINT_1,          // Hint: Short Command
        GENERAL_HELP,       // How to use help command
        SV_HINT_2,          // Hint: Getting Started help
        SV_HINT_3,          // Hint: List of available commands
        NO_PERMISSION,      // Permission denied
        HELP_NOT_FOUND,     // Help not found
        POS1_SET,           // Position 1 for selection set
        POS2_SET,           // Position 2 for selection set
        NOT_PLAYER,         // Error: Not executed by player
        POS1_NOT_SET,       // Position 1 not set
        POS2_NOT_SET,       // Position 2 not set
        POS_WORLD_NO_MATCH, // Position 1 & 2 not in the same world
        SCANNING_TEMPLATE,  // Template is being scanned
        SCAN_COMPLETED,     // Scan completed.
    }
    private Languages Language;
    private boolean error_text_not_found;

    public Localization(String Slanguage, SVLogger logger){
        logger.log("Initializing Localization Manager.", SVLogger.DEBUG);
        this.logger = logger;
        for(Languages Language : Languages.values()){
            if (Slanguage.toUpperCase().equals(Language.name())){
                this.Language = Language;
                logger.log("Language set to " + Slanguage, SVLogger.INFO);
                return;
            }
        }
        this.Language = Languages.ENGLISH;
        logger.log(Slanguage + " is not a valid language. Defaulting to ENGLISH.", SVLogger.WARNING);
    }

    private void error_text_not_found(Text text){
        logger.log("[" + this.Language.name() + "]"
                + "[" + text.name() + "]"
                + "Text not found.", SVLogger.ERROR);
    }

    private String get_default_text(Text text){
        this.error_text_not_found = true;
        String def_text = ChatColor.RED + "Text not found in " + this.Language.name() + ": ";
        Localization default_language = new Localization("ENGLISH", this.logger);
        def_text += default_language.get_text(text);

        return def_text;
    }

    public String get_text(Text text){
        logger.log("Getting Text [" +this.Language + "][" + text.name() + "].", SVLogger.DEBUG);
        this.error_text_not_found = false;
        String Slocalized_text = "";
        switch (this.Language){
            default:
                Slocalized_text = "Language not found.";
                logger.log("Invalid language.", SVLogger.ERROR);
                break;
            case ENGLISH:
                switch (text){
                    default:
                        this.error_text_not_found(text);
                        Slocalized_text = "Text not found.";
                        break;
                    case SV_PREFIX:
                        Slocalized_text = ChatColor.DARK_GREEN + "[SimVillages] " + ChatColor.WHITE + "";
                        break;
                    case SV_HINT_1:
                        Slocalized_text = ChatColor.WHITE + "A shorter way to enter commands is " + ChatColor.BLUE
                                + " /sv <command>" + ChatColor.WHITE + ".";
                        break;
                    case GENERAL_HELP:
                        Slocalized_text = ChatColor.WHITE + "To get help with a specific command enter"
                                + ChatColor.BLUE + " /simvillages help <command>" + ChatColor.WHITE + ".";
                        break;
                    case SV_HINT_2:
                        Slocalized_text = ChatColor.WHITE + "To get help getting started with"
                                + ChatColor.DARK_GREEN + " SimVillages" + ChatColor.WHITE + " use"
                                + ChatColor.BLUE + " /simvillages start" + ChatColor.WHITE + ".";
                        break;
                    case SV_HINT_3:
                        Slocalized_text = ChatColor.WHITE + "For a list of commands available to you use"
                                + ChatColor.BLUE + " /simvillages list" + ChatColor.WHITE + ".";
                        break;
                    case NO_PERMISSION:
                        Slocalized_text = ChatColor.RED + "You do not have permission to do this.";
                        break;
                    case HELP_NOT_FOUND:
                        Slocalized_text = ChatColor.WHITE + "Help for the following command was not found: ";
                        break;
                    case POS1_SET:
                        Slocalized_text = ChatColor.WHITE + "Position 1 selected at " + ChatColor.LIGHT_PURPLE + "";
                        break;
                    case POS2_SET:
                        Slocalized_text = ChatColor.WHITE + "Position 2 selected at " + ChatColor.LIGHT_PURPLE + "";
                        break;
                    case NOT_PLAYER:
                        Slocalized_text = "Command must be executed by player.";
                        break;
                    case POS1_NOT_SET:
                        Slocalized_text = ChatColor.YELLOW + "Position 1 was not set.";
                        break;
                    case POS2_NOT_SET:
                        Slocalized_text = ChatColor.YELLOW + "Position 2 was not set.";
                        break;
                    case POS_WORLD_NO_MATCH:
                        Slocalized_text = ChatColor.YELLOW + "Position 1 and 2 not set in the same world.";
                        break;
                    case SCANNING_TEMPLATE:
                        Slocalized_text = ChatColor.WHITE + "Template is being scanned.";
                        break;
                    case SCAN_COMPLETED:
                            Slocalized_text = ChatColor.WHITE + "Template has been scanned.";
                        break;
                }
                break;
            case GERMAN:
                switch (text){
                    default:
                        this.error_text_not_found(text);
                        Slocalized_text = this.get_default_text(text);
                        break;
                    case SV_PREFIX:
                        Slocalized_text = ChatColor.DARK_GREEN + "[SimVillages] " + ChatColor.WHITE + "";
                        break;
                }
                break;
        }
        return Slocalized_text;
    }

    public void test_all_text(){
        Localization test_lang;
        List<String> error_text;
        Integer text_count = Text.values().length;
        Integer error_count;
        for (Languages lang : Languages.values()){
            test_lang = new Localization(lang.name(), this.logger);
            error_text = new ArrayList<String>();
            error_count = 0;
            logger.log("Testing " + lang.name(), SVLogger.DEBUG);
            for (Text txt : Text.values()){
                test_lang.get_text(txt);
                if (test_lang.error_text_not_found){
                    error_count++;
                    error_text.add(txt.name());
                }
            }
            logger.log(error_count + " errors.", SVLogger.WARNING);
            if (error_count == 0){
                logger.log("All text for " + lang + " set.", SVLogger.WARNING);
            }
            else{
                logger.log((text_count-error_count) + "/" + text_count + " set.", SVLogger.WARNING);
                logger.log("Following text needs to be set for " + lang + ": " + error_text, SVLogger.WARNING);
            }
        }
    }
}
