package de.do1eh.Advent2025;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day07 {
    final Map<String, Long> memo = new HashMap<>();
    /**
     * Tag 7: Teleporter
     * Heute wars wieder einfacher. Zumindest Teil 1.
     * Ich dachte zuerst das schreit nach Rekursion...hab mich dann aber dagegen erschienen
     * Ich arbeite hier immer mit der Originalzeile aus dem Input und einer Referenzzeile,
     * die der vorherigen Zeile entspricht.
     */
    public void day7(){
        int loesung=0;
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/7/input",SESSION_COOKIE);
        //testdaten
        /**
         List<String> input =new ArrayList<>();

         input.add(".......S.......");
         input.add("...............");
         input.add(".......^.......");
         input.add("...............");
         input.add("......^.^......");
         input.add("...............");
         input.add(".....^.^.^.....");
         input.add("...............");
         input.add("....^.^...^....");
         input.add("...............");
         input.add("...^.^...^.^...");
         input.add("...............");
         input.add("..^...^.....^..");
         input.add("...............");
         input.add(".^.^.^.^.^...^.");
         input.add("...............");
         */
        int[] referenz=new int[input.getFirst().length()+2];
        referenz[input.getFirst().indexOf('S')+1]=1;
        for (int zeile=1;zeile<input.size();zeile++) {
            //Zeile durchlaufen
            for (int position=0;position<input.get(zeile).length();position++) {
                //Wenn Zeile an der Position "^" hat und referenz 'I' dann
                //referenz -1=I 0='' +1=I
                if(input.get(zeile).charAt(position)=='^' && referenz[position+1]==1) {
                    referenz[position]=1;
                    referenz[position+1]=0;
                    referenz[position+2]=1;
                    loesung++;
                }
            }
            System.out.println(zeile+" ."+input.get(zeile)+".");
            System.out.println(zeile+" "+array2String(referenz));
        }
        System.out.println("Lösung: "+loesung);
    }

    /**
     * Tag 7 Teil 2: Tachionen
     * Jetzt geht's nciht mehr ohne Rekursion....allerdings auch nicht ohne optimierung (siehe unten)
     * Ist quasi ein klassischer "All pairs shortest path" Algo.
     */
    public void day7part2(){
        long loesung=0;
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/7/input",SESSION_COOKIE);
        //testdaten
/*
       List<String> input =new ArrayList<>();
         input.add(".......S.......");
         input.add("...............");
         input.add(".......^.......");
         input.add("...............");
         input.add("......^.^......");
         input.add("...............");
         input.add(".....^.^.^.....");
         input.add("...............");
         input.add("....^.^...^....");
         input.add("...............");
         input.add("...^.^...^.^...");
         input.add("...............");
         input.add("..^...^.....^..");
         input.add("...............");
         input.add(".^.^.^.^.^...^.");
         input.add("...............");
*/
        int[] referenz=new int[input.getFirst().length()+2];
        referenz[input.getFirst().indexOf('S')+1]=1;
        loesung=day7rekustiv2(input,1,referenz);
        System.out.println("Lösung: "+loesung);
    }

    /**
     * Diese Methode ist nicht wirklich nutzbar, da die eine exponentielle Laufzeit hat.
     * Das TestBeispiel funktioniert, aber bei der richtigen Aufgabe
     * habe ich nach ca 30 Minuten ohne Ergebnis abgebrochen.
     * Optimierte Version mit polinomieller LAufzeit: day7rekursiv2
     * @param input
     * @param zeile
     * @param referenz
     * @param loesung
     * @deprecated
     * @return
     */
    public long day7rekustiv(List<String> input, int zeile,int[] referenz,long loesung) {
        boolean split=false;
        for (int position=0;position<input.get(zeile).length();position++) {
            if(input.get(zeile).charAt(position)=='^' && referenz[position+1]==1) {
                split=true;
                referenz=new int[input.getFirst().length()+2];
                referenz[position]=1;
                loesung=day7rekustiv(input,zeile+1,referenz,loesung);
                referenz=new int[input.getFirst().length()+2];
                referenz[position+2]=1;
                loesung=day7rekustiv(input,zeile+1,referenz,loesung);
            }
        }
        if(zeile<input.size()-1 && !split) {
            loesung=day7rekustiv(input,zeile+1 ,referenz,loesung);
        }
        else if(zeile==input.size()-1 && !split){
            loesung++;
        }
        System.out.println(loesung);
        return loesung;
    }

    private String array2String(int[] arr) {
        return Arrays.toString(arr);
    }

    /**
     * Optimierte Version Laufzeit ca 4 Sekunden
     * @param input
     * @param zeile
     * @param referenz
     * @return
     */
    public long day7rekustiv2(List<String> input, int zeile, int[] referenz) {
        String currentState = zeile + "_" + array2String(referenz);
        //Fall Ergebnis schon bekannt: direkt zurückgeben. Erklärung siehe unten beim return
        if (memo.containsKey(currentState)) {
            return memo.get(currentState);
        }
        if (zeile == input.size() - 1) {
            // Hier zählen wir, ob der Pfad in der letzten Zeile valide endet.
            // Wenn der Pfad bis hierher gekommen ist, zählen wir ihn.
            // Der ursprüngliche Code zählt hier 'loesung++', was 1 Pfad entspricht.
            return 1L;
        }
        long anzahlPfade = 0;
        boolean split = false;
        // Zeile durchlaufen
        for (int position = 0; position < input.get(zeile).length(); position++) {
            if (input.get(zeile).charAt(position) == '^' && referenz[position + 1] == 1) {
                split = true;

                // --- LINKER PFAD ---
                int[] refLinks = new int[referenz.length]; // Array-Größe beibehalten
                refLinks[position] = 1;
                // Addiere die Anzahl der Pfade, die der linke rekursive Aufruf zurückgibt
                anzahlPfade += day7rekustiv2(input, zeile + 1, refLinks);
                // --- RECHTER PFAD ---
                int[] refRechts = new int[referenz.length]; // Array-Größe beibehalten
                refRechts[position + 2] = 1;
                // Addiere die Anzahl der Pfade, die der rechte rekursive Aufruf zurückgibt
                anzahlPfade += day7rekustiv2(input, zeile + 1, refRechts);
            }
        }
        // Wenn kein Split stattfand, führe die Rekursion zur nächsten Zeile fort
        if (!split) {
            anzahlPfade += day7rekustiv2(input, zeile + 1, referenz);
        }

        // Die Übergabe und Papameter wegspeichern, damit falls die Methode nocheinmal
        //mit den selben Paparemtern aufgerufen wird nnicht mehr berechnet werden muss
        //sondern direkt das gespeicherte Ergebnis zurückgegeben werden kann.
        //Das ist die eigentliche optimierung die Den Algo überhaupt
        //erst benutzbar macht.
        memo.put(currentState, anzahlPfade);
        return anzahlPfade;
    }
}
