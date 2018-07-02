package com.pyxlhaus.SimVillages;

/**
 * Created by Psi on 1/13/2018.
 */

import org.bukkit.ChatColor;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Severity:
 *  -1 = OFF
 *          Doesn't print any errors.
 *  0 = UNKNOWN
 *          An unknown message that should always be logged.
 *  1 = FATAL
 *          An unhandleable error that results in a program crash.
 *  2 = ERROR
 *          A handleable error condition.
 *  3 = WARNING
 *          A warning.
 *  4 = INFO
 *          Generic (useful) information about system operation.
 *  5 = DEBUG
 *          Low-level information for developers.
 */
public class SVLogger {

    public static final int UNKNOWN = 0;
    public static final int FATAL = 1;
    public static final int ERROR = 2;
    public static final int WARNING = 3;
    public static final int INFO = 4;
    public static final int DEBUG = 5;

    private int Nmax_printable_severity;
    private boolean Bprint_date;
    private SimVillages plugin;
    private static final DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     *
     * @param plugin
     */
    public SVLogger(SimVillages plugin){
        this.Nmax_printable_severity = 5;
        this.Bprint_date = false;
        this.plugin = plugin;
        this.log("Logger initialized.", SVLogger.DEBUG);
    }

    /**
     *
     * @param Smessage
     * @param Nseverity
     * @return
     */
    public boolean log(String Smessage, int Nseverity){

        if (Nseverity <= this.Nmax_printable_severity){
            String Sresult_message = ChatColor.ITALIC + "" + ChatColor.DARK_GREEN + "[SimVillages]";
            if (this.Bprint_date) {
                Date date = new Date();
                String Sdate_string = date_format.format(date);
                Sresult_message += ChatColor.GRAY + "[" + Sdate_string + "]";
            }
            if (Nseverity == 0) { Sresult_message += ChatColor.RED + "[UNKNOWN] "; }
            if (Nseverity == 1) { Sresult_message += ChatColor.RED + "[FATAL] "; }
            if (Nseverity == 2) { Sresult_message += ChatColor.RED + "[ERROR] "; }
            if (Nseverity == 3) { Sresult_message += ChatColor.YELLOW + "[WARNING] "; }
            if (Nseverity == 4) { Sresult_message += ChatColor.WHITE + "[INFO] "; }
            if (Nseverity == 5) { Sresult_message += ChatColor.DARK_GREEN + "[DEBUG] "; }

            Sresult_message += Smessage;

            plugin.getServer().getConsoleSender().sendMessage(Sresult_message);
            return true;
        }
        return false;
    }

}
