package de.do1eh.Advent2025;
import java.util.ArrayList;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day03 {
    int position=0;
    /**
     * Tag 3: Batterien an die Rolltreppe anschließen damit sie fährt
     * Teil 1: Genau 2 Batterien anschließen so dass die maximale Jpannung erreicht wird.
     * Teil 2: Genau 12 Batterien anschließen so dass die maximale Jpannung erreicht wird.
     *
     * Gemeinheit dabei: Die Zahlen werden so groß dass long verwendet werden muss, bei Teil 1 hat int genügt.
     * Man kann nicht mehr einfach die Batterie mit der höchsten Jpannung nehmen.
     * Man muss darauf achten dass nach dieser Batterie noch genug weiter Batterien vorhanden sind
     * mit man zum Schluss auch 12 Batterien anschließen kann.
     * Hätten z.B. alle Batterien 1Jot und die letzt 100 JOlt und man würde diese einschalten könnte
     * man keine weitere mehr anschließen da diese bereits die letzte war.
     * Also Läuft die Schleife rückwärts von 12 bis 1 und ignoriert immer die letzten i Batterien bei
     * der Auswahl der größten. So ist garantiert dass noch genug Batterien da sind.
     */
    public void day3(){
        //16854
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/3/input",SESSION_COOKIE);
        long joltage=0;
        int anzahlbatterien=12;

        for (String zeile : input) {
            position=-1;
            String joltagezeile="";
            List<Character> joltagedigits=new ArrayList<>();

            for(int i=anzahlbatterien-1;i>=0;i--) {
                joltagedigits.add(getHighestNumber(zeile,position+1,zeile.length()-i));
            }
            for (Character digit:joltagedigits) {
                joltagezeile+=String.valueOf(digit);
            }
            long wert= Long.parseLong(joltagezeile);
            //System.out.println(zeile);
            //System.out.println(wert);
            joltage+=wert;
        }
        System.out.println("Lösung: "+joltage);
    }

    /**
     * Hilfsmethode für Tag 3 um die höchste Jpannung zu ermitteln
     * @param zeile
     * @param von
     * @param bis
     * @return
     */
    public Character getHighestNumber(String zeile,int von,int bis) {
        char wert='0';
        System.out.println("vonbis "+von+"-"+bis+" "+zeile.substring(von,bis));
        for ( int i = von; i < bis; i++) {
            if(zeile.charAt(i)>wert) {
                wert=zeile.charAt(i);
                position=(int)i;
            }
        }
        return wert;
    }
}
