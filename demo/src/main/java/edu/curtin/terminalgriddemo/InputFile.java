package edu.curtin.terminalgriddemo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class InputFile {
    private List<EventObject> entries = new LinkedList<>();

    public InputFile(String pFileName){
        readFile(pFileName);
    }

    private void readFile(String pFileName){ 
        String line;
        try(            
            FileInputStream fileStream  = new FileInputStream(pFileName);
            InputStreamReader rdr       = new InputStreamReader(fileStream);
            BufferedReader bufRdr       = new BufferedReader(rdr);
            ){
                line = bufRdr.readLine();
                while(line != null){
                    processLine(line);
                    line = bufRdr.readLine();
                }
        }
        catch(IOException errorDetails){
            System.out.println("Error in fileProcessing: " + errorDetails.getMessage());
        }
    }

    private void processLine(String csvRow){
        String[] splitLine;
        splitLine = csvRow.split(" ");
        String event = "";

        if(splitLine[0].equals("event")){
            EventObject entry = new EventObject(LocalDate.parse(splitLine[1]));
            
            if(splitLine[2].equals("all-day")){
                entry.setItAllday(true);

                
                for(int i = 3; i < splitLine.length; i++){
                    event = event + splitLine[i] + " ";
                }
            }
            else{
                entry.setItAllday(false);
                entry.setDuration(Integer.parseInt(splitLine[3]));
                entry.setTime(LocalTime.parse(splitLine[2]));

                
                for(int i = 4; i < splitLine.length; i++){
                    event = event + splitLine[i] + " ";
                }
            }


            entry.setEvent(event);

            entries.add(entry);
        }
    }


    public List<EventObject> getEntryList(){
        return entries;
    }
}
