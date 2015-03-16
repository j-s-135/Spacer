
package EzTree;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Formatter;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.util.ArrayList;

/**
 *
 * @author Derek Smith
 */

public class Numberer extends JDialog {

    private JPanel sizepanel;
    private JTextField textbox1, textbox3;
    private JLabel label3, label4, label5;
    private JButton finished_button, browse_button, cancel_button;
    private ButtonGroup buttongroup1, buttongroup2, buttongroup3;
    private JRadioButton yesbutton1, nobutton1, yesbutton2, nobutton2, addbutton, removebutton;
    private View thisview;
    private NumberedTreeDialog num_tree_dialog;

    public Numberer(View view) {
        super.setTitle("ADD/REMOVE LINE NUMBERS");
        thisview = view;
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int quarter_screen_width = screen_size.width / 4;
        int third_screen_height = screen_size.height / 3;
        int center_x = screen_size.width / 2;
        int center_y = screen_size.height / 2;
        super.setLocation(center_x - quarter_screen_width, center_y - third_screen_height);
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
        String file = thisview.returnshortfilename();
        if (file.endsWith(".txt")) {
            file = file.substring(0, file.length() - 4);
        }
        H h = new H();
        label3 = new JLabel("CHANGE TO UNIFORM SPACING?");
        buttongroup1 = new ButtonGroup();
        yesbutton1 = new JRadioButton("yes", false);
        nobutton1 = new JRadioButton("no", true);
        buttongroup1.add(yesbutton1);
        buttongroup1.add(nobutton1);
        RadioHandler radio_handler = new RadioHandler();
        yesbutton1.addItemListener(radio_handler);
        nobutton1.addItemListener(radio_handler);
        buttongroup2 = new ButtonGroup();
        yesbutton2 = new JRadioButton("yes", false);
        nobutton2 = new JRadioButton("no", true);
        buttongroup2.add(yesbutton2);
        buttongroup2.add(nobutton2);
        yesbutton2.addItemListener(radio_handler);
        nobutton2.addItemListener(radio_handler);
        buttongroup3 = new ButtonGroup();
        addbutton = new JRadioButton("add line numbers", false);
        removebutton = new JRadioButton("remove line numbers", false);
        buttongroup3.add(addbutton);
        buttongroup3.add(removebutton);
        addbutton.addItemListener(radio_handler);
        removebutton.addItemListener(radio_handler);
        addbutton.setEnabled(false);
        removebutton.setEnabled(false);
        label4 = new JLabel("AMOUNT OF INDENTATION SPACES");
        textbox3 = new JTextField("5", 10);
        textbox3.setEditable(true);
        textbox3.setEnabled(false);
        label5 = new JLabel("ADD/REMOVE LINE NUMBERS?");
        finished_button = new JButton("FINISHED");
        finished_button.addActionListener(h);
        finished_button.setEnabled(true);
        cancel_button = new JButton("CANCEL");
        cancel_button.addActionListener(h);
        cancel_button.setEnabled(true);
        JLabel warning_label = new JLabel("Use this tool with caution.");
        warning_label.setForeground(Color.red);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(warning_label, constraints);
        sizepanel.add(warning_label);
        JLabel dash1 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash1, constraints);
        sizepanel.add(dash1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label3, constraints);
        sizepanel.add(label3);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        layout.setConstraints(yesbutton1, constraints);
        sizepanel.add(yesbutton1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(nobutton1, constraints);
        sizepanel.add(nobutton1);
        JLabel dash3 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash3, constraints);
        sizepanel.add(dash3);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label4, constraints);
        sizepanel.add(label4);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(textbox3, constraints);
        sizepanel.add(textbox3);
        JLabel dash4 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash4, constraints);
        sizepanel.add(dash4);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label5, constraints);
        sizepanel.add(label5);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        layout.setConstraints(yesbutton2, constraints);
        sizepanel.add(yesbutton2);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(nobutton2, constraints);
        sizepanel.add(nobutton2);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        layout.setConstraints(addbutton, constraints);
        sizepanel.add(addbutton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(removebutton, constraints);
        sizepanel.add(removebutton);
        JLabel dash5 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash5, constraints);
        sizepanel.add(dash5);
        JLabel finished_button_spacer = new JLabel("\t\t");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(finished_button_spacer, constraints);
        sizepanel.add(finished_button_spacer);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(finished_button, constraints);
        sizepanel.add(finished_button);
        JLabel cancel_button_spacer = new JLabel("\t\t");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(cancel_button_spacer, constraints);
        sizepanel.add(cancel_button_spacer);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(cancel_button, constraints);
        sizepanel.add(cancel_button);
        add(sizepanel, BorderLayout.CENTER);
    }

    private class RadioHandler implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            if (event.getSource().equals(yesbutton1) && yesbutton1.isSelected()) {
                if (thisview.HAS_ERRORS) {
                    JOptionPane.showMessageDialog(null, "Even spacing is not allowed until you correct the errors in your file.");
                    nobutton1.setSelected(true);
                    return;
                }
                textbox3.setEnabled(true);
                return;
            } else if (event.getSource().equals(nobutton1) && nobutton1.isSelected()) {
                textbox3.setEnabled(false);
                return;
            } else if (event.getSource().equals(yesbutton2) && yesbutton2.isSelected()) {
                addbutton.setEnabled(true);
                removebutton.setEnabled(true);
            } else if (event.getSource().equals(addbutton) && addbutton.isSelected()) {
                if (thisview.HAS_ERRORS) {
                    JOptionPane.showMessageDialog(null, "Tree numbering is not allowed until you correct all the errors in your file.");
                    nobutton2.setSelected(true);
                    return;
                }
                String filename = thisview.returnpath() + "/" + thisview.returnshortfilename();
                num_tree_dialog = new NumberedTreeDialog(filename, thisview);
                num_tree_dialog.open();
                int maxlines = thisview.writingArea.getLineCount() - 1;
                if (maxlines < 0) {
                    maxlines = 0;
                }
                if (num_tree_dialog.start_line - 1 <= maxlines) {
                    thisview.START_NUMBERING_AT_LINE = num_tree_dialog.start_line - 1;
                    if (thisview.writingArea.getText().split("\n")[thisview.START_NUMBERING_AT_LINE].startsWith(" ")) {
                        JOptionPane.showMessageDialog(null, "To number entire file, must start first number at an unindented line.");
                        num_tree_dialog.close();
                        nobutton2.setSelected(true);
                        //thisview.START_NUMBERING_AT_LINE = 0;
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Starting line number too high.");
                    thisview.START_NUMBERING_AT_LINE = 0;
                }
                return;
            } else if (event.getSource().equals(removebutton) && removebutton.isSelected()) {
                if (thisview.HAS_ERRORS) {
                    JOptionPane.showMessageDialog(null, "Number functions are not allowed until you correct all the errors in your file.");
                    nobutton2.setSelected(true);
                    return;
                }
                String filename = thisview.returnpath() + "/" + thisview.returnshortfilename();
                return;
            } else if (event.getSource().equals(nobutton2) && nobutton2.isSelected()) {
                addbutton.setEnabled(false);
                removebutton.setEnabled(false);
                return;
            }
        }
    }

    private class H implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == finished_button) {
                for (Component c : sizepanel.getComponents()) {
                    c.setEnabled(false);
                }
                save();
                close();
            } else if (event.getSource() == cancel_button) {
                close();
            }
        }
    }

    public void save() {
        thisview.remove_tabs();
        thisview.initarrays();
        thisview.loadstringintoarrays(thisview.writingArea.getText());
        thisview.maketree();
        thisview.init_tabs();
        thisview.set_writing_area(thisview.get_text_for_writing_area());
        thisview.clean_up_resources();
        thisview.jlist.setSelectedIndex(0);
        thisview.file_label.setText("" + thisview.returnshortfilename());
        thisview.closed_file = false;
        try {
            boolean change_to_even_spacing = yesbutton1.isSelected();
            boolean set_new_spacing = false;
            int new_spacing = 5;
            if (change_to_even_spacing) {
                String new_spacing2 = textbox3.getText();
                String new_spacing3 = new_spacing2.trim();
                char[] chars = new_spacing3.toCharArray();
                boolean all_numbers = true;
                for (char c : chars) {
                    if (!Character.isDigit(c)) {
                        all_numbers = false;
                    }
                }
                if (all_numbers) {
                    set_new_spacing = true;
                    new_spacing = Integer.parseInt(new_spacing3);
                }
            }
            if (set_new_spacing && thisview.HAS_ERRORS) {
                JOptionPane.showMessageDialog(null, "Uniform spacing will not be allowed until you correct all errors in your file.");
                set_new_spacing = false;
            }
            if (set_new_spacing) {
                String overwrite = "";
                Thread.sleep(3000);
                String[] strings2 = thisview.writingArea.getText().split("\n");
                for (int index = 0; index < strings2.length; ++index) {
                    String s = strings2[index];
                    char[] chars = s.toCharArray();
                    String blanks = "";
                    for (int count = 0; count < chars.length; ++count) {
                        if (chars[count] == ' ') {
                            blanks += " ";
                        } else {
                            break;
                        }
                    }
                    int blank_count = blanks.length();
                    if (blank_count != 0) {
                        String secondpart = s.trim();
                        int new_blank_count = 0;
                        try {
                            new_blank_count = (thisview.Levels.get(index) - 1) * new_spacing;
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(null, "error with levels: " + exc.getMessage());
                        }
                        blanks = "";
                        for (int count = 0; count < new_blank_count; ++count) {
                            blanks += " ";
                        }
                        s = blanks + secondpart;
                    }
                    overwrite += String.format("%s\r\n", s);
                }
                thisview.writingArea.setText(overwrite);
                thisview.remove_tabs(); 
                thisview.initarrays();
                thisview.loadstringintoarrays(overwrite);
                thisview.maketree(); 
                thisview.init_tabs();
                thisview.set_writing_area(thisview.get_text_for_writing_area());
                thisview.clean_up_resources();
                thisview.jlist.setSelectedIndex(0);
                thisview.file_label.setText(" " + thisview.returnshortfilename()); 
                thisview.closed_file = false;
            }
            boolean and_numberless_version = removebutton.isSelected();
            if (and_numberless_version && thisview.HAS_ERRORS) {
                JOptionPane.showMessageDialog(null, "Number functions will not be allowed until you correct all errors in your file.");
                and_numberless_version = false;
            }
            if (and_numberless_version == true) {
                String alltext = thisview.writingArea.getText();
                alltext = remove_tree_numbers(alltext);
                thisview.writingArea.setText(alltext);
            }
            boolean and_numbered_headings_version = yesbutton2.isSelected() && addbutton.isSelected();
            if (and_numbered_headings_version && thisview.HAS_ERRORS) {
                JOptionPane.showMessageDialog(null, "Automatic numbering will not be allowed until you correct all errors in your file.");
                and_numbered_headings_version = false;
            }
            if (and_numbered_headings_version == true) {
                boolean over_26 = false;
                boolean over_100 = false;
                for (String s : thisview.Numbered_tree) {
                    if (!s.contains(".")) {
                        continue;
                    }
                    String prefix = s.split("[.]")[0].trim();
                    if (!prefix.matches("[0-9]*")) {
                        continue;
                    }
                    String[] parts = s.split("[.]");
                    String firstpart = parts[0];
                    String somenum = firstpart.trim();
                    int num = Integer.parseInt(somenum);
                    if (num > 26) {
                        over_26 = true;
                    }
                    if (num > 100) {
                        over_100 = true;
                    }
                }
                boolean within_bounds = true;
                if (over_26 && (num_tree_dialog.style_result1.contains("alpha") || num_tree_dialog.style_result2.contains("alpha"))) {
                    within_bounds = false;
                    JOptionPane.showMessageDialog(null, "Your outline has numbers above 26. \nAlphabetic numbering not possible.");
                } else if (over_100 && (num_tree_dialog.style_result1.contains("roman") || num_tree_dialog.style_result2.contains("roman"))) {
                    within_bounds = false;
                    JOptionPane.showMessageDialog(null, "Your outline has numbers over 100. \nNot possible until a future update of this program.");
                }
                if (within_bounds) {
                    String overwrite = "";
                    for (int count = 0; count < thisview.Numbered_tree.size(); ++count) {
                        String line = thisview.Numbered_tree.get(count);
                        line = num_tree_dialog.convert_manager(line, count, num_tree_dialog);
                        overwrite += String.format("%s\r\n", line);
                    }
                    thisview.writingArea.setText(overwrite);
                }
            }
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "error in save: " + exc.getMessage() + ", " + exc.getCause());
            thisview.combobox.setSelectedIndex(0);
            return;
        }
    }

    public String remove_tree_numbers(String txt) {
        try {
            String[] textarray = txt.split("\n");
            ArrayList<String> textarray2 = new ArrayList<String>();
            for (String t : textarray) {
                String line = t;
                line = line.replace("\r", "").replace("\n", "");
                if (line.contains(".")) { //.indexOf(".") >= 0) {
                    String prefix = line.split("[\\.]")[0].trim(); //.replace(" ", "").replace("\r", "").replace("\n", "");
                    if (prefix.matches("[0-9]+") || prefix.matches("[a-zA-Z]{1}") || prefix.matches("[ivxlcIVXLC]+")) {
                        String part1 = line.split(prefix)[0];
                        String part2 = line.substring(line.indexOf(".") + 1);
                        part2 = part2.trim();
                        line = part1 + part2;
                    }
                }
                textarray2.add(line);
            }
            String text = "";
            for (String t : textarray2) {
                text += t + "\r\n";
            }
            return text;
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "problem in remove tree numbers: " + exc);
            return txt;
        }
    }

    public void close() {
        super.setVisible(false);
    }
}
