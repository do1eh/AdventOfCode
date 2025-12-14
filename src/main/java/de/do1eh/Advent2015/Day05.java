package de.do1eh.Advent2015;

import de.do1eh.Advent2025.Tools;

import java.util.LinkedList;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day05 {


    public void part1() {
        int loesung = 0;
        int loesung2 = 0;
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2015/day/5/input", SESSION_COOKIE);
        List<Character> vokale= new LinkedList<>();
        vokale.add('a');
        vokale.add('e');
        vokale.add('i');
        vokale.add('o');
        vokale.add('u');

        boolean nice;
        boolean nice1;
        boolean nice2;
        boolean p2nice;
        boolean p2nice1;


        for (String line : input) {

            //zeile buchstabenweise durhclaufen
            Character lastletter = ',';
            int anzahlvokale = 0;
            nice = true; //#vokale;
            nice1 = false; //doppelbuchstabe
            nice2 = true; //ab bc cd
            p2nice = false; // pärchen das 2x vorkommt ohne überlappung
            p2nice1 = false; //doppelbuchstabe mit einen buchstaben dazwischen

            for (int i = 0; i < line.length(); i++) {
                //#vokale zählen
                Character c = line.charAt(i);
                if (vokale.contains(c)) {
                    anzahlvokale++;
                }

                //doppelbuchstabe
                if (c == lastletter) {
                    nice1 = true;
                    //System.out.println("Doppenbuchstabe:"+c+lastletter);
                }
                lastletter = c;

                //doppelbuchstabe mit einem dazwischen
                if (i < line.length() -2) {

                    if (line.charAt(i)==line.charAt(i+2)) {
                        p2nice1=true;
                        //System.out.println("BxB:"+line.substring(i,i+3));
                    }
                }

                // pärchen das 2x vorkommt ohne überlappung
                if (i < line.length() -2) {
                    String paerchen = ""+line.charAt(i) + line.charAt(i + 1);

                    if (line.indexOf(paerchen,line.indexOf(paerchen)+2)>-1)
                    {
                        //System.out.println("P:"+paerchen);
                        p2nice=true;
                    }
                }


                //ab bc cd,..
               /*
                if (i < line.length() -1) {
                    int b1 = (int) c;
                    int b2 = (int) line.charAt(i + 1);

                    if (b2 - b1 == 1) {
                        nice2 = false;
                        System.out.println("Folgebuchstabe:"+c+line.charAt(i + 1));

                    }
                }
              */

            }
            //#vokale
            //System.out.println("Anzahlvokale:"+anzahlvokale);
            if (anzahlvokale < 3) nice = false;

            //ab, cd, pq, or xy
            if (line.contains("ab") ||line.contains("cd") ||line.contains("pq") ||line.contains("xy") ) {
                nice2=false;
            }

            if (nice && nice1 && nice2) {
                loesung++;
            }
            if (p2nice && p2nice1 ) {
                loesung2++;
            }


        }
        //164 zu klein
        //269
        System.out.println("Lösung:"+loesung);
        System.out.println("Lösung2:"+loesung2);
    }

    public void part2() {
    }
    }



