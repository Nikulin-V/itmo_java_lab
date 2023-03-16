package classes.commands;


import classes.DataStorage;
import classes.NamedCommand;
import classes.console.TextColor;
import interfaces.Commandable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static classes.xml_manager.XMLMovieManager.readEmptyXMLCollection;

public class ClearFile extends NamedCommand implements Commandable {

    @Override
    public void execute(String... args) {
        File file = new File(DataStorage.getCurrentStorageFilePath());
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(readEmptyXMLCollection());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(TextColor.cyan("Файл коллекции был очищен"));
    }

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t-\tочистить файл коллекции";
    }

}

