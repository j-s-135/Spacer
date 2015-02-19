/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EzTree;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
/**
 *
 * @author Derek
 */
public class NumberedTreeDialog extends JDialog{
    private JPanel sizepanel;
    private JList style_list1, style_list2, style_list3;
    private ButtonGroup buttongroup;
    private JTextField textbox0; //, filename_textbox;
    private listlistener listhandler;
    private JLabel label0, label1, label2, label3; //, filename_label;
    private JButton finished_button;
    public int start_line;
    public String style_result1, style_result2, style_result3;
    public int style_index1, style_index2, style_index3;
    public String FILENAME;
    public View thisview;

    public NumberedTreeDialog(String filename, View view) {
        super.setTitle("NUMBERED TREE");
        FILENAME = filename;
        thisview = view;
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int quarter_screen_width = screen_size.width/4;
        int third_screen_height = screen_size.height/3;
        int center_x = screen_size.width/2;
        int center_y = screen_size.height/2;
        super.setLocation(center_x - quarter_screen_width + 150, center_y - third_screen_height);
        super.setModal(true);
        initcomponents();
        super.pack();
    }

    public void open() {
        super.setVisible(true);
    }

    public void initcomponents() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        sizepanel = new JPanel();
        sizepanel.setBorder(new MatteBorder(10, 10, 10, 10, sizepanel.getBackground()));
        sizepanel.setLayout(layout);
        initjlists();
        label0 = new JLabel("START NUMBERS AT LINE #:");
        textbox0 = new JTextField("1", 10);
        textbox0.setEditable(true);
        textbox0.setEnabled(true);
        label1 = new JLabel("HEADING STYLE");
        label2 = new JLabel("ITEM STYLE 1");
        label3 = new JLabel("ITEM STYLE 2");
        finished_button = new JButton("FINISHED");
        H h = new H();
        finished_button.addActionListener(h);
        finished_button.setEnabled(true);
        JLabel filename_spacer = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(filename_spacer, constraints);
        sizepanel.add(filename_spacer);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label0, constraints);
        sizepanel.add(label0);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(textbox0, constraints);
        sizepanel.add(textbox0);
        JLabel dash0 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash0, constraints);
        sizepanel.add(dash0);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label1, constraints);
        sizepanel.add(label1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(style_list1, constraints);
        sizepanel.add(style_list1);
        JLabel dash1 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash1, constraints);
        sizepanel.add(dash1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label2, constraints);
        sizepanel.add(label2);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(style_list2, constraints);
        sizepanel.add(style_list2);
        JLabel dash2 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash2, constraints);
        sizepanel.add(dash2);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label3, constraints);
        sizepanel.add(label3);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(style_list3, constraints);
        sizepanel.add(style_list3);
        JLabel dash4 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash4, constraints);
        sizepanel.add(dash4);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(finished_button, constraints);
        sizepanel.add(finished_button);
        add(sizepanel, BorderLayout.CENTER);
    }

    public void initjlists() {
        style_list1 = new JList(new String[]{"numeric", "upper-case-alphabetic", "lower-case-alphabetic", "upper-case-roman", "lower-case-roman"});
        style_list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        style_list1.setSelectedIndex(0);
        style_list1.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        listhandler = new listlistener();
        style_list1.addListSelectionListener(listhandler);
        style_list2 = new JList(new String[]{"numeric", "upper-case-alphabetic", "lower-case-alphabetic", "upper-case-roman", "lower-case-roman"});
        style_list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        style_list2.setSelectedIndex(0);
        style_list2.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        style_list2.addListSelectionListener(listhandler);
        style_list3 = new JList(new String[]{"numeric", "upper-case-alphabetic", "lower-case-alphabetic", "upper-case-roman", "lower-case-roman"});
        style_list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        style_list3.setSelectedIndex(0);
        style_list3.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        style_list3.addListSelectionListener(listhandler);
    }

    private class H implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == finished_button) {
                String start = textbox0.getText().trim();
                if (start.matches("[0-9]*")){
                    start_line = Integer.parseInt(start);
                    if (start_line < 1){
                        start_line = 1;
                    }
                } else {
                    start_line = 1;
                }
                style_result1 = style_list1.getSelectedValue().toString();
                style_index1 = style_list1.getSelectedIndex();
                style_result2 = style_list2.getSelectedValue().toString();
                style_index2 = style_list2.getSelectedIndex();
                style_result3 = style_list3.getSelectedValue().toString();
                style_index3 = style_list3.getSelectedIndex();
                close();
            }
        }
    }

    private class listlistener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent event) {
            //int index = style_list.getSelectedIndex();
        }
    }

    public String convert_manager(String s, int line_index, NumberedTreeDialog num_tree_dialog) {
        String result = s;
        if (!s.contains(".")) {
            return s;
        }
        String prefix = s.split("[.]")[0].trim();
        if (!prefix.matches("[0-9]*")) {
            return s;
        }
        int index = 0;
        if (Character.isLetterOrDigit(s.charAt(0))) {
            index = num_tree_dialog.style_index1;
        } else {
            index = num_tree_dialog.style_index2;
            if (line_index - 1 >= thisview.START_NUMBERING_AT_LINE && (line_index - 1 >= 0)){
                int levels_before = 0;
                int last_level = thisview.Levels.get(line_index);
                int start_level = 0;
                for (int count = line_index; count > thisview.START_NUMBERING_AT_LINE; --count){
                    if (thisview.Masterlist.get(count).startsWith(" ")){
                        int this_level = thisview.Levels.get(count);
                        if (this_level < last_level){
                           ++levels_before;
                           last_level = this_level;
                        }
                    } else {
                        start_level = thisview.Levels.get(count + 1);
                        break;
                    }
                }
                if (levels_before % 2 == 0){
                    index = num_tree_dialog.style_index2;
                } else {
                    index = num_tree_dialog.style_index3;
                }
            }
        }
        switch (index) {
            case 0: // numeric
                result = convert_numbered_string(s, "numeric", line_index);
                break;
            case 1: // upper-case-alpha
                result = convert_numbered_string(s, "upper-case-alphabetic", line_index);
                break;
            case 2: // lower-case-alpha
                result = convert_numbered_string(s, "lower-case-alphabetic", line_index);
                break;
            case 3: // upper-case-roman
                result = convert_numbered_string(s, "upper-case-roman", line_index);
                break;
            case 4: // lower-case-roman
                result = convert_numbered_string(s, "lower-case-roman", line_index);
                break;
            default:
                break;
        }
        return result;
    }

    public String convert_numbered_string(String s, String option, int line_index) {
        String[] parts = s.split("[.]");
        String firstpart = parts[0];
        String secondpart = s.substring(s.indexOf(".") + 1);//parts[1];
        String blanks = "";
        for (char c : firstpart.toCharArray()) {
            if (c == ' ') {
                blanks += " ";
            }
        }
        String num = firstpart.trim();
        String alpha = "";
        if (option.equals("numeric")) {
            alpha = num;
        } else if (option.equals("upper-case-alphabetic")) {
            alpha = convert_to_upper_alpha(Integer.parseInt(num));
        } else if (option.equals("lower-case-alphabetic")) {
            alpha = convert_to_lower_alpha(Integer.parseInt(num));
        } else if (option.equals("upper-case-roman")) {
            alpha = convert_to_upper_roman(Integer.parseInt(num));
        } else if (option.equals("lower-case-roman")) {
            alpha = convert_to_lower_roman(Integer.parseInt(num));
        }
        s = blanks + alpha + "." + secondpart;
        return s;
    }

    public String convert_to_upper_alpha(int num) {
        String result = "";
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        if (num <= 26) {
            result = alphabet[num - 1];
        }
        return result;
    }

    public String convert_to_lower_alpha(int num) {
        String result = "";
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        if (num <= 26) {
            result = alphabet[num - 1];
        }
        return result;
    }

    public String convert_to_upper_roman(int num) {
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] tens = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String somenum = "" + num;
        char[] chars = somenum.toCharArray();
        String result = "";
        if (num <= 100) {
            switch (somenum.length()) {
                case 1:
                    result = ones[num];
                    break;
                case 2:
                    result = tens[Integer.parseInt("" + chars[0]) - 1] + ones[(num % 10)];
                    break;
                case 3:
                    result = "C";
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    public String convert_to_lower_roman(int num) {
        String[] ones = {"", "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix"};
        String[] tens = {"x", "xx", "xxx", "xl", "l", "lx", "lxx", "lxxx", "xc", "c"};
        String somenum = "" + num;
        char[] chars = somenum.toCharArray();
        String result = "";
        if (num <= 100) {
            switch (somenum.length()) {
                case 1:
                    result = ones[num];
                    break;
                case 2:
                    result = tens[Integer.parseInt("" + chars[0]) - 1] + ones[(num % 10)];
                    break;
                case 3:
                    result = "c";
                    break;
                default:
                    break;
            }
        }
        return result;
    }
    
    public void close() {
        super.setVisible(false);
    }    
}
