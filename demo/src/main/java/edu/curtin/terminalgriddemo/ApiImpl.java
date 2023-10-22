package edu.curtin.terminalgriddemo;

import edu.curtin.calplugins.AppPluginAPI;

public class ApiImpl implements AppPluginAPI {
    private TerminalGridDemo obj;

    public ApiImpl(TerminalGridDemo obj){
        this.obj = obj;
    }

    @Override
    public Object getInfo(){
        return obj.getInfo();
    }
}