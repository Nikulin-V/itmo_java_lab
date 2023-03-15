package classes.xml_manager;

import classes.DataStorage;
import classes.console.TextColor;
import classes.movie.Movies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Model of XML manager. Sub-model of the <code>Route</code>. Singleton to read-write of some class.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class XMLMovieManager {
    private static XMLMovieManager INSTANCE;
    private final Class BASE_CLASS = Movies.class;

    private XMLMovieManager() {
    }

    public static XMLMovieManager getInstance() {
        return INSTANCE != null ? INSTANCE : new XMLMovieManager();
    }

    public Movies readCollectionFromXML() {
        Movies movies;
        try {
            JAXBContext context = JAXBContext.newInstance(BASE_CLASS);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            File file = new File(DataStorage.getCurrentStorageFilePath());
            if (!(file.exists() && !file.isDirectory())) {
                System.out.println(TextColor.cyan("Файл коллекции по-умолчанию не был найден, был создан новый"));
                try {
                    PrintWriter writer = new PrintWriter(file);
                    writer.print("");
                    for (String line : readEmptyXMLCollection()) {
                        writer.print(line + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            movies = (Movies) jaxbUnmarshaller.unmarshal(file);
            return movies;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
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
            System.out.println(TextColor.grey("File not found error"));
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(TextColor.grey("An IO error occurred"));
            e.printStackTrace();
        } catch (JAXBException e) {
            System.out.println(TextColor.grey("Ошибка чтения XML файла"));
            e.printStackTrace();
        }
    }


    public static List<String> readEmptyXMLCollection() throws IOException {
        List<String> strs = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(DataStorage.EMPTY_STORAGE_SAMPLE_FILE_PATH));
        String str;
        while ((str = in.readLine()) != null) {
            strs.add(str);
        }
        return strs;
    }
}