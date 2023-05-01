package classes.commands;


import classes.DataStorage;
import classes.Response;
import classes.abs.NamedCommand;
import classes.console.TextColor;
import interfaces.Commandable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static classes.xml_manager.XMLMovieManager.readEmptyXMLCollection;

public class ClearFile extends NamedCommand implements Commandable {
    @Override
    public Response execute(Object inputData) {
        File file = new File(DataStorage.getCurrentStorageFilePath());
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(readEmptyXMLCollection());
        } catch (IOException e) {
            return new Response(1).setData(TextColor.red("Проблема чтением файла коллекции"));
        }
        return new Response(0).setData(TextColor.cyan("Файл коллекции был очищен"));
    }

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t\t\t\t\t-\tочистить файл коллекции";
    }
}

