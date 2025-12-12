package de.do1eh.Advent2025;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;

public class Day12 {

    /**
     * Für jede Form eine liste mit allen varianten erstellen (gereht und geflippt=
     * Der Raum ist ein boolean array. 1=platz belegt 0=platz frei
     * Rekursiver Backtracking algo
     * Aufruf mit Geschenkenummer (Rückgabe: boolean)
     *    Abbruchbedingung: Wenn kein Geschenkenummer> als #Geschenke -> gewonnen
     *    Varianten durchlaufen
     *       Alle Positionen im Raum durchlaufen
     *          Wenn Geschenk in der Variante in der Posistion passt (alle notwendigen punkte=0)
     *              Geschenk plazieren (im Raum die 0en in 1en ändern
     *              if (Rekursiver Aufruf mit Geschenkenummer+1) return true
     *              Geschenk wieder wegnehmen (Backtracking)
     *           Else
     *              Nichts machrn
     *        Wenn am ende alle schleifen durchgelaufen sind also nichts passiert ist:
     *           return false
     */
    public void part1() {}

    List<String> input= readUrlContent("https://adventofcode.com/2025/day/12/input", SESSION_COOKIE);
        /*
         input.add("7,1");
      List<String> input =new ArrayList<String>();
        input.add("7,1");
        input.add("11,1");
        input.add("11,7");
        input.add("9,7");
        input.add("9,5");
        input.add("2,5");
        input.add("2,3");
        input.add("7,3"); List<String> input= readUrlContent("https://adventofcode.com/2025/day/9/input", SESSION_COOKIE);
        /*
        */

    int loesung=0;

}
