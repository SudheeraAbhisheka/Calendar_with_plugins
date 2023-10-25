package edu.curtin.calplugins;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.*;

public class Notify implements AppPlugin{
    @Override
    public void startPlugin(AppPluginAPI api) {
        ArrayList<Map<String, Object>> notificationItems;
        String title;


        title = api.getInfo().toString();
        notificationItems = api.getEvents();
        ExecutorService executor = Executors.newCachedThreadPool();


        for(Map<String, Object> notificationItem : notificationItems){
            LocalDate startDate;
            LocalTime startTime;
            String event;

            startDate = (LocalDate) notificationItem.get("startDate");
            startTime = (LocalTime) notificationItem.get("startTime");
            event = notificationItem.get("event").toString();

            if(event.toLowerCase().contains(title.toLowerCase())){
                CompletableFuture.runAsync(() -> thread(event, startDate, startTime), executor);

                executor.execute(() -> {

                });
            }
        }

    }

    private void thread(String event, LocalDate startDate, LocalTime startTime){
        while(true){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


                            System.out.printf("%s %s - %s %n", event, LocalTime.now().format(formatter), startTime.format(formatter));

                if(LocalDate.now().equals(startDate)){
                    if(LocalTime.now().format(formatter).equals(startTime.format(formatter))){
                        JOptionPane.showMessageDialog(null, event);

//                                    executor.shutdown();

                        return;
                    }
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                System.out.printf("%s %n", e.getMessage());
            }
        }
    }
}
