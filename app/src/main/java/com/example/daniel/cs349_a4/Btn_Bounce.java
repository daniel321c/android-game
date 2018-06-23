package com.example.daniel.cs349_a4;

/**
 * Created by daniel on 2017-12-02.
 */

class Btn_Bounce implements android.view.animation.Interpolator {
    private double mAmplitude = 1;
    private double mFrequency = 10;

    Btn_Bounce(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}