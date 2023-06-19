package ui;

import javax.swing.table.TableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.sql.Date;
import java.util.Locale;


public class DateCellRenderer extends JLabel implements TableCellRenderer {
    private final DateFormat format;
    /**
     * Constructs renderer
     *
     * @param locale locale format to get specified data
     */
    public DateCellRenderer(Locale locale) {
        this.format = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    }

    /**
     * Returns renderer component
     *
     * @param table      renderable table
     * @param value      value to render
     * @param isSelected flag that indicates wether the cell is selected
     * @param hasFocus   flag that indicates wether the cell has focus
     * @param row        cell's row
     * @param column     cell's column
     *
     * @return cell renderer component
     */
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        if (!(value instanceof Date date)) {
            setForeground(Color.red);
            setBackground(Color.white);
            setText("Table element is not a java.util.Date!");
            return this;
        }
        setText(format.format(date));
        return this;
    }
}
