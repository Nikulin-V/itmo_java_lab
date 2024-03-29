package interfaces;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public interface Commandable extends Serializable {
    String getName();

    String getInfo();

    Object execute(Object inputData, String userID) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
