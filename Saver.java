/**
   Copyright 2015 Treeconverter.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
**/
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
public class Saver extends JDialog{
    private JPanel sizepanel;
    private JTextField textbox1, textbox2, textbox4;
    private JLabel label1, label2;
    private JButton finished_button, browse_button, cancel_button;
    private View thisview;
    private NumberedTreeDialog num_tree_dialog;
    
    public Saver(View view) {
        super.setTitle("SAVE FILE");
        thisview = view;
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int quarter_screen_width = screen_size.width/4;
        int third_screen_height = screen_size.height/3;
        int center_x = screen_size.width/2;
        int center_y = screen_size.height/2;
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
        label1 = new JLabel("FILE NAME (without extension):");
        String file = thisview.returnshortfilename();
        if (file.endsWith(".txt")){
            file = file.substring(0, file.length() - 4);
        }
        textbox1 = new JTextField(file, 10);
        textbox1.setEditable(true);
        textbox1.setEnabled(true);
        label2 = new JLabel("FOLDER:");
        textbox2 = new JTextField(thisview.returnpath(), 10);
        if (!textbox2.getText().equals("outlines")){
            JOptionPane.showMessageDialog(null, "Warning: the original file will be overwritten unless you change the folder name.");
        }
        textbox2.setEditable(true);
        textbox2.setEnabled(true);
        browse_button = new JButton("BROWSE");
        H h = new H();
        browse_button.addActionListener(h);
        textbox4 = new JTextField("", 20);
        textbox4.setForeground(Color.red);
        textbox4.setEditable(false);
        textbox4.setEnabled(false);
        finished_button = new JButton("FINISHED");
        finished_button.addActionListener(h);
        finished_button.setEnabled(true);
        cancel_button = new JButton("CANCEL");
        cancel_button.addActionListener(h);
        cancel_button.setEnabled(true);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label1, constraints);
        sizepanel.add(label1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(textbox1, constraints);
        sizepanel.add(textbox1);
        JLabel dash1 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash1, constraints);
        sizepanel.add(dash1);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(label2, constraints);
        sizepanel.add(label2);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(textbox2, constraints);
        sizepanel.add(textbox2);
        JLabel browse_button_spacer = new JLabel("\t\t");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(browse_button_spacer, constraints);
        sizepanel.add(browse_button_spacer);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(browse_button, constraints);
        sizepanel.add(browse_button);
        JLabel dash5 = new JLabel("------------------------------------------");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(dash5, constraints);
        sizepanel.add(dash5);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(textbox4, constraints);
        sizepanel.add(textbox4);
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

    private class H implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == finished_button) {
                String filename = textbox1.getText();
                if (!filename.endsWith(".txt")){
                    filename += ".txt";
                }
                textbox4.setText(textbox2.getText() + "/" + filename);
                textbox4.setEnabled(true);
                String name = textbox4.getText();
                int confirm = JOptionPane.showConfirmDialog(thisview, "Save " + name + "?");
                if (confirm != JOptionPane.YES_OPTION){
                    return;
                }
                for (Component c : sizepanel.getComponents()){
                    c.setEnabled(false);
                }
                save();
                close();
            } else if (event.getSource() == browse_button){
                String result = "";
                JFileChooser filechooser = new JFileChooser();
                filechooser.setDialogTitle("******* PLEASE CHOOSE A FOLDER TO SAVE IN *********");
                filechooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
                int chosefile = filechooser.showSaveDialog(null);
                if (chosefile == JFileChooser.CANCEL_OPTION) {
                    result = null;
                    thisview.combobox.setSelectedIndex(0);
                    return;
                } else {
                    File pathname = filechooser.getSelectedFile();
                    if (pathname == null || pathname.getName().equals("")) {
                        JOptionPane.showMessageDialog(null, "There was a problem with that foldername.");
                        thisview.combobox.setSelectedIndex(0);
                        return;
                    }
                    result = pathname.getAbsolutePath();
                    textbox2.setText(result);
                    textbox4.setText(result + "/" + textbox1.getText());
                    textbox4.setEnabled(true);
                }
            } else if (event.getSource() == cancel_button){
                close();
            }
        }
    }
    
    public void save(){
        String save_as = textbox1.getText();
        try {
          if (save_as.equals("")){}
        } catch (NullPointerException exc){
           thisview.combobox.setSelectedIndex(0);
           return; // cancelled
        }
        if (save_as.trim().equals("")){
            thisview.combobox.setSelectedIndex(0);
            return;
        }
        if (!save_as.endsWith(".txt")) {
           save_as = save_as + ".txt";
        }
        String foldername = textbox2.getText();
        if (foldername.trim().equals("")){
            return;
        }
        String save_file_name = "";
        if (foldername.endsWith("/")){
            save_file_name = foldername + save_as;
        } else {
            save_file_name = foldername + "/" + save_as;
        }
        try {
            Formatter formatter = new Formatter(save_file_name);
            String[] strings = thisview.writingArea.getText().split("\n");
            for (int index = 0; index < strings.length; ++index) {
                String s = strings[index];
                formatter.format("%s\r\n", s);
            }
            formatter.close();
            strings = null;
            thisview.HAS_ERRORS = false;
            thisview.currentfilename = save_file_name;
            // or combobox.setselectedindex(3), but resummons file dialog
            thisview.remove_tabs();
            thisview.newfilename = "";
            thisview.initarrays();
            thisview.loadfileintoarrays("infilename", thisview.currentfilename);
            thisview.maketree();
            thisview.init_tabs();
            thisview.set_writing_area(thisview.get_text_for_writing_area());
            thisview.clean_up_resources();
            thisview.jlist.setSelectedIndex(0);
            thisview.file_label.setText(" " + thisview.returnshortfilename());
            thisview.closed_file = false;
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "error in save: " + exc.getMessage() + ", " + exc.getCause());
            thisview.combobox.setSelectedIndex(0);
            return;
        }
    }
    
    public void close() {
        super.setVisible(false);
    }
    
    
}
