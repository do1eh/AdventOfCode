package de.do1eh.Advent2025;

import java.util.Comparator;


/**
 * Ein Comparator, der Strings im Format "Zahl-Zahl" (z.B. "10-2")
 * basierend auf dem numerischen Wert der Zahl vor dem Bindestrich (-) sortiert.
 */
public class RangeComparator  implements Comparator<String> {


    @Override
    public int compare(String s1, String s2) {

        // 1. Erste Zahl aus s1 extrahieren und konvertieren
        long zahl1 = getErsteZahl(s1);

        // 2. Erste Zahl aus s2 extrahieren und konvertieren
        long zahl2 = getErsteZahl(s2);

        // 3. Numerischer Vergleich der ersten Zahlen
        // Integer.compare(a, b) ist der empfohlene Weg, um int-Werte zu vergleichen.
        // Es gibt: < 0, 0, oder > 0 zurück.
        return Long.compare(zahl1, zahl2);
    }

    /**
     * Hilfsmethode zur Extraktion der ersten Zahl aus dem String (alles vor dem ersten Bindestrich).
     * Gibt 0 zurück, falls der String nicht dem erwarteten Format entspricht (Fehlerbehandlung).
     */
    private long getErsteZahl(String s) {
        try {
            // Finde den Index des ersten Bindestrichs
            int bindestrichIndex = s.indexOf('-');

            if (bindestrichIndex == -1) {
                // Kein Bindestrich gefunden, was nicht erwartet wird
                return 0;
            }

            // Extrahiere den Teil des Strings vor dem Bindestrich
            String zahlTeil = s.substring(0, bindestrichIndex);

            // Konvertiere den extrahierten String in einen Integer
            return Long.parseLong(zahlTeil.trim());

        } catch (NumberFormatException e) {
            // Fehler bei der Konvertierung (z.B. wenn "abc-5" übergeben wird)
            System.err.println("Fehler beim Parsen der Zahl in String: " + s);
            return 0;
        }
    }
}


