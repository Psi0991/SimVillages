package com.pyxlhaus.SimVillages.Buildings;

import com.google.common.primitives.UnsignedInteger;
import com.pyxlhaus.SimVillages.Vector3;
import org.bukkit.block.Block;

import java.util.HashMap;

/**
 * Created by Psi on 1/17/2018.
 */
public class BuildingTemplate {
    private HashMap<Integer, Block> building_blocks;
    private Integer block_count;
    private String creator;
    private UnsignedInteger building_stage;
    private String building_name;
    private Vector3 max_dimensions;
    private char base_orientation;
    public enum BuildingTypes{
        RESIDENTIAL,
        AGRICULTURAL,
        RECREATIONAL,
        COMMERCIAL,
        INDUSTRIAL,
        MARTIAL
    }

    public BuildingTemplate(){

    }
}
