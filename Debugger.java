/*
 * EzTree by James Smith 2011
 *    james@ejsweb.biz
 */
package EzTree;
import java.awt.*;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author James Smith
 */
public class Debugger extends JDialog{
    private String Message;
    private JPanel sizepanel;
    private JTextArea textarea;
    private JButton close_button, next_button, previous_button;
    private Map<Integer, String> boxmap;
    private int current_box = -1;
    private View target;
    private JTextArea target_textarea;
    private ArrayList<Box> boxes;

    public Debugger(String message)
    {
        super.setTitle("ERRORS");
        super.setLocation(0, 0);
        Message = message;
        initcomponents();
        super.pack();
        boxmap = new HashMap<Integer, String>();
        boxes = new ArrayList<Box>();
    }

    public void initcomponents(){
        sizepanel = new JPanel();
        sizepanel.setPreferredSize(new Dimension(530, 500));
        sizepanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        textarea = new JTextArea();
        textarea.setPreferredSize(new Dimension(510, 400));
        textarea.append(Message);
        sizepanel.add(new JScrollPane(textarea));
        close_button = new JButton("CLOSE");
        close_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                close();
            }});
        sizepanel.add(close_button);
        next_button = new JButton("HIGHLIGHT ERRORS");
        next_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                highlight_errors();
            }
        });
        sizepanel.add(next_button);
        previous_button = new JButton("HIDE ERRORS");
        previous_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                hide_errors();
            }
        });
        sizepanel.add(previous_button);
        add(sizepanel, BorderLayout.CENTER);
    }

    public void open(){
        super.setVisible(true);
    }

    public boolean is_open(){
        return (this.isVisible());
    }
    
    public void set_boxes(Map<Integer, String> m, View v){
        try{
           Iterator<Integer> it = m.keySet().iterator();
           while (it.hasNext()){
               int index = it.next();
               String s = m.get(index);
               boxmap.put(index, s);
           }
           target = v;
        } catch (Exception exc){
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }
    
    public void highlight_errors(){
        target_textarea = target.writingArea;
        int first_box_position = 0;
        for (int count = 0; count < boxmap.size(); ++count){
            int current_box = count;
            Iterator<Integer> it = boxmap.keySet().iterator();
            int index = 0;
            int iteration = 0;
            while (it.hasNext()){
                index = it.next();
                ++iteration;
                if (iteration > current_box){
                    break;
                }
            }
            int current_line_number = index;
            int current_offset = -1;
            try{
               current_offset = target_textarea.getLineStartOffset(current_line_number);
               String current_line = boxmap.get(index);
            } catch (Exception exc){
               JOptionPane.showMessageDialog(null, "error 1");
            }
            int original_caret_position = target_textarea.getCaretPosition();
            int new_caret_position = current_offset;
            if (count == 0){
                first_box_position = new_caret_position;
            }
            target_textarea.setCaretPosition(new_caret_position);
            Box borderbox = Box.createHorizontalBox();
            borderbox.setFocusable(false);
            borderbox.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));
            borderbox.setSize(new Dimension(700, 17));
            FontMetrics measure = target_textarea.getFontMetrics(target_textarea.getFont());
            int fontheight = measure.getHeight();
            try{
               borderbox.setLocation(0, target_textarea.getLineOfOffset(new_caret_position) * fontheight);
            } catch (Exception exc){
               JOptionPane.showMessageDialog(null, "error: " + exc.getMessage());
            }
            borderbox.setVisible(true);
            boxes.add(borderbox);
            target_textarea.add(borderbox);
        }
        target_textarea.update(target.getGraphics());
        target.update(target.getGraphics());
        target_textarea.setCaretPosition(first_box_position);
    }
    
    public void hide_errors(){
        remove_boxes();
        target_textarea.update(target.getGraphics());
        target.update(target.getGraphics());
    }
    
    public void remove_boxes(){
        for (Box b : boxes){
            target_textarea.remove(b);
        }
        boxes.clear();
    }

    public void add_message(String message){
        Message += "\n";
        Message += message;
        textarea.append("\n");
        textarea.append(message);
    }

    public void clear_message(){
        Message = "";
        textarea.setText("");
    }

    public boolean has_message(){
        return (Message.equals("")? false : true);
    }

    public void close(){
        try{
            boxmap.clear();
            boxes.clear();
            this.setVisible(false);
            this.dispose();
        } catch (Exception exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }
}
