package edu.curtin.terminalgriddemo;
import edu.curtin.calplugins.AppPlugin;
import edu.curtin.terminalgrid.TerminalGrid;
import edu.curtin.terminalgriddemo.eventparser.MyParser;

import java.io.*;
import java.nio.charset.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * This illustrates different ways to use TerminalGrid. You may not feel you _need_ all the
 * different features shown here.
 */
public class TerminalGridDemo
{
    private static LocalDate firstDisplayDay = LocalDate.now();
    private static ArrayList<Event> events = new ArrayList<>();
    private static ArrayList<Map<String, Object>> notificationItems = new ArrayList<>();
    private static final ArrayList<ArrayList<Event>> hoursArrayList = new ArrayList<>();
    private static final ArrayList<Event> allDayEvents = new ArrayList<>();
    private static final Map<LocalDate, Integer> lastHourOfEachDay = new HashMap<>();
    private static final Map<LocalDate, Integer> firstHourOfEachDay = new HashMap<>();
    private static ResourceBundle bundle;
    private Map<String, String> info = null;

    public static void main(String[] args)
    {
        final int hoursPerDay = 24;
        String fileName;
        Map<String, Object> pluginsHashMap;
        String input = "";
        MyParser parser;
        String command = "";
        Map<String, ArrayList<Object>> outputMap = null;
        ArrayList<PluginObject> plugins = new ArrayList<>();
        Locale locale;
        Charset charset = null;

        /*Parsing the file*/
        fileName = args[0];

        String[] splitFileName = fileName.split("\\.");

        if(splitFileName.length == 3){
            if(splitFileName[1].equals("utf8")){
                charset = StandardCharsets.UTF_8;
            }
            else if(splitFileName[1].equals("utf16")){
                charset = StandardCharsets.UTF_16;
            }
            else if(splitFileName[1].equals("utf32")){
                charset = Charset.forName("UTF-32");
            }
        }
        else{
            charset = StandardCharsets.UTF_8;
        }


        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), charset);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }
            input = text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        parser = new MyParser(new StringReader(input));



        try {
            outputMap = parser.EventList();
        } catch (edu.curtin.terminalgriddemo.eventparser.ParseException e) {
            throw new RuntimeException(e);
        }


        ArrayList<Object> pluginsT = outputMap.get("plugins");
        ArrayList<Object> eventsT = outputMap.get("events");

        for(Object t : pluginsT ){
            plugins.add((PluginObject) t);
        }
        for(Object t : eventsT){
            events.add((Event) t);
        }

        for(Event event : events){
            pluginsHashMap = new HashMap<>();
            pluginsHashMap.put("event", event.getEvent());
            pluginsHashMap.put("startDate", event.getDate());
            pluginsHashMap.put("startTime", event.getTime());

            notificationItems.add(pluginsHashMap);

        }

        /*Locale selection*/
        System.out.printf("%s %n", "Enter the Locale (Keep this as blank for the default locale)");
        System.out.printf("%s %n", "(Available locales: English (Default), Sinhalese(si-LK), Dutch(de-DE))");

        Scanner scanner = new Scanner(System.in);
        String inputLocale = scanner.nextLine();

        if(inputLocale.isBlank()){
            bundle = ResourceBundle.getBundle("bundle");
        }else{
            bundle = ResourceBundle.getBundle("bundle", Locale.forLanguageTag(inputLocale));
        }

        new TerminalGridDemo().run(plugins);

        for(Event event : events){
             if(event.isItAllday()){
                 allDayEvents.add(event);
             }
         }

         for(int i = 0; i < hoursPerDay; i++){
             ArrayList<Event> hourX = new ArrayList<>();

             for(Event event : events){
                 if(!event.isItAllday()){
                     if(event.getHour() == i){
                         hourX.add(event);

                         lastHourOfEachDay.put(event.getDate(), event.getHour());

                         if(!firstHourOfEachDay.containsKey(event.getDate())){
                             firstHourOfEachDay.put(event.getDate(), event.getHour());
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

    public void setNewCalendarEvent(LocalDate date, String event){
        events.add(new Event(date, event));


    }

    public void setNewCalendarEvent(LocalDate date, LocalTime time, int duration, String event){
        events.add(new Event(date, time, duration, event));


    }

    public List<Event> getEventList(){
        return events;
    }

    public Map<String, String> getInfo(){
        return info;
    }
    public void run(ArrayList<PluginObject> plugins){
        for(PluginObject pluginObject : plugins){

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
                String dayOfWeek = firstDisplayDay.plusDays(i).getDayOfWeek().toString();
                colHeadings[i] = ""+firstDisplayDay.plusDays(i)+"\n"+bundle.getString(dayOfWeek);
            }

            rowHeadings[0] = bundle.getString("ALL-DAY EVENTS");

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
