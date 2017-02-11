/*
 * EzTree by Derek Smith 2011
 *    derekjsmith@mail.com
 */
package EzTree;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Derek Smith
 */
public class View extends JFrame{
    public static View thisview;
    public static JFrame thisframe;
    private final static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int screenwidth = screensize.width;
    private final static int screenheight = screensize.height;
    private final static int framewidth = screenwidth - 200;
    private final static int frameheight = screenheight - 200;
    private final static int location_x = (screenwidth - framewidth)/2;
    private final static int location_y = (screenheight - frameheight)/2;
    private static LinkedList<String> Rawlines; // first file read, sometimes contains formatting code so has to be meticulously trimmed
    private static LinkedList<String> Trimmedlines; // no spaces
    public static LinkedList<String> Keys; // amount of spaces
    public static LinkedList<String> Masterlist; // both the text and key put back together
    private static Map<Integer, String> Tablemap; // the table of contents with numbers added
    private static ArrayList<Integer> Tablemapkeys; // the indexes of the titles
    private static String[] Tableofcontents; // the titles
    public static ArrayList<String> Numbered_tree;
    public static int START_NUMBERING_AT_LINE = 0;
    public static ArrayList<Integer> Levels;
    private static JTextArea jtextarea;
    public static JTextArea writingArea;
    private static TextboxHandler textbox_handler; // custom
    public static Action actionCutText;
    public static Action actionPasteText;
    public static Action actionTextRight;
    public static Action actionTextLeft;
    public static Action actionClosePopup;
    public static JPopupMenu popup_menu;
    public static JMenuItem popup_menu_item1;
    public static JMenuItem popup_menu_item2;
    public static JMenuItem popup_menu_item3;
    public static JMenuItem popup_menu_item4;
    public static JMenuItem popup_menu_item5;
    public static String SELECTED_TEXT = "";
    public static boolean CLICKED_SCREEN_ONE = false;
    public static int SCREEN_ONE_POSITION = 0;
    private static ArrayList<Box> boxes;
    public static JList jlist;
    private static JSplitPane groovepane;
    private static JTabbedPane tabbedpane;
    public static boolean closed_file = false;
    public static JComboBox combobox;
    private static JToolBar top_toolbar;
    public static JTextField textbox, file_label;
    public static JLabel search_label;
    private static JButton search_button, next_button, previous_button, up_button, down_button;
    private static int Masterlistindex = 0;
    private static String search_string = "";
    private static int current_search_index = -1;
    private static ArrayList<Integer> search_results;
    public static String currentfilename = "";
    private static String shortfilename = "";
    public static String newfilename = "";
    private static JTree tree;
    private static DefaultTreeModel treemodel;
    private static DefaultMutableTreeNode rootnode = new DefaultMutableTreeNode("START");
    private static String intro_message = 
            "Spacer version 5.6\n"
            + "by Derek James Smith\n"
            + "email: support@infiniteoutline.com\n"
            + "copyright 2011 treeconverter.com\n"
            + "all rights reserved\n"
            + "--------What it does--------\n" 
            + "Reads and writes .txt files (must resave Microsoft Word, .rtf, or Linux files as .txt files).\n"
            + "Converts outlines to data trees.\n"
            + "Write an outline into the writer area, then view it in the data-tree viewer or the table-of-contents viewer.\n"
            + "--------Disclaimer------\n" 
            + "It is your responsibility to manage your own data.\n"
            + "We assume no liability for data loss.\n"
            + "We also make no guarantees that this program will run without errors.\n"
            + "We assume no responsibility for any computer problems experienced while running this program.";
    private static String intro_message2 = 
            "     Spacer version 5.6\ncopyright 2011 treeconverter.com\n\n     --------What it does--------\n     reads and writes .txt files (must resave .rtf or Word files as .txt files),\n     finds any part of your outline instantly!\n     but, you must organize the outline according to certain rules...\n     the first screen allows you to create, change, or save an outline,\n     select and right-click to quickly rearrange entire sections,\n     the second two screens are for quickly reading an outine,\n     if there are formatting mistakes, they are immediately reported.\n\n     --------Formatting rules--------\n     the far-left headings must have no indentations and must be unique, \n     must indent evenly within each section,\n     basically, just write an outline that has perfectly even spacing,\n     do not use tabs,\n     blank lines are removed.\n\n     --------Example:--------\n     EZ-TREE\n     What it does\n          reads and writes text files\n               only .txt files\n               will not read .rtf or Word files\n               but, just resave them as .txt files\n          helps you instantly find topics in your outline\n          warns you if formatting rules are broken\n     Formatting rules\n          ...";
    private static boolean case_sensitive_search = false;
    private static boolean exact_matches_only_search = false;
    private static Debugger debugger;
    private static boolean MAKING_NEW_FILE = false;
    private static boolean EMPTY_FILE = false;
    public static boolean HAS_ERRORS = false;
    public static int TAB_CLICKED = 0;
    public static int LAST_TAB_CLICKED = 0;
    public static boolean SYNC_TABLE_OF_CONTENTS = true;

    public View() {
        super("------------------------------ Spacer ----------------------------------------");
        thisview = this;
        thisframe = this;
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("starlogo_light_blue.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(framewidth, frameheight);
        setLocation(location_x, location_y);
        setBackground(Color.WHITE);
        init_folder();
        initarrays();
        initcomponents(); // the string array for the jlist had to be made first
        splash_screen();
        setVisible(true);
    }
    
    public void splash_screen(){
        JPanel splashScreen = new JPanel();
        splashScreen.setSize(500, 500);
        splashScreen.setBackground(Color.white);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        splashScreen.setLayout(layout);
        ImageIcon img = new ImageIcon(getClass().getResource("Spacer.png"));
        JLabel imagelabel = new JLabel(img);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(imagelabel, constraints);
        splashScreen.add(imagelabel);
        for (int count = 0; count < 3; ++count){
            JLabel spacer = new JLabel("\t");
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            layout.setConstraints(spacer, constraints);
            splashScreen.add(spacer);
        }
        JLabel instructions_label = new JLabel("to get started, please select 'info' from the main menu");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(instructions_label, constraints);
        splashScreen.add(instructions_label);
        tabbedpane.addTab("splash screen", null, splashScreen, "splash screen");
    }
    
    public void init_folder(){
        File folder = new File("outlines");
        if (!folder.exists() && !folder.isDirectory()){
            folder.mkdir();
        }
    }

    // INITVARIABLES
    public void initarrays() {
        Rawlines = new LinkedList<String>(); // first file read
        Trimmedlines = new LinkedList<String>(); // just the text
        Keys = new LinkedList<String>(); // just the key
        Masterlist = new LinkedList<String>(); // both
        Tablemap = new HashMap<Integer, String>(); // the table of contents as key and heading text
        Tablemapkeys = new ArrayList<Integer>();
        if (MAKING_NEW_FILE == true){
           Tableofcontents = new String[100]; // the titles
        }
        search_results = new ArrayList<Integer>();
        Numbered_tree = new ArrayList<String>();
        Levels = new ArrayList<Integer>();
    }

    // LOAD AND READ FILE
    public boolean loadfileintoarrays(String... folder_file) {
        try {
            String folder = "";
            String filename = "";
            if (folder_file.length == 2){
               folder = folder_file[0];
               filename = folder_file[1];
            } else {
                filename = getfilename();
            }
            if (filename.equals("keeplooking")) {
                String filepath = usefilechooser();
                if (filepath != null) {
                    folder = "infilename";
                    filename = filepath;
                } else {
                    System.exit(1);
                }
            } else if (filename.equals("")){
                return false;
            } else {
                if (folder_file.length != 2){
                   folder = "outlines";
                }
            }
            loadfile(folder, filename);
            currentfilename = filename;
            shortfilename = returnshortfilename();
            maketableofcontents();
            makemasterlist();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problem in loadfileintoarrays " + exception.toString());
            return false;
        }
        return true;
    }

    public String getfilename() {
        String result = "";
        String[] strings = new File("outlines").list();
        boolean keepgoing = true;
        while (keepgoing){
            boolean keepgo = false;
            for (int count = 0; count < strings.length; ++count){
                if (count+1 < strings.length){
                    String s1 = strings[count].toLowerCase();
                    String s2 = strings[count+1].toLowerCase();
                    if (s2.charAt(0) < s1.charAt(0)){
                        String temp = strings[count];
                        strings[count] = strings[count+1];
                        strings[count+1] = temp;
                        keepgo = true;
                    }
                }
            }
            keepgoing = keepgo;
        }
        Filedialog2 filedialog = new Filedialog2(new javax.swing.JFrame(), strings, true, true);
        filedialog.setVisible(true);
        currentfilename = filedialog.filename;
        result = currentfilename;
        return result;
    }

    public String returnlongfilename(){
        String result = returnpath() + "/" + returnshortfilename();
        return result;
    }
    
    public String returnshortfilename(){
        String result = "";
        try{
           File file = new File(currentfilename);
           result = file.getName();
        } catch (Exception exception){}
        return result;
    }

    public String returnpath(){ // folder name
        String result = "";
        try{
           File file = new File(currentfilename);
           if (file.isAbsolute()){
               result = file.getParent();
           } else {
               result = "outlines";
           }
        } catch (Exception exception){}
        return result;
    }
    
    public String usefilechooser() {
        String result = "";
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("******* PLEASE CHOOSE A .TXT FILE *********");
        filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int chosefile = filechooser.showOpenDialog(null);
        if (chosefile == JFileChooser.CANCEL_OPTION) {
            result = null;
        } else {
            File pathname = filechooser.getSelectedFile();
            if (pathname == null || pathname.getName().equals("")) {
                System.exit(1);
            }
            result = pathname.getAbsolutePath();
            currentfilename = result;
        }
        return result;
    }

    public void loadfile(String foldername, String filename) {
        try {
            if (foldername.equalsIgnoreCase("outlines")) {
                File folder = new File(foldername);
                if (folder.exists() && folder.isDirectory()) {
                    if (filename.endsWith(".txt")) {
                        File file = new File(folder, filename);
                        if (file.exists() && file.isFile()) {
                            readfile(file);
                        } else {
                            throw new Exception("File not found");
                        }
                    } else {
                        throw new Exception("Wrong file extension");
                    }
                }
            } else if (foldername.equals("infilename")) {
                if (filename.endsWith(".txt")) {
                    File file = new File(filename);
                    if (file.exists() && file.isFile()) {
                        readfile(file);
                    } else {
                        throw new Exception("File not found");
                    }
                } else {
                    throw new Exception("Wrong file extension");
                }
            } else {
                throw new Exception("outlines folder not found ");
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage() + exception.toString());
            System.exit(1);
        }
    }

    public void readfile(File file) {
        try {
            EMPTY_FILE = false;
            Rawlines.clear();
            Scanner filescan = new Scanner(file);
            while (filescan.hasNextLine()) {
                String s = filescan.nextLine();
                Rawlines.add(s);
            }
            if (filescan != null) {
                filescan.close();
            }
            if (Rawlines.isEmpty()) {
                EMPTY_FILE = true;
                //throw new Exception("Empty file");
            }
            boolean tab_test = false;
            Map<Integer, String> boxmap = new HashMap<Integer, String>();
            String bads = "";
            for (int count = 0; count < Rawlines.size(); ++count){
                String r = Rawlines.get(count);
                if (r.contains("\t")){
                    tab_test = true;
                    boxmap.put(count, r);
                    bads = bads + (count + 1) + ", ";
                }
            }
            String message = "";
            if (tab_test == true){
                HAS_ERRORS = true;
                message = "You have used tabs.\nPlease remove all tabs, they have been marked in red.\nHere are the first ten lines with tabs (counting from 1):\n" + bads + "\n";
                if (debugger != null && debugger.is_open()){
                    debugger.add_message(message);
                    debugger.set_boxes(boxmap, thisview);
                } else if (debugger != null && !debugger.is_open()){
                    debugger.open();
                    debugger.clear_message();
                    debugger.add_message(message);
                    debugger.set_boxes(boxmap, thisview);
                } else if (debugger == null){
                    debugger = new Debugger(message);
                    debugger.open();
                    debugger.set_boxes(boxmap, thisview);
                }
            }
            String empty_test = "";
            for (String r : Rawlines){
                r = r.trim();
                empty_test += r;
            }
            if (empty_test.equals("")){
                EMPTY_FILE = true;
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problem in readfile, " + exception.getMessage());
        }
    }

    public void loadstringintoarrays(String strng) {
        try {
            Rawlines.clear();
            String[] strngs = strng.split("\n");
            for (String s: strngs){
                Rawlines.add(s);
            }
            if (Rawlines.isEmpty()) {
                EMPTY_FILE = true;
                //throw new Exception("Empty file");
            }
            boolean tab_test = false;
            Map<Integer, String> boxmap = new HashMap<Integer, String>();
            String bads = "";
            for (int count = 0; count < Rawlines.size(); ++count) {
                String r = Rawlines.get(count);
                if (r.contains("\t")) {
                    tab_test = true;
                    boxmap.put(count, r);
                    bads = bads + (count + 1) + ", ";
                }
            }
            String message = "";
            if (tab_test == true) {
                HAS_ERRORS = true;
                message = "You have used tabs.\nPlease remove all tabs, they have been marked in red.\nHere are the first ten lines with tabs (counting from 1):\n" + bads + "\n";
                if (debugger != null && debugger.is_open()) {
                    debugger.add_message(message);
                    debugger.set_boxes(boxmap, thisview);
                } else if (debugger != null && !debugger.is_open()) {
                    debugger.open();
                    debugger.clear_message();
                    debugger.add_message(message);
                    debugger.set_boxes(boxmap, thisview);
                } else if (debugger == null) {
                    debugger = new Debugger(message);
                    debugger.open();
                    debugger.set_boxes(boxmap, thisview);
                }
            }
            String empty_test = "";
            for (String r : Rawlines) {
                r = r.trim();
                empty_test += r;
            }
            if (empty_test.equals("")) {
                EMPTY_FILE = true;
            }
            maketableofcontents();
            makemasterlist();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problem in loadstringintoarrays, " + exception.getMessage());
        }
    }

    // finds header lines from outline, makes linenumbers, trims keys and text, stores them separate in tablemap and together in tableofcontents
    public void maketableofcontents() {
        if (EMPTY_FILE){
            //return;
        }
        ArrayList<String> temptable = new ArrayList<String>();
        boolean has_repeats = false;
        int chapternumber = 0;
        char[] chars = new char[300];
        Rawlines.remove("\n");
        Rawlines.remove("\r");
        ArrayList<String> badones = new ArrayList<String>();
        Map<Integer, String> boxmap = new HashMap<Integer, String>();
        int trimmedcount = -1;
        for (String s : Rawlines) {
            if (!s.trim().equals("")){
                ++trimmedcount;
            }
            chars = s.toCharArray();
            if (chars.length == 0) {
                continue;
            }
            if (!(Character.isLetterOrDigit(chars[0]) || Character.isDefined(chars[0]))) {
                continue;
            }
            if (chars[0] == ' ' || chars[0] == '\t') { // rule: first level has no indentation
                continue;
            }
            temptable.add(s);
            //Tableofcontents[chapternumber] = s;
            ++chapternumber;
            if (Tablemap.containsValue(s)){
                has_repeats = true;
                badones.add(s);
                boxmap.put(trimmedcount, s);
            }
            Tablemap.put(chapternumber, s); // creates the option to list each chapter with the chapter number in front of it, but these numbers are one higher than Tableofcontents array index
            Tablemapkeys.add(chapternumber);
        }
        Tableofcontents = new String[temptable.size()];
        for (int count = 0; count < temptable.size(); ++count){
            Tableofcontents[count] = temptable.get(count);
        }
        temptable.clear();
        temptable = null;
        if (has_repeats == true){
            HAS_ERRORS = true;
            String bads = "";
            for (int count = 0; count < (badones.size() < 10? badones.size() : 10); ++count){
                bads += badones.get(count) + "\n";
            }
            String message = "There are repeat headings, screen 2 will not work\n"
                    + "Number of lines with problems: " + badones.size()
                    + "\nRepeat headings have been marked in red."
                    + "\nHere are the first ten repeat headings:\n"
                    + bads;
            if (debugger != null && debugger.is_open()){
                debugger.add_message(message);
                debugger.set_boxes(boxmap, thisview);
            } else if (debugger != null && !debugger.is_open()){
                debugger.open();
                debugger.clear_message();
                debugger.add_message(message);
                debugger.set_boxes(boxmap, thisview);
            } else if (debugger == null){
                debugger = new Debugger(message);
                debugger.open();
                debugger.set_boxes(boxmap, thisview);
            }
        }
    }

    // trims the keys and texts and stores them in Textmap and trimmedlines
    public void makemasterlist() {
        if (EMPTY_FILE){
            //return;
        }
        char[] chars = new char[300];
        for (String s : Rawlines) {
            chars = s.toCharArray();
            StringBuilder stringbuilder = new StringBuilder("");
            if (chars.length == 0) {
                continue;
            }
            int textindex = -1;
            int tabindex = 0;
            for (char c : chars) {
                if (c == ' ') {
                    ++textindex;
                } else if (c == '\t') {
                    ++tabindex;
                    ++textindex;
                } else if (Character.isLetterOrDigit(c) || Character.isDefined(c)) {
                    ++textindex;
                    break;
                }
            }
            if (textindex != -1) {
                if (tabindex > 0) {
                    for (int i = 0; i < tabindex; ++i) {
                        stringbuilder.append("\t");
                    }
                    Keys.add(stringbuilder.toString());
                    Trimmedlines.add(s.substring(textindex));
                    Masterlist.add(s);
                } else {
                    int keyindex = (textindex == 0 ? 0 : textindex); // previously textindex - 1
                    Keys.add(s.substring(0, keyindex));
                    Trimmedlines.add(s.substring(textindex));
                    Masterlist.add(s);
                }
            }
        } // end first loop
    }

    public void maketree() {
        if (EMPTY_FILE){
            return;
        }
        ArrayList<DefaultMutableTreeNode> nodelist = new ArrayList<DefaultMutableTreeNode>();
        for (String s : Trimmedlines) {
            nodelist.add(new DefaultMutableTreeNode(s));
        }
        rootnode.add((DefaultMutableTreeNode) nodelist.get(0));
        if (Numbered_tree != null){
            Numbered_tree.clear();
        } else {
            Numbered_tree = new ArrayList<String>();
        }
        if (Levels != null){
            Levels.clear();
        } else {
            Levels = new ArrayList<Integer>();
        }
        try{
            processtree(-1, 0, nodelist);
        } catch (Exception exc){
            JOptionPane.showMessageDialog(null, "unknown error in process tree: " + exc);
        }
        // heading numbers might have been skipped because they are all written first
        boolean didnt_number_headings = false;
        if (START_NUMBERING_AT_LINE < Numbered_tree.size() && !Character.isDigit(Numbered_tree.get(START_NUMBERING_AT_LINE).charAt(0))){
            String s = Numbered_tree.get(START_NUMBERING_AT_LINE);
            if (!s.contains(".")){
               didnt_number_headings = true;
            } else {
                String num = s.split("[.]")[0].trim();
                boolean all_numbers = true;
                for (char c : num.toCharArray()){
                    if (!Character.isDigit(c)){
                        all_numbers = false;
                    }
                }
                if (!all_numbers){
                    didnt_number_headings = true;
                }
            }
        }
        if (didnt_number_headings){
            int addcount = 1;
            for (int count = START_NUMBERING_AT_LINE; count < Numbered_tree.size(); ++count){
                if (!Numbered_tree.get(count).startsWith(" ")){
                    String line = Numbered_tree.get(count);
                    line = "" + addcount + ". " + line;
                    Numbered_tree.set(count, line);
                    ++addcount;
                }
            }
        }
        Map<Integer, String> boxmap = new HashMap<Integer, String>();
        if (Masterlist.get(0).startsWith(" ")){
            String message = "Your first line should not be indented.\n"
                    + "Even if you have an information area at the top of the file,\n"
                    + "it still must fit into the tree correctly.\n"
                    + "But, you can still make an area at the top with no line-numbers.\n";
            boxmap.put(0, Masterlist.get(0));
            if (debugger != null && debugger.is_open()) {
                debugger.add_message(message);
                debugger.set_boxes(boxmap, thisview);
            } else if (debugger != null && !debugger.is_open()) {
                debugger.clear_message();
                debugger.add_message(message);
                debugger.open();
                debugger.set_boxes(boxmap, thisview);
            } else if (debugger == null) {
                debugger = new Debugger(message);
                debugger.open();
                debugger.set_boxes(boxmap, thisview);
            }
        } else {
            boolean incomplete = false;
            ArrayList<String> badones = new ArrayList<String>();
            int count = 1;
            for (DefaultMutableTreeNode dmtn : nodelist) {
                if (dmtn.getParent() == null) {
                    incomplete = true;
                    int rawcount = -1; // for external text file with untrimmed blank lines
                    for (int r = 0; r < Rawlines.size(); ++r){
                        String trimmedraw = Rawlines.get(r).trim();
                        if (trimmedraw.equals(dmtn.toString())){
                            rawcount = r;
                            break;
                        }
                    }
                    if (!boxmap.containsKey(count)){ // just use 'count' to fix within eztree
                       badones.add("line #" + count + ": " + dmtn.toString());
                       boxmap.put(count - 1, dmtn.toString());
                    }
                }
                ++count;
            }
            if (incomplete == true) {
                HAS_ERRORS = true;
                String bads = "";
                for (count = 0; count < (badones.size() < 10? badones.size() : 10); ++count){
                    bads += badones.get(count) + "\n";
                }
                String message = "Your indentation is uneven.\n"
                    + "The tree view will have missing lines unless you straighten all the text.\n"
                    + "Check the rules on the help menu.\n"
                    + "Uneven lines have been marked in red.\n"
                    + "Number of lines with problems: " + badones.size()
                    + "\nHere are the first ten problems, with line numbers (counting from 1):\n"
                    + bads;
                if (debugger != null && debugger.is_open()){
                    debugger.add_message(message);
                    debugger.set_boxes(boxmap, thisview);
                } else if (debugger != null && !debugger.is_open()){
                    debugger.clear_message();
                    debugger.add_message(message);
                    debugger.open();
                    debugger.set_boxes(boxmap, thisview);
                } else if (debugger == null){
                    debugger = new Debugger(message);
                    debugger.open();
                    debugger.set_boxes(boxmap, thisview);
                }
            }
        }
    }
    
    public void processtree(int thisindex, int nextindex, ArrayList<DefaultMutableTreeNode> nodelist){
        if (EMPTY_FILE){
            return;
        }
        int COUNTER = 0;
        for (int start = thisindex; start < nodelist.size(); ++start){
            thisindex = start;
            nextindex = start + 1;
            if (Keys.size() == nextindex){
                return;
            }
            if (thisindex == -1){ // root node
                rootnode.add((DefaultMutableTreeNode) nodelist.get(0));
                Levels.add(nodelist.get(0).getLevel());
                if (Numbered_tree.size() < START_NUMBERING_AT_LINE){
                    Numbered_tree.add(Trimmedlines.get(0));
                } else {
                    ++COUNTER;
                    Numbered_tree.add(String.format("%d. %s", 1, Trimmedlines.get(0))); 
                }
            } else if (Keys.get(nextindex).length() == Keys.get(thisindex).length()){ // sibling
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode)nodelist.get(thisindex).getParent();
                parent.add((DefaultMutableTreeNode)nodelist.get(nextindex));
                Levels.add(nodelist.get(nextindex).getLevel());
                if (nextindex < START_NUMBERING_AT_LINE){
                    Numbered_tree.add(String.format("%s%s", Keys.get(nextindex), Trimmedlines.get(nextindex)));
                } else {
                    if (parent == rootnode){
                        ++COUNTER;
                        String str = String.format("%d. %s", COUNTER, Trimmedlines.get(nextindex)); 
                        Numbered_tree.add(str);
                    } else {
                        Numbered_tree.add(String.format("%s%d. %s", Keys.get(nextindex), parent.getIndex(nodelist.get(nextindex)) + 1, Trimmedlines.get(nextindex)));
                    }
                }
            } else if (Keys.get(nextindex).length() < Keys.get(thisindex).length()){ // more shallow
                boolean success = false;
                for (int count = thisindex; count >= 0; --count){
                    if (Keys.get(count).length() == Keys.get(nextindex).length()){
                        success = true;
                        DefaultMutableTreeNode parent = (DefaultMutableTreeNode)nodelist.get(count).getParent();
                        parent.add((DefaultMutableTreeNode)nodelist.get(nextindex));
                        Levels.add(nodelist.get(nextindex).getLevel());
                        if (nextindex < START_NUMBERING_AT_LINE){
                            Numbered_tree.add(String.format("%s%s", Keys.get(nextindex), Trimmedlines.get(nextindex)));
                        } else {
                            if (parent == rootnode){
                                ++COUNTER;
                                String str = String.format("%d. %s", COUNTER, Trimmedlines.get(nextindex)); //parent.getIndex(nodelist.get(nextindex)) + 1 - START_NUMBERING_AT_LINE, Trimmedlines.get(nextindex));
                                Numbered_tree.add(str);
                            } else {
                                Numbered_tree.add(String.format("%s%d. %s", Keys.get(nextindex), nodelist.get(nextindex).getParent().getIndex(nodelist.get(nextindex)) + 1, Trimmedlines.get(nextindex)));
                            }
                        }
                        break;
                    }
                }
                if (success == false){
                    // finds afterward
                }
            } else if (Keys.get(nextindex).length() > Keys.get(thisindex).length()){ //child
                nodelist.get(thisindex).add(nodelist.get(nextindex));
                Levels.add(nodelist.get(nextindex).getLevel());
                if (nextindex < START_NUMBERING_AT_LINE){
                    Numbered_tree.add(String.format("%s%s", Keys.get(nextindex), Trimmedlines.get(nextindex)));
                } else {
                    Numbered_tree.add(String.format("%s%d. %s", Keys.get(nextindex), nodelist.get(nextindex).getParent().getIndex(nodelist.get(nextindex)) + 1, Trimmedlines.get(nextindex)));
                }
            }
        }
    }

    public void processtree_recursive(int thisindex, int nextindex, ArrayList<DefaultMutableTreeNode> nodelist) {
        if (EMPTY_FILE) {
            return;
        }
        if (Keys.size() == nextindex){ //(nextindex >= Keys.size() - 1) {
            return;
        }
        if (thisindex == -1) {
            int level = Keys.get(nextindex).length();
            for (int index = 0; index < Trimmedlines.size(); ++index) {
                if (Keys.get(index).length() == level) {
                    rootnode.add((DefaultMutableTreeNode) nodelist.get(index));
                    try{
                       Levels.add(nodelist.get(index).getLevel());
                       if (Numbered_tree.size() < START_NUMBERING_AT_LINE){
                           Numbered_tree.add(Trimmedlines.get(index));
                       } else {
                           Numbered_tree.add(String.format("%d. %s", nodelist.get(index).getParent().getIndex(nodelist.get(index)) + 1 - START_NUMBERING_AT_LINE, Trimmedlines.get(index)));
                       }
                    } catch (Exception exc){
                       // file has heading or spacing errors
                    }
                }
            }
            processtree(0, 1, nodelist);
        } else if (Keys.get(nextindex).length() == Keys.get(thisindex).length()) {
            if (nextindex < Trimmedlines.size() - 1) {
                processtree(nextindex, nextindex + 1, nodelist);
            }
        } else if (Keys.get(nextindex).length() < Keys.get(thisindex).length()) {
            if (nextindex < Trimmedlines.size() - 1) {
                processtree(nextindex, nextindex + 1, nodelist);
            }
        } else if (Keys.get(nextindex).length() > Keys.get(thisindex).length()) {
            int level = Keys.get(nextindex).length(); // custom - how many blanks
            for (int index = thisindex; index < Trimmedlines.size(); ++index) {
                if (Keys.get(index).length() < level && index != thisindex) {
                    break;
                }
                if (Keys.get(index).length() == level) {
                    ((DefaultMutableTreeNode) nodelist.get(thisindex)).add((DefaultMutableTreeNode) nodelist.get(index));
                    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)nodelist.get(index);
                    try{
                       Levels.add(thisindex + nodelist.get(thisindex).getChildCount(), dmtn.getLevel());
                       if (index < START_NUMBERING_AT_LINE){
                           Numbered_tree.add(thisindex + nodelist.get(thisindex).getChildCount(), String.format("%s%s", Keys.get(index), Trimmedlines.get(index)));
                       } else {
                           Numbered_tree.add(thisindex + nodelist.get(thisindex).getChildCount(), String.format("%s%d. %s", Keys.get(index), nodelist.get(index).getParent().getIndex(nodelist.get(index)) + 1, Trimmedlines.get(index)));
                       }
                    } catch (Exception exc){
                        // file has heading or spacing errors
                    }
                }
            }
            if (nextindex < Trimmedlines.size() - 1) {
                processtree(nextindex, nextindex + 1, nodelist);
            }
        }
    }
    
    class Tabhandler implements MouseListener {

        public void mouseClicked(MouseEvent event) {
            JTabbedPane source = (JTabbedPane)event.getSource();
            TAB_CLICKED = source.getSelectedIndex();
            if (TAB_CLICKED == LAST_TAB_CLICKED){
                return;
            }
            boolean reset_tabs = false;
            int tab = 0;
            switch (source.getSelectedIndex()){
                case 0:
                    reset_tabs = false;
                    tab = 0;
                    break;
                case 1:
                    reset_tabs = true;
                    tab = 1;
                    break;
                case 2:
                    reset_tabs = true;
                    tab = 2;
                    break;
                default:
                    reset_tabs = false;
                    tab = 0;
                    break;
            }
            LAST_TAB_CLICKED = tab;
            if (reset_tabs == true){
                thisview.remove_tabs();
                thisview.initarrays();
                thisview.loadstringintoarrays(thisview.writingArea.getText());
                thisview.maketree();
                thisview.init_tabs();
                thisview.set_writing_area(thisview.get_text_for_writing_area());
                thisview.clean_up_resources();
                thisview.jlist.setSelectedIndex(0);
                thisview.file_label.setText(" " + thisview.returnshortfilename());
                thisview.closed_file = false;
                thisview.tabbedpane.setSelectedIndex(tab);
            }
        }
        
        public void mousePressed(MouseEvent event){
            //
        }
        
        public void mouseReleased(MouseEvent event){
            //
        }
        
        public void mouseEntered(MouseEvent event){
            //
        }
        
        public void mouseExited(MouseEvent event){
            //
        }
    }
    
    // INITCOMPONENTS
    public void initcomponents() {

        tabbedpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        Tabhandler tabhandler = new Tabhandler();
        tabbedpane.addMouseListener(tabhandler);
        add(tabbedpane, BorderLayout.CENTER);

        top_toolbar = new JToolBar();
        top_toolbar.setOrientation(JToolBar.HORIZONTAL);
        top_toolbar.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
        top_toolbar.setFloatable(false);
        add(top_toolbar, BorderLayout.NORTH);
        
        combobox = new JComboBox();
        combobox.setToolTipText("main menu");
        combobox.addItem(" MAIN MENU");
        combobox.addItem("New File");
        combobox.addItem("Open File");
        combobox.addItem("Close File");
        combobox.addItem("Save File");
        combobox.addItem("Numbering");
        combobox.addItem("Options");
        combobox.addItem("Delete File");
        combobox.addItem("Exit");
        combobox.addItem("Info");
        Comboboxhandler comboboxhandler = new Comboboxhandler();
        combobox.addItemListener(comboboxhandler);
        combobox.setMaximumRowCount(10);
        top_toolbar.add(combobox);
        
        init_actions();
        top_toolbar.add(actionCutText);
        top_toolbar.add(actionPasteText);
        top_toolbar.add(actionTextLeft);
        top_toolbar.add(actionTextRight);

        search_label = new JLabel("TYPE SEARCH TERM AND PRESS ENTER:  ");
        textbox = new JTextField(30);
        textbox.setEditable(true);
        textbox.setEnabled(false);
        textbox.setToolTipText("search box");
        textbox_handler = new TextboxHandler();
        textbox.addActionListener(textbox_handler);
        search_button = new JButton(" SEARCH");
        search_button.setToolTipText("search");
        search_button.setEnabled(false);
        next_button = new JButton("NEXT ");
        next_button.setToolTipText("next search result");
        next_button.setEnabled(false);
        previous_button = new JButton("PREVIOUS");
        previous_button.setToolTipText("previous search result");
        previous_button.setEnabled(false);
        Buttonhandler buttonhandler = new Buttonhandler();
        search_button.addActionListener(buttonhandler);
        next_button.addActionListener(buttonhandler);
        previous_button.addActionListener(buttonhandler);
        top_toolbar.add(textbox);
        top_toolbar.add(search_button);
        top_toolbar.add(next_button);
        top_toolbar.add(previous_button);

        up_button = new JButton(" UP ");
        up_button.setToolTipText("move second screen up");
        up_button.setEnabled(false);
        down_button = new JButton("DOWN");
        down_button.setToolTipText("move second screen down");
        down_button.setEnabled(false);
        up_button.addActionListener(buttonhandler);
        down_button.addActionListener(buttonhandler);
        top_toolbar.add(up_button);
        top_toolbar.add(down_button);

        file_label = new JTextField(10);
        file_label.setText("closed");
        top_toolbar.add(file_label);
        file_label.setEditable(false);
    }
    
    public void init_actions(){
        actionCutText = new AbstractAction(){
            public void actionPerformed(ActionEvent event){
                writingArea.cut();
                popup_menu.setVisible(false);
            }
        };
        actionCutText.putValue(Action.NAME, "CUT  ");
        actionCutText.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("cut_text.jpg")));
        actionCutText.putValue(Action.SHORT_DESCRIPTION, "cut text");
        actionCutText.setEnabled(false);
        actionPasteText = new AbstractAction(){
            public void actionPerformed(ActionEvent event){
                writingArea.paste();
                popup_menu.setVisible(false);
            }
        };
        actionPasteText.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("paste_text.jpg")));
        actionPasteText.putValue(Action.NAME, "PASTE");
        actionPasteText.putValue(Action.SHORT_DESCRIPTION, "paste text");
        actionPasteText.setEnabled(false);
        actionTextRight = new AbstractAction(){
            public void actionPerformed(ActionEvent event){
                if (writingArea.getSelectedText() == null || SELECTED_TEXT == ""){
                    return;
                }
                int selection_start = writingArea.getSelectionStart();
                int selection_end = writingArea.getSelectionEnd();
                if (selection_start > 0){
                    String chr = " ";
                    int start_value = selection_start;
                    while (chr.equals(" ") && selection_start > 0){
                        try{
                            chr = writingArea.getText(selection_start - 1, 1);
                            if (chr.equals(" ")){
                                --selection_start;
                            }
                        } catch (Exception exc){
                        }
                    }
                    if (selection_start != start_value){
                        writingArea.select(selection_start, selection_end);
                        SELECTED_TEXT = writingArea.getSelectedText();
                    }
                }
                writingArea.cut();
                String[] strings = SELECTED_TEXT.split("\n");
                SELECTED_TEXT = "";
                int count = 0;
                for (String s : strings){
                    ++count;
                    s = " " + s;
                    SELECTED_TEXT += s;
                    if (count < strings.length){
                        SELECTED_TEXT += "\n";
                    }
                    ++selection_end;
                }
                writingArea.insert(SELECTED_TEXT, selection_start);
                writingArea.grabFocus();
                writingArea.select(selection_start, selection_end);
                try{
                   if (event.getSource().getClass().equals(Class.forName("javax.swing.JMenuItem"))){
                      popup_menu.setVisible(true);
                      float[] floats = Color.RGBtoHSB(0, 200, 255, null);
                      popup_menu_item3.setBackground(Color.getHSBColor(floats[0], floats[1], floats[2]));
                      floats = null;
                   }
                } catch (Exception exc){
                   JOptionPane.showMessageDialog(null, exc.getMessage());
                }
                strings = null;
            }
        };
        actionTextRight.putValue(Action.NAME, "RIGHT");
        actionTextRight.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("textrightbutton.png")));
        actionTextRight.putValue(Action.SHORT_DESCRIPTION, "move text right");
        actionTextRight.setEnabled(false);
        actionTextLeft = new AbstractAction(){
            public void actionPerformed(ActionEvent event){
                if (writingArea.getSelectedText() == null || SELECTED_TEXT == ""){
                    return;
                }
                int selection_start = writingArea.getSelectionStart();
                int selection_end = writingArea.getSelectionEnd();
                if (selection_start > 0){
                    String chr = " ";
                    int start_value = selection_start;
                    while (chr.equals(" ") && selection_start > 0){
                        try{
                            chr = writingArea.getText(selection_start - 1, 1);
                            if (chr.equals(" ")){
                                --selection_start;
                            }
                        } catch (Exception exc){
                        }
                    }
                    if (selection_start != start_value){
                        writingArea.select(selection_start, selection_end);
                        SELECTED_TEXT = writingArea.getSelectedText();
                    }
                }
                String[] strings = SELECTED_TEXT.split("\n");
                SELECTED_TEXT = "";
                boolean cant_move = false;
                for (String s : strings){
                    if (!s.startsWith(" ")){
                        cant_move = true;
                        return;
                    }
                }
                writingArea.cut();
                int count = 0;
                for (String s : strings){
                    ++count;
                    s = s.substring(1);
                    SELECTED_TEXT += s;
                    if (count < strings.length){
                        SELECTED_TEXT += "\n";
                    }
                    --selection_end;
                }
                writingArea.insert(SELECTED_TEXT, selection_start);
                writingArea.grabFocus();
                writingArea.select(selection_start, selection_end);
                try{
                   if (event.getSource().getClass().equals(Class.forName("javax.swing.JMenuItem"))){
                      popup_menu.setVisible(true);
                      float[] floats = Color.RGBtoHSB(0, 200, 255, null);
                      popup_menu_item4.setBackground(Color.getHSBColor(floats[0], floats[1], floats[2]));
                      floats = null;
                   }
                } catch (Exception exc){
                   JOptionPane.showMessageDialog(null, exc.getMessage());
                }
                strings = null;
            }
        };
        actionTextLeft.putValue(Action.NAME, "LEFT ");
        actionTextLeft.putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource("textleftbutton.png")));
        actionTextLeft.putValue(Action.SHORT_DESCRIPTION, "move text left");
        actionTextLeft.setEnabled(false);
        actionClosePopup = new AbstractAction(){
            public void actionPerformed(ActionEvent event){
                popup_menu.setVisible(false);
            }
        };
        actionClosePopup.putValue(Action.NAME, "CLOSE");
        actionClosePopup.setEnabled(true);
    }
    
    public void reset_popup_background(){
        float[] floats = Color.RGBtoHSB(238, 238, 238, null);
        popup_menu_item3.setBackground(Color.getHSBColor(floats[0], floats[1], floats[2]));
        popup_menu_item4.setBackground(Color.getHSBColor(floats[0], floats[1], floats[2]));
        floats = null;
    }

    public void init_tabs(){
        
        actionCutText.setEnabled(true);
        actionPasteText.setEnabled(true);
        actionTextRight.setEnabled(true);
        actionTextLeft.setEnabled(true);
        
        popup_menu = new JPopupMenu();
        popup_menu_item1 = new JMenuItem("CUT  ");
        popup_menu_item2 = new JMenuItem("PASTE");
        popup_menu_item3 = new JMenuItem("RIGHT");
        popup_menu_item4 = new JMenuItem("LEFT ");
        popup_menu_item5 = new JMenuItem("CLOSE");
        popup_menu.add(popup_menu_item1);
        popup_menu.add(popup_menu_item2);
        popup_menu.add(popup_menu_item3);
        popup_menu.add(popup_menu_item4);
        popup_menu.add(popup_menu_item5);
        popup_menu_item1.setAction(actionCutText);
        popup_menu_item2.setAction(actionPasteText);
        popup_menu_item3.setAction(actionTextRight);
        popup_menu_item3.addMouseListener(new MouseAdapter(){
            public void mouseExited(MouseEvent event){
                float[] floats = Color.RGBtoHSB(238, 238, 238, null);
                popup_menu_item3.setBackground(Color.getHSBColor(floats[0], floats[1], floats[2]));
                floats = null;
            }
        });
        popup_menu_item4.setAction(actionTextLeft);
        popup_menu_item4.addMouseListener(new MouseAdapter(){
            public void mouseExited(MouseEvent event){
                float[] floats = Color.RGBtoHSB(238, 238, 238, null);
                popup_menu_item4.setBackground(Color.getHSBColor(floats[0], floats[1], floats[2]));
                floats = null;
            }
        });
        popup_menu_item5.setAction(actionClosePopup);
        
        writingArea = new JTextArea("");
        writingArea.setEditable(true);
        writingArea.setEnabled(true);
        writingArea.setDisabledTextColor(Color.BLACK);
        writingArea.setLineWrap(false);
        writingArea.setFocusable(true);
        writingArea.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent event){
                CLICKED_SCREEN_ONE = true;
                SCREEN_ONE_POSITION = writingArea.getCaretPosition();
                if (event.isMetaDown()){
                    reset_popup_background();
                    popup_menu.show(writingArea, event.getX(), event.getY());
                }
            }
        });
        writingArea.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent event){
                SELECTED_TEXT = writingArea.getSelectedText();
            }
        });
        writingArea.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent event){
                if (popup_menu.isVisible()){
                   popup_menu.setVisible(false);
                }
            }
        });
        tabbedpane.addTab("writing area", null, new JScrollPane(writingArea), "create, change, and save...right-click for quick-functions");

        jtextarea = new JTextArea(intro_message2);
        //jtextarea.setPreferredSize(new Dimension(900, 3000));//might comment this out to allow scrollbar through entire outline
        jtextarea.setEditable(false);
        jtextarea.setEnabled(true);
        jtextarea.setDisabledTextColor(Color.BLACK);
        jtextarea.setLineWrap(false);
        jtextarea.setFocusable(true);
        boxes = new ArrayList<Box>();
        jtextarea.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent event){
                jtextarea.grabFocus();
            }
        });
        jtextarea.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent event){
                if (event.isActionKey()){
                    String s = event.getKeyText(event.getKeyCode());
                    if (s.equals("Up")){
                       if (Masterlistindex > 0){
                          --Masterlistindex;
                          printmasterlist(Masterlistindex);
                       }
                    } else if (s.equals("Down")){
                       if (Masterlistindex < Keys.size() - 1){
                          ++Masterlistindex;
                          printmasterlist(Masterlistindex);
                       }
                    }
                }
            }
            public void keyReleased(KeyEvent event){
                //
            }
            public void keyTyped(KeyEvent event){
                //
            }
        });

        jlist = new JList(Tableofcontents);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        Listhandler listhandler = new Listhandler();
        jlist.addListSelectionListener(listhandler);

        JScrollPane jsp = new JScrollPane(jtextarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.getVerticalScrollBar().addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent event){
                try{
                    int viewport_height = jtextarea.getHeight();
                    java.awt.Rectangle visible_rectangle = jtextarea.getVisibleRect();
                    int x = visible_rectangle.x;
                    int y = visible_rectangle.y;
                    FontMetrics measure = jtextarea.getFontMetrics(jtextarea.getFont());
                    int fontheight = measure.getHeight();
                    float total_lines = viewport_height/fontheight;
                    float lines_to_viewport = y/fontheight;
                    int offset = jtextarea.getLineStartOffset((int)lines_to_viewport);
                    int masterlist_start = Masterlistindex;
                    Masterlistindex = masterlist_start + (int)lines_to_viewport;
                    printmasterlist(Masterlistindex);
                } catch (Exception exc){}
            }
        });
        groovepane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(jlist), jsp);
        groovepane.setDividerLocation(180);
        groovepane.setOneTouchExpandable(false);
        tabbedpane.addTab("table of contents", null, groovepane, "navigate by clicking");

        treemodel = new DefaultTreeModel(rootnode);
        tree = new JTree(treemodel);
        tabbedpane.addTab("tree view", null, new JScrollPane(tree), "double click the folders to open them");
        tabbedpane.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent event){
                switch (tabbedpane.getSelectedIndex()){
                    case 0:
                        actionCutText.setEnabled(true);
                        actionPasteText.setEnabled(true);
                        actionTextRight.setEnabled(true);
                        actionTextLeft.setEnabled(true);
                        textbox.setEnabled(false);
                        search_button.setEnabled(false);
                        next_button.setEnabled(false);
                        previous_button.setEnabled(false);
                        up_button.setEnabled(false);
                        down_button.setEnabled(false);
                        break;
                    case 1:
                        jtextarea.grabFocus();
                        actionCutText.setEnabled(false);
                        actionPasteText.setEnabled(false);
                        actionTextRight.setEnabled(false);
                        actionTextLeft.setEnabled(false);
                        textbox.setEnabled(true);
                        search_button.setEnabled(true);
                        next_button.setEnabled(true);
                        previous_button.setEnabled(true);
                        up_button.setEnabled(true);
                        down_button.setEnabled(true);
                        break;
                    case 2:
                        actionCutText.setEnabled(false);
                        actionPasteText.setEnabled(false);
                        actionTextRight.setEnabled(false);
                        actionTextLeft.setEnabled(false);
                        textbox.setEnabled(false);
                        search_button.setEnabled(false);
                        next_button.setEnabled(false);
                        previous_button.setEnabled(false);
                        up_button.setEnabled(false);
                        down_button.setEnabled(false);
                        break;
                    default:
                        actionCutText.setEnabled(true);
                        actionPasteText.setEnabled(true);
                        actionTextRight.setEnabled(true);
                        actionTextLeft.setEnabled(true);
                        textbox.setEnabled(true);
                        search_button.setEnabled(true);
                        next_button.setEnabled(true);
                        previous_button.setEnabled(true);
                        up_button.setEnabled(true);
                        down_button.setEnabled(true);
                        break;
                }
            }
        });
    }
    
    public String get_text_for_writing_area(){
        ArrayList<String> selection = new ArrayList<String>();
        for (int i = 0; i < Masterlist.size(); ++i) {
            StringBuilder stringbuilder1 = new StringBuilder("");
            stringbuilder1.append(String.format("%s%s", Keys.get(i), Trimmedlines.get(i))); // if the key is just tab commands, they equal an x-location, rather than move forward a certain amount
            selection.add(stringbuilder1.toString());
        }
        StringBuilder stringbuilder2 = new StringBuilder("");
        for (String s : selection) {
            stringbuilder2.append(s);
            stringbuilder2.append("\n");
        }
        String screentext = stringbuilder2.toString();
        return screentext;
    }
    
    public void set_writing_area(String txt){
        writingArea.setText("");
        writingArea.append(txt);
        writingArea.setCaretPosition(0);
    }
    
    public void clean_up_resources(){
        if (Rawlines != null){
           Rawlines.clear();
        }
        Rawlines = null;
    }
    
    public void clean_up_all_resources(){
        if (Rawlines != null){
           Rawlines.clear();
           Trimmedlines.clear();
           Keys.clear();
           Masterlist.clear();
           Tablemap.clear();
           Tablemapkeys.clear();
           //Tableofcontents.clear();
           search_results.clear();
           Numbered_tree.clear();
           Levels.clear();
       }
       Rawlines = null; // first file read
       Trimmedlines = null; // just the text
       Keys = null; // just the key
       Masterlist = null; // both
       Tablemap = null; // the table of contents as key and heading text
       Tablemapkeys = null;
       Tableofcontents = null; // the titles
       search_results = null;
       Numbered_tree = null;
       Levels = null;
    }

    // LISTENERS
    
    class Comboboxhandler implements ItemListener {
        public void itemStateChanged(ItemEvent event){
            if (event.getStateChange() != ItemEvent.SELECTED){
              return;
            }
            if (combobox.getSelectedIndex() == 1){ // new
                if (JOptionPane.showConfirmDialog(thisview, "NEW FILE\nThis will erase your current text.") != JOptionPane.YES_OPTION){
                    return;
                }
                if (closed_file == false){
                    remove_tabs();
                }
                String newname = JOptionPane.showInputDialog("Choose a file name (without extension)");
                if (newname == null){
                    return;
                }
                if (newname.trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Invalid file name");
                } else {
                    if (!newname.endsWith(".txt")){
                       newfilename = newname + ".txt";
                    } else {
                       newfilename = newname;
                    }
                    currentfilename = newfilename;
                    MAKING_NEW_FILE = true;
                    initarrays();
                    MAKING_NEW_FILE = false;
                    //loadfileintoarrays();
                    //maketree();
                    init_tabs();
                    set_writing_area("");
                    clean_up_resources();
                    file_label.setText(" " + returnshortfilename());
                    closed_file = false;
                    HAS_ERRORS = false;
                }
            } else if (combobox.getSelectedIndex() == 4){ // save
                if (closed_file || currentfilename.equals("")){
                    combobox.setSelectedIndex(0);
                    return;
                }
                if (debugger != null && debugger.is_open()){
                       debugger.close();
                }
                Saver saver = new Saver(thisview);
                saver.open();
                if (debugger != null && debugger.is_open()){
                    debugger.highlight_errors();
                }
            } else if (combobox.getSelectedIndex() == 5) { // numbering
                if (closed_file || currentfilename.equals("")) {
                    combobox.setSelectedIndex(0);
                    return;
                }
                if (debugger != null && debugger.is_open()) {
                    debugger.close();
                }
                try {
                    Numberer numberer = new Numberer(thisview);
                    numberer.open();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc);
                }
                if (debugger != null && debugger.is_open()) {
                    debugger.highlight_errors();
                }
            } else if (combobox.getSelectedIndex() == 2){ // open
                if (JOptionPane.showConfirmDialog(thisview, "OPEN NEW FILE\nThis will erase your current text.") != JOptionPane.YES_OPTION){
                    return;
                }
                HAS_ERRORS = false;
                if (closed_file == false){
                    remove_tabs();
                }
                if (debugger != null && debugger.is_open()){
                       debugger.close();
                }
                newfilename = "";
                initarrays();
                boolean got_file = loadfileintoarrays();
                if (got_file == false){
                    combobox.setSelectedIndex(4);
                    return;
                }
                maketree();
                init_tabs();
                set_writing_area(get_text_for_writing_area());
                clean_up_resources();
                jlist.setSelectedIndex(0);
                file_label.setText(" " + returnshortfilename());
                closed_file = false;
                if (debugger != null && debugger.is_open()){
                    debugger.highlight_errors();
                }
            } else if (combobox.getSelectedIndex() == 3){ // close
                if (JOptionPane.showConfirmDialog(thisview, "CLOSE FILE\nThis will erase your current text.") != JOptionPane.YES_OPTION){
                    return;
                }
                closed_file = true;
                remove_tabs();
                file_label.setText(" closed");
                if (debugger != null && debugger.is_open()){
                       debugger.close();
                }
                textbox.setEnabled(false);
                search_button.setEnabled(false);
                next_button.setEnabled(false);
                previous_button.setEnabled(false);
                up_button.setEnabled(false);
                down_button.setEnabled(false);
                actionCutText.setEnabled(false);
                actionPasteText.setEnabled(false);
                actionTextRight.setEnabled(false);
                actionTextLeft.setEnabled(false);
                CLICKED_SCREEN_ONE = false;
                SCREEN_ONE_POSITION = 0;
                SYNC_TABLE_OF_CONTENTS = false;
                HAS_ERRORS = false;
            } else if (combobox.getSelectedIndex() == 7){ // delete
                try{
                    String[] strings = new File("outlines").list();
                    boolean keepgoing = true;
                    while (keepgoing){
                        boolean keepgo = false;
                        for (int count = 0; count < strings.length; ++count){
                            if (count+1 < strings.length){
                                String s1 = strings[count].toLowerCase();
                                String s2 = strings[count+1].toLowerCase();
                                if (s2.charAt(0) < s1.charAt(0)){
                                    String temp = strings[count];
                                    strings[count] = strings[count+1];
                                    strings[count+1] = temp;
                                    keepgo = true;
                                }
                            }
                        }
                        keepgoing = keepgo;
                    }
                   Filedialog2 deletefile = new Filedialog2(new javax.swing.JFrame(), strings, true, false);
                   deletefile.setVisible(true);
                   String result = deletefile.filename;
                   if (result.equals("")){
                       combobox.setSelectedIndex(4);
                       return;
                   }
                   File file = new File("outlines/" + result);
                   if (JOptionPane.showConfirmDialog(thisview, "Erase " + file.getName() + "?") != JOptionPane.YES_OPTION){
                       return;
                   }
                   if (file.isFile()){
                      file.delete();
                   } else {
                       JOptionPane.showMessageDialog(null, "cannot find that file in the outlines folder");
                   }
                } catch (Exception exc){
                    JOptionPane.showMessageDialog(null, exc.getMessage());
                }
            } else if (combobox.getSelectedIndex() == 6){ // options
                Yesnobox yesno = new Yesnobox("Adjust writing area position to match table of contents position?");
                yesno.open();
                if (yesno.getresult() == true){
                    SYNC_TABLE_OF_CONTENTS = true;
                    CLICKED_SCREEN_ONE = false;
                } else {
                    SYNC_TABLE_OF_CONTENTS = false;
                }
            } else if (combobox.getSelectedIndex() == 8){ // exit
                if (JOptionPane.showConfirmDialog(thisview, "EXIT PROGRAM\nThis will erase your current text.") != JOptionPane.YES_OPTION){
                    return;
                }
                System.exit(1);
            } else if (combobox.getSelectedIndex() == 9){ // help
                JOptionPane.showMessageDialog(null, intro_message);
            }
            combobox.setSelectedIndex(0);
        }
    }

    public void remove_tabs(){
       tabbedpane.removeAll();
       rootnode.removeAllChildren();
    }

    class Listhandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent event) {
            try {
                if (jlist.getSelectedIndex() > Tablemapkeys.size() || jlist.getSelectedIndex() < 0) { // the jlist has 1000 indexes because the string array has 1000 indexes
                    return;
                }
                int index = jlist.getSelectedIndex();
                String selection = Tableofcontents[index]; // or tablemap.get(index)
                for (int i = 0; i < Masterlist.size(); ++i) {
                    if (selection.equals(Masterlist.get(i))) { // rule: tableofcontents titles must be unique
                        Masterlistindex = i;
                    }
                }
                printmasterlist(Masterlistindex);
                jtextarea.grabFocus();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Problem in list handler " + exception.toString());
            }
        }
    }

    class TextboxHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            search_string = event.getActionCommand();
            textbox.setText("");
            current_search_index = -1;
            if (!search_string.equals("")){
                Yesnobox yesnobox = new Yesnobox("Case sensitive search?");
                yesnobox.open();
                case_sensitive_search = yesnobox.getresult();
                Yesnobox yesnobox2 = new Yesnobox("Exact matches only?");
                yesnobox2.open();
                exact_matches_only_search = yesnobox2.getresult();
                search_results.clear();
                for (int count = 0; count < Masterlist.size(); ++count){
                    String s = Masterlist.get(count);
                    if (case_sensitive_search){
                        if (s.contains(search_string)){
                            if (exact_matches_only_search == false){
                                search_results.add(count);
                            } else {
                                boolean is_exact_match = true;
                                int startpoint = s.indexOf(search_string);
                                int stoppoint = startpoint + search_string.length() - 1;
                                char[] chars = s.toCharArray();
                                if (startpoint > 0){
                                    if (chars[startpoint - 1] != ' '){
                                        is_exact_match = false;
                                    }
                                }
                                if (stoppoint < chars.length - 1){
                                    if (chars[stoppoint + 1] != ' '){
                                        is_exact_match = false;
                                    }
                                }
                                if (is_exact_match == true){
                                   search_results.add(count);
                                }
                            }
                        }
                    } else {
                        String s2 = s.toLowerCase();
                        String text2 = search_string.toLowerCase();
                        if (s2.contains(text2)){
                            if (exact_matches_only_search == false){
                                search_results.add(count);
                            } else {
                                boolean is_exact_match = true;
                                int startpoint = s2.indexOf(text2);
                                int stoppoint = startpoint + text2.length() - 1;
                                char[] chars = s2.toCharArray();
                                if (startpoint > 0){
                                    if (chars[startpoint - 1] != ' '){
                                        is_exact_match = false;
                                    }
                                }
                                if (stoppoint < chars.length - 1){
                                    if (chars[stoppoint + 1] != ' '){
                                        is_exact_match = false;
                                    }
                                }
                                if (is_exact_match == true){
                                   search_results.add(count);
                                }
                            }
                        }
                    }
                }
                if (search_results.size() > 0){
                   current_search_index = 0;
                   // have to set jlist first
                   Masterlistindex = search_results.get(current_search_index);
                   jlist.setSelectedIndex(find_nearest_listitem(Masterlistindex));
                   // setting jlist index triggered jlist click handler
                   Masterlistindex = search_results.get(current_search_index);
                   printmasterlist(Masterlistindex);
                   setbox(search_string, Masterlist.get(Masterlistindex), case_sensitive_search, exact_matches_only_search);
                } else {
                   JOptionPane.showMessageDialog(null, "No results found");
                }
            }
        }
    }
    

    public int find_nearest_listitem(int masterlist_index){
        int result = 0;
        try{
           int next_list_index = 0;
           for (int count = 0; count < Masterlist.size(); ++count){
               String s = Masterlist.get(count);
               if (Tableofcontents[next_list_index].equals(s)){
                   ++next_list_index;
                   if (next_list_index >= Tableofcontents.length){
                       break;
                   }
               }
               if (count == masterlist_index){
                   break;
               }
           }
           result = next_list_index - 1;
           if (result < 0){
               result = 0;
           }
        } catch (Exception exc){
            //JOptionPane.showMessageDialog(null, "error: " + exc.getMessage());
        }
        return result;
    }

    public void setbox(String part, String whole, boolean case_sensitive, boolean exact_matches){
        remove_boxes();
        FontMetrics measure = jtextarea.getFontMetrics(jtextarea.getFont());
        String measurewhole = "" + whole;
        if (case_sensitive == false){
            part = part.toLowerCase();
            whole = whole.toLowerCase();
        }
        int charloc = 0;
        int next_index = 0;
        while((charloc = whole.indexOf(part, next_index)) != -1){
           next_index = charloc + 1;
           if (exact_matches == true){
               char[] tochar = whole.toCharArray();
               if (charloc > 0 && tochar[charloc - 1] != ' '){
                   continue;
               }
               if (charloc + part.length() < whole.length() && tochar[charloc + part.length()] != ' '){
                   continue;
               }
           }
           int chars = part.length();
           String start = measurewhole.substring(0, charloc);
           String text = measurewhole.substring(charloc, charloc + chars);
           int offset = measure.stringWidth(start);
           int textwidth = measure.stringWidth(text);
           int textareawidth = jtextarea.getWidth();
           int xlocation = offset;
           int boxwidth = textwidth;
           Box box = Box.createHorizontalBox();
           box.setFocusable(false);
           box.setBorder(new MatteBorder(1, 1, 1, 1, Color.GREEN));
           box.setSize(boxwidth, 17);
           box.setLocation(xlocation, 0);
           box.setVisible(true);
           jtextarea.add(box);
           boxes.add(box);
        }
        Box borderbox = Box.createHorizontalBox();
        borderbox.setFocusable(false);
        borderbox.setBorder(new MatteBorder(1, 1, 1, 1, Color.PINK));
        borderbox.setSize(new Dimension(700, 17));
        borderbox.setLocation(0, 0);
        borderbox.setVisible(true);
        jtextarea.add(borderbox);
        boxes.add(borderbox);
    }

    public void remove_boxes(){
        for (Box box : boxes){
            jtextarea.remove(box);
        }
        boxes.clear();
    }
    
    class Buttonhandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if (event.getSource() == search_button){
                java.awt.event.ActionEvent temp_evt = new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_FIRST, textbox.getText());
                textbox_handler.actionPerformed(temp_evt);
            } else if (event.getSource() == next_button){
                if (current_search_index + 1 < search_results.size() && current_search_index != -1){
                    ++current_search_index;
                    // have to set jlist first
                    Masterlistindex = search_results.get(current_search_index);
                    jlist.setSelectedIndex(find_nearest_listitem(Masterlistindex));
                    // setting jlist index triggered jlist click handler
                    Masterlistindex = search_results.get(current_search_index);
                    printmasterlist(Masterlistindex);
                    setbox(search_string, Masterlist.get(Masterlistindex), case_sensitive_search, exact_matches_only_search);
                } else {
                    JOptionPane.showMessageDialog(null, "No more results");
                }
            } else if (event.getSource() == previous_button){
                if (current_search_index - 1 >= 0 && current_search_index != -1){
                    --current_search_index;
                    // have to set jlist first
                    Masterlistindex = search_results.get(current_search_index);
                    jlist.setSelectedIndex(find_nearest_listitem(Masterlistindex));
                    // setting jlist index triggered jlist click handler
                    Masterlistindex = search_results.get(current_search_index);
                    printmasterlist(Masterlistindex);
                    setbox(search_string, Masterlist.get(Masterlistindex), case_sensitive_search, exact_matches_only_search);
                } else {
                    JOptionPane.showMessageDialog(null, "No more results");
                }
            } else if (event.getSource() == up_button){
                if (Masterlistindex > 0){
                    --Masterlistindex;
                    printmasterlist(Masterlistindex);
                }
            } else if (event.getSource() == down_button){
                if (Masterlistindex < Keys.size() - 1){
                    ++Masterlistindex;
                    printmasterlist(Masterlistindex);
                }
            }
        }
    }

    // this reveals a possible formatting rule that each text line must be unique
    public void printmasterlist(int masterlistindex) {
        jtextarea.setText("");
        remove_boxes();
        ArrayList<String> selection = new ArrayList<String>();
        for (int i = masterlistindex; i < Masterlist.size(); ++i) {
            StringBuilder stringbuilder1 = new StringBuilder("");
            stringbuilder1.append(String.format("%s%s", Keys.get(i), Trimmedlines.get(i))); // if the key is just tab commands, they equal an x-location, rather than move forward a certain amount
            selection.add(stringbuilder1.toString());
        }
        StringBuilder stringbuilder2 = new StringBuilder("");
        for (String s : selection) {
            stringbuilder2.append(s);
            stringbuilder2.append("\n");
        }
        String screentext = stringbuilder2.toString();
        jtextarea.append(screentext);
        jtextarea.setCaretPosition(0);
        if (CLICKED_SCREEN_ONE == true && SYNC_TABLE_OF_CONTENTS == false){
            if (writingArea.getCaretPosition() >= 0){
                writingArea.setCaretPosition(SCREEN_ONE_POSITION);
            }
            return;
        }
        ArrayList<String> selection2 = new ArrayList<String>();
        for (int i = 0; i < masterlistindex; ++i){
            StringBuilder stringbuilder3 = new StringBuilder("");
            stringbuilder3.append(String.format("%s%s", Keys.get(i), Trimmedlines.get(i))); // if the key is just tab commands, they equal an x-location, rather than move forward a certain amount
            selection2.add(stringbuilder3.toString());
        }
        StringBuilder stringbuilder4 = new StringBuilder("");
        for (String s : selection2) {
            stringbuilder4.append(s);
            stringbuilder4.append("\n");
        }
        String screentext2 = stringbuilder4.toString();
        int original_caret_position = writingArea.getCaretPosition();
        int new_caret_position = screentext2.length();
        writingArea.setCaretPosition(new_caret_position);
        if (new_caret_position > original_caret_position){
           try{
              int current_textarea_line = writingArea.getLineOfOffset(screentext2.length());
              int total_textarea_lines = writingArea.getLineCount();
              int visible_rows = 30;
              if (total_textarea_lines > current_textarea_line){
                  for (int count = 0; count < visible_rows; ++count){
                      ++current_textarea_line;
                      if (current_textarea_line < total_textarea_lines){
                         int offset = writingArea.getLineEndOffset(current_textarea_line);
                         writingArea.setCaretPosition(offset);
                      } else {
                          break;
                      }
                  }
              }
           } catch (Exception exc){
             //   
           }
        }
    }

    public void resetjlist() {
        try {
            Tablemap.clear();
            Tablemapkeys.clear();
            for (int i = 0; i < Tableofcontents.length; ++i) {
                Tableofcontents[i] = "";
            }
            int chapternumber = 0;
            char[] chars = new char[300];
            for (String s : Masterlist) {
                chars = s.toCharArray();
                if (chars.length == 0) {
                    continue;
                }
                if (!(Character.isLetterOrDigit(chars[0]) || Character.isDefined(chars[0]))) {
                    continue;
                }
                if (chars[0] == ' ' || chars[0] == '\t') { // rule: first level has no indentation
                    continue;
                }
                Tableofcontents[chapternumber] = s;
                ++chapternumber;
                Tablemap.put(chapternumber, s); // creates the option to list each chapter with the chapter number in front of it
                Tablemapkeys.add(chapternumber);
            }
            printjlist();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problem in resetjlist " + exception.toString());
        }
    }

    public void printjlist() {
        try {
            jlist.setListData(Tableofcontents);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problem in printjlist " + exception.toString());
        }
    }
}
