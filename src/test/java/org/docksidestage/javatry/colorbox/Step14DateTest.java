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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tomcat.jni.Local;
import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Date with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step14DateTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is date in color-boxes formatted as plus-separated (e.g. 2019+04+24)? <br>
     * (カラーボックスに入っている日付をプラス記号区切り (e.g. 2019+04+24) のフォーマットしたら？)
     */
    public void test_formatDate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox box : colorBoxList) {
            Stream<Object> date = box.getSpaceList().stream()
                    .filter(x -> x.getContent() instanceof LocalDate)
                    .map(y -> ((LocalDate)y.getContent()).format(DateTimeFormatter.ofPattern("YYYY+MM+dd")));
            date.forEach(x -> log(x));
        }
    }

    /**
     * How is it going to be if the slash-separated date string in yellow color-box is converted to LocaDate and toString() is used? <br>
     * (yellowのカラーボックスに入っているSetの中のスラッシュ区切り (e.g. 2019/04/24) の日付文字列をLocalDateに変換してtoString()したら？)
     */
    public void test_parseDate() {
        //Doesn't seem to convert to LocalDate for some reason!
        //I found out why! 2019/04/24 has a typo -- it's actually 2O (letter O) 19... rip the time I spent looking at this
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Object target = colorBoxList.get(7).getSpaceList().get(0).getContent(); //Target has ID 7
        if ((Set<String>) target != null) {
            Set<String> datesToLog = ((Set<String>)target);
            datesToLog.forEach(x -> {
                try {
                    log(LocalDate.parse(x, DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString());
                } catch (Exception e) {
                    log("Ran into an error");
                }
            });
        }
    }

    /**
     * What is total of month numbers of date in color-boxes? <br>
     * (カラーボックスに入っている日付の月を全て足したら？)
     */
    public void test_sumMonth() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox yellow = colorBoxList.stream().filter((box) -> box.getColor().getColorName() == "yellow").findFirst().get();
        int sum = 0;
        for (BoxSpace space : yellow.getSpaceList()) {
            if (space.getContent() instanceof LocalDate) {
                sum += ((LocalDate) space.getContent()).getMonthValue();
            } else if (space.getContent() instanceof LocalDateTime) {
                sum += ((LocalDateTime)space.getContent()).toLocalDate().getMonthValue();
            }
        }
        log("Month Sum: " + sum); //Verified: 9 + 4 => 13
    }

    /**
     * Add 3 days to second-found date in color-boxes, what day of week is it? <br>
     * (カラーボックスに入っている二番目に見つかる日付に3日進めると何曜日？)
     */
    public void test_plusDays_weekOfDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox yellow = colorBoxList.stream().filter((box) -> box.getColor().getColorName() == "yellow").findFirst().get();
        List<LocalDate> dates = new ArrayList<>();
        for (BoxSpace space : yellow.getSpaceList()) {
            if (space.getContent() instanceof LocalDate) {
                dates.add((LocalDate)space.getContent());
            } else if (space.getContent() instanceof LocalDateTime) {
                dates.add(((LocalDateTime)space.getContent()).toLocalDate());
            }
        }
        if (dates.size() >= 2) {
            LocalDate date = dates.get(1);
            date.plusDays(3);
            log(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * How many days (number of day) are between two dates in yellow color-boxes? <br>
     * (yellowのカラーボックスに入っている二つの日付は何日離れている？)
     */
    public void test_diffDay() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox yellow = colorBoxList.stream().filter((box) -> box.getColor().getColorName() == "yellow").findFirst().get();
        LocalDate date1 = null;
        LocalDateTime date2 = null;
        for (BoxSpace space : yellow.getSpaceList()) {
            if (space.getContent() instanceof LocalDate) {
                date1 = (LocalDate)space.getContent();
            } else if (space.getContent() instanceof LocalDateTime) {
                date2 = (LocalDateTime)space.getContent();
            }
        }
        long dayDiff = ChronoUnit.DAYS.between(date2.toLocalDate(), date1);
        log("Difference in days: " + dayDiff);
    }

    /**
     * Find LocalDate in yellow color-box,
     * and add same color-box's LocalDateTime's seconds as number of months to it,
     * and add red color-box's Long number as days to it,
     * and subtract the first decimal place of BigDecimal that has three(3) as integer in List in color-boxes from it,
     * What date is it? <br>
     * (yellowのカラーボックスに入っているLocalDateに、同じカラーボックスに入っているLocalDateTimeの秒数を月数として足して、
     * redのカラーボックスに入っているLong型を日数として足して、カラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数を日数として引いた日付は？)
     */
    public void test_birthdate() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        ColorBox red = colorBoxList.stream().filter((box) -> box.getColor().getColorName() == "red").findFirst().get();
        ColorBox yellow = colorBoxList.stream().filter((box) -> box.getColor().getColorName() == "yellow").findFirst().get();
        for (ColorBox box : colorBoxList) {
            for (BoxSpace space : box.getSpaceList()) {
                if (space.getContent() instanceof LocalDateTime) {
                    LocalDateTime date = (LocalDateTime)space.getContent();
                    date = date.plusMonths(date.getSecond());
                    date = date.plusDays((Long)red.getSpaceList().stream().filter((num) -> num.getContent() instanceof Long).findFirst().get().getContent());
                    //There are so many improvements to ColorBox that I would make if I could make them... grr...
                    //log(Long.parseLong(((BigDecimal)((List)yellow.getSpaceList().stream().filter((num) -> num.getContent() instanceof List).findFirst().get().getContent()).get(2)).toString().substring(2, 3)));
                    date = date.minusDays(Long.parseLong(((BigDecimal)((List)yellow.getSpaceList().stream().filter((num) -> num.getContent() instanceof List).findFirst().get().getContent()).get(2)).toString().substring(2, 3)));
                    log("Date: " + date);
                    break;
                }
            }
        }
    }

    /**
     * What second is LocalTime in color-boxes? <br>
     * (カラーボックスに入っているLocalTimeの秒は？)
     */
    public void test_beReader() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        for (ColorBox box : colorBoxList) {
            for (BoxSpace space : box.getSpaceList()) {
                if (space.getContent() instanceof LocalDateTime) {
                    LocalDateTime date = (LocalDateTime)space.getContent();
                    log(date.getSecond());
                }
            }
        }
    }
}
