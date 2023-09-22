package edu.curtin.terminalgriddemo;
import edu.curtin.terminalgrid.TerminalGrid;

import java.time.LocalDate;
import java.util.*;

/**
 * This illustrates different ways to use TerminalGrid. You may not feel you _need_ all the 
 * different features shown here.
 */
public class TerminalGridDemo
{
    private static LocalDate firstDisplayDay = LocalDate.now();
    private static ArrayList<ArrayList<EventObject>> hoursArrayList = new ArrayList<>(); 
    private static ArrayList<EventObject> allDayEvents = new ArrayList<>();
    private static Map<LocalDate, Integer> lastHourOfEachDay = new HashMap<>();
    private static Map<LocalDate, Integer> firstHourOfEachDay = new HashMap<>();

    public static void main(String[] args)
    {
        final int hoursPerDay = 24;

        InputFile textFile;
        String command = "";

        textFile = new InputFile("input.txt");

        

        List<EventObject> list = textFile.getEntryList();

        for(EventObject eventObject : list){
            if(eventObject.isItAllday()){
                allDayEvents.add(eventObject);
            }
        }

        for(int i = 0; i < hoursPerDay; i++){
            ArrayList<EventObject> hourX = new ArrayList<>();

            for(EventObject eventObject : list){
                if(!eventObject.isItAllday()){
                    if(eventObject.getHour() == i){
                        hourX.add(eventObject);

                        lastHourOfEachDay.put(eventObject.getDate(), eventObject.getHour());

                        if(!firstHourOfEachDay.containsKey(eventObject.getDate())){
                            firstHourOfEachDay.put(eventObject.getDate(), eventObject.getHour());
                        }
                    }
                }
            }

            hoursArrayList.add(hourX);
        }


        



        oneWeek(command);
        try(Scanner sc = new Scanner(System.in)){
            while(!command.equals("quit")){
                command = sc.nextLine();
                oneWeek(command);
            }
        }catch(IllegalArgumentException e){
            System.out.println("Invalid move");
        }

    }

    private static void oneWeek(String command){
        System.out.println("\033[2J\033[H"); // Clearing the screen
        int lastHour = 0; // Initialization
        int firstHour = 24; // Initialization

        final int daysPerWeek = 7;

        switch(command){
            case ""     -> {

            }
            case "+d"   -> {
                firstDisplayDay = firstDisplayDay.plusDays(1);
            }
            case "+w"   -> {
                firstDisplayDay = firstDisplayDay.plusWeeks(1);
            }
            case "+m"   -> {
                firstDisplayDay = firstDisplayDay.plusMonths(1);
            }
            case "+y"   -> {
                firstDisplayDay = firstDisplayDay.plusYears(1);
            }
            case "-d"   -> {
                firstDisplayDay = firstDisplayDay.minusDays(1);
            }
            case "-w"   -> {
                firstDisplayDay = firstDisplayDay.minusWeeks(1);
            }
            case "-m"   -> {
                firstDisplayDay = firstDisplayDay.minusMonths(1);
            }
            case "-y"   -> {
                firstDisplayDay = firstDisplayDay.minusYears(1);
            }
            case "t"   -> {
                firstDisplayDay = LocalDate.now();
            }
            default     -> {
                // do nothing
            }
            
        }

        for(int i = 0; i < daysPerWeek; i++){
            if(lastHourOfEachDay.containsKey(firstDisplayDay.plusDays(i))){
                if(lastHour < lastHourOfEachDay.get(firstDisplayDay.plusDays(i))){
                    lastHour = lastHourOfEachDay.get(firstDisplayDay.plusDays(i));
                }
            }
        }

        for(int i = 0; i < daysPerWeek; i++){
            if(firstHourOfEachDay.containsKey(firstDisplayDay.plusDays(i))){
                if(firstHour > firstHourOfEachDay.get(firstDisplayDay.plusDays(i))){
                    firstHour = firstHourOfEachDay.get(firstDisplayDay.plusDays(i));
                }
            }
        }

        boolean thereAreAllDayEvents = false;
        for(EventObject e : allDayEvents){
            for(int day = 0; day < daysPerWeek; day++){
                if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                    thereAreAllDayEvents =true;
                    break;
                }
            }
        }

        if(thereAreAllDayEvents){
            String[][] messages = new String[1][7];
            for(int i=0; i < 7; i++){
                messages[0][i] = "";
            }

            for(EventObject e : allDayEvents){
                for(int day = 0; day < daysPerWeek; day++){
                    if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                        messages[0][day] = e.getEvent();
                    }
                }
            }

            String[] colHeadings = new String[daysPerWeek];
            String[] rowHeadings = new String[1];

            for(int i = 0; i < daysPerWeek; i++){
                colHeadings[i] = ""+firstDisplayDay.plusDays(i)+"\n"+firstDisplayDay.plusDays(i).getDayOfWeek();
            }
            rowHeadings[0] = "Add-day events";

            var terminalGrid = TerminalGrid.create();
            terminalGrid.setTerminalWidth(140);
            terminalGrid.print(messages, rowHeadings, colHeadings);
            System.out.println();
        }
        else{
            if(firstHour == 24){
                firstHour = 0;
            }

            final int hoursPerDay = lastHour-firstHour+1;

            String[][] messages = new String[hoursPerDay][daysPerWeek];
            
            for(int i=0; i < hoursPerDay; i++){
                for(int j=0; j < daysPerWeek; j++){
                    messages[i][j] = "";
                }
            }

            int hour = 0;

            for(ArrayList<EventObject> hourX : hoursArrayList){
                for(EventObject e : hourX){
                    for(int day = 0; day < daysPerWeek; day++){
                        if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                            messages[hour-firstHour][day] = e.getEvent();
                        }
                    }
                }

                hour++;
            }
                        
            String[] colHeadings = new String[daysPerWeek];
            String[] rowHeadings = new String[hoursPerDay];

            for(int i = 0; i < daysPerWeek; i++){
                colHeadings[i] = ""+firstDisplayDay.plusDays(i)+"\n"+firstDisplayDay.plusDays(i).getDayOfWeek();
            }
            for(int i = 0; i < hoursPerDay; i++){
                rowHeadings[i] = "Hour "+(i+firstHour);
            }

            var terminalGrid = TerminalGrid.create();
            terminalGrid.setTerminalWidth(140);
            terminalGrid.print(messages, rowHeadings, colHeadings);
            System.out.println();
        }
    }
}
