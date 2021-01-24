package com.example.weathertoday.containers;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class OptionsContainer {

    private static final ArrayList<ConstraintLayout> visibleLayouts = new ArrayList<>();

    public static void addVisible(ConstraintLayout targetLayout, Object tag) {
        for (ConstraintLayout layout : visibleLayouts) {
            if (layout.getTag().equals(tag)) {
                return;
            }
        }
        visibleLayouts.add(targetLayout);
    }

    public static void removeVisible(Object tag) {
        ConstraintLayout layoutToRemove = null;
        for (ConstraintLayout layout : visibleLayouts) {
            if (layout.getTag().equals(tag)) {
                layoutToRemove = layout;
            }
        }
        visibleLayouts.remove(layoutToRemove);
    }

    public static ArrayList<ConstraintLayout> getVisibleLayouts() {
        return visibleLayouts;
    }

    public static void clearVisibilitiesList() {
        visibleLayouts.clear();
    }
}

