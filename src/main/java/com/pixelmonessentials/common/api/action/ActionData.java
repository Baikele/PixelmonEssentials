package com.pixelmonessentials.common.api.action;

import com.pixelmonessentials.common.api.requirement.RequirementData;

public class ActionData {
    public String name;
    public String value;
    public boolean closeInv;
    public RequirementData[] requirements;

    public ActionData(String name, String value){
        this.name=name;
        this.value=value;
    }

    public ActionData(){
    }
}
