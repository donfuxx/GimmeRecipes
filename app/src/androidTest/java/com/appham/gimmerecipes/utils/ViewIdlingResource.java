package com.appham.gimmerecipes.utils;

import android.support.test.espresso.IdlingResource;
import android.view.View;

/**
 * @author thomas
 */

public class ViewIdlingResource implements IdlingResource {

    private final View view;
    private ResourceCallback resourceCallback;

    public ViewIdlingResource(View view) {
        this.view = view;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    /**
     * The view is idle if it is not visible. (useful for loading bars)
     * @return true if the view is currently idle
     */
    @Override
    public boolean isIdleNow() {

        boolean idle = view.getVisibility() != View.VISIBLE;
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return view.getVisibility() != View.VISIBLE;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
