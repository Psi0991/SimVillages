package com.pyxlhaus.SimVillages.Buildings;

import com.google.common.primitives.UnsignedInteger;
import com.pyxlhaus.SimVillages.UserInterface.CardinalDirection;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;

/**
 * Created by Psi on 1/17/2018.
 */
public class BuildingTemplate {
    private HashMap<Vector, Block> building_blocks;
    private Vector dimensions;
    private Player creator;
    private UnsignedInteger building_stage;
    private String building_name;
    private CardinalDirection base_orientation;
    private BuildingType building_type;

    public Vector getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector dimensions) {
        this.dimensions = dimensions;
    }

    public Player getCreator() {
        return creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
    }

    public UnsignedInteger getBuilding_stage() {
        return building_stage;
    }

    public void setBuilding_stage(UnsignedInteger building_stage) {
        this.building_stage = building_stage;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public CardinalDirection getBase_orientation() {
        return base_orientation;
    }

    public void setBase_orientation(CardinalDirection base_orientation) {
        this.base_orientation = base_orientation;
    }

    public BuildingType getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(BuildingType building_type) {
        this.building_type = building_type;
    }

    public HashMap<Vector, Block> getBuilding_blocks() {
        return building_blocks;
    }

    public void setBuilding_blocks(HashMap<Vector, Block> building_blocks) {
        this.building_blocks = building_blocks;
    }

    public enum BuildingType{
        RESIDENTIAL,
        AGRICULTURAL,
        RECREATIONAL,
        COMMERCIAL,
        INDUSTRIAL,
        MARTIAL
    }

    public BuildingTemplate(Vector dimensions, Player creator, UnsignedInteger building_stage, String building_name,
                            BuildingType building_type){
        this.dimensions = dimensions;
        this.creator = creator;
        this.building_stage = building_stage;
        this.building_name = building_name;
        this.building_type = building_type;
    }
}
