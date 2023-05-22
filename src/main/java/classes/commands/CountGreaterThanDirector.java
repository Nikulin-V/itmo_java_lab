package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountGreaterThanDirector extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <person_name>\t\t-\tвывести количество элементов, значение поля director которых больше заданного";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        String[] arg = (String[]) inputData;
        if (arg != null) {
            String referenceDirector = arg[0];
            List<String> directorsList = new ArrayList<>();
            for (Movie movie : new CollectionManager().getCollection()) {
                directorsList.add(movie.getDirector().getName());
            }
            directorsList.add(referenceDirector);
            directorsList.sort(Comparator.naturalOrder());
            int count = 0;
            for (String director : directorsList) {
                if (!director.equals(referenceDirector))
                    count++;
                else break;
            }
            return new Response(0).setData(TextColor.cyan("Число фильмов, удовлетворяющих условию: "
                    + count));
        }
        return new Response(1).setData(TextColor.yellow("Неверное количество аргументов для этой команды\n"
                + "Введите имя режиссёра без пробелов"));
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
