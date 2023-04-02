package interfaces;

import java.io.Serializable;

public interface Commandable extends Serializable {
    String getName();

    String getInfo();

    String execute(String... args);
}
