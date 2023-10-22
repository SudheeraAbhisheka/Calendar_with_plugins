package edu.curtin.terminalgriddemo;
import edu.curtin.calplugins.AppPlugin;
import edu.curtin.calplugins.RepeatObject;
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

        String command = "";

        InputFileSecond textFileSecond = new InputFileSecond("input.txt");

         List<EventObject> list = textFileSecond.getEntryList();

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

//        new TerminalGridDemo().run(); // This should run without hardcoded line


    }

    private Object info = "";
    public String Hallelujha;

    public Object getInfo(){
        return info;
    }
    public void run(){
        var plugins = new ArrayList<AppPlugin>();

        String api_name = "edu.curtin.calplugins.Repeat";
        RepeatObject repeatObject = new RepeatObject("Sunday", "2023-12-25", "5");
        info = repeatObject;

        try{
            Class<?> pluginClass = Class.forName(api_name);
            plugins.add((AppPlugin) pluginClass.getConstructor().newInstance());

        }
        catch(ReflectiveOperationException | ClassCastException e){
            System.out.printf("%s: %s %n", e.getClass().getName(), e.getMessage());
        }

        ApiImpl apiImpl = new ApiImpl(this);

        for(AppPlugin plugin : plugins){
            plugin.startPlugin(apiImpl);
        }
    }
    private static void oneWeek(String command){
        System.out.println("\033[2J\033[H"); // Clearing the screen
        int lastHour = 0; // Initialization
        int firstHour = 24; // Initialization

        final int daysPerWeek = 7;
        final int allDayEventRow = 1;

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
            int hoursPerDay = 0;

            if(firstHour == 24){
                hoursPerDay = 0;

            }
            else{
                hoursPerDay = lastHour-firstHour+1;

            }


            String[][] messages = new String[hoursPerDay+allDayEventRow][daysPerWeek];
            
            for(int i=0; i < hoursPerDay+allDayEventRow; i++){
                for(int j=0; j < daysPerWeek; j++){
                    messages[i][j] = "";
                }
            }

            for(EventObject e : allDayEvents){
                for(int day = 0; day < daysPerWeek; day++){
                    if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                        String s = String.format("%s", e.getEvent());
                        messages[0][day] = s;
                    }
                }
            }

            int hour = 1;

            for(ArrayList<EventObject> hourX : hoursArrayList){
                for(EventObject e : hourX){
                    for(int day = 0; day < daysPerWeek; day++){
                        if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                            String s = String.format("%s\nDuration: %smins\nStart time: %s", e.getEvent(), e.getDuration(), e.getTime());
                            messages[hour-firstHour][day] = s;
                        }
                    }
                }

                hour++;
            }
                        
            String[] colHeadings = new String[daysPerWeek];
            String[] rowHeadings = new String[hoursPerDay+allDayEventRow];

            for(int i = 0; i < daysPerWeek; i++){
                colHeadings[i] = ""+firstDisplayDay.plusDays(i)+"\n"+firstDisplayDay.plusDays(i).getDayOfWeek();
            }

            rowHeadings[0] = "Add-day events";

            for(int i = 0; i < hoursPerDay; i++){
                rowHeadings[i+allDayEventRow] = "Hour "+(i+firstHour);
            }

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
                            String s = String.format("%s\nDuration: %smins\nStart time: %s", e.getEvent(), e.getDuration(), e.getTime());
                            messages[hour-firstHour][day] = s;
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
