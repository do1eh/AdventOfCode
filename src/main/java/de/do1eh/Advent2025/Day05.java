package de.do1eh.Advent2025;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day05 {
    /**
     * Tag5 Teil 2: Abgelaufene Lebensmittel finden
     * Teil 1 ist hier nicht zu finden, da ich diesen in einer Datenbank mit SQL gelöst habe.
     * Bei Teil 2 war dies nicht mehr so einfach möglich.
     * Die gemeinheit hier ist dass Nummernbereiche mit Anfangs und Endnummer angegeben sind, die sich überlappen können.
     * Da die Nummern einzeln gezählt werden müssen , müssen die Überlappungen entfernt werden.
     * Algo:
     * 1. sotieren so dass die Bereiche in der richtigen Reihenfolge sind
     * 2. Immer wenn die Endnummer kleiner als die Anfangsnummer der nächsten Bereis ist,
     * dann hat man eine Überlappung und man kann die beiden Bereiche zusammenfassen sprich:
     * Anfangsnummer des ersten Bereich bis zu endnummer de zweiten Bereichs.
     * Dann diesen neuen Bereich auf die selbe art mit dem nächsten Bereich vergleichen.
     * Immer wenn ein Bereich nicht zusammengefasst wird, dann diesen wegspeichern.
     * Am Ende die Anzahl ermitteln indem man von allen zusammengefassten Bereichen
     * der Endnummer die Anfangsnummer subtrahiert.
     *
     * Ein einfaches vollständiges durchlaufen der Bereiche ist nicht möglich, da
     * die Zahlen so groß sind, das das den Heap Speicher zum überlauf bringen würde.
     */
    public void day5part2() {
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2025/day/5/input", SESSION_COOKIE);
        List<String> input2=new ArrayList<>();
        long loesung=0;
        //System.out.println(input.size());
        for (String range : input) {
            //nur bis zur Leerzeile lesenif ()
            if (range.trim().equals("")) {
                break;
            }
            input2.add(range);
        }

        Comparator<String> comp=new RangeComparator();
        input2.sort(comp);
        boolean first=true;
        Long vonref=0L;
        Long bisref=0L;
        List<String> bereinigteinputs = new ArrayList<>();
        for (String range : input2) {
            System.out.println(range);
            if (first) {
                vonref = Long.parseLong(range.substring(0, range.indexOf('-')));
                bisref = Long.parseLong(range.substring(range.indexOf('-') + 1));
                first=false;
            } else {
                //System.out.println(range);
                long von = Long.parseLong(range.substring(0, range.indexOf('-')));
                long bis = Long.parseLong(range.substring(range.indexOf('-') + 1));
                //wenn von < als der vorherige bis wert-> zusammenfassen, sonst vorherigen wegspeichern
                if (von<=bisref){
                    if (bis>bisref) {
                        bisref = bis;
                    }
                } else{
                    bereinigteinputs.add(vonref+"-"+bisref);
                    vonref=von;
                    bisref=bis;
                }
            }
        }
        bereinigteinputs.add(vonref+"-"+bisref);
        for (String range : bereinigteinputs) {
            System.out.println(range);
            long von = Long.parseLong(range.substring(0, range.indexOf('-')));
            long bis = Long.parseLong(range.substring(range.indexOf('-') + 1));
            loesung+=(bis-von)+1;
        }
        System.out.println("original: "+ input2.size());
        System.out.println("bereinigt: "+ bereinigteinputs.size());
        System.out.println("Loesung: "+loesung);
    }
}
