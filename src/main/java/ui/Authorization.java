package ui;


import classes.Response;
import classes.UserCredentials;
import classes.console.TextColor;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import static ui.Introduction.in;
import static ui.Introduction.out;

public class Authorization extends JFrame {
    private JPanel MainAuthPanel;
    private JButton BackButton;
    private JPanel AuthPanel;
    private JLabel LoginLabel;
    private JLabel PasswordLabel;
    private JTextField LoginField;
    private JPasswordField PasswordField;
    private JButton AuthButton;
    private JLabel ErrorLabel;

    public Authorization() {
        $$$setupUI$$$();
        setContentPane(MainAuthPanel);
        setTitle("База данных фильмов - Авторизация");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Authorization authorizationFrame = new Authorization();
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
        MainAuthPanel = new JPanel();
        MainAuthPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        MainAuthPanel.setBackground(new Color(-16729671));
        AuthPanel = new JPanel();
        AuthPanel.setLayout(new GridLayoutManager(6, 1, new Insets(10, 10, 10, 10), -1, 3));
        AuthPanel.setBackground(new Color(-16729671));
        AuthPanel.setForeground(new Color(-1));
        MainAuthPanel.add(AuthPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        AuthPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1)), "Авторизация", TitledBorder.CENTER, TitledBorder.BELOW_TOP, this.$$$getFont$$$(null, Font.BOLD, 14, AuthPanel.getFont()), new Color(-1)));
        LoginLabel = new JLabel();
        LoginLabel.setForeground(new Color(-1));
        LoginLabel.setText("Логин");
        AuthPanel.add(LoginLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PasswordLabel = new JLabel();
        PasswordLabel.setForeground(new Color(-1));
        PasswordLabel.setText("Пароль");
        AuthPanel.add(PasswordLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AuthPanel.add(LoginField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AuthPanel.add(PasswordField, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AuthButton.setBackground(new Color(-1));
        AuthButton.setBorderPainted(true);
        AuthButton.setFocusPainted(false);
        AuthButton.setFocusable(true);
        AuthButton.setForeground(new Color(-16729671));
        AuthButton.setMargin(new Insets(0, 0, 0, 0));
        AuthButton.setSelected(true);
        AuthButton.setText("Войти");
        AuthPanel.add(AuthButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(70, 30), new Dimension(100, 30), new Dimension(200, 30), 0, false));
        ErrorLabel = new JLabel();
        ErrorLabel.setEnabled(true);
        ErrorLabel.setFocusable(false);
        Font ErrorLabelFont = this.$$$getFont$$$(null, Font.BOLD, 12, ErrorLabel.getFont());
        if (ErrorLabelFont != null) ErrorLabel.setFont(ErrorLabelFont);
        ErrorLabel.setForeground(new Color(-4521984));
        ErrorLabel.setOpaque(false);
        ErrorLabel.setText("");
        ErrorLabel.setVisible(true);
        AuthPanel.add(ErrorLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BackButton.setBackground(new Color(-16729671));
        BackButton.setFocusPainted(false);
        BackButton.setForeground(new Color(-1));
        BackButton.setSelected(true);
        BackButton.setText("Назад");
        MainAuthPanel.add(BackButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, 30), new Dimension(80, 30), new Dimension(100, 30), 0, false));
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
        return MainAuthPanel;
    }


    private void createUIComponents() {
        BackButton = new JButton();
        BackButton.addActionListener(this::backButtonPressed);

        AuthButton = new JButton();
        AuthButton.addActionListener(this::authButtonPressed);

        KeyListener enterListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    AuthButton.doClick();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        LoginField = new JTextField();
        LoginField.addKeyListener(enterListener);
        PasswordField = new JPasswordField();
        PasswordField.addKeyListener(enterListener);
    }

    private void authButtonPressed(ActionEvent e) {
        String login = LoginField.getText();
        if (login.isBlank()) {
            printErrorMessage("Введите логин");
            return;
        }
        String password = Arrays.toString(PasswordField.getPassword());
        UserCredentials credentials = new UserCredentials(login, password);
        try {
            out.writeObject(credentials);
            out.flush();
            Response response = (Response) in.readObject();
            if (response.getCode() == 0) {
                // TODO: jokes generator while authorizing
                Introduction.mainActivityWindow = new MainActivity(credentials);
                this.dispose();
            } else printErrorMessage((String) response.getData());
        } catch (ClassNotFoundException error) {
            printErrorMessage(TextColor.yellow("Ошибка авторизации"));
        } catch (IOException ex) {
            printErrorMessage(TextColor.red("Ошибка ввода-вывода"));
        }
    }

    void printErrorMessage(String message) {
        this.ErrorLabel.setText(message);
        this.ErrorLabel.setVisible(true);
    }

    private void backButtonPressed(ActionEvent e) {
        this.dispose();
        new Introduction();
    }
}