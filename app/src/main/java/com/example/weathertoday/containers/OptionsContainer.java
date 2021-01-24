package com.example.weathertoday.containers;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class OptionsContainer {

    private static final ArrayList<ConstraintLayout> visibleLayouts = new ArrayList<>();

    public static void addVisible(ConstraintLayout layout) {
        visibleLayouts.add(layout);
    }

    public static void removeVisible(ConstraintLayout layout) {
        visibleLayouts.remove(layout);
    }

    public static ArrayList<ConstraintLayout> getVisibleLayouts() {
        return visibleLayouts;
    }
}

