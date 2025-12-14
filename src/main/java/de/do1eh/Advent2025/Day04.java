package de.do1eh.Advent2025;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day04 {

    /**
     * Tag 4: Papierrollen sortieren
     * Hier habe ich ein Zweidimensionales Array als Datenstruktur zur Abbildung des Lagers gewählt um
     * direkt mit relativen Koordinaten auf die Stellplätze zugreigen zu können.
     * Damit es keine out of bounds exception gibt hat das Virtuelle Lager einen Schutzbereich rings herum
     * sprich eine Reihe leerer Lagerplätze die nicht verwendet aber abgefragt werden können.
     */
    public void day4() {
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/4/input",SESSION_COOKIE);
        long loesung=0;
        //135x135 mit einem Leeren Rand
        boolean[][] lager=new boolean[137][137];
        int y=1;
        for (String zeile : input) {
            //Lager füllen
            for (int x = 1; x <= 135; x++) {
                if (zeile.charAt(x-1) == '@') {
                    lager[x][y] = true;
                }
            }
            y++;
        }
        //Lager durchlaufen
        for (y=1;y<=135;y++) {
            for (int x=1;x<=135;x++) {
                //Nachbarn zählen
                if (lager[x][y]) {
                    int nachbarn = 0;
                    if (lager[x - 1][y - 1]) {
                        nachbarn++;
                    }
                    if (lager[x][y - 1]) {
                        nachbarn++;
                    }
                    if (lager[x + 1][y - 1]) {
                        nachbarn++;
                    }
                    if (lager[x - 1][y]) {
                        nachbarn++;
                    }
                    if (lager[x + 1][y]) {
                        nachbarn++;
                    }
                    if (lager[x - 1][y + 1]) {
                        nachbarn++;
                    }
                    if (lager[x][y + 1]) {
                        nachbarn++;
                    }
                    if (lager[x + 1][y + 1]) {
                        nachbarn++;
                    }
                    if (nachbarn < 4) {
                        loesung++;
                    }
                }
            }
        }
        System.out.println("Loesung: "+loesung);

    }

    /**
     * Tag4 Teil 2:
     * Führt man den Algo mehrfach aus können weitere Rollen entfernt werden.
     * Das ganze so lange bis es nicht mehr geht.
     * Dazu wird eine Boolean r genutzt die anzeigt ob in diese Runde Rollen entfernt wurden.
     * Falls das nicht der Fall war, hat man das Ziel erreicht.
     */
    public void day4part2() {
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2025/day/4/input", SESSION_COOKIE);
        long loesung = 0;
        //135x135 mit einem Leeren Rand
        boolean[][] lager = new boolean[137][137];
        int y = 1;
        for (String zeile : input) {
            //Lager füllen
            for (int x = 1; x <= 135; x++) {
                if (zeile.charAt(x - 1) == '@') {
                    lager[x][y] = true;
                }
            }
            y++;
        }
        boolean weiter = true;
        while (weiter) {
            weiter = false;
            //Lager durchlaufen
            for (y = 1; y <= 135; y++) {
                for (int x = 1; x <= 135; x++) {
                    //Nachbarn zählen
                    if (lager[x][y]) {
                        int nachbarn = 0;
                        if (lager[x - 1][y - 1]) {
                            nachbarn++;
                        }
                        if (lager[x][y - 1]) {
                            nachbarn++;
                        }
                        if (lager[x + 1][y - 1]) {
                            nachbarn++;
                        }
                        if (lager[x - 1][y]) {
                            nachbarn++;
                        }
                        if (lager[x + 1][y]) {
                            nachbarn++;
                        }
                        if (lager[x - 1][y + 1]) {
                            nachbarn++;
                        }
                        if (lager[x][y + 1]) {
                            nachbarn++;
                        }
                        if (lager[x + 1][y + 1]) {
                            nachbarn++;
                        }

                        if (nachbarn < 4) {
                            loesung++;
                            lager[x][y] = false;
                            weiter = true;
                        }
                    }
                }
            }
        }
        System.out.println("Loesung: " + loesung);
    }
}
