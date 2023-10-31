package edu.curtin.terminalgriddemo;

import java.util.Map;

public class PluginObject {
    private String pluginName;
    private Map<String, String> object;

    public PluginObject(String pluginName, Map<String, String> object) {
        this.pluginName = pluginName;
        this.object = object;
    }

    public Map<String, String> getObject() {
        return object;
    }

    public String getPluginName() {
        return pluginName;
    }
}
