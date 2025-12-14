package de.do1eh.Advent2015;

import de.do1eh.Advent2025.Tools;

import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day01 {

    public void part1(){
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2015/day/1/input", SESSION_COOKIE);

        int loesung1=0;
        int loesung2=0;
        String in=input.getFirst();
        boolean fertig=false;
        for (int i=0;i<in.length();i++) {
            if (in.charAt(i)=='(') {loesung1++;} else {loesung1--;}

            if (loesung1!=-1 && !fertig) {
                loesung2++;
            } else {
                fertig=true;
            }

        }
        System.out.println("Part1:"+loesung1);
        System.out.println("Part2:"+loesung2);
    }
}
