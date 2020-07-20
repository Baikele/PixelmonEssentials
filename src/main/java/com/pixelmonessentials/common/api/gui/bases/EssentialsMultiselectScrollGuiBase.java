package com.pixelmonessentials.common.api.gui.bases;

public class EssentialsMultiselectScrollGuiBase extends EssentialsScrollGuiBase{
    private int[] multiselectScrolls;

    public EssentialsMultiselectScrollGuiBase(int id){
        super(id);
    }

    public EssentialsMultiselectScrollGuiBase(int id, int[] multiselectScrolls){
        super(id);
        this.multiselectScrolls=multiselectScrolls;
    }

    public int[] getMultiselectScrolls(){
        return this.multiselectScrolls;
    }

    public void updateFromMultiSelect(String[] selection){
    }
}
