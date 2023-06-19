package ui.locale;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberCellRenderer extends JLabel implements TableCellRenderer {
    private final NumberFormat format;
    /**
     * Constructs renderer
     *
     * @param locale locale format to get specified data
     */
    public NumberCellRenderer(Locale locale) {
        this.format = NumberFormat.getCurrencyInstance(locale);
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
        if (value instanceof Float floatNumber) {
            setText(format.format(floatNumber));
            return this;
        }
        if (value instanceof Long longNumber) {
            setText(format.format(longNumber));
            return this;
        }
        setText("not found");
        return this;
    }
}