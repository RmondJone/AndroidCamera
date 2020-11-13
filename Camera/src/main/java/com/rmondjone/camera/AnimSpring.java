package com.rmondjone.camera;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.view.View;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

public class AnimSpring {
    public static AnimSpring animSpring;
    public static SpringSystem springSystem;
    public SpringConfig springConfig = SpringConfig.fromBouncinessAndSpeed(8.0D, 2.0D);
    private double tension = -1.0D;
    private double friction = -1.0D;
    private View animView;

    public AnimSpring(View animView) {
        this.animView = animView;
    }

    public static synchronized AnimSpring getInstance(View view) {
        animSpring = new AnimSpring(view);
        if (springSystem == null) {
            springSystem = SpringSystem.create();
        }

        return animSpring;
    }

    public AnimSpring setTension(double tension) {
        this.tension = tension;
        if (this.friction != -1.0D) {
            this.springConfig = SpringConfig.fromBouncinessAndSpeed(tension, this.friction);
        } else {
            this.springConfig = SpringConfig.fromBouncinessAndSpeed(tension, 2.0D);
        }

        return this;
    }

    public AnimSpring setFriction(double friction) {
        this.friction = friction;
        if (this.tension != -1.0D) {
            this.springConfig = SpringConfig.fromBouncinessAndSpeed(this.tension, friction);
        } else {
            this.springConfig = SpringConfig.fromBouncinessAndSpeed(8.0D, friction);
        }

        return this;
    }

    public AnimSpring startTranslationAnim(double startX, double startY, double endX, double endY) {
        Spring transSpringX = springSystem.createSpring();
        Spring transSpringY = springSystem.createSpring();
        transSpringX.setSpringConfig(this.springConfig);
        transSpringY.setSpringConfig(this.springConfig);
        transSpringX.setCurrentValue(startX);
        transSpringY.setCurrentValue(startY);
        transSpringX.setEndValue(endX);
        transSpringY.setEndValue(endY);
        transSpringX.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                AnimSpring.this.animView.setTranslationX((float) spring.getCurrentValue());
            }

            @Override
            public void onSpringAtRest(Spring spring) {
            }

            @Override
            public void onSpringActivate(Spring spring) {
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        transSpringY.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                AnimSpring.this.animView.setTranslationY((float) spring.getCurrentValue());
            }

            @Override
            public void onSpringAtRest(Spring spring) {
            }

            @Override
            public void onSpringActivate(Spring spring) {
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        return this;
    }

    public AnimSpring startRotateAnim(float startValue, float endValue) {
        Spring rotateSpring = springSystem.createSpring();
        rotateSpring.setSpringConfig(this.springConfig);
        rotateSpring.setCurrentValue((double) startValue);
        rotateSpring.setEndValue((double) endValue);
        rotateSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                AnimSpring.this.animView.setRotation((float) spring.getCurrentValue());
            }

            @Override
            public void onSpringAtRest(Spring spring) {
            }

            @Override
            public void onSpringActivate(Spring spring) {
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        return this;
    }

    public AnimSpring startScaleAnim(double startX, double startY, double endX, double endY) {
        Spring transSpringX = springSystem.createSpring();
        Spring transSpringY = springSystem.createSpring();
        transSpringX.setSpringConfig(this.springConfig);
        transSpringY.setSpringConfig(this.springConfig);
        transSpringX.setCurrentValue(startX);
        transSpringY.setCurrentValue(startY);
        transSpringX.setEndValue(endX);
        transSpringY.setEndValue(endY);
        transSpringX.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                AnimSpring.this.animView.setScaleX((float) spring.getCurrentValue());
            }

            @Override
            public void onSpringAtRest(Spring spring) {
            }

            @Override
            public void onSpringActivate(Spring spring) {
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        transSpringY.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                AnimSpring.this.animView.setScaleY((float) spring.getCurrentValue());
            }

            @Override
            public void onSpringAtRest(Spring spring) {
            }

            @Override
            public void onSpringActivate(Spring spring) {
            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });
        return this;
    }
}
