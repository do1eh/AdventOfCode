package de.do1eh.Advent2015;

import de.do1eh.Advent2025.Tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day03 {

    public void part1() {
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2015/day/3/input", SESSION_COOKIE);

        Set<String> grid= new HashSet<>();
        long x=10000;
        long y=10000;
        //Start
        grid.add("10000,10000");
        String in=input.getFirst();

        for (int anweisung=0;anweisung<in.length();anweisung++) {
            Character befehl=in.charAt(anweisung);

            switch (befehl) {

                case '^':
                y++;
                break;
                case 'v':
                    y--;
                    break;
                case '>':
                    x++;
                    break;
                case '<':
                    x--;
                    break;
            }
            grid.add(String.valueOf(x)+","+String.valueOf(y));
          }

        System.out.println("Lösung1:"+grid.size());
    }

    public void part2() {
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2015/day/3/input", SESSION_COOKIE);

        Set<String> grid= new HashSet<>();
        long x=10000;
        long y=10000;
        long robox=10000;
        long roboy=10000;
        //Start
        grid.add("10000,10000");
        String in=input.getFirst();
        boolean santa=true;
        for (int anweisung=0;anweisung<in.length();anweisung++) {
            Character befehl=in.charAt(anweisung);

            switch (befehl) {

                case '^':
                    if (santa) {y++;} else {roboy++;}
                    break;
                case 'v':
                    if (santa) {y--;} else {roboy--;}
                    break;
                case '>':
                    if (santa) {x++;} else {robox++;}
                    break;
                case '<':
                    if (santa) {x--;} else {robox--;}
                    break;
            }
            if (santa) {grid.add(String.valueOf(x)+","+String.valueOf(y));}
            else {{grid.add(String.valueOf(robox)+","+String.valueOf(roboy));}}
            santa=!santa;
        }

        System.out.println("Lösung2:"+grid.size());
    }
}
