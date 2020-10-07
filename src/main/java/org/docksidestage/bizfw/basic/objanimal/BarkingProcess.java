package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);
    public BarkedSound bark(Animal animal) {
        breatheIn();
        animal.downHitPoint();
        prepareAbdominalMuscle();
        animal.downHitPoint();

        String barkWord = animal.getBarkWord();
        BarkedSound barkedSound = animal.doBark(barkWord);

        return barkedSound;
    }

    protected void prepareAbdominalMuscle() {
        logger.debug("...Using my abdominal muscle"); // dummy implementation
    }

    protected void breatheIn() {
        logger.debug("...Breathing in"); // dummy implementation
    }

}
