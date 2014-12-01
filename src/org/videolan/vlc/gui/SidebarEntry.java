package org.videolan.vlc.gui;

import the.joevlc.VLCApplication;

public class SidebarEntry {
    public String id;
    String name;
    int drawableID;

    public SidebarEntry(String _id, String _name, int _drawableID) {
        this.id = _id;
        this.name = _name;
        this.drawableID = _drawableID;
    }

    public SidebarEntry(String _id, int _name, int _drawableID) {
        this.id = _id;
        this.name = VLCApplication.getAppContext().getString(_name);
        this.drawableID = _drawableID;
    }
}