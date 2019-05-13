package com.eum.program.type;

public enum OpenType {
    A("All"), M("Member");

    String type;

    OpenType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
