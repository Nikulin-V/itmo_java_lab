package interfaces;

public interface Commandable {
    String getName();

    String getInfo();

    void execute(String... args);
}
