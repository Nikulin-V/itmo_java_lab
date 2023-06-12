package ui.locale;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundle_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = new Object[15][2];

        resources[0][0] = "greetings_main_title";
        resources[0][1] = "Добро пожаловать!";

        resources[1][0] = "greetings_sub_title";
        resources[1][1] = "Это пожалуй лучшее приложение для кино-хранилища, из оставшихся...";

        resources[2][0] = "greetings_sign_in";
        resources[2][1] = "Войти";

        resources[3][0] = "greetings_sign_up";
        resources[3][1] = "Зарегистрироваться";


        resources[4][0] = "sign_up_label";
        resources[4][1] = "Регистрация нового пользователя";

        resources[5][0] = "sign_in_label";
        resources[5][1] = "Вход для пользователя";

        resources[6][0] = "enter_login";
        resources[6][1] = "Логин";

        resources[7][0] = "enter_password";
        resources[7][1] = "Пароль";

        resources[8][0] = "confirm_button";
        resources[8][1] = "Окей";


        resources[9][0] = "collection_label";
        resources[9][1] = "Коллекция";

        resources[10][0] = "chosen_movie_label";
        resources[10][1] = "Выбранный фильм";

        resources[11][0] = "clear_collection_button";
        resources[11][1] = "Очистить коллекцию";

        resources[12][0] = "sort_collection_button";
        resources[12][1] = "Сортировать";

        resources[13][0] = "update_movie_button";
        resources[13][1] = "Обновить";

        resources[14][0] = "remove_collection_button";
        resources[14][1] = "Удалить";
        
        return resources;
    }
}
