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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Derek Smith
 */
public class Yesnobox extends JDialog{
    private String Issue;
    private boolean result = true;
    private JPanel sizepanel;

    public boolean getresult() {
        return result;
    }

    public Yesnobox(String issue) {
        super.setTitle("Yes or no");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        super.setLocation((screensize.width/2) - (screensize.width/4), screensize.height/2);
        super.setModal(true);
        Issue = issue;
        initcomponents();
        super.pack();
    }

    public void initcomponents() {
        sizepanel = new JPanel();
        sizepanel.setPreferredSize(new Dimension(400, 80));
        sizepanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        JLabel label = new JLabel(Issue);
        sizepanel.add(label);
        ButtonGroup buttongroup = new ButtonGroup();
        final JRadioButton yesbutton = new JRadioButton("Yes", true);
        final JRadioButton nobutton = new JRadioButton("No");
        buttongroup.add(yesbutton);
        buttongroup.add(nobutton);
        sizepanel.add(nobutton);
        sizepanel.add(yesbutton);
        JButton button = new JButton("Done");
        button.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        if (yesbutton.isSelected()) {
                            result = true;
                            close();
                        } else if (nobutton.isSelected()) {
                            result = false;
                            close();
                        }
                    }
                });
        sizepanel.add(button);
        add(sizepanel, BorderLayout.CENTER);
    }

    public void open() {
        super.setVisible(true);
    }

    public void close() {
        super.setVisible(false);
    }
}
