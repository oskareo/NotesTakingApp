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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.File;
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
 * @author USER
 */
public class CourseWork extends JFrame implements ActionListener, KeyListener {
    
   
    
    JPanel pnl = new JPanel(new BorderLayout());
    JTextArea txtNewNote = new JTextArea();
    JTextArea txtDisplayNotes = new JTextArea();
    ArrayList<String> note = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    JComboBox SpecList = new JComboBox();
    JComboBox courseList= new JComboBox();
    JComboBox courseWorkList= new JComboBox();
    //getInfo data = new getInfo();
    
    String crse = "";
    String crsework = "";
    String req="";
    AllNotes allNotes = new AllNotes();
    Course myCourse = new Course();
    JTextField search = new JTextField();
    public static String courseName;
    public static String CWname;
      
      
    
    public CourseWork() {
  
        model();
        view();
        controller();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if ("NewNote".equals(e.getActionCommand())) {
             req = SpecList.getSelectedItem().toString();
            if (txtNewNote.getText().isEmpty() || txtNewNote.getText().trim().length() <= 0||(req=="File not found"||req=="" )/*empty.equals(txtNewNote.getText())*/) {
                //repeat = true;
                if(req=="File not found"||req==""){
                   JOptionPane.showConfirmDialog(null, "The file does not exist", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            
                }else{
                JOptionPane.showConfirmDialog(null, "The note is empty. Please enter a note", "Error Message", JOptionPane.INFORMATION_MESSAGE);}
            } else {
               
                addNote(txtNewNote.getText(), req);
                txtNewNote.setText("");
                
            }

        }
     if ("ReturnToCourseNotes".equals(e.getActionCommand())) {
  
     WindowEvent winevent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winevent);
     }
     
      if ("Exit".equals(e.getActionCommand())) {
            //only close the window current window and not the whole program
            WindowEvent winevent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winevent);
        }
      
      if ("courseList".equals(e.getActionCommand())) {
            crse = courseList.getSelectedItem().toString();
            //Let know that this action command has been activated
         myCourse.setcheckCourseOrWork(false);
            myCourse.setifOpenCW(true);
           
           showCourseWork(crse);
           crsework = courseWorkList.getSelectedItem().toString();
            showRequirements(crsework);
             req= SpecList.getSelectedItem().toString();
             
             showNotes(req);
             //set it back to false
             myCourse.setifOpenCW(false);
            System.out.println(crse);
          
            
        }
      
    if ("courseWorkList".equals(e.getActionCommand())) {
         // crse = courseList.getSelectedItem().toString();
          boolean checking=myCourse.getIFopenCW();
            if(checking==false){
          
    crsework = courseWorkList.getSelectedItem().toString();
            req = SpecList.getSelectedItem().toString();
            myCourse.setcheckCWOrReq(false);
            myCourse.setifOpenReq(true);
            showRequirements(crsework);
             showNotes(req);
             myCourse.setifOpenReq(false);
            }
            
     }
    
 if ("Requirements".equals(e.getActionCommand())) {
          
           boolean checking=myCourse.getIFopenReq();
           boolean check2=myCourse.getIFopenCW();
            if(checking==false && check2==false){
            req = SpecList.getSelectedItem().toString();
           // myCourse.setcheckCourseOrWork(false);
           // myCourse.setifOpenCW(true);
            showNotes(req);
          // showCourseWork(crse);
         // myCourse.setifOpenCW(false);
            System.out.println(req);
           // myCourse.setifOpenCW(false);
            }
        }
      
       if ("AddNewRequirement".equals(e.getActionCommand())) {
            // String input = javax.swing.JOptionPane.showInputDialog("Enter new course name: ");
            // addCourse(input);
            crsework=courseWorkList.getSelectedItem().toString();
            String input = javax.swing.JOptionPane.showInputDialog("Enter new requirement name: ");
            String empty = " ";
            if (input.isEmpty() || input.trim().length() <= 0||(crsework=="File not found"||crsework=="")) {
                if(crsework=="File not found"||crsework==""){
                   JOptionPane.showConfirmDialog(null, "The file does not exist", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            
                }else{
                JOptionPane.showConfirmDialog(null, "Course not entered. Please enter a course", "Error Message", JOptionPane.INFORMATION_MESSAGE);}
            } else {
                myCourse.setcheckCourseOrWork(false);
                addRequirements(input);

            }
        }
       if("AmendNotesInRequirements".equals(e.getActionCommand())){
           req = SpecList.getSelectedItem().toString();
           String theCommand="AmendNotesInRequirements";
       Ammend ammendfiles = new Ammend(req,theCommand);
       }

        if ("DeleteRequirement".equals(e.getActionCommand())) {
            req = SpecList.getSelectedItem().toString();
            deleteRequirements(req);
            txtNewNote.setText("");
        }
        
     
     if ("SaveNote".equals(e.getActionCommand())) {
            
            if (txtNewNote.getText().isEmpty() || txtNewNote.getText().trim().length() <= 0 /*empty.equals(txtNewNote.getText())*/) {
                //repeat = true;
                JOptionPane.showConfirmDialog(null, "The note is empty. Please enter a note", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                crse = SpecList.getSelectedItem().toString();
                addNote(txtNewNote.getText(), crse);
                txtNewNote.setText("");
            }
    
    }
     if ("SearchKeyword".equals(e.getActionCommand())) {
            String lyst = allNotes.searchAllNotesByKeyword("", 0, search.getText());
            txtDisplayNotes.setText(lyst);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void model() {
        showCourses();
       
         
        crse = courseList.getSelectedItem().toString();
        
       
         showCourseWork(crse);
         crsework=courseWorkList.getSelectedItem().toString();
        
        showRequirements(crsework);
         req= SpecList.getSelectedItem().toString();
        showNotes(req);

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void view() {
        Font fnt = new Font("Georgia", Font.PLAIN, 24);
        JMenuBar menuBar = new JMenuBar();
        JMenu course = new JMenu("Requirements");
        course.setToolTipText("List of requirements for this coursework");
        course.setFont(fnt);
        course.add(makeMenuItem("Add new requirement", "AddNewRequirement", " Add a new requirement", fnt));
        course.addSeparator();
        course.add(makeMenuItem("Delete a requirement", "DeleteRequirement", " Delete an existing requirement", fnt));
        course.addSeparator();
        course.add(makeMenuItem("Amend a requirement", "AmendRequirement", " Edit an existing requirement", fnt));
        course.addSeparator();
        course.add(makeMenuItem("Amend notes of requirements", "AmendNotesInRequirements", " Edit an existing requirement", fnt));

        menuBar.add(course);
        menuBar.add(makeMenuItem("Exit", "Exit", "Close this program", fnt));
        
        
        
        
        courseList.setFont(fnt);
        courseList.setToolTipText("Course List");
        courseList.setMaximumSize(courseList.getPreferredSize());
        courseList.addActionListener(this);
        courseList.setActionCommand("courseList");

        menuBar.add(courseList);
        
        courseWorkList.setFont(fnt);
        courseWorkList.setToolTipText("Coursework List");
        courseWorkList.setMaximumSize(courseWorkList.getPreferredSize());
        courseWorkList.addActionListener(this);
        courseWorkList.setActionCommand("courseWorkList");

        menuBar.add(courseWorkList);
        
        SpecList.setFont(fnt);
        SpecList.setToolTipText("Requirements");
        SpecList.setMaximumSize(SpecList.getPreferredSize());
        SpecList.addActionListener(this);
        SpecList.setActionCommand("Requirements");

        menuBar.add(SpecList);
        
   this.setJMenuBar(menuBar);
        
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
        btnAddNote.setActionCommand("SaveNote");
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
        setTitle(/*data.courseList.getSelectedItem().toString()+*/"CourseWork & Requirements");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);  // Needed to ensure that the items can be seen.

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
       
       public void showRequirements(String x) {
        ArrayList<String> course = new ArrayList<>();
        boolean check;
        check = myCourse.getCheck();
      myCourse.setCurrentCourse(x+"Requirements");
      SpecList.removeAllItems();
            course = myCourse.readCourses();
            //course.remove("File not found");
            for (String crso : course) {
                
                SpecList.addItem(crso);
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
       
       public void showCourses() {
        ArrayList<String> course = new ArrayList<>();
        boolean check;
        check = myCourse.getCheck();
      myCourse.setCurrentCourse("Courses");
      String path= myCourse.appDir+ "//Courses.txt";
      
            course = myCourse.readCourses();
   for (String crso : course) {
                
                courseList.addItem(crso);
            }
 }
       
       private void addNote(String text, String coursename) {
      
           int x= allNotes.getMaxID();
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
    
    private void deleteRequirements(String dltcourse) {
        myCourse.setCurrentCourse(crsework+"Requirements");
        String path = myCourse.appDir + "//Courses.txt";
        // String dltpath="//"+dltcourse+".txt";
        ArrayList<String> course = new ArrayList<>();
        course = myCourse.readCourses();
        int i = course.indexOf(dltcourse);
        SpecList.removeItem(course.get(i));
        myCourse.deleteCourseFile(dltcourse, course);

    }
    
    
    public void addRequirements(String x) {
        // myCourse.setCurrentCourse(x);
        // courses=myCourse.readCourses();
        boolean crseOrCW = myCourse.getCheck();

        ArrayList<String> course = new ArrayList<>();
        String getCourse;
        String spec;
       String fileName = x;
        // courses.add(x);
        // courses.add("COMP1753");
        // courses.add(x);
        
            getCourse = myCourse.appDir + "//"+crsework+"Requirements.txt";
            myCourse.setCurrentCourse(crsework+"Requirements");
            String reqName = myCourse.appDir + "//" + x + ".txt";
            course = myCourse.readCourses();
            //check if "File not found is the list and deletes it"
            if(course.contains("File not found")){
            course.remove("File not found");
             myCourse.IncludeItem(getCourse, course);
             //SpecList.removeItem("File not found");
            }
            myCourse.createCourse(fileName, getCourse,reqName/*,courses*/);
            course.clear();
            course = myCourse.readCourses();
            int i = course.indexOf(fileName);   
            SpecList.addItem(course.get(i)); 
        
    }
    private void controller() {
        //System.out.println("controller not coded yet.");
        addAllNotes();
    }
}
