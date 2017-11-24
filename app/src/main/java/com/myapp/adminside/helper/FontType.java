package com.myapp.adminside.helper;

/**
 * Created by sagartahelyani on 23-02-2017.
 */

public enum FontType {

    Regular(1),

    Bold(2);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    FontType(int id) {
        this.id = id;
    }
}
