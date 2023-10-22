package edu.curtin.terminalgriddemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class InputFileSecond {
    private List<EventObject> entries = new LinkedList<>();

    InputFileSecond(String fileName){
        boolean eventStarted = false;
        boolean messageStarted = false;
        String message = "";
        ArrayList<String> oneEvent = new ArrayList<>();

        try {
          File myObj = new File(fileName);
          Scanner myReader = new Scanner(myObj);

          while (myReader.hasNext()) {
            String data = myReader.next();

            if(data.equals("event")){
                oneEvent = new ArrayList<>();
                eventStarted = true;
            }
            else if(data.equals("plugin")){

            }

            else if(eventStarted){
                if(data.charAt(0) == '"'){
                    messageStarted = true;
                    message = data;
                }
                else if(messageStarted) {
                    message = message + " " + data;

                    if(data.charAt(data.length() - 1) == '"'){
                        messageStarted = false;
                        eventStarted = false;
                        String valuesInQuotes = message.substring(1, message.length() - 1);
                        oneEvent.add(valuesInQuotes);

                        splitLine(oneEvent);
                    }
                }
                else{
                    oneEvent.add(data);
                }
            }


          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }

    private void splitLine(ArrayList<String> oneEvent){
        EventObject entry;

        if(oneEvent.get(1).equals("all-day")){
            entry = new EventObject(LocalDate.parse(oneEvent.get(0)), oneEvent.get(2));
        }
        else{
            entry = new EventObject(LocalDate.parse(oneEvent.get(0)), LocalTime.parse(oneEvent.get(1)),
                    Integer.parseInt(oneEvent.get(2)), oneEvent.get(3));
        }

        entries.add(entry);

    }

    public List<EventObject> getEntryList(){
        return entries;
    }

}
