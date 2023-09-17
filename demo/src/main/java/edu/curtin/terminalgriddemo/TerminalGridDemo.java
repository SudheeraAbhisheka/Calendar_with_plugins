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
    // private static ArrayList<EventObject> hourZero = new ArrayList<>();
    // private static ArrayList<EventObject> hourOne = new ArrayList<>();
    private static LocalDate firstDisplayDay = LocalDate.now();
    private static ArrayList<ArrayList<EventObject>> hoursArrayList = new ArrayList<>(); 
    public static void main(String[] args)
    {
        final int hoursPerDay = 24;

        InputFile textFile;
        String command = "";

        textFile = new InputFile("input.txt");

        List<EventObject> list = textFile.getEntryList();

        // ArrayList<EventObject> hour0 = new ArrayList<>();
        // ArrayList<EventObject> hour1 = new ArrayList<>();

        // for(EventObject l : list){
        //     if(l.getHour() == 0){
        //         hour0.add(l);
        //     }
        //     else if(l.getHour() == 1){
        //         hour1.add(l);
        //     }
        // }

        // hoursArrayList.add(hourZero);
        // hoursArrayList.add(hourOne);


        /****************** */

        for(int i = 0; i < hoursPerDay; i++){
            ArrayList<EventObject> hourX = new ArrayList<>();

            for(EventObject enEventObject : list){
                if(enEventObject.getHour() == i){
                    hourX.add(enEventObject);
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

    private static void printCalendar(){
        System.out.println("\033[2J\033[H");
        var terminalGrid = TerminalGrid.create();

        var listMessages = new ArrayList<List<String>>();
        var hour1 = new ArrayList<String>();
        var hour2 = new ArrayList<String>();
        var hour3 = new ArrayList<String>();
        var hour4 = new ArrayList<String>();
        var hour5 = new ArrayList<String>();
        var hour6 = new ArrayList<String>();
        var hour7 = new ArrayList<String>();
                
        hour1.add("one");
        hour1.add("two");
        hour2.add("one");
        hour2.add("two");
        hour3.add("three");
        hour3.add("four");
        hour4.add("one");
        hour4.add("two");
        hour5.add("three");
        hour5.add("four");
        hour6.add("one");
        hour6.add("two");
        hour7.add("three");
        hour7.add("four");

        listMessages.add(hour1);
        listMessages.add(hour2);
        listMessages.add(hour4);
        listMessages.add(hour4);
        listMessages.add(hour5);
        listMessages.add(hour6);
        listMessages.add(hour7);

        var rowHeadings = new ArrayList<String>();
        rowHeadings.add("Hour1");
        rowHeadings.add("Hour2");
        rowHeadings.add("Hour3");
        rowHeadings.add("Hour4");
        rowHeadings.add("Hour5");
        rowHeadings.add("Hour6");
  

        ArrayList<String> colHeadings = new ArrayList<String>();
        colHeadings.add("Day1");
        colHeadings.add("Day2");

        
        terminalGrid.print(listMessages); // , List.of("row 1", "row 2"), List.of("col A", "col B")
        System.out.println();

    }

    private static void exampleTables(){
        System.out.println("\033[2J\033[H");
            // Demonstration data
        String[][] messages = {{"one two three",     "four five six",             "seven eight nine"}, 
                               {"ten eleven twelve", "thirteen fourteen fifteen", "sixteen seventeen eighteen"}};
                      
        String[] rowHeadings = {"row 1", "row 2"};
        String[] colHeadings = {"column A", "column B", "column C"};
        
        
        // Initialising
        var terminalGrid = TerminalGrid.create();
        
        
        // Without headings
        terminalGrid.print(messages);
        System.out.println();

        
        // With headings
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();
        
        
        // Using nested lists rather than arrays
        var listMessages = new ArrayList<List<String>>();
        var row1 = new ArrayList<String>();
        var row2 = new ArrayList<String>();
        row1.add("one");
        row1.add("two");
        row2.add("three");
        row2.add("four");
        listMessages.add(row1);
        listMessages.add(row2);
        terminalGrid.print(listMessages, List.of("row 1", "row 2"), List.of("col A", "col B"));
        System.out.println();
        
        
        // With limited space
        terminalGrid.setTerminalWidth(42);
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();
        
        
        // With plain ASCII characters (if the real box-drawing characters don't display properly)
        terminalGrid.setTerminalWidth(120);
        terminalGrid.setBoxChars(TerminalGrid.ASCII_BOX_CHARS);
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();

        
        // With custom box-drawing characters (if you must!)
        terminalGrid.setBoxChars(new TerminalGrid.BoxCharSet("│ ", " ┊ ", " │", "─", "╌", "─", "╭─", "─╮",  "╰─",  "─╯", "─┬─", "─┴─", "├╌", "╌┤", "╌┼╌"));
        terminalGrid.print(messages, rowHeadings, colHeadings);     
    }
    private static void oneWeek(String command){
        System.out.println("\033[2J\033[H"); // Clear the screen

        final int daysPerWeek = 7;
        final int hoursPerDay = 24;

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
                firstDisplayDay = LocalDate.now();
            }
            
        }

        String[][] messages = new String[24][7];
        
        for(int i=0; i < hoursPerDay; i++){
            for(int j=0; j < daysPerWeek; j++){
                messages[i][j] = "";
            }
        }

        int k = 0;

        for(ArrayList<EventObject> hourX : hoursArrayList){
            for(EventObject e : hourX){
                for(int day = 0; day < daysPerWeek; day++){
                    if(firstDisplayDay.plusDays(day).equals(e.getDate())){
                        messages[k][day] = e.getEvent();
                    }
                }
            }

            k++;
        }

        // for(EventObject e : hourZero){
        //     for(int day = 0; day < daysPerWeek; day++){
        //         if(firstDisplayDay.plusDays(day).equals(e.getDate())){
        //             messages[0][day] = e.getEvent();
        //         }
        //     }
        // }
        // for(EventObject e : hourOne){
        //     for(int day = 0; day < daysPerWeek; day++){
        //         if(firstDisplayDay.plusDays(day).equals(e.getDate())){
        //             messages[1][day] = e.getEvent();
        //         }
        //     }
        // }


                      
        String[] colHeadings = new String[daysPerWeek];
        String[] rowHeadings = new String[hoursPerDay];

        for(int i = 0; i < daysPerWeek; i++){
            colHeadings[i] = ""+firstDisplayDay.plusDays(i)+"\n"+firstDisplayDay.plusDays(i).getDayOfWeek();
        }
        for(int i = 0; i < hoursPerDay; i++){
            rowHeadings[i] = "Hour "+i;
        }

        var terminalGrid = TerminalGrid.create();
        terminalGrid.setTerminalWidth(140);
        terminalGrid.print(messages, rowHeadings, colHeadings);
        System.out.println();

    }
}
