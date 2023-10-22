package edu.curtin.calplugins;

public class Repeat implements AppPlugin{
    @Override
    public void startPlugin(AppPluginAPI api) {
        System.out.printf("%s %n", "This is a plugin doing this!!");
        RepeatObject repeatObject = (RepeatObject) api.getInfo();

        System.out.printf("%s %s %s %n", repeatObject.getTitle(), repeatObject.getStartDate(), repeatObject.getRepeat());

    }
}
