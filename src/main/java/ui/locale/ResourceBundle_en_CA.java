package ui.locale;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundle_en_CA extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = new Object[15][2];

        resources[0][0] = "greetings_main_title";
        resources[0][1] = "Welcome back, sir!";

        resources[1][0] = "greetings_sub_title";
        resources[1][1] = "This is the best collection manager of whose who remains";

        resources[2][0] = "greetings_sign_in";
        resources[2][1] = "Sign in";

        resources[3][0] = "greetings_sign_up";
        resources[3][1] = "Sign up";


        resources[4][0] = "sign_up_label";
        resources[4][1] = "Registration of new user";

        resources[5][0] = "sign_in_label";
        resources[5][1] = "Log in of new user";

        resources[6][0] = "enter_login";
        resources[6][1] = "Login";


        resources[7][0] = "enter_password";
        resources[7][1] = "Password";

        resources[8][0] = "confirm_button";
        resources[8][1] = "OK";


        resources[9][0] = "collection_label";
        resources[9][1] = "Collection";

        resources[10][0] = "chosen_movie_label";
        resources[10][1] = "Chosen film";

        resources[11][0] = "clear_collection_button";
        resources[11][1] = "Clear collection";

        resources[12][0] = "sort_collection_button";
        resources[12][1] = "Sort";

        resources[13][0] = "update_movie_button";
        resources[13][1] = "Update";

        resources[14][0] = "remove_collection_button";
        resources[14][1] = "Remove";

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
