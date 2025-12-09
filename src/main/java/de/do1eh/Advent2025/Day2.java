package de.do1eh.Advent2025;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;
public class Day2 {

    /**
     * Tag 2: Falsche Artikelnummern im Gift-Shop aussortieren
     * 1. Die Ranges von Artikelnummern durchlaufen
     * 2. Die Artikelnummer genau in der Mitte Teilen.
     * 3. Wenn beide Teil gleich sind ist die Nummer ungültig
     */
    public void part1() {
        List<String> input=List.of(Tools.readUrlContent("https://adventofcode.com/2025/day/2/input",SESSION_COOKIE).getFirst().split(","));
        long loesung=0;
        System.out.println(input.size());
        for (String range : input) {
            long von=Long.parseLong(range.substring(0,range.indexOf('-')));
            long bis=Long.parseLong(range.substring(range.indexOf('-')+1));
            for (long i=von;i<=bis;i++) {
                String zahlalsString=String.valueOf(i);
                String string1=zahlalsString.substring(0,zahlalsString.length()/2);
                String string2=zahlalsString.substring((zahlalsString.length()/2));
                if(string1.equals(string2)) {
                    loesung+=i;
                }
            }
        }
        System.out.println("Loesung: "+loesung);
    }

    /**
     * Tag 2 Teil 2: Jetzt dürfen sich auch Teile der Artikelnummern nicht mehr wiederholen
     * 1. Einen Cursor  der größe 1 definieren und durch die Nummer laufen
     * 2. Wenn alle die Zahlen im Cursor beim gesamten durchlauf gleich sind, dann ist die Nummer ungültig
     * 3. Cursor um eins vergößer, so dass jetzt zwei Ziffern hineinpassen.
     * 4. siehe 2.
     * 5. Cursor so lange vergößern bis er halb so groß wie die Artikelnummer ist.
     * Größer macht keinen sinn da es mindestens eine Wiederholung der ZIffern geben muss
     * und der Cursor dann nur noch 2 mal in die Nummer passt. Ein Schitt größer würde
     * nicht mehr zwei mal hineinpassen und es kann keine wiederholung geben.
     */
    public void part2() {
        List<String> input = List.of(Tools.readUrlContent("https://adventofcode.com/2025/day/2/input", SESSION_COOKIE).getFirst().split(","));
        long loesung = 0;
        int breiteste=0;
        for (String range : input) {
            long von = Long.parseLong(range.substring(0, range.indexOf('-')));
            long bis = Long.parseLong(range.substring(range.indexOf('-') + 1));
            for (long i = von; i <= bis; i++) {
                String zahlalsString = String.valueOf(i);
                if (zahlalsString.length() > breiteste) {breiteste = zahlalsString.length();}
                //nur wenn zahl durch cursorbreite teilbar ist
                //Cursorfenster über zahl laufen lassen
                //Abbruck sobald der nächste step nicht übereinstimmt
                //Wenn am ende angekommen loesung++
                //sonst Cursor um1 breiter machen
                boolean invalid = true;
                for (int cursorbreite = 1; cursorbreite <= zahlalsString.length() / 2; cursorbreite++) {
                    if (zahlalsString.length() % cursorbreite == 0) {
                        String referenz = zahlalsString.substring(0, cursorbreite);
                        for (int cursorpos = cursorbreite; cursorpos <= zahlalsString.length() - cursorbreite; cursorpos += cursorbreite) {
                            if (!referenz.equals(zahlalsString.substring(cursorpos, cursorpos + cursorbreite))) {
                                invalid = false;
                                break;
                            }
                        }
                        if (invalid) {
                            //System.out.println(cursorbreite + " inv:" + zahlalsString);
                            loesung+=Long.parseLong(zahlalsString);
                            break;
                        } else {
                            invalid = true;
                        }
                    }
                }
            }
        }
        System.out.println("Loesung: " + loesung);
    }
}
