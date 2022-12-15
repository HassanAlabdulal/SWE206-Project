package com.example.swe206project;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Plan implements Initializable, Serializable{
    protected int id;
    protected String planName;
    protected ArrayList<Session> sessionsList = new ArrayList<>();

    public Plan(){
        //sessionsList = (ArrayList<Plan>) initilize("Plan"); // to be worked in init
    }

    public Plan(String planName, ArrayList<Session> sessionsList){
        this.planName = planName;
        this.sessionsList = sessionsList;
        id = IDGenerator.generate("Plans.txt");
        save(id, planName, sessionsList);
    }

    public Plan(int id){ 
        this.id = id;
        this.planName = getPlanName(id);
        this.sessionsList = getSessionsList(id);
    }

    public void save(int id, String planName, ArrayList<Session> sessionsList){
        WriteFiles writer = new WriteFiles("Plans.txt", true);
        String data = id + " " + planName + "$ " + sessionsList;
        try {
            writer.writeToFile(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ArrayList<Session> getSessionsList(int id){
        ReadFiles read = new ReadFiles<>("Plans.txt");
        ArrayList<Session> list = new ArrayList<>();
        for(String session : read.fetch(String.valueOf(id), "\\[\\p{ASCII}*\\]").replaceAll("\\s|\\[|\\]", "").split(",")){
            Session ses = new Session(Integer.valueOf(session));
            list.add(ses);
        }
        return list;
    }

    public static String getPlanName(int id){
        ReadFiles read = new ReadFiles<>("Plans.txt");
        return read.fetch(String.valueOf(id), "\\p{Graph}*\\$").replaceAll("\\$", "");
    }

    public ArrayList<Session> getSessionsList(){
        return sessionsList;
    }

    public String getPlanName(){
        return planName;
    }

    public String toString(){
        if(id != 0)
            return planName+" "+id+"";
        else
            return "No plan";
    }

}
