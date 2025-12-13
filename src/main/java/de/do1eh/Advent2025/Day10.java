package de.do1eh.Advent2025;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;

public class Day10 {

    public void part1() {

        //List<String> input = readUrlContent("https://adventofcode.com/2025/day/9/input", SESSION_COOKIE);
        List<String> input = new ArrayList<String>();
        input.add("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");
        input.add("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}");
        input.add("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}");


        for (int i = 0; i < input.size(); i++) {

            String lampenstr = input.get(i).substring(input.get(i).indexOf('[') + 1, input.get(i).indexOf(']'));
            lampenstr = lampenstr.replace(".", "0");
            lampenstr = lampenstr.replace("#", "1");
            int lampe = Integer.parseInt(lampenstr, 2);

            String schalterstr = input.get(i).substring(input.get(i).indexOf(']') + 1, input.get(i).indexOf('{'));
            List<Integer> schalter=new ArrayList<>();

            Pattern pattern = Pattern.compile("\\([^)]+\\)");
            Matcher matcher = pattern.matcher(schalterstr);
            List<String> schalterstrlst = new ArrayList<>();

            while (matcher.find()) {
                 schalterstrlst.add(matcher.group());
            }


            for (String schalterstring:schalterstrlst) {
                List<Integer> schalterint=new ArrayList<>();
                schalterstring=schalterstring.replace("(","");
                schalterstring=schalterstring.replace(")","");
                String[] schaltertmp=schalterstring.split(",");

                //getrennte schalterstrings in int umwandeln
                for (String schalter2:schaltertmp) {
                    schalterint.add(Integer.parseInt(schalter2));
                }

                //schalteeints durchlaufen und in bitmuster ändern
                String bitmuster="";
                for (int pos:schalterint) {
                    while(pos>bitmuster.length()) {bitmuster+="0";}
                    bitmuster+="1";
                }
                System.out.println("schalterresultat:"+bitmuster);
                //bitmuster speichern
                schalter.add(Integer.parseInt(bitmuster, 2));
             }
            //String joltage = input.get(i).substring(input.get(i).indexOf('{') + 1, input.get(i).indexOf('}'));

            System.out.println("lampe:"+lampe);
            System.out.println("lampestring:"+lampenstr);
            //System.out.println(1 << lampenstr.length());
            System.out.println("schalterresultatliste"+schalter);
            //System.out.println(joltage);

            //Drückalgo alle Kombinationen aus der schalterresultatliste durchgehen per xor verknüpfen und mit







/*
            String ergebnis = "0000";
            int button = 0;
            for (String schalten : schalter) {
                schalten = schalten.replace("(", "");
                schalten = schalten.replace(")", "");
                System.out.println(schalten);
                String[] bitsetzen = schalten.split(",");
                List<Integer> bits = new ArrayList<>();
                int bitmuster = 0;

                for (String bit : bitsetzen) {

                    switch (bit) {
                        case "0":
                            bitmuster += 1;
                            break;
                        case "1":
                            bitmuster += 2;
                            break;
                        case "2":
                            bitmuster += 4;
                            break;
                        case "3":
                            bitmuster += 8;
                            break;
                        case "4":
                            bitmuster += 16;
                            break;
                        case "5":
                            bitmuster += 32;
                            break;
                        case "6":
                            bitmuster += 64;
                            break;
                        case "7":
                            bitmuster += 128;
                            break;
                        case "8":
                            bitmuster += 256;
                            break;
                        case "9":
                            bitmuster += 512;
                            break;
                        case "10":
                            bitmuster += 1024;
                            break;
                    }


                }
                druecken.put(button, bitmuster);
                //System.out.println(bitmuster);
                //System.out.println(Integer.toBinaryString(bitmuster));
            }

            //alle schalterstellungen durchlaufen
            for (int schalterstellungen = 0; schalterstellungen < 1 << lampenstr.length(); schalterstellungen++) {

                List<Integer> muster = new ArrayList<>();
                String schalterstellung = Integer.toBinaryString(schalterstellungen);
                for (int lauf = 0; lauf < schalterstellung.length(); lauf++) {
                    if (schalterstellung.charAt(lauf) == 1) {
                        muster.add(druecken.get(lauf));
                    }
                }

                int ergebnis2 = 0;
                for (int lauf = 0; lauf < muster.size(); lauf++) {
                    ergebnis2 = ergebnis2 ^ muster.get(lauf);
                }

            }//ende input durchlaufen
*/
        }
    }
}
