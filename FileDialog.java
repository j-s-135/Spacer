/*
 * EzTree by James Smith 2011
 *    james@ejsweb.biz
 */
package EzTree;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.MatteBorder;
/**
 *
 * @author James Smith
 */
public class Filedialog extends JDialog{
    private JPanel sizepanel;
    private JList filelistarea;
    private JScrollPane filescroll;
    private listlistener showorientation;
    private JLabel filelabel;
    private JButton selectbutton, browsebutton, cancel_button;
    private String[] files;
    public String filechoice = "";
    public String filename = "";
    private boolean is_browseable;

    public Filedialog(boolean browseable) {
        super.setTitle("CHOOSE FILES");
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        int quarter_screen_width = screen_size.width/4;
        int third_screen_height = screen_size.height/3;
        int center_x = screen_size.width/2;
        int center_y = screen_size.height/2;
        super.setLocation(center_x - quarter_screen_width, center_y - third_screen_height);
        super.setModal(true);
        is_browseable = browseable;
        initvariables();
        initcomponents();
        super.setPreferredSize(new Dimension(300, 400));
        super.pack();
    }

    public void open() {
        super.setVisible(true);
    }

    public void initvariables() {
        File folder = new File("outlines");
        if (folder.exists() && folder.isDirectory()) {
            files = folder.list();
        } else {
            return;
        }
    }

    public void initcomponents() {
        sizepanel = new JPanel();
        sizepanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        initjlists();
        filelabel = new JLabel("********************FILE LIST********************");
        filelabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        selectbutton = new JButton("Select file");
        browsebutton = new JButton("Browse");
        H h = new H();
        selectbutton.addActionListener(h);
        browsebutton.addActionListener(h);
        selectbutton.setEnabled(true);
        if (is_browseable){
            browsebutton.setEnabled(true);
        } else {
            browsebutton.setEnabled(false);
        }
        cancel_button = new JButton("Cancel");
        cancel_button.addActionListener(h);
        add(filelabel, BorderLayout.NORTH);
        add(new JScrollPane(filelistarea), BorderLayout.CENTER);
        sizepanel.add(cancel_button);
        sizepanel.add(selectbutton);
        sizepanel.add(browsebutton);
        add(new JScrollPane(sizepanel), BorderLayout.SOUTH);
    }

    public void initjlists() {
        filelistarea = new JList(files);
        filelistarea.setVisibleRowCount(15); // dont set preferred size...cuts off list
        filelistarea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        showorientation = new listlistener();
        filelistarea.addListSelectionListener(showorientation);
    }

    private class H implements ActionListener {

        public void actionPerformed(ActionEvent event) { // change sketchbox texthandler to getselectedvalue
            if (event.getSource() == selectbutton) {
                if (filelistarea.getSelectedIndex() >= 0){
                    filechoice = filelistarea.getSelectedValue().toString();
                    int index = filelistarea.getSelectedIndex();
                    filename = filechoice;
                    close();
                }
            } else if (event.getSource() == browsebutton) {
                filename = "keeplooking";
                close();
            } else if (event.getSource() == cancel_button){
                filename = "";
                close();
            }
        }
    }

    private class listlistener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent event) {
            int index = filelistarea.getSelectedIndex();
        }
    }

    public void close() {
        super.setVisible(false);
    }
}
