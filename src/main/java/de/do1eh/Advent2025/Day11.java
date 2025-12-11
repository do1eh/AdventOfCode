package de.do1eh.Advent2025;

import java.util.*;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;

public class Day11{
long loesung=0;
TreeMap<String,List<String>> tree;
private final Map<String, Long> memo = new HashMap<>();

    /**
     * Teil 1 einfach Brute-force rekursiv durch den Baum und Pfade zählen: fertig
     */
    public void part1() {
    List<String> input = readUrlContent("https://adventofcode.com/2025/day/11/input", SESSION_COOKIE);
/*
    List<String> input =new ArrayList<String>();
    input.add("aaa: you hhh");
    input.add("you: bbb ccc");
    input.add("bbb: ddd eee");
    input.add("ccc: ddd eee fff");
    input.add("ddd: ggg");
    input.add("eee: out");
    input.add("fff: out");
    input.add("ggg: out");
    input.add("hhh: ccc fff iii");
    input.add("iii: out");
*/
    //In Treemap stecken
    tree=new TreeMap<>();
    for (int i = 0; i < input.size(); i++) {
        String zeile=input.get(i);
        String key=zeile.substring(0,zeile.indexOf(":"));
        String[] value=zeile.substring(zeile.indexOf(":")+2).split(" ");
        List<String> values= Arrays.asList(value);
        tree.put(key,values);
    }
    //startknoten holen
    List<String> start=tree.get("you");
    //System.out.println(start);
    checkZiel(start);
    System.out.println("Lösung:" + loesung);
    }

    private void checkZiel(List<String> ausgaenge) {
        //wenn Ziel erreich: Weg zählen
        if (ausgaenge.contains("out")) {
            loesung++;
        } else {
            //Für alle ausgänge weitersuchen
            for (String ausgang:ausgaenge) {
                List<String> nextausgaenge=tree.get(ausgang);
                checkZiel(nextausgaenge);
            }
        }
    }

    /**
     * In Zeil 2 geht das mal wieder nicht so einfach weil der Weg durch den Baum extrem viel größer ist.
     * Folgende verbesserungen:
     * 1. Schleifen vermeiden: sofort beenden wenn man an einer Stelle schon einmal war (besucht)
     * 2. Memorization wirkt wie immer wunder.
     */
    public void part2() {

        List<String> input = readUrlContent("https://adventofcode.com/2025/day/11/input", SESSION_COOKIE);
 /*
        List<String> input =new ArrayList<String>();
        input.add("svr: aaa bbb");
        input.add("aaa: fft");
        input.add("fft: ccc");
        input.add("bbb: tty");
        input.add("tty: ccc");
        input.add("ccc: ddd eee");
        input.add("ddd: hub");
        input.add("hub: fff");
        input.add("eee: dac");
        input.add("dac: fff");
        input.add("fff: ggg hhh");
        input.add("ggg: out");
        input.add("hhh: out");
*/
        //In Treemap stecken
        tree=new TreeMap<>();
        for (int i = 0; i < input.size(); i++) {
            String zeile=input.get(i);
            String key=zeile.substring(0,zeile.indexOf(":"));
            String[] value=zeile.substring(zeile.indexOf(":")+2).split(" ");
            List<String> values= Arrays.asList(value);
            tree.put(key,values);
        }
        //startknoten holen
        List<String> start=tree.get("svr");
        List<String> naechsterbesuch = new ArrayList<>();
        naechsterbesuch.add("svr");
        for (String ausgang:start) {
            loesung += checkZiel2(naechsterbesuch,ausgang, false, false);
        }
        System.out.println("Lösung:" + loesung);
    }

    private long checkZiel2(List<String> besucht,String ausgang, boolean dac,boolean fft) {
        if (besucht.contains(ausgang)) {
            return 0;
        }
     //wenn Ziel erreicht: Weg zählen
        if (ausgang.equals("dac")) {
            dac=true;
        }
        if (ausgang.equals("fft")) {
            fft=true;
        }
        String key = ausgang + ":" + dac + ":" + fft;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        //zielbedingung:
        if (ausgang.equals("out")) {
            if (dac&&fft) {
                return 1;
            } else return 0;
        }
            List<String> naechsterbesuch = new ArrayList<>();
            naechsterbesuch.add(ausgang);
            long anzahlpfade=0;
            List<String> ausgaenge = tree.get(ausgang);
            //Für alle Ausgänge weitersuchen
            for (String nextausgaenge:ausgaenge) {
                if (!nextausgaenge.isEmpty()) {
                    anzahlpfade+=checkZiel2(naechsterbesuch,nextausgaenge, dac, fft);
                }
                memo.put(key, anzahlpfade);
            }
     return anzahlpfade;
    }
}
