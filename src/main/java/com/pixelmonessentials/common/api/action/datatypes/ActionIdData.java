package com.pixelmonessentials.common.api.action.datatypes;

import com.pixelmonessentials.common.api.action.ActionData;

public class ActionIdData extends ActionData {
    int id;

    public ActionIdData(String name, int id){
        super(name);
        this.id=id;
    }

    public int getId(){
        return this.id;
    }
}
