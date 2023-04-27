package interfaces;

import java.io.Serializable;

public interface Commandable extends Serializable {
    String getName();

    String getInfo();

    Object execute(Object inputData);
}
