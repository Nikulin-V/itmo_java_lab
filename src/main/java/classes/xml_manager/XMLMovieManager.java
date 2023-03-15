package classes.xml_manager;

import classes.DataStorage;
import classes.console.TextColor;
import classes.movie.Movies;
import exceptions.DangerException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;


/**
 * Model of XML manager. Sub-model of the <code>Route</code>. Singleton to read-write of some class.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class XMLMovieManager {
    private static XMLMovieManager INSTANCE;
    private final Class BASE_CLASS = Movies.class;

    private XMLMovieManager() {}

    public static XMLMovieManager getInstance() {
        return INSTANCE != null ? INSTANCE : new XMLMovieManager();
    }

    public Movies readCollectionFromXML() {
        Movies movies = null;
        try {
            JAXBContext context = JAXBContext.newInstance(BASE_CLASS);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            movies = (Movies) jaxbUnmarshaller.unmarshal(new File(DataStorage.getCurrentStorageFilePath()));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public Movies readCollectionFromXML(String filepath) {
        Movies movies = null;
        File f = new File(filepath);
        if (!(f.exists() && !f.isDirectory())) {
            DataStorage.setCurrentStorageFilePath(DataStorage.DEFAULT_STORAGE_FILE_PATH);
            System.out.println(TextColor.purple("Введённой коллекции не существует. Выбрана коллекция по-умолчанию"));
        } else {
            DataStorage.setCurrentStorageFilePath(filepath);
            System.out.println(TextColor.purple("Коллекция найдена и была выбрана"));
        }
        File file = new File(DataStorage.getCurrentStorageFilePath());
        try {
            JAXBContext context = JAXBContext.newInstance(BASE_CLASS);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            movies = (Movies) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void saveCollectionToXML(Movies movies) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(BASE_CLASS);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = new File(DataStorage.getCurrentStorageFilePath());
            file.createNewFile(); // checks existence of file and create if necessary
            Movies m = new Movies();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(movies, sw);
            sw.close();
            PrintWriter writer = new PrintWriter(DataStorage.getCurrentStorageFilePath());
            writer.print(sw);
            writer.close();
        } catch (FileNotFoundException e) {
            new DangerException("Файл не найден").printMessage();
        } catch (IOException e) {
            new DangerException("Ошибка ввода-вывода").printMessage();

            System.out.println(TextColor.grey("File not found error"));
            e.printStackTrace();
        } catch (JAXBException e) {
            System.out.println(TextColor.grey("Ошибка чтения XML файла"));
            e.printStackTrace();
        }
    }
}
