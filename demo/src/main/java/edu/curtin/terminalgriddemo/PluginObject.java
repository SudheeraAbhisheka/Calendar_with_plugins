package edu.curtin.terminalgriddemo;

public class PluginObject {
    private String pluginName;
    private Object object;

    public PluginObject(String pluginName, Object object) {
        this.pluginName = pluginName;
        this.object = object;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
