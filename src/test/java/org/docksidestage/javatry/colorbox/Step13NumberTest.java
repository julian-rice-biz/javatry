/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.awt.color.ColorSpace;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author julian
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54 (includes)? <br>
     * (カラーボックの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        int val = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace space : colorBox.getSpaceList()) {
                if (space.getContent() instanceof Integer)
                    val += 1;
            }
        }
        log(val);
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        int val = 0;
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox colorBox : colorBoxList) {
            for (BoxSpace space : colorBox.getSpaceList()) {
                if (space.getContent() instanceof Number && (((Number) space.getContent()).intValue() >= 0 && ((Number) space.getContent()).intValue() <= 54)) {
                    //log("-- " + ((Number) space.getContent()).intValue());
                    val += 1;
                }
            }
        }
        log(val);
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof Integer))
                .reduce((a,b) -> a.getSize().getWidth() > b.getSize().getWidth() ? a : b);
        log("Color: " + goal.get().getColor().getColorName() + " with width: " + goal.get().getSize().getWidth());
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof List)).findFirst();
        log(goal.get().getColor().getColorName());
        int answer = goal.get().getSpaceList().stream().mapToInt(item -> {
            if (item.getContent() instanceof List) {
                return ((List<Number>)item.getContent()).stream().reduce((a,b) -> {
                    if (a instanceof BigDecimal && b instanceof BigDecimal) {
                        return ((BigDecimal)a).add(((BigDecimal)b));
                    } else {
                        if (a instanceof BigDecimal) {
                            return ((BigDecimal)a);
                        } else if (b instanceof BigDecimal) {
                            return ((BigDecimal)b);
                        } else {
                            return 0; //Should be an impossible situation
                        }
                    }
                }).get().intValue();
            } else {
                return 0;
            }
        }).sum();

        log(answer);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof LinkedHashMap)).findFirst();

        int answer = 0;
        String ansKey = "";
        for (BoxSpace space : goal.get().getSpaceList()) {
            if (space.getContent() instanceof LinkedHashMap) {
                LinkedHashMap<String, Integer> map = (LinkedHashMap) space.getContent();
                for (String name : map.keySet()) {
                    if (map.get(name) > answer) {
                        answer = map.get(name);
                        ansKey = name;
                    }
                }
            }
        }
        log(ansKey + " | " + answer);
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getColor().getColorName() == "purple").findFirst();

        BoxSpace space = goal.get().getSpaceList().get(2); //Lower
        Map<String, Object> map = (Map)space.getContent();
        int sum = 0;
        for (String item : map.keySet()) {
            if (map.get(item) instanceof Number) {
                Number val = ((Number)map.get(item));
                sum += val.intValue();
            }
        }
        log(sum);
    }
}
