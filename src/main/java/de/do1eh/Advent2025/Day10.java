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

        int loesung=0;
        //List<String> input = readUrlContent("https://adventofcode.com/2025/day/10/input", SESSION_COOKIE);

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
                    //System.out.println("schalterint:"+pos);
                    while(pos>bitmuster.length()) {bitmuster+="0";}
                    bitmuster+="1";
                }
                while (bitmuster.length()<lampenstr.length()) {
                    bitmuster+=0;
                }
                //System.out.println("schalterresultat:"+bitmuster);
                //bitmuster speichern
                schalter.add(Integer.parseInt(bitmuster, 2));
             }
            String joltage = input.get(i).substring(input.get(i).indexOf('{') + 1, input.get(i).indexOf('}'));

            //System.out.println("lampe:"+lampe);
            //System.out.println("lampestring:"+lampenstr);
            //System.out.println(1 << lampenstr.length());
            //System.out.println("schalterresultatliste"+schalter);
            //System.out.println(joltage);

            //Drückalgo:  Alle Kombinationen aus der Schalterresultatliste durchgehen, per xor verknüpfen und mit
            //Ziel vergleichen Wenn Ergebnis==Ziel -> anzahl benutzte schalter speichern
            //Am Ende: loesung+=kleinste Anzahl Schalter dieser Runde
            List<List<Integer>> allekombinationen= generateSubsets(schalter);
            //System.out.println(allekombinationen);

            int anzahlknopfdruecke=0;
            for (List<Integer> kombinationen:allekombinationen) {
                Integer xor=0;
                for (Integer knopfdruck:kombinationen) {
                    xor^= knopfdruck;
                 }
                if (xor==lampe && (anzahlknopfdruecke==0 ||anzahlknopfdruecke>kombinationen.size())) {
                    anzahlknopfdruecke=kombinationen.size();
                    //System.out.println("xor:"+Integer.toBinaryString(xor));
                    //System.out.println(kombinationen);
                }
            }
            //System.out.println("anzahl:"+anzahlknopfdruecke);
           loesung+=anzahlknopfdruecke;

        //Part2: joltage anpassung

        }
        System.out.println("Loesung:"+loesung);
    }

    /**
     * Die Methode ist hat freundlicherweise gemini erstellt
     * @param numbers
     * @return
     */
    public static List<List<Integer>> generateSubsets(List<Integer> numbers) {
        List<List<Integer>> subsets = new ArrayList<>();
        int n = numbers.size();

        // Die Gesamtzahl der möglichen Teilmengen (einschließlich der leeren Menge)
        // ist 2^n, wobei n die Größe der Liste ist.
        int totalSubsets = 1 << n; // Das ist gleich 2 hoch n (2^n)
       // Wir durchlaufen alle Zahlen von 0 bis 2^n - 1. Jede Zahl ist eine Bitmaske.
        for (int i = 0; i < totalSubsets; i++) {
            List<Integer> currentSubset = new ArrayList<>();
            // Überprüfe jedes Element im Eingabearray
            for (int j = 0; j < n; j++) {

                // Bitmasken-Logik:
                // Wenn das j-te Bit in der Zahl 'i' gesetzt ist (d.h. 1),
                // dann gehört das Element numbers[j] zur aktuellen Teilmenge.
                if ((i & (1 << j)) != 0) {
                    currentSubset.add(numbers.get(j));
                }
            }
            // Füge die gefundene Teilmenge zur Gesamtliste hinzu
            subsets.add(currentSubset);
        }
          return subsets;
    }
}
