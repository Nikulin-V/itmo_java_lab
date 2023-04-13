package interfaces;

import java.io.Serializable;

public interface Commandable extends Serializable {
    boolean needInput = false;
    boolean hasTransferData = false;
    String getName();

    String getInfo();

    String execute(Object inputData);

    default boolean isNeedInput() {
        return needInput;
    }

    default boolean hasTransferData() {
        return hasTransferData;
    }
}
