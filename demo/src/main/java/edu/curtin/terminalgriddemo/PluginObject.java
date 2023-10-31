package edu.curtin.terminalgriddemo;

public class PluginObject {
    private String pluginName;
    private Object object;

    public PluginObject(String pluginName, Object object) {
        this.pluginName = pluginName;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String getPluginName() {
        return pluginName;
    }
}
