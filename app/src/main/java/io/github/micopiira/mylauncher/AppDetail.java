package io.github.micopiira.mylauncher;

import android.graphics.drawable.Drawable;

@SuppressWarnings("WeakerAccess")
class AppDetail {
    private String label;
    private String name;
    private Drawable icon;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
