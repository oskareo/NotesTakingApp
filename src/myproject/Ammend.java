/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;

/**
 *
 * @author USER
 */
public class Ammend extends JFrame implements getData, ActionListener, KeyListener {

    JTextArea txtDisplayNotes = new JTextArea();
    JPanel pnl = new JPanel(new BorderLayout());
    Font fnt = new Font("Georgia", Font.PLAIN, 24);
    ArrayList<String> notes = new ArrayList<>();
    AllNotes allNotes = new AllNotes();
    private static String theObjName;
    private static String theCommand;
    //to count how many notes are going to 
    private static int counting = 1;

    Ammend(String fileName, String command) {
        //gets name of the file where the notes are
        this.theObjName = fileName;
        //gets the commands that are being used
        this.theCommand = command;
        model();
        view();
        controller();
    }

    private void model() {
        checkWhatToAmmend();
    }

    private void checkWhatToAmmend() {
        String checking = getCommandName();
        String getfile = getObjName();
        if (checking.contains("AmendCourse")) {
            showCourse(getfile);
        }
        if (checking.contains("AmendCoursework")) {
            showCW(getfile);
        }
        if (checking.contains("AmendRequirement")) {
            showReq(getfile);
        }
        if (checking.contains("AmendNotesInRequirements")) {
            showReqNotes(getfile);
        }
    }

    protected JButton makeButton(
            String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {

        //Create and initialize the button.
        JButton button = new JButton();
        button.setToolTipText(toolTipText);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        //Look for the image.
        String imgLocation = System.getProperty("user.dir")
                + "\\icons\\"
                + imageName
                + ".png";

        File fyle = new File(imgLocation);
        if (fyle.exists() && !fyle.isDirectory()) {
            // image found
            Icon img;
            img = new ImageIcon(imgLocation);
            button.setIcon(img);
        } else {
            // image NOT found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

    private void view() {
        JToolBar toolBar = new JToolBar();
        // Setting up the ButtonBar
        JButton button = null;
        button = makeButton("Left", "ReturnToCourseNotes",
                "Return to the Course notes page",
                "Note");
        toolBar.add(button);
        toolBar.addSeparator();         // This forces anything after it to the right.       
        button = makeButton("Save", "SaveNote",
                "Save a note",
                "SaveIt");
        toolBar.add(button);
        toolBar.add(Box.createHorizontalGlue());
        add(toolBar, BorderLayout.NORTH);

        JPanel cen = new JPanel();
        cen.setLayout(new BoxLayout(cen, BoxLayout.Y_AXIS));
        cen.setBorder(BorderFactory.createLineBorder(Color.black));
        txtDisplayNotes.setFont(fnt);
        cen.add(txtDisplayNotes);

        add(cen, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(/*data.courseList.getSelectedItem().toString()+*/"Ammend Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

    }

    private void controller() {
        addAllNotes();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if ("SaveNote".equals(ae.getActionCommand())) {

            if (txtDisplayNotes.getText().isEmpty() || txtDisplayNotes.getText().trim().length() <= 0 /*empty.equals(txtNewNote.getText())*/) {
                //repeat = true;
                JOptionPane.showConfirmDialog(null, "The note is empty. Please enter a note", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    // crse = SpecList.getSelectedItem().toString();
                    int lncount = txtDisplayNotes.getLineCount() - 1;

                    int lncount2, lncount3, textLegth;
                    String textLine;
                    //this code get every line of text from the text area 
                    for (int i = 0; i < lncount;) {
                        //gets the beginig of the line
                        lncount2 = txtDisplayNotes.getLineStartOffset(i);
                        //gets the end of the line
                        lncount3 = txtDisplayNotes.getLineEndOffset(i);
                        //calculates the length of the line
                        textLegth = (lncount3 - lncount2) - 1;
                        //gets the line of texts
                        textLine = txtDisplayNotes.getText(lncount2, textLegth);
                        System.out.println(lncount);
                        addNote(textLine, getObjName(), counting);
                        i++;
                        //keeps track of how many lines of texts there are
                        counting++;
                    }

                    System.out.println(lncount);

                    // addNote(txtDisplayNotes.getText(), getObjName());
                    txtDisplayNotes.setText("");
                } catch (BadLocationException ex) {
                    Logger.getLogger(Ammend.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        if ("ReturnToCourseNotes".equals(ae.getActionCommand())) {
//prevents the whole program from closin
            WindowEvent winevent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winevent);
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    
    public void setObjName(String x) {
        this.theObjName = x;
    }

    @Override
    public String getObjName() {

        return this.theObjName;

    }

    @Override
    public void setCommandName(String x) {
        this.theCommand = x;

    }

    @Override
    public String getCommandName() {
        return this.theCommand;
    }

    private void showCourse(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showCW(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showReq(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showReqNotes(String file) {
        //Shows the notes to be  edited in the text area of the panel
        showNotes(file);
    }

    private void showNotes(String crse) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        AllNotes theNotes = new AllNotes();
        theNotes.setCurrentCourse(crse);
        theNotes.readAllNotes();
        String txtNotes = "";

        for (Note n : theNotes.getAllNotes()) {
            txtNotes += n.getNote() + "\n";
        }

        txtDisplayNotes.setText(txtNotes);
    }

    private void addNote(String text, String coursename, int countedlns) {
        //this if statements decides wheter notes are to be written to a file
        //Or appended to them
        //If there is only one line of text then edited notes are written
        //if there are more than one, the ones after the first one are appended
        if (countedlns == 1) {
            allNotes.setCheckifAmmend(true);
        } else if (countedlns > 1) {
            allNotes.setCheckifAmmend(false);
        }
        
        allNotes.addNote(allNotes.getMaxID(), coursename, text);

        
        addAllNotes();

    }

    private void addAllNotes() {
        String txtNotes = "";
        AllNotes Notes = new AllNotes();
        Notes.setCurrentCourse(getObjName());
        Notes.readAllNotes();
        for (Note n : Notes.getAllNotes()) {
            txtNotes += n.getNote() + "\n";
        }

        
    }

}
