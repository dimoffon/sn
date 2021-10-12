package io.dimoffon.sn.controller;

public enum ViewHolder {

    PERSONAL_PAGE("personal")
    ;

    private String viewName;

    ViewHolder(final String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
