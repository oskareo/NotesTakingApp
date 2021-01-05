/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject;

/**
 *
 * @author oe7863y
 */
/*public class AllNotes {

    public ArrayList<Note> allNotes = new ArrayList<>();
    public int max = 0;

    public AllNotes() {
        readAllNotes();
    }

    private void readAllNotes() {
        System.out.println("readAllNotes has not been coded yet.");
    }
}*/
import java.io.IOException;
import java.util.ArrayList;

public class AllNotes extends CommonCode {

    private ArrayList<Note> allNotes = new ArrayList<>();
    private ArrayList<Note> notesRead = new ArrayList<>();
    private static String thePath;
    private String crse = "";
    private int maxID = 0;
    private boolean checkingIFammend=false;

    AllNotes() {
//getCurrentCourse();   
//getCurrentCourse();
       // readAllNotes();
//getAllNotes();

    }

    public int getMaxID() {
        maxID++;
        return maxID;
    }

    public void readAllNotes() {
        ArrayList<String> readNotes = new ArrayList<>();
        String currntPth = getCurrentCourse();
        
        readNotes = readTextFile(currntPth/*appDir + "\\Notes.txt"*/);
        System.out.println(readNotes.get(0));
        if (!"File not found".equals(readNotes.get(0))) {
      allNotes.clear();
            for (String str : readNotes) {
                String[] tmp = str.split("\t");
               
                int nid = Integer.parseInt(tmp[0]);
                 Note n = new Note(nid,tmp[1],tmp[2],tmp[3]);                
                allNotes.add(n);
                if(nid>maxID){
                    maxID=nid;
                }
            }
        }
        maxID++;
    }

    public void addNote(int maxID, String course, String note) {
        Note myNote = new Note(maxID, course, note);
    
        allNotes.clear();
        allNotes.add(myNote);//Hay problema con allNotes aqui por que tine dos notas dentro en vez de solo una
        String ntpath = appDir + "//" + course + ".txt";
        writeAllNotes(ntpath);
    }

    public void setCurrentCourse(String path) {
        String currentPath = appDir + "//" + path + ".txt";
        thePath = currentPath;
//return thePath;
    }

    public String getCurrentCourse() {

        return thePath;
    }

    public ArrayList<Note> getAllNotes() {
        return allNotes;
    }

    private void writeAllNotes(String path) {
//String path = appDir + "\\Notes.txt";
        ArrayList<String> writeNote = new ArrayList<>();
        for (Note n : allNotes) {
            String tmp = n.getNoteID() + "\t";
            tmp += n.getCourse() + "\t";
            tmp += n.getDayte() + "\t";
            tmp += n.getNote() + "\t";

            writeNote.add(tmp);
        }
        try {
            if(getCheckifAmmend()!=true){
            appendTextFile(path, writeNote);
            }
            else{
            writeTextFile(path,writeNote );
            }
        } catch (IOException ex) {
            System.out.println("Problem! " + path);
        }
    }

    public String searchAllNotesByKeyword(String noteList, int i, String s) {
        readAllNotes();
        if (i == allNotes.size()) {
            return noteList;
        }

        if (allNotes.get(i).getNote().contains(s)) {
            noteList += allNotes.get(i).getNote() + "\n";
        }
        return searchAllNotesByKeyword(noteList, i + 1, s);
    }
    public void setCheckifAmmend(boolean checkAmmend){
    checkingIFammend= checkAmmend;
    }
    
    private boolean getCheckifAmmend(){
    
    return this.checkingIFammend;}

}
