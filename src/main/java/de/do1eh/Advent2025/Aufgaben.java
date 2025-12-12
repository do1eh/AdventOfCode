package de.do1eh.Advent2025;

/**
 * Achtung: Zum Ausführen benötigt man noch das SESSION_COOKIE
 * Dieses findet man indem man sich mit einem beliebigen browser bei Adventofcode.com einloggt,
 * Mit F12 die Dev Tools aufruft und sucht dort unter Application->Cookies  den Wert von 'session'.
 * Diesen mit STRG+C kopieren und einer Variablen
 * public final static String SESSION_COOKIE = "xxxxxx"; zur Verügung stellen.
 */
public class Aufgaben {

    public static void main(String[] args) {

        try {
            Day12 advent = new Day12();
            advent.part1();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}