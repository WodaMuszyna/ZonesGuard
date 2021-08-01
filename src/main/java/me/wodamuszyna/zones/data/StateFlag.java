package me.wodamuszyna.zones.data;

import java.util.HashMap;
import java.util.Map;

public class StateFlag{
    private String name;
    private boolean def;

    public StateFlag(String name, boolean def){
        this.name = name;
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public State getDefault() { return this.def ? State.ALLOW : null; }

    public enum State{
        ALLOW,
        DENY;
    }

    public static class Registry {

        public static StateFlag verify(String name){
            for(StateFlag f : flags){
                if(f.getName().equalsIgnoreCase(name)) return f;
            }
            return null;
        }

        public static Map<StateFlag, State> getFlags(){
            Map<StateFlag, State> flagsMap = new HashMap<>();
            for(StateFlag flag : flags){
                flagsMap.put(flag, flag.getDefault());
            }
            return flagsMap;
        }

        public static final StateFlag BUILD = new StateFlag("build", false);
        public static final StateFlag PVP = new StateFlag("pvp", false);
        public static final StateFlag BLOCK_BREAK = new StateFlag("block-break", false);
        public static final StateFlag BLOCK_PLACE = new StateFlag("block-place", false);
        public static final StateFlag USE = new StateFlag("use", false);
        public static final StateFlag OPEN_CHEST = new StateFlag("open-chest", false);
        public static final StateFlag ITEM_DROP = new StateFlag("item-drop", false);
        public static final StateFlag ITEM_PICKUP = new StateFlag("item-pickup", false);
        public static final StateFlag ENDERPEARL = new StateFlag("enderpearl", false);


        public static final StateFlag[] flags = {BUILD, PVP, BLOCK_BREAK, BLOCK_PLACE, USE, OPEN_CHEST, ITEM_DROP, ITEM_PICKUP, ENDERPEARL};

    }

}
