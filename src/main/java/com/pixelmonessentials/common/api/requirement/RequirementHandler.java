package com.pixelmonessentials.common.api.requirement;

import com.pixelmonessentials.PixelmonEssentials;
import com.pixelmonessentials.common.api.requirement.types.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;

public class RequirementHandler {
    ArrayList<Requirement> requirementTypes=new ArrayList<Requirement>();

    public void init(){
        this.requirementTypes.add(new DialogRequirement());
        this.requirementTypes.add(new ItemRequirement());
        this.requirementTypes.add(new MoneyRequirement());
        this.requirementTypes.add(new QuestFinishedRequirement());
        this.requirementTypes.add(new QuestStartedRequirement());
    }

    public Requirement getRequirementType(String type){
        for(Requirement requirementType: this.requirementTypes){
            if(requirementType.getName().equals(type)){
                return requirementType;
            }
        }
        return null;
    }

    public Requirement getRequirementType(RequirementData data){
        for(Requirement requirementType: this.requirementTypes){
            if(requirementType.getName().equals(data.name)){
                return requirementType;
            }
        }
        return null;
    }

    public boolean checkRequirement(RequirementData requirementData, EntityPlayerMP playerMP){
        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(playerMP.getGameProfile())!=null){
            return true;
        }
        else{
            Requirement requirement= PixelmonEssentials.requirementHandler.getRequirementType(requirementData);
            if(requirement!=null) {
                return requirement.hasRequirement(requirementData.value, playerMP);
            }
            return true;
        }
    }

    public boolean checkRequirements(RequirementData[] requirements, EntityPlayerMP player){
        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(player.getGameProfile())!=null){
            return true;
        }
        if(requirements!=null) {
            for (RequirementData requirement : requirements) {
                if (!checkRequirement(requirement, player)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkRequirements(List<RequirementData> requirements, EntityPlayerMP player){
        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(player.getGameProfile())!=null){
            return true;
        }
        if(requirements!=null) {
            for (RequirementData requirement : requirements) {
                if (!checkRequirement(requirement, player)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addRequirement(Requirement requirement){
        this.requirementTypes.add(requirement);
    }
}
