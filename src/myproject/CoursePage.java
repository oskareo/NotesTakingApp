/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 *
 * @author oe7863y
 */
public class CoursePage extends JFrame implements ActionListener, KeyListener {

    JPanel pnl = new JPanel(new BorderLayout());
    JTextArea txtNewNote = new JTextArea();
    JTextArea txtDisplayNotes = new JTextArea();
    ArrayList<String> note = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    JComboBox courseList = new JComboBox();
    JComboBox courseWorkList = new JComboBox();
    String crse = "";
    String crsework = "";
    AllNotes allNotes = new AllNotes();
    Course myCourse = new Course();
    JTextField search = new JTextField();
Font fnt = new Font("Georgia", Font.PLAIN, 24);
    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CoursePage prg = new CoursePage();
    }

    public CoursePage() {
        model();
        view();
        controller();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println("actionPerformed not coded yet.");
        if ("NewNote".equals(e.getActionCommand())) {
            //Checks that no empty spaces are inputed
            //shows an error message
            if (txtNewNote.getText().isEmpty() || txtNewNote.getText().trim().length() <= 0 /*empty.equals(txtNewNote.getText())*/) {
                //repeat = true;
                JOptionPane.showConfirmDialog(null, "The note is empty. Please enter a note", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                crse = courseList.getSelectedItem().toString();
                addNote(txtNewNote.getText(), crse);
                txtNewNote.setText("");
            }

        }
        if ("Close".equals(e.getActionCommand())) {

        }
        if ("Exit".equals(e.getActionCommand())) {
            System.exit(0);
        }
        if ("Course".equals(e.getActionCommand())) {
            crse = courseList.getSelectedItem().toString();
            //Let know that this action commad has been activated
            //not run the methods that belong to the coursework coommads
            myCourse.setcheckCourseOrWork(false);
            myCourse.setifOpenCW(true);
           
            //When this is accesed the coursework command is triggered
           showCourseWork(crse);
            showNotes(crse);
            //set this to false a again
            //if it gets to the coursework command as false
            //then the methods in there are executed
            myCourse.setifOpenCW(false);
            System.out.println(crse);
           // myCourse.setifOpenCW(false);
            
        }

        if ("AddNewCourse".equals(e.getActionCommand())) {
            //check that the user enters the correct inout
            String input = javax.swing.JOptionPane.showInputDialog("Enter new course name: ");
            String empty = " ";
            if (input.isEmpty() || input.trim().length() <= 0) {
                JOptionPane.showConfirmDialog(null, "Course not entered. Please enter a course", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                myCourse.setcheckCourseOrWork(false);
                addCourse(input);

            }
        }

        if ("DeleteCourse".equals(e.getActionCommand())) {
            crse = courseList.getSelectedItem().toString();
            deleteCourse(crse);
            txtNewNote.setText("");

        }
        if ("AddCoursework".equals(e.getActionCommand())) {
            //checks User enters the correct input
            String input = javax.swing.JOptionPane.showInputDialog("Enter new coursework name: ");
            String empty = " ";
            if (input.isEmpty() || input.trim().length() <= 0) {
                JOptionPane.showConfirmDialog(null, "Course not entered. Please enter a course", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                myCourse.setcheckCourseOrWork(true);
                addCourse(input);

            }
            
        }
        if ("DeleteCoursework".equals(e.getActionCommand())) {
crse= courseList.getSelectedItem().toString();
crsework=courseWorkList.getSelectedItem().toString();
            myCourse.deleteCW(crse, crsework);
            dleteCourseWork(crsework);
        }
        if ("Coursework".equals(e.getActionCommand())) {
            crsework = courseList.getSelectedItem().toString();
           
            myCourse.setcheckCourseOrWork(true);
            boolean checking=myCourse.getIFopenCW();
            //only if the course command has not been accessed
            //prevents from opening multiple new windows
            if(checking==false){
                //open a new Jframe for the coursework and the requirements
            CourseWork cwork= new CourseWork();
            
           
            }
      
        }
        if ("SearchKeyword".equals(e.getActionCommand())) {
            String lyst = allNotes.searchAllNotesByKeyword("", 0, search.getText());
            txtDisplayNotes.setText(lyst);
        }
        
        
    }
    protected JMenuItem makeMenuItem(
            String txt,
            String actionCommand,
            String toolTipText,
            Font fnt) {
        JMenuItem mnuItem = new JMenuItem();
        mnuItem.setText(txt);
        mnuItem.setActionCommand(actionCommand);
        mnuItem.setToolTipText(toolTipText);
        mnuItem.setFont(fnt);
        mnuItem.addActionListener(this);
        return mnuItem;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped not coded yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed not coded yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased not coded yet.");
    }

    private void model() {
        // System.out.println("model not coded yet.");

        showCourses();
       crse = courseList.getSelectedItem().toString();
        showCourseWork(crse);
        
        showNotes(crse);
        /* Note nt = new Note();
        nt.setNoteID(1);
nt.setDayte(getDateAndTime());
nt.setCourse(crse);
nt.setNote("Arrays are of fixed length and are inflexible.");
allNotes.addNote(nt.getNoteID(), nt.getCourse(), nt.getNote());
        nt = new Note();
        nt.setNoteID(2);
nt.setDayte(getDateAndTime());
nt.setCourse(crse);
nt.setNote("ArraysList can be added to and items can be deleted.");
allNotes.addNote(nt.getNoteID(), nt.getCourse(), nt.getNote());*/

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
        JMenuBar menuBar = new JMenuBar();

        JMenu course = new JMenu("Course");
        course.setToolTipText("List of course options");
        course.setFont(fnt);
        course.add(makeMenuItem("Add new course", "AddNewCourse", " Add a new Course", fnt));
        course.addSeparator();
        course.add(makeMenuItem("Delete a course", "DeleteCourse", " Delete an existing course", fnt));
        course.addSeparator();
        course.add(makeMenuItem("Amend a course", "AmendCourse", " Edit an existing course", fnt));
        

        menuBar.add(course);

        JMenu courseWork = new JMenu("Coursework");
        courseWork.setToolTipText("List of coursework options");
        courseWork.setFont(fnt);
        courseWork.add(makeMenuItem("Open Coursework", "Coursework", "Open Coursework window", fnt));
        course.addSeparator();
        courseWork.add(makeMenuItem("Add Coursework", "AddCoursework", "Add a coursework to your course", fnt));
        course.addSeparator();
        courseWork.add(makeMenuItem("Delete Coursework", "DeleteCoursework", "Delete a coursework to your course", fnt));
        course.addSeparator();
        courseWork.add(makeMenuItem("Amend Coursework", "AmendCoursework", "Amend a coursework of your course", fnt));
        menuBar.add(courseWork);

        JMenu note = new JMenu("Note");
        note.setToolTipText("Note tasks");
        note.setFont(fnt);

        note.add(makeMenuItem("New", "NewNote", "Create a new note.", fnt));
        note.addSeparator();
        note.add(makeMenuItem("Close", "Close", "Clear the current note.", fnt));

        menuBar.add(note);
        menuBar.add(makeMenuItem("Exit", "Exit", "Close this program", fnt));

        // This will add each course to the combobox
        courseList.setFont(fnt);
        courseList.setToolTipText("Courses");
        courseList.setMaximumSize(courseList.getPreferredSize());
        courseList.addActionListener(this);
        courseList.setActionCommand("Course");

        menuBar.add(courseList);

        courseWorkList.setFont(fnt);
        courseWorkList.setToolTipText("Coursework");
        courseWorkList.setMaximumSize(courseWorkList.getPreferredSize());
        courseWorkList.addActionListener(this);
        
        menuBar.add(courseWorkList);

        this.setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar();
        // Setting up the ButtonBar
        JButton button = null;
        button = makeButton("Create", "NewNote",
                "Create a new note.",
                "New");
        toolBar.add(button);
        button = makeButton("closed door", "Close",
                "Close this note.",
                "Close");
        toolBar.add(button);
        toolBar.addSeparator();
        button = makeButton("exit", "Exit",
                "Exit from this program.",
                "Exit");
        toolBar.add(button);
        toolBar.addSeparator();         // This forces anything after it to the right.       
        toolBar.add(Box.createHorizontalGlue());

        search.setMaximumSize(new Dimension(6900, 30));
        search.setFont(fnt);
        toolBar.add(search);
        toolBar.addSeparator();
        button = makeButton("search", "SearchKeyword", "Search for this text.", "Search");
        toolBar.add(button);
        add(toolBar, BorderLayout.NORTH);

        JPanel pnlWest = new JPanel();
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
        pnlWest.setBorder(BorderFactory.createLineBorder(Color.black));

        txtNewNote.setFont(fnt);
        pnlWest.add(txtNewNote);

        JButton btnAddNote = new JButton("Add note");
        btnAddNote.setActionCommand("NewNote");
        btnAddNote.addActionListener(this);
        pnlWest.add(btnAddNote);

        add(pnlWest, BorderLayout.WEST);

        JPanel cen = new JPanel();
        cen.setLayout(new BoxLayout(cen, BoxLayout.Y_AXIS));
        cen.setBorder(BorderFactory.createLineBorder(Color.black));
        txtDisplayNotes.setFont(fnt);
       
        cen.add(txtDisplayNotes);

        add(cen, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Course - Oscar Eko Osa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);  // Needed to ensure that the items can be seen.

    }

    private void controller() {
        //System.out.println("controller not coded yet.");
        addAllNotes();
    }

    private void addNote(String text, String coursename) {
       
        allNotes.addNote(allNotes.getMaxID(), coursename, text);

        // note.clear();
        addAllNotes();

    }

    private void addAllNotes() {
        String txtNotes = "";
        AllNotes Notes = new AllNotes();
        Notes.setCurrentCourse(crse);
        Notes.readAllNotes();
        for (Note n : Notes.getAllNotes()) {
            txtNotes += n.getNote() + "\n";
        }

        txtDisplayNotes.setText(txtNotes);
    }

    public String getDateAndTime() {
        String UK_DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
        String ukDateAndTime;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat uksdf = new SimpleDateFormat(UK_DATE_FORMAT_NOW);
        ukDateAndTime = uksdf.format(cal.getTime());
        return ukDateAndTime;
    }

    //adds a course to  the course list
    public void addCourse(String x) {
      //checks if either a course or coursework is created
        boolean crseOrCW = myCourse.getCheck();

        ArrayList<String> course = new ArrayList<>();
        String getCourse;
        String spec;
        crse = x;
        // courses.add(x);
        // courses.add("COMP1753");
        // courses.add(x);
        
        //adds a new course or creates a coursework
        if (crseOrCW == true) {
            //add a new coursework to the list of courseworks of the course
            getCourse = myCourse.appDir + "//" + courseList.getSelectedItem().toString() + "CourseWork" + ".txt";
            //creates a path for the requiremenst of the coursework
spec=myCourse.appDir + "//" + crse+ "Requirements" + ".txt";
            
            myCourse.setCurrentCourse(courseList.getSelectedItem().toString() + "CourseWork");
            course = myCourse.readCourses();
            if(course.contains("File not found")){
            course.remove("File not found");
             myCourse.IncludeItem(getCourse, course);
            // courseWorkList.removeItem("File not found");
            }
            //created the course
            myCourse.createCourse(crse, getCourse,spec/*,courses*/);
            course.clear();
            course = myCourse.readCourses();
            int i = course.indexOf(crse);
            //new course added to the drop down list
            courseWorkList.addItem(course.get(i));
        } else {
            //adds a course
            getCourse = myCourse.appDir + "//Courses.txt";
            myCourse.setCurrentCourse("Courses");
            course = myCourse.readCourses();
            //check if "File not found is the list and deletes it"
            if(course.contains("File not found")){
            course.remove("File not found");
             myCourse.IncludeItem(getCourse, course);
            // courseList.removeItem("File not found");
            }
            myCourse.createCourse(crse, getCourse,""/*,courses*/);
            course.clear();
            course = myCourse.readCourses();
            int i = course.indexOf(crse);
            courseList.addItem(course.get(i));
        }
        
    }
//Shows the list of courses
    public void showCourses() {
        ArrayList<String> course = new ArrayList<>();
        boolean check;
       // check = myCourse.getCheck();
      myCourse.setCurrentCourse("Courses");
      //creates a path
      String path= myCourse.appDir+ "//Courses.txt";
      
            course = myCourse.readCourses();
      for (String crso : course) {
                //Course added to the course drop-down list
                courseList.addItem(crso);
            }
        
    }
    
    public void showCourseWork(String x){
    ArrayList<String>coursework= new ArrayList<>();
    String path= x+"CourseWork";
    myCourse.setCurrentCourse(path);
    courseWorkList.removeAllItems();
    coursework= myCourse.readCourses();
    for(String crsework: coursework){
        courseWorkList.addItem(crsework);
    }
    }

    private void deleteCourse(String dltcourse) {
        myCourse.setCurrentCourse("Courses");
        String path = myCourse.appDir + "//Courses.txt";
        // String dltpath="//"+dltcourse+".txt";
        ArrayList<String> course = new ArrayList<>();
        course = myCourse.readCourses();
        
        int i = course.indexOf(dltcourse);
        courseList.removeItem(course.get(i));
        myCourse.deleteCourseFile(dltcourse, course);
        
        
        
        

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

    private void dleteCourseWork(String cwork) {
       myCourse.setCurrentCourse(crse+"CourseWork");
       String path = myCourse.appDir + "//"+cwork+".txt";
        // String dltpath="//"+dltcourse+".txt";
        ArrayList<String> coursework = new ArrayList<>();
        coursework = myCourse.readCourses();
        
        int i = coursework.indexOf(cwork);
       
    courseWorkList.removeItem(cwork);
    myCourse.deleteCWFiles(cwork, coursework);
    }
}