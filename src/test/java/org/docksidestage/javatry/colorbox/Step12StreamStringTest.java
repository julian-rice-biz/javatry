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

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.print.attribute.IntegerSyntax;

import org.docksidestage.bizfw.basic.supercar.SupercarClient;
import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.size.BoxSize;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author your_name_here
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                .map(colorBox -> colorBox.getColor().getColorName())
                .map(colorName -> colorName.length() + " (" + colorName + ")")
                .orElse("*not found");
        log(answer);
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Stream<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof String));
        int max = goal.mapToInt(x -> x.getSpaceList().stream()
                .filter(y -> y.getContent() instanceof String)
                .mapToInt(z -> ((String)z.getContent()).length()).max().getAsInt()).max().getAsInt();
        log(max);
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> max = colorBoxList.stream().
                reduce((a,b) -> a.getColor().getColorName().length() > b.getColor().getColorName().length() ? a : b);
        Optional<ColorBox> min = colorBoxList.stream().
                reduce((a,b) -> a.getColor().getColorName().length() < b.getColor().getColorName().length() ? a : b);
        log(max.get().getColor().getColorName().length() - min.get().getColor().getColorName().length());
    }

    // has small #adjustmemts from ClassicStringTest
    //  o sort allowed in Stream
    /**
     * Which value (toString() if non-string) has second-max length in color-boxes? (sort allowed in Stream)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (Streamでのソートありで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Stream<Object> mapped = colorBoxList.stream().map(x -> x.getSpaceList().stream().mapToInt(y -> y.getContent().toString().length()));
        mapped.forEach(x -> log(x.toString().length()));
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        //The English is pretty unclear/ambiguous, but I'm going to assume that they're
        //asking for the total length of all strings that are in any of the contents of any box, meaning
        //that we ignore the box color string and other non-content related strings.
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        int max = colorBoxList.stream().mapToInt(box -> box.getSpaceList()
                .stream().filter(spaceType -> spaceType.getContent() instanceof String)
                .mapToInt(len ->((String)len.getContent()).length()).sum()).sum();

        log(max);

        //Debug Test
        colorBoxList.stream().forEach(box -> box.getSpaceList()
                .stream().filter(spaceType -> spaceType.getContent() instanceof String)
                .forEach(len -> log(((String)len.getContent()))));
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> max = colorBoxList.stream().
                reduce((a,b) -> a.getColor().getColorName().length() > b.getColor().getColorName().length() ? a : b);
        log(max.get().getColor().getColorName() + " | Length: " + max.get().getColor().getColorName().length());
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        String target = "Water";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof String
                                && ((String) space.getContent()).startsWith(target))).findFirst();
        if (goal.isPresent()) {
            log("Found [" + target + "] in " + goal.get().getColor().getColorName());
        } else {
            log("Failed to find it!");
        }
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        String target = "front";
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof String
                                && ((String) space.getContent()).endsWith(target))).findFirst();
        if (goal.isPresent()) {
            log("Found [" + target + "] in " + goal.get().getColor().getColorName());
        } else {
            log("Failed to find it!");
        }
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================

    //Helper Function
    public String GetStringWithTarget(String target) {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof String
                                && ((String) space.getContent()).contains(target))).findFirst();
        Optional<BoxSpace> targetSpace = goal.get().getSpaceList().stream()
                .filter(space -> space.getContent() instanceof String && ((String) space.getContent()).contains(target)).findFirst();
        return (String)targetSpace.get().getContent();
    }
    /**
     * What number character is starting with first "front" of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列で、最初の "front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        String target = "front";
        String word = GetStringWithTarget(target);
        log("Starting index of " + target + " for [" + word + "] is: " + word.indexOf(target));
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        String target = "ど";
        String word = GetStringWithTarget(target);
        log("Latter index of " + target + " for [" + word + "] is: " + word.lastIndexOf(target));
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        log("First char of [" + GetStringWithTarget("front") + "] is: " + GetStringWithTarget("front").substring(0, 1));
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        String word = GetStringWithTarget("Water");
        log("First char of [" + word + "] is: " + word.substring(word.length()-1));
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        //Only one 'String' type that is some content (Object) contains an "o" -> Waterfr >o< nt
        String word = GetStringWithTarget("o");
        word = word.replace("o", "");
        log("TEST_REPLACE_REMOVE_O: " + word + " | Length: " + word.length());
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof File)).findFirst();
        Optional<BoxSpace> targetSpace = goal.get().getSpaceList().stream()
                .filter(space -> space.getContent() instanceof File).findFirst();
        String path = ((File)targetSpace.get().getContent()).getPath().replace("/","\\");
        log("TEST_REPLACE_FILESEPARATOR: " + path);
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof YourPrivateRoom.DevilBox)).findFirst();

        int sum = goal.get().getSpaceList().stream()
                .mapToInt(space -> {

                    ((YourPrivateRoom.DevilBox)space.getContent()).wakeUp();
                    ((YourPrivateRoom.DevilBox)space.getContent()).allowMe();
                    ((YourPrivateRoom.DevilBox)space.getContent()).open();

                    try {
                        ((YourPrivateRoom.DevilBox)space.getContent()).getText();
                        return ((YourPrivateRoom.DevilBox)space.getContent()).getText().length();
                    } catch (Exception e) {
                        return 0;
                    }

                }).sum();

        log(sum);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof LinkedHashMap)).findFirst();

        goal.get().getSpaceList().forEach(x -> {
            if (x.getContent() instanceof LinkedHashMap) {
                log(((LinkedHashMap) x.getContent()).toString());
            } //{1-Day Passport=7400, Starlight Passport=5400, After 6 Passport=4200, 2-Day Passport=13200, 3-Day Magic Passport=17800, 4-Day Magic Passport=22400, Land Annual Passport=61000, Sea Annual Passport=61000, Group Passport=6700}
        });
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        //Semi-Incomplete
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<ColorBox> goal = colorBoxList.stream()
                .filter(box -> box.getSpaceList().stream()
                        .anyMatch(space -> space.getContent() instanceof LinkedHashMap)).findFirst();

        goal.get().getSpaceList().forEach(x -> {
            if (x.getContent() instanceof LinkedHashMap) {
                log(((LinkedHashMap) x.getContent()).toString());
            }
        });
    }
}
