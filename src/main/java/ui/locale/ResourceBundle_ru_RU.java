package ui.locale;

import java.util.ListResourceBundle;

public class ResourceBundle_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"table_name", "Имя"},
                {"table_coordinates", "Координаты"},
                {"table_creation_date", "Дата создания"},
                {"table_count_oscars", "Количество оскаров"},
                {"table_count_golden_palms", "Количество золотых пальм"},
                {"table_budget", "Бюджет"},
                {"table_mpaarating", "Возрастной рейтинг"},
                {"table_creator", "Создатель"},
                {"table_uuid_director", "ИД режиссёра"},
                {"table_name_director", "Имя режиссёра"},
                {"table_birthday_director", "Дата рождения"},
                {"table_height_director", "Рост"},
                {"table_passport_id_director", "Паспорт"},
                {"table_eye_color_director", "Цвет глаз"},

                {"russian_language", "Русский"},
                {"belorussian_language", "Белорусский"},
                {"canadian_language", "Канадский английский"},

                {"greetings_main_title", "Добро пожаловать!"},
                {"greetings_sub_title", "Это пожалуй лучшее приложение для кино-хранилища, из оставшихся..."},
                {"greetings_sign_in", "Войти"},
                {"greetings_sign_up", "Зарегистрироваться"},
                {"sign_up_label", "Регистрация нового пользователя"},
                {"sign_in_label", "Вход для пользователя"},
                {"enter_login", "Логин"},
                {"enter_password", "Пароль"},
                {"confirm_button", "По рукам, впусти меня уже"},
                {"collection_label", "Коллекция"},
                {"chosen_movie_label", "Выбранный фильм"},
                {"movie_information_label", "Дополнительная информация"},
                {"clear_collection_button", "Очистить коллекцию"},
                {"sort_collection_button", "Сортировать"},
                {"update_movie_button", "Обновить"},
                {"remove_collection_button", "Удалить"},
        };

        return resources;
    }
}
