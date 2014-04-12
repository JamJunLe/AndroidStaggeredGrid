
package com.etsy.android.grid.util;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The algorithm used for finding the next focusable view in a given direction
 * from a view that currently has focus.
 */
public class FocusFinder {

    // private static final ThreadLocal<FocusFinder> tlFocusFinder =
    // new ThreadLocal<FocusFinder>() {
    // @Override
    // protected FocusFinder initialValue() {
    // return new FocusFinder();
    // }
    // };

    private static FocusFinder instance;

    /**
     * Get the focus finder for this thread.
     */
    public static FocusFinder getInstance() {
        if (instance == null)
            instance = new FocusFinder();
        return instance;
    }

    final Rect mFocusedRect = new Rect();
    final Rect mOtherRect = new Rect();
    final SparseArray<Set<View>> mMinXViews = new SparseArray<Set<View>>();
    final SparseArray<Set<View>> mMinYViews = new SparseArray<Set<View>>();

    public final View findNextFocus(ViewGroup root, View focused, int direction) {

        // ArrayList<View> touchables = root.getTouchables();
        int minDistanceX = Integer.MAX_VALUE, minDistanceY = Integer.MAX_VALUE;
        View closest = null;

        int numTouchables = root.getChildCount();// touchables.size();

        Rect touchableBounds = mOtherRect;

        focused.getHitRect(mFocusedRect);
        // int x = mFocusedRect.left, y = mFocusedRect.bottom;

        for (int i = 0; i < numTouchables; i++) {
            View touchable = root.getChildAt(i);// touchables.get(i);
            if (touchable == focused) {
                continue;
            }

            // get visible bounds of other view in same coordinate system
            touchable.getHitRect(touchableBounds);
            int distanceX, distanceY;
            switch (direction) {
                case View.FOCUS_LEFT:
                    distanceX = mFocusedRect.right - touchableBounds.right;
                    distanceY = Math.abs(touchableBounds.top - mFocusedRect.top);
                    if (distanceX > 0 && distanceY <= minDistanceY) {
                        Set<View> positions = mMinYViews.get(distanceY);
                        if (positions == null) {
                            mMinYViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinYViews.put(distanceY, positions);
                        }
                        positions.add(touchable);
                        minDistanceY = distanceY;
                    }
                    if (distanceX > 0 && distanceX <= minDistanceX) {
                        Set<View> positions = mMinXViews.get(distanceX);
                        if (positions == null) {
                            mMinXViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinXViews.put(distanceX, positions);
                        }
                        positions.add(touchable);
                        minDistanceX = distanceX;
                    }
                    break;
                case View.FOCUS_RIGHT:
                    distanceX = touchableBounds.left - mFocusedRect.left;
                    distanceY = Math.abs(touchableBounds.top - mFocusedRect.top);
                    if (distanceX > 0 && distanceY <= minDistanceY) {
                        Set<View> positions = mMinYViews.get(distanceY);
                        if (positions == null) {
                            mMinYViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinYViews.put(distanceY, positions);
                        }
                        positions.add(touchable);
                        minDistanceY = distanceY;
                    }
                    if (distanceX > 0 && distanceX <= minDistanceX) {
                        Set<View> positions = mMinXViews.get(distanceX);
                        if (positions == null) {
                            mMinXViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinXViews.put(distanceX, positions);
                        }
                        positions.add(touchable);
                        minDistanceX = distanceX;
                    }
                    break;
                case View.FOCUS_UP:
                    distanceX = Math.abs(touchableBounds.left - mFocusedRect.left);
                    distanceY = mFocusedRect.bottom - touchableBounds.bottom;
                    if (distanceY > 0 && distanceY <= minDistanceY) {
                        Set<View> positions = mMinYViews.get(distanceY);
                        if (positions == null) {
                            mMinYViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinYViews.put(distanceY, positions);
                        }
                        positions.add(touchable);
                        minDistanceY = distanceY;
                    }
                    if (distanceY > 0 && distanceX <= minDistanceX) {
                        Set<View> positions = mMinXViews.get(distanceX);
                        if (positions == null) {
                            mMinXViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinXViews.put(distanceX, positions);
                        }
                        positions.add(touchable);
                        minDistanceX = distanceX;
                    }
                    break;
                case View.FOCUS_DOWN:
                    distanceX = Math.abs(touchableBounds.left - mFocusedRect.left);
                    distanceY = touchableBounds.top - mFocusedRect.top;
                    if (distanceY > 0 && distanceY <= minDistanceY) {
                        Set<View> positions = mMinYViews.get(distanceY);
                        if (positions == null) {
                            mMinYViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinYViews.put(distanceY, positions);
                        }
                        positions.add(touchable);
                        minDistanceY = distanceY;
                    }
                    if (distanceY > 0 && distanceX <= minDistanceX) {
                        Set<View> positions = mMinXViews.get(distanceX);
                        if (positions == null) {
                            mMinXViews.clear();
                            positions = new LinkedHashSet<View>();
                            mMinXViews.put(distanceX, positions);
                        }
                        positions.add(touchable);
                        minDistanceX = distanceX;
                    }
                    break;
            }
        }

        int minTop = Integer.MAX_VALUE, minBottom = Integer.MAX_VALUE;
        if (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) {
            for (int i = 0; i < mMinXViews.size(); i++) {
                Set<View> views = mMinXViews.valueAt(i);
                if (views == null)
                    continue;
                for (View touchable : views) {
                    if (touchable == focused) {
                        continue;
                    }
                    touchable.getHitRect(touchableBounds);
                    int distance = Math.abs(mFocusedRect.top - touchableBounds.top);
                    if (distance <= minTop) {
                        minTop = distance;
                        closest = touchable;
                    }

                }
            }
        } else if (direction == View.FOCUS_UP || direction == View.FOCUS_DOWN) {
            for (int i = 0; i < mMinXViews.size(); i++) {
                Set<View> views = mMinXViews.valueAt(i);
                if (views == null)
                    continue;
                for (View touchable : views) {
                    if (touchable == focused) {
                        continue;
                    }
                    touchable.getHitRect(touchableBounds);
                    switch (direction) {
                        case View.FOCUS_DOWN:
                            if (touchableBounds.top < minTop) {
                                minTop = touchableBounds.top;
                                closest = touchable;
                            }
                            break;
                        case View.FOCUS_UP:
                            int distance = Math.abs(mFocusedRect.bottom - touchableBounds.bottom);
                            if (distance < minBottom) {
                                minBottom = distance;
                                closest = touchable;
                            }
                            break;
                    }
                }
            }
        }

        mMinXViews.clear();
        mMinYViews.clear();

        return closest;
    }

}
