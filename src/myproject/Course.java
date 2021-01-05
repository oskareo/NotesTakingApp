
package myproject;

import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
/**
 *
 * @author oe7863y
 */
public class Course extends CommonCode {

    public static String crseName;
    public static String crseNamePath;
    private static boolean check;
     private boolean checkCW=false;

    ArrayList<String> theCourse = new ArrayList<>();
    private static boolean chekReq;
    private boolean checkIfReqOpen=false;
    private boolean dltOnlyCW;

    //Following class set the name of the course that is being selected in the list of courses
    public void setCurrentCourse(String x) {
        String name = x;
        crseName = name;
        crseNamePath = appDir + "//" + name + ".txt";//gets the name of the course and adds a path to it to create a file
    }
//Following method get the path created in the method setCurrentCourse and returns it when its called
    public String getCurrentCoursePath() {

        return crseNamePath;
    }
//Used to get the name of the course, coursework or requirements selected
    public String getCurrentCourse() {

        return crseName;
    }

    //Creates a new course file 
    public void createCourse(String x,String path, String a) {
        ArrayList<String> course = new ArrayList<>();//Variable to add the new course o a list
        ArrayList<String> thecourses = new ArrayList<>();

       
        CreateCourseFile(path,a);
        course.clear();//List is cleared to make sure its empty before receiving any new course
        course.add(x);//course is being added to a list
        //Next code adds a space to separate in the files where all the name of courses ar stored
        //Adds the new course to a new list
        for (int i = 0; i < course.size(); i++) {
            String courses = course.get(i) + "\t";
            thecourses.add(courses);

        }
        if ("Courses".equals(getCurrentCourse())){
        File file= new File(appDir+ "//" + x + ".txt");

//chekc if the file alredy exist and if it doesn't
//it creates a new one
try{
if(!file.exists()){
file.createNewFile();
}else{
System.out.println("File "+x+" alredy exists.");
}
    
}catch(IOException e){
   // System.out.println("File "+x+" alredy exists.");
    //this is to create a file to store a list of requirements 
//if a new coursework is created
}
        }
        //appens the new course to the list of courses
        try {
            appendTextFile(path, thecourses);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }

    }
//reads from a file and passes it to a list
    public ArrayList<String> readCourses() {
        ArrayList<String> course = new ArrayList<>();
        ArrayList<String> readingcourse = new ArrayList<>();
        String path = getCurrentCoursePath();
        course = readTextFile(path);

        for (String str : course) {
            int i = 0;
            i++;
            String[] tmp = str.split("\t");

            readingcourse.add(tmp[i -1]);
   }
     return readingcourse;
    }
    
    
//Creates a new file for the new course, coursework or requirements
    //Gets the new course from the CourseParge class
    public void CreateCourseFile(String x,String a) {
        String path=x;
File file= new File(path);

//chekc if the file alredy exist and if it doesn't
//it creates a new one
try{
if(!file.exists()){
file.createNewFile();
}else{
System.out.println("File "+x+" alredy exists.");
}
    
}catch(IOException e){
    //this is to create a file to store a list of requirements 
//if a new coursework is created
}
    if (a!=""){
        File Specfile= new File(a);
    try{
if(!Specfile.exists()){
Specfile.createNewFile();
}else{
System.out.println("File "+a+" alredy exists.");
}
    
}catch(IOException e){
    
}
    }
    }
//whenever a course is deleted this method is used to delete its according file
    public void deleteCourseFile(String x, ArrayList<String> a) {
        ArrayList<String> course = new ArrayList<>();//to get the list from a file
        ArrayList<String> backupcourse = new ArrayList<>();//get the list sorted
        course = a;//take the list courses
       course.remove(x);//the wanted course id removed from the list
        String path = appDir + "//Courses.txt";

        //Separate the names of the courses and put it in a new list
        //and rewrite the new edited list of courses to its file
        for (int i = 0; i < course.size(); i++) {
            String courses = course.get(i) + "\t";
            backupcourse.add(courses);

        }
        try {
            writeTextFile(path, backupcourse);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }

       deleteCW(x);//delete all the courses that belong to the deleted course
        String notePath = appDir + "//" + x + ".txt";
        //delete the file of the course and its notes
       try
        {
            Files.deleteIfExists(Paths.get(notePath));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
         
        System.out.println("Deletion successful.");
    
    }
    
    public void deleteCWFiles(String x, ArrayList<String> a){
    
    ArrayList<String> course = new ArrayList<>();//to get the list from a file
        ArrayList<String> backupcourse = new ArrayList<>();//get the list sorted
        course = a;//take the list courses
       course.remove(x);//the wanted course id removed from the list
        String path = getCurrentCoursePath();

        //Separate the names of the courses and put it in a new list
        //and rewrite the new edited list of courses to its file
        for (int i = 0; i < course.size(); i++) {
            String courses = course.get(i) + "\t";
            backupcourse.add(courses);

        }
        try {
            writeTextFile(path, backupcourse);
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }

      
        String notePath = appDir + "//" + x + ".txt";
        //delete the file of the course and its notes
       try
        {
            Files.deleteIfExists(Paths.get(notePath));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
         
        System.out.println("Deletion successful.");
    
    }
    //deletes all the course works belonging to the deleted course
    private void deleteCW(String x){
        //path to file containig the list of courseworks
    String pathCW= appDir +"//" + x + "CourseWork"+".txt";
    String pathCWreq="";
    ArrayList<String> cWList1= new ArrayList<>();
    ArrayList<String> cWList2= new ArrayList<>();
    ArrayList<String> cWList3= new ArrayList<>();
    cWList1 = readTextFile(pathCW);//get the list of courseworks from the file
    ArrayList<String> reqList= new ArrayList<>();
    
    //creates list of course works separated
     for (String str : cWList1) {
            int i = 0;
            i++;
            String[] tmp = str.split("\t");

            cWList2.add(tmp[i -1]);
            int j=0;
            //each coursework has requiremts
            //these are the paths for the file containing the list requirements
          pathCWreq=appDir+"//"+ cWList2.get(j)+"Requirements"+".txt";
          //reads from the requirements files
          cWList3=readTextFile(pathCWreq);
           //creates list of the requiremts in a course work 
          for (String list : cWList3) {
            int a = 0;
            a++;
            String[] temp = list.split("\t");

            reqList.add(temp[a -1]);
            
            int b=0;
            //these are the paths for the files of the requirements
            String reqfiles= appDir+"//"+ reqList.get(b)+".txt";
            
            //delete all requirement's files
            try
        {
            Files.deleteIfExists(Paths.get(reqfiles));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
            b++;
          }
          //deletes all the coursework files
           try
        {
            Files.deleteIfExists(Paths.get(pathCWreq));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
            j++;
            }
    // deletes the list of courseworks
     try
        {
            Files.deleteIfExists(Paths.get(pathCW));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
    
    }
    
    //method similar to the one before
    //used when only courseworks want to be deleted
    public void deleteCW(String x,String y){
    String thePath= appDir+"//" + x + "CourseWork"+".txt";
    ArrayList<String> courseW = new ArrayList<>();
    ArrayList<String> courseW2 = new ArrayList<>();
    ArrayList<String> readingcourseW = new ArrayList<>();
    courseW=readTextFile(thePath);
    for (String str : courseW) {
            int i = 0;
            i++;
            String[] tmp = str.split("\t");

            readingcourseW.add(tmp[i -1]);
    }
    readingcourseW.remove(y);
    String thePath2= appDir+"//" + y+ "Requirements"+".txt";
    courseW2=readTextFile(thePath2);
    ArrayList<String> cwList = new ArrayList<>();
     for (String list : courseW2) {
            int a = 0;
            a++;
            String[] temp = list.split("\t");

            cwList.add(temp[a -1]);
            
            int b=0;
            String cwfiles= appDir+"//"+ cwList.get(b)+".txt";
            try
        {
            Files.deleteIfExists(Paths.get(cwfiles));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
            b++;
          }
     try
        {
            Files.deleteIfExists(Paths.get(thePath2));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
    
    }
    
    //this method is used for stetic
    //add all the courses to a file when a new course is added
    //and "file not found" appers in the list of courses
    public void IncludeItem(String y, ArrayList<String> course){
        ArrayList<String> thecourses= new ArrayList<>();
         for (int i = 0; i < course.size(); i++) {
            String courses = course.get(i) + "\t";
            thecourses.add(courses);

        }
    try {
            writeTextFile(y, thecourses);
        } catch (IOException ex) {
            System.out.println("Problem! " + y);
        }
    
    }

    //checks if either a course or coursework is being created
    //input is set
public void setcheckCourseOrWork(boolean x){

check=x;
    
}

public boolean getCheck(){
return check;
}
//checks if the action comand for the coursework has been activated
   public void setifOpenCW(boolean b) {
      checkCW=b;
   
   }
   
   public boolean getIFopenCW(){
       boolean checking=false;
   return checkCW;
   }

    void setcheckCWOrReq(boolean b) {
         chekReq= b;
    }
//checks if action comand for requiremts has been accesed
    void setifOpenReq(boolean b) {
        checkIfReqOpen=b;
    }

    boolean getIFopenReq() {
       boolean checking=false;
       return checkIfReqOpen;
    }

}
