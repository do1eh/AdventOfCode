package de.do1eh.Advent2025;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;
public class Day1 {

    /**
     * Tag 1: Türschloss knacken
     * Teil1: Drehungen ausführen und wenn die 0 angezeigt wird für die Lösung zählen
     * Teil2: Jeden einzelnen Klick am Drehrad durchführen und immer wenn eine 0 kommt für Lösung2 zählem
     */
    public void part12() {
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/1/input", SESSION_COOKIE);
        int zerocounter=0;
        int zeroclickcounter=0;
        int aktuelleNummer=50;
        int click=50;
        for (String zeile : input) {
            int zahl=Integer.parseInt(zeile.substring(1));
            if (zeile.startsWith("R")) {
                aktuelleNummer+=zahl;
                for (int clicks=0;clicks<zahl;clicks++) {
                    click++;
                    if (click==100) {
                        click=0;
                    }
                    if (click==0) {
                        zeroclickcounter++;
                    }
                }
            } else {
                aktuelleNummer-=zahl;
                for (int clicks=0;clicks<zahl;clicks++) {
                    click--;
                    if (click==-1) {
                        click=99;
                    }
                    if (click==0) {
                        zeroclickcounter++;
                    }
                }
            }
            while (aktuelleNummer<0) {
                aktuelleNummer+=100;
            }
            while (aktuelleNummer>99) {
                aktuelleNummer-=100;
            }
            if (aktuelleNummer==0) {
                zerocounter++;
            }
        }
        //Lösung: 1135
        System.out.println("Lösung: "+zerocounter);
        System.out.println("Lösung2: "+zeroclickcounter);
    }
}
