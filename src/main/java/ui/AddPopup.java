package ui;

import classes.movie.Coordinates;
import classes.movie.Movie;
import classes.movie.MpaaRating;
import classes.movie.Person;
import classes.sql_managers.SQLManager;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class AddPopup extends JFrame {
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
    private JButton saveButton;
    private JButton cancelButton;
    private JTextField passportIDTextField;
    private JLabel errorLabel;

    AddPopup() {
        $$$setupUI$$$();
        setContentPane(mainPanel);
        setTitle("База данных фильмов - Создание фильма");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame addPopupFrame = new AddPopup();
    }

    private void createUIComponents() {
        DateComponentFormatter dateFormatter = new DateComponentFormatter();
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
        saveButton.addActionListener(this::addMovie);
    }

    private Object createMovie() {
        String name = nameTextField.getText();
        if (name.isBlank())
            return "Название фильма не может быть пустым";

        Coordinates coordinates;
        try {
            coordinates = new Coordinates(
                    (int) coordinateXSpinner.getValue(),
                    (int) coordinateYSpinner.getValue()
            );
        } catch (NotGreatThanException ex) {
            return "Координата Y должна быть больше -230";
        } catch (NullValueException ex) {
            return "Координаты не могут быть пустыми";
        } catch (GreatThanException ex) {
            return "Координата X не может быть больше 279";
        }

        long oscarsCount = (int) oscarSpinner.getValue();
        if (oscarsCount <= 0)
            return "Количество наград\"Оскар\" должно быть больше 0";
        long goldenPalmCount = (int) goldenPalmSpinner.getValue();
        if (goldenPalmCount <= 0)
            return "Количество наград\"Золотая пальма\" должно быть больше 0";

        float budget = (float) budgetSpinner.getValue();
        if (budget <= 0)
            return "Бюджет фильма не может быть меньше или равен 0";

        MpaaRating mpaaRating = MpaaRating.getById(mpaaRatingComboBox.getSelectedIndex());

        String directorName = directorNameTextField.getText();
        if (directorName.isBlank())
            return "Имя режиссёра не может быть пустым";
        Date directorBirthday = (Date) birthdayJDatePicker.getModel().getValue();
        double directorHeight = (double) heightSpinner.getValue();
        String directorPassportID = passportIDTextField.getText();
        classes.movie.Color directorEyeColor = classes.movie.Color.getById(eyeColorComboBox.getSelectedIndex());

        Person director;
        try {
            director = new Person(
                    directorName,
                    directorBirthday,
                    directorHeight,
                    directorPassportID,
                    directorEyeColor
            );
        } catch (NotUniqueException e) {
            return "Номер паспорта должен быть уникальным";
        } catch (BadValueLengthException e) {
            return "Длина номера паспорта должна быть не меньше 7";
        } catch (NotGreatThanException e) {
            return "Рост должен быть больше 0";
        } catch (BlankValueException | NullValueException |
                 GreatThanException ignored) {
            return "Ошибка создания режиссёра";
        }

        try {
            return new Movie(name, coordinates, oscarsCount, goldenPalmCount, budget, mpaaRating, director, MainActivity.credentials.getUsername());
        } catch (NotUniqueException e) {
            return "Номер паспорта должен быть уникальным";
        } catch (BlankValueException | NullValueException | NotGreatThanException | BadValueLengthException |
                 GreatThanException ignored) {
            return "Ошибка создания фильма";
        }
    }

    private void addMovie(ActionEvent e) {
        errorLabel.setVisible(false);
        Object obj = createMovie();
        if (obj instanceof Movie) {
            SQLManager.insertMovie((Movie) obj);
            this.dispose();
            new InfoPopup("Создание фильма", "Фильм добавлен успешно", new Color(0, 80, 0), 400, 200);
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Название фильма");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameTextField = new JTextField();
        panel1.add(nameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Коордианата X");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coordinateXSpinner.setDoubleBuffered(false);
        coordinateXSpinner.setEnabled(true);
        panel1.add(coordinateXSpinner, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Координата Y");
        panel1.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        coordinateYSpinner.setDoubleBuffered(false);
        coordinateYSpinner.setEnabled(true);
        panel1.add(coordinateYSpinner, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Награды \"Оскар\"");
        panel1.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(oscarSpinner, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Награды \"Золотая Пальма\"");
        panel1.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(goldenPalmSpinner, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Бюджет");
        panel1.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(budgetSpinner, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mpaaRatingComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("General Audiences");
        defaultComboBoxModel1.addElement("Parental Guideness Suggested");
        defaultComboBoxModel1.addElement("Parents Strongly Cautioned");
        defaultComboBoxModel1.addElement("Restricted");
        defaultComboBoxModel1.addElement("No One 17 And Under Admitted");
        mpaaRatingComboBox.setModel(defaultComboBoxModel1);
        panel1.add(mpaaRatingComboBox, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Рейтинг MPAA");
        panel1.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Имя режиссёра");
        panel2.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        directorNameTextField = new JTextField();
        panel2.add(directorNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Дата рождения");
        panel2.add(label9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(birthdayJDatePicker, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Рост");
        panel2.add(label10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(heightSpinner, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Номер паспорта");
        panel2.add(label11, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Цвет глаз");
        panel2.add(label12, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eyeColorComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Красные");
        defaultComboBoxModel2.addElement("Чёрные");
        defaultComboBoxModel2.addElement("Голубые");
        defaultComboBoxModel2.addElement("Карие");
        eyeColorComboBox.setModel(defaultComboBoxModel2);
        panel2.add(eyeColorComboBox, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passportIDTextField = new JTextField();
        panel2.add(passportIDTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveButton.setRequestFocusEnabled(false);
        saveButton.setText("Сохранить");
        panel3.add(saveButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton.setRequestFocusEnabled(false);
        cancelButton.setText("Отмена");
        panel3.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
