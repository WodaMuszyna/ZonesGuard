package me.wodamuszyna.zones.config;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SerializableAs("Zones")
public class Config implements ConfigurationSerializable {
    public String notAllowed;
    public Config(Map<String, Object> m){
        this.notAllowed = "You are not allowed to do that here!";
        deserialize(m, this);
    }

    public static void deserialize(Map<String, Object> m, Object o) {
        Field[] fields = o.getClass().getFields();
        for (Field f : fields) {
            if (Modifier.isPublic(f.getModifiers()) && !Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers()) && m.containsKey(f.getName())) {
                try {
                    f.set(o, m.get(f.getName()));
                } catch (IllegalArgumentException | IllegalAccessException | NullPointerException ex2) {
                    if (!(ex2 instanceof NullPointerException)) {
                        ex2.printStackTrace();
                    }
                }
            }
        }
    }

    public static Map<String, Object> serialize(Object o) {
        TreeMap<String, Object> m = new TreeMap<>();
        Field[] fields = o.getClass().getFields();
        for (Field f : fields) {
            if (Modifier.isPublic(f.getModifiers()) && !Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                try {
                    m.put(f.getName(), f.get(o));
                } catch (IllegalArgumentException|IllegalAccessException|NullPointerException ex2) {
                    if (!(ex2 instanceof NullPointerException)) {
                        ex2.printStackTrace();
                    }
                }
            }
        }
        return m;
    }

    public static Config deserialize(Map<String, Object> m) { return new Config(m); }

    public Map<String, Object> serialize() { return serialize(this); }
}
