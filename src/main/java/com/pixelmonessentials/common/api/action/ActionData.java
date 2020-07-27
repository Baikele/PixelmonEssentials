package com.pixelmonessentials.common.api.action;

import com.pixelmonessentials.common.api.requirement.RequirementData;

public class ActionData {
    public String name;
    public boolean closeInv;
    public RequirementData[] requirements;

    public ActionData(String name){
        this.name=name;
    }

    public ActionData(){
    }
}
