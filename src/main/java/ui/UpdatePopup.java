package ui;

import classes.commands.Update;
import classes.movie.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import exceptions.*;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

import static ui.MainActivity.credentials;

public class UpdatePopup extends JFrame {
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JSpinner coordinateXSpinner;
    private JSpinner coordinateYSpinner;
    private JSpinner oscarSpinner;
    private JSpinner goldenPalmSpinner;
    private JSpinner budgetSpinner;
    private JComboBox mpaaRatingComboBox;
    private JTextField directorNameTextField;
    private JDatePickerImpl birthdayJDatePicker;
    private JSpinner heightSpinner;
    private JComboBox eyeColorComboBox;
    private JTextField passportIDTextField;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel errorLabel;
    private Movie movie;

    UpdatePopup(Movie movie) {
        this.movie = movie;
        $$$setupUI$$$();
        setContentPane(mainPanel);
        setTitle("База данных фильмов - Изменение фильма");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame addPopupFrame = new UpdatePopup(RandomMovie.generate("vasil1y"));
    }

    private void createUIComponents() {
        JFormattedTextField.AbstractFormatter dateFormatter = new DateComponentFormatter();
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        birthdayJDatePicker = new JDatePickerImpl(datePanel, dateFormatter);

        coordinateXSpinner = new JSpinner(new SpinnerNumberModel(0, null, 279, 1));
        coordinateYSpinner = new JSpinner(new SpinnerNumberModel(0, -279, null, 1));
        oscarSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        goldenPalmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
        budgetSpinner = new JSpinner(new SpinnerNumberModel(0f, 0f, null, 0.001f));
        heightSpinner = new JSpinner(new SpinnerNumberModel(0d, 0d, null, 0.1d));

        cancelButton = new JButton();
        cancelButton.addActionListener((ActionEvent e) -> this.dispose());

        saveButton = new JButton();
        saveButton.addActionListener(this::updateMovie);

        nameTextField = new JTextField();
        coordinateXSpinner = new JSpinner();
        coordinateYSpinner = new JSpinner();
        oscarSpinner = new JSpinner();
        goldenPalmSpinner = new JSpinner();
        budgetSpinner = new JSpinner();
        mpaaRatingComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("General Audiences");
        defaultComboBoxModel1.addElement("Parental Guideness Suggested");
        defaultComboBoxModel1.addElement("Parents Strongly Cautioned");
        defaultComboBoxModel1.addElement("Restricted");
        defaultComboBoxModel1.addElement("No One 17 And Under Admitted");
        mpaaRatingComboBox.setModel(defaultComboBoxModel1);
        directorNameTextField = new JTextField();
        passportIDTextField = new JTextField();
        eyeColorComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Красные");
        defaultComboBoxModel2.addElement("Чёрные");
        defaultComboBoxModel2.addElement("Голубые");
        defaultComboBoxModel2.addElement("Карие");
        eyeColorComboBox.setModel(defaultComboBoxModel2);

        nameTextField.setText(movie.getName());
        coordinateXSpinner.setValue(movie.getCoordinates().getX());
        coordinateYSpinner.setValue(movie.getCoordinates().getY());
        oscarSpinner.setValue(movie.getOscarsCount());
        goldenPalmSpinner.setValue(movie.getGoldenPalmCount());
        budgetSpinner.setValue(movie.getBudget());
        mpaaRatingComboBox.setSelectedIndex(movie.getMpaaRating().ordinal());
        Person director = movie.getDirector();
        directorNameTextField.setText(director.getName());
        Calendar directorBirthday = Calendar.getInstance();
        directorBirthday.setTime(director.getBirthday());
        birthdayJDatePicker.getJFormattedTextField().setValue(directorBirthday);
        heightSpinner.setValue(director.getHeight());
        passportIDTextField.setText(director.getPassportID());
        eyeColorComboBox.setSelectedIndex(director.getEyeColor().ordinal());
    }

    private Object getUpdatedMovie() {
        String name = nameTextField.getText();
        if (name.isBlank())
            return "Название фильма не может быть пустым";

        Coordinates coordinates;
        try {
            coordinates = new Coordinates(
                    (long) coordinateXSpinner.getValue(),
                    (long) coordinateYSpinner.getValue()
            );
        } catch (NotGreatThanException ex) {
            return "Координата Y должна быть больше -230";
        } catch (NullValueException ex) {
            return "Координаты не могут быть пустыми";
        } catch (GreatThanException ex) {
            return "Координата X не может быть больше 279";
        }

        long oscarsCount = (long) oscarSpinner.getValue();
        if (oscarsCount <= 0)
            return "Количество наград\"Оскар\" должно быть больше 0";
        long goldenPalmCount = (long) goldenPalmSpinner.getValue();
        if (goldenPalmCount <= 0)
            return "Количество наград\"Золотая пальма\" должно быть больше 0";

        float budget = (float) budgetSpinner.getValue();
        if (budget <= 0)
            return "Бюджет фильма не может быть меньше или равен 0";

        MpaaRating mpaaRating = MpaaRating.getById(mpaaRatingComboBox.getSelectedIndex());

        String directorName = directorNameTextField.getText();
        if (directorName.isBlank())
            return "Имя режиссёра не может быть пустым";
        Date directorBirthday = ((GregorianCalendar) birthdayJDatePicker.getJFormattedTextField().getValue()).getTime();
        double directorHeight = (double) heightSpinner.getValue();
        String directorPassportID = passportIDTextField.getText();
        classes.movie.Color directorEyeColor = classes.movie.Color.getById(eyeColorComboBox.getSelectedIndex());

        Person director = null;
        try {
            director = new Person(
                    directorName,
                    directorBirthday,
                    directorHeight,
                    directorPassportID,
                    directorEyeColor
            );
        } catch (NotUniqueException e) {
            if (!director.getPassportID().equals(movie.getDirector().getPassportID()))
                return "Номер паспорта должен быть уникальным";
        } catch (BadValueLengthException e) {
            return "Длина номера паспорта должна быть не меньше 7";
        } catch (NotGreatThanException e) {
            return "Рост должен быть больше 0";
        } catch (BlankValueException | NullValueException |
                 GreatThanException ignored) {
            return "Ошибка изменения режиссёра";
        }

        Movie newMovie = null;
        try {
            newMovie = new Movie(movie.getId(), name, coordinates, movie.getCreationDate(), oscarsCount, goldenPalmCount, budget, mpaaRating, director, movie.getUserID());
        } catch (BlankValueException | NullValueException | NotGreatThanException | BadValueLengthException |
                 GreatThanException ignored) {
            return "Ошибка изменения фильма";
        } catch (NotUniqueException e) {
            if (!director.getPassportID().equals(movie.getDirector().getPassportID()))
                return "Номер паспорта должен быть уникальным";
        }
        return newMovie;
    }

    private void updateMovie(ActionEvent e) {
        errorLabel.setVisible(false);
        Object obj = getUpdatedMovie();
        if (obj instanceof Movie) {
            new Update().execute(obj, credentials.getUsername());
            this.dispose();
            new InfoPopup("Изменение фильма", "Фильм успешно изменён", new Color(0, 80, 0), 400, 200);
        } else printErrorMessage((String) obj);
    }

    private void printErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel1.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Название фильма");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(nameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Коордианата X");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coordinateXSpinner.setDoubleBuffered(false);
        coordinateXSpinner.setEnabled(true);
        panel2.add(coordinateXSpinner, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Координата Y");
        panel2.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coordinateYSpinner.setDoubleBuffered(false);
        coordinateYSpinner.setEnabled(true);
        panel2.add(coordinateYSpinner, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Награды \"Оскар\"");
        panel2.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(oscarSpinner, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Награды \"Золотая Пальма\"");
        panel2.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(goldenPalmSpinner, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Бюджет");
        panel2.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(budgetSpinner, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("General Audiences");
        defaultComboBoxModel1.addElement("Parental Guideness Suggested");
        defaultComboBoxModel1.addElement("Parents Strongly Cautioned");
        defaultComboBoxModel1.addElement("Restricted");
        defaultComboBoxModel1.addElement("No One 17 And Under Admitted");
        mpaaRatingComboBox.setModel(defaultComboBoxModel1);
        panel2.add(mpaaRatingComboBox, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Рейтинг MPAA");
        panel2.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Имя режиссёра");
        panel3.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.add(directorNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Дата рождения");
        panel3.add(label9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.add(birthdayJDatePicker, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Рост");
        panel3.add(label10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.add(heightSpinner, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Номер паспорта");
        panel3.add(label11, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Цвет глаз");
        panel3.add(label12, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Красные");
        defaultComboBoxModel2.addElement("Чёрные");
        defaultComboBoxModel2.addElement("Голубые");
        defaultComboBoxModel2.addElement("Карие");
        eyeColorComboBox.setModel(defaultComboBoxModel2);
        panel3.add(eyeColorComboBox, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.add(passportIDTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        mainPanel.add(separator1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 5), new Dimension(-1, 5), new Dimension(-1, 5), 0, false));
        final JLabel label13 = new JLabel();
        Font label13Font = this.$$$getFont$$$(null, Font.BOLD, 16, label13.getFont());
        if (label13Font != null) label13.setFont(label13Font);
        label13.setText("Режиссёр");
        mainPanel.add(label13, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        Font label14Font = this.$$$getFont$$$(null, Font.BOLD, 18, label14.getFont());
        if (label14Font != null) label14.setFont(label14Font);
        label14.setText("Фильм");
        mainPanel.add(label14, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveButton.setRequestFocusEnabled(false);
        saveButton.setText("Сохранить");
        panel4.add(saveButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton.setRequestFocusEnabled(false);
        cancelButton.setText("Отмена");
        panel4.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        errorLabel = new JLabel();
        Font errorLabelFont = this.$$$getFont$$$(null, Font.BOLD, 14, errorLabel.getFont());
        if (errorLabelFont != null) errorLabel.setFont(errorLabelFont);
        errorLabel.setForeground(new Color(-4521984));
        errorLabel.setText("");
        errorLabel.setVisible(false);
        mainPanel.add(errorLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

}
