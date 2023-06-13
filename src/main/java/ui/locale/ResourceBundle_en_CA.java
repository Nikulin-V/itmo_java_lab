package ui.locale;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundle_en_CA extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = {
                {"table_name", "Name"},
                {"table_coordinates", "Coordinates"},
                {"table_creation_date", "Creation date"},
                {"table_count_oscars", "Oscars count"},
                {"table_count_golden_palms", "Golden Palm's count"},
                {"table_budget", "Budget"},
                {"table_mpaarating", "Mpaarating"},
                {"table_creator", "Creator"},
                {"table_uuid_director", "ID director"},
                {"table_name_director", "Name of director"},
                {"table_birthday_director", "Birthday"},
                {"table_height_director", "Height"},
                {"table_passport_id_director", "Passport ID"},
                {"table_eye_color_director", "Eye color"},

                {"russian_language", "Russian"},
                {"belorussian_language", "Belorussian"},
                {"canadian_language", "Canadian"},

                {"greetings_main_title", "Welcome back, sir!"},
                {"greetings_sub_title", "This is the best collection manager of whose who remains"},
                {"greetings_sign_in", "Sign in"},
                {"greetings_sign_up", "Sign up"},
                {"sign_up_label", "Registration of new user"},
                {"sign_in_label", "Log in of new user"},
                {"enter_login", "Login"},
                {"enter_password", "Password"},
                {"confirm_button", "OK"},
                {"collection_label", "Collection"},
                {"chosen_movie_label", "Chosen film"},
                {"movie_information_label", "Extra information"},
                {"clear_collection_button", "Clear collection"},
                {"sort_collection_button", "Sort"},
                {"update_movie_button", "Update"},
                {"remove_collection_button", "Remove"},
        };


        return resources;
    }

    public static void main(String[] args) {


        ResourceBundle rd = ResourceBundle
                .getBundle("ui.locale.ResourceBundle",
                        new Locale("en", "CA"));
        System.out.println("String for Title key: "
                + rd.getString("greetings_main_title"));


    }
}
