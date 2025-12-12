package de.do1eh.Advent2025;

import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;

/**
 * Was 'ne Verarsche...von wegen drehen und flippen
 * 2 Prüfunen:
 * 1. gesamtzahl der punkte ist größer als punkte der fläche -> passt nicht rein
 * 2. alle 3x3 kacheln nebeneinander passen in den Raum -> passt auf jeden Fall
 */
public class Day12 {

    public void part1() {

        List<String> input = readUrlContent("https://adventofcode.com/2025/day/12/input", SESSION_COOKIE);

    int passtnicht=0;
    int passtAufJedenFall=0;
    for (int i=30;i<input.size();i++) {

        int raumgroesse=(Integer.parseInt(input.get(i).substring(0,2))*Integer.parseInt(input.get(i).substring(3,5)));


        int anzahlpuzzleteile=0;

        for (int p=0;p<17;p+=3) {
            anzahlpuzzleteile+=Integer.parseInt(input.get(i).substring(7+p,9+p));
        }
         if (raumgroesse>=anzahlpuzzleteile*9) {passtAufJedenFall++;}
        if (raumgroesse<anzahlpuzzleteile*9) {passtnicht++;}
        System.out.println(input.get(i));
        System.out.println("Raum:"+raumgroesse);
        System.out.println("Puzzleteile:"+anzahlpuzzleteile);
        System.out.println();
    }
        System.out.println("Passt:"+passtAufJedenFall);
        System.out.println("Passt nicht:"+passtnicht);
        System.out.println("Summe:"+(passtnicht+passtAufJedenFall));
    }

}
