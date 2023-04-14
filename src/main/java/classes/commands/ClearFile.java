package classes.commands;


import classes.DataStorage;
import classes.abs.NamedCommand;
import classes.console.TextColor;
import interfaces.Commandable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static classes.xml_manager.XMLMovieManager.readEmptyXMLCollection;

public class ClearFile extends NamedCommand implements Commandable {
    @Override
    public String execute(Object inputData) {
        File file = new File(DataStorage.getCurrentStorageFilePath());
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(readEmptyXMLCollection());
            writer.close();
        } catch (IOException e) {
            return Arrays.toString(e.getStackTrace());
        }
        return TextColor.cyan("Файл коллекции был очищен");
    }

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t\t\t\t\t-\tочистить файл коллекции";
    }

}

