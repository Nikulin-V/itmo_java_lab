package ui.locale;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundle_en_CA extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] resources = new Object[100][100];

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

        resources[7][0] = "enter_login";
        resources[7][1] = "Login";

        resources[8][0] = "enter_password";
        resources[8][1] = "Password";

        resources[9][0] = "confirm_button";
        resources[9][1] = "OK";


        resources[10][0] = "collection_label";
        resources[10][1] = "Collection";

        resources[11][0] = "chosen_movie_label";
        resources[11][1] = "Chosen film";

        resources[12][0] = "enter_login";
        resources[12][1] = "Login";

        resources[13][0] = "enter_login";
        resources[13][1] = "Login";

        resources[14][0] = "enter_password";
        resources[14][1] = "Password";

        resources[15][0] = "confirm_button";
        resources[15][1] = "OK";

        return resources;
    }
    public static void main(String[] args) {


        ResourceBundle rd = ResourceBundle
                .getBundle("ui.locale.ResourceBundle",
                        new Locale("en", "CA"));
        System.out.println("\nGerman Version");
        System.out.println("String for Title key: "
                + rd.getString("title"));
        System.out.println("String for StopText key: "
                + rd.getString("StopText"));
        System.out.println("String for StartText key: "
                + rd.getString("StartText"));
    }

}
