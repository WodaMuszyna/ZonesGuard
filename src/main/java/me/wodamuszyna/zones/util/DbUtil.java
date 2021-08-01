package me.wodamuszyna.zones.util;

import me.wodamuszyna.zones.data.StateFlag;

import java.io.*;
import java.util.Map;

public class DbUtil {

    public static byte[] serializeFlags(Map<StateFlag, StateFlag.State> flags){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(flags);
            oos.close();
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }finally {
            try {
                os.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return os.toByteArray();
    }

    public static Map<StateFlag, StateFlag.State> deserializeFlags(byte[] bytes){
        try
        {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Map<StateFlag, StateFlag.State> flags = (Map<StateFlag, StateFlag.State>) in.readObject();
            in.close();
            return flags;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
