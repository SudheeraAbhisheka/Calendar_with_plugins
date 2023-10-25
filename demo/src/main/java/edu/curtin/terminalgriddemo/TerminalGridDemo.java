package edu.curtin.terminalgriddemo;
import edu.curtin.calplugins.AppPlugin;
import edu.curtin.terminalgrid.TerminalGrid;
import edu.curtin.terminalgriddemo.eventparser.MyParser;
import edu.curtin.terminalgriddemo.eventparser.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

/**
 * This illustrates different ways to use TerminalGrid. You may not feel you _need_ all the
 * different features shown here.
 */
public class TerminalGridDemo
{
    private static LocalDate firstDisplayDay = LocalDate.now();
    private static List<Event> list = new Stack<>();
    private static List<PluginObject> listPlugins = new ArrayList<>();
    private static ArrayList<Map<String, Object>> notificationItems = new ArrayList<>();
//    private static List<Event> list = Collections.synchronizedList(new ArrayList<>());
    private static final ArrayList<ArrayList<Event>> hoursArrayList = new ArrayList<>();
    private static final Object mutex = new Object();

    private static final ArrayList<Event> allDayEvents = new ArrayList<>();
    private static final Map<LocalDate, Integer> lastHourOfEachDay = new HashMap<>();
    private static final Map<LocalDate, Integer> firstHourOfEachDay = new HashMap<>();

    public static void main(String[] args)
    {
        final int hoursPerDay = 24;
        Map<String, Object> pluginsHashMap = new HashMap<>();
        String input;

        String command = "";

        InputFileSecond textFileSecond = new InputFileSecond("input.txt");

        list = textFileSecond.getEntryList();

        pluginsHashMap.put("title", "Sunday");
        pluginsHashMap.put("startDate", LocalDate.parse("2023-10-25"));
        pluginsHashMap.put("repeat", 7);

        listPlugins.add(new PluginObject("edu.curtin.calplugins.Repeat", pluginsHashMap));

        pluginsHashMap = new HashMap<>();
        pluginsHashMap.put("title", "Monday");
        pluginsHashMap.put("startDate", LocalDate.parse("2023-10-26"));
        pluginsHashMap.put("repeat", 7);

        listPlugins.add(new PluginObject("edu.curtin.calplugins.Repeat", pluginsHashMap));


        /*********/


        for(Event event : list){
            pluginsHashMap = new HashMap<>();
            pluginsHashMap.put("event", event.getEvent());
            pluginsHashMap.put("startDate", event.getDate());
            pluginsHashMap.put("startTime", event.getTime());

            notificationItems.add(pluginsHashMap);

        }

        listPlugins.add(new PluginObject("edu.curtin.calplugins.Notify", "Holiday"));


        /*********/


        try {
            input = Files.readString(Path.of("second.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MyParser parser = new MyParser(new StringReader(input));
        Map<String, String> parserHashMap;

        try {
            parserHashMap = parser.Event();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("%s %n", parserHashMap);


//        new TerminalGridDemo().run();



//        for(Event event : list){
//             if(event.isItAllday()){
//                 allDayEvents.add(event);
//             }
//         }
//
//        //         executor.shutdown();
//
//         for(int i = 0; i < hoursPerDay; i++){
//             ArrayList<Event> hourX = new ArrayList<>();
//
//             synchronized (mutex){
//
//             }
//
//             for(Event event : list){
//                 if(!event.isItAllday()){
//                     if(event.getHour() == i){
//                         hourX.add(event);
//
//                         lastHourOfEachDay.put(event.getDate(), event.getHour());
//
//                         if(!firstHourOfEachDay.containsKey(event.getDate())){
//                             firstHourOfEachDay.put(event.getDate(), event.getHour());
//                         }
//                     }
//                 }
//             }
//
//
//
//             hoursArrayList.add(hourX);
//         }
//
//         oneWeek(command);
//         try(Scanner sc = new Scanner(System.in)){
//             while(!command.equals("quit")){
//                 command = sc.nextLine();
//                 oneWeek(command);
//             }
//         }catch(IllegalArgumentException e){
//             System.out.println("Invalid move");
//         }
    }

    private Object info = "";
    public void setNewCalendarEvent(LocalDate date, String event){
        synchronized (mutex){

        }

        list.add(new Event(date, event));

    }

    public List<Event> getEventList(){
        return list;
    }

    public Object getInfo(){
        return info;
    }
    public void run(){
        for(PluginObject pluginObject : listPlugins){
            try{
                Class<?> pluginClass = Class.forName(pluginObject.getPluginName());
                AppPlugin appPlugin = (AppPlugin) pluginClass.getConstructor().newInstance();

                info = pluginObject.getObject();

                ApiImpl apiImpl = new ApiImpl(this);
                appPlugin.startPlugin(apiImpl);
            }
            catch(ReflectiveOperationException | ClassCastException e){
                System.out.printf("%s: %s %n", e.getClass().getName(), e.getMessage());
            }

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
        for(Event e : allDayEvents){
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

            for(Event e : allDayEvents){
                for(int day = 0; day < daysPerWeek; day++){
                    if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                        String s = String.format("%s", e.getEvent());
                        messages[0][day] = s;
                    }
                }
            }

            int hour = 1;

            for(ArrayList<Event> hourX : hoursArrayList){
                for(Event e : hourX){
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

            for(ArrayList<Event> hourX : hoursArrayList){
                for(Event e : hourX){
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

    public ArrayList<Map<String, Object>> getNotificationItems(){
        return notificationItems;
    }
}
