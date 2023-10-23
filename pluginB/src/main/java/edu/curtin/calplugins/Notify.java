package edu.curtin.calplugins;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class Notify implements AppPlugin{
    @Override
    public void startPlugin(AppPluginAPI api) {
        ArrayList<Map<String, Object>> notificationItems;
        LocalDate startDate;
        LocalTime startTime;
        String title;

        notificationItems = (ArrayList<Map<String, Object>>) api.getInfo();

//        while(true){
//            try {
//                for(Map<String, Object> notificationItem : notificationItems){
//                    startDate = (LocalDate) notificationItem.get("startDate");
//                    startTime = (LocalTime) notificationItem.get("startTime");
//                    title = notificationItem.get("title").toString();
//
//                    if(LocalDate.now().equals(startDate)){
//                        if(LocalTime.now().equals(startTime)){
//                            JOptionPane.showMessageDialog(null, title);
//                        }
//
//                    }
//
//                }
//
//
//                Thread.sleep(1000);
//
//            } catch (InterruptedException e) {
//                System.out.printf("%s %n", e.getMessage());
//            }
//        }

        JOptionPane.showMessageDialog(null, "Hello world");




    }
}
