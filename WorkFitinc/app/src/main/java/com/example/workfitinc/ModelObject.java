package com.example.workfitinc;

public enum ModelObject {

    RED(R.string.profile_1, R.layout.profile_1),
    BLUE(R.string.profile_2, R.layout.profile_2),
    GREEN(R.string.profile_3, R.layout.profile_3);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}