package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kuribo extends Animal implements StepByStep {
    private static final Logger logger = LoggerFactory.getLogger(Kuribo.class);

    private int speed = 3;
    public Kuribo() {}

    @Override
    protected String getBarkWord() {
        return "kuri kuri"; //From Mario >o<
    }

    @Override
    public int getWalkSpeed() {
        return speed;
    }
}
