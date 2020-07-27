package com.pixelmonessentials.common.api.action.datatypes;

import com.pixelmonessentials.common.api.action.ActionData;

public class ActionStringData extends ActionData{
    String value;

    public ActionStringData(String name, String value){
        super(name);
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }
}
