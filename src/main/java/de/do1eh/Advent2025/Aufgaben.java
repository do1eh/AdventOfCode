package de.do1eh.Advent2025;

import java.util.*;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

/**
 * Achtung: Zum Ausführen benötigt man noch das SESSION_COOKIE
 * Dieses findet man indem man sich mit einem beliebigen browser bei Adventofcode.com einloggt,
 * Mit F12 die Dev Tools aufruft und sucht dort unter Application->Cookies  den Wert von 'session'.
 * Diesen mit STRG+C kopieren und einer Variablen
 * public final static String SESSION_COOKIE = "xxxxxx"; zur Verügung stellen.
 */
public class Aufgaben {
    int position=0;
     final Map<String, Long> memo = new HashMap<>();

    /**
     * Tag 1: Türschloss knacken
     * Teil1: Drehungen ausführen und wenn die 0 angezeigt wird für die Lösung zählen
     * Teil2: Jeden einzelnen Klick am Drehrad durcjführen und immer wenn eine 0 kommt für Lösung2 zählem
     */
     public void day1() {
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

    /**
     * Tag 2: Falsche Artikelnummern im Gift-Shop aussortieren
     * 1. Die Ranges von Artikelnummern durchlaufen
     * 2. Die Artikelnummer genau in der Mitte Teilen.
     * 3. Wenn beide Teil gleich sind ist die Nummer ungültig
      */
   public void day2() {
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
public void day2part2() {
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
                int pos = new Integer(i).intValue();
                position=pos;
            }
        }
    return wert;
}

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

    /**
     * Tag5 Zeil 2: Abgelaufene Lebensmittel finden
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

    /**
     * Tag 6:  Matheaufgaben der Cephaloiden
     * Da ist eigentlich nur eine Matrixtransformation bei der die Zeilen und Spalten
     * umgewandelt werden.
     * Ein Spaltenende kann man immer am Leerzeichen erkennen.
     */
    public void day6() {
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2025/day/6/input", SESSION_COOKIE);
        //Testdaten
        //List<String> input =new ArrayList<>();
        //input.add("123 328  51 64");
        //input.add(" 45 64  387 23");
        //input.add("  6 98  215 314");
        //input.add("*   +   *   +");
        long loesung=0;
        int anzahlaufgaben=0;
        while (!input.getLast().trim().isEmpty()) {
            String operator=input.getLast().trim()+" ";
            //System.out.println("Operator: "+operator.substring(0, operator.indexOf(' ')).trim()+"!");
            String operation = operator.substring(0, operator.indexOf(' ')).trim();
            //Operator aus input entdernen;
            input.set(input.size()-1, operator.substring(operator.indexOf(' ')).trim());
            long aufgabenloesung = 0;
            anzahlaufgaben++;

            for (int i = 0; i < input.size() - 1; i++) {
                //leerzeichen einfügen um ende zu markieren
                String operant=input.get(i).trim()+" ";
                switch (operation) {
                    case "+":
                        //System.out.print(operant.substring(0, operant.indexOf(' ')).trim()+" "+operation);
                        aufgabenloesung += Integer.parseInt(operant.substring(0, operant.indexOf(' ')).trim());
                        break;
                    case "*":
                        if (aufgabenloesung==0) {aufgabenloesung=1;}
                        //System.out.print(operant.substring(0, operant.indexOf(' ')).trim()+" "+operation);
                        aufgabenloesung *= Integer.parseInt(operant.substring(0, operant.indexOf(' ')).trim());
                        break;
                }
                System.out.print(operant.substring(0, operant.indexOf(' ')).trim()+" "+operation);
                String zahl=operant.substring(0, operant.indexOf(' ')).trim();
                //input schrumpfen
                input.set(i, operant.substring(operant.indexOf(' ')));
            }
            System.out.println(" = "+aufgabenloesung);
            loesung+=aufgabenloesung;
            }
        System.out.println("Anzahl Aufgaben: "+anzahlaufgaben);
        System.out.println("Loesung: "+loesung);
    }

    /**
     * Tag 6 Teil 2
     * Der war ganz schön fies. Man musst ersteinmal die Aufgabe verstehen.
     * In der Aufgabe ist die rede davon dass man die Spalten von rechts nach links lesen muss.
     * Diese Information ist völlig irrelevant, da addition und Multiplikation kommutativ sind.
     * Es ist also egal in welcher Richtung man liest.
     * Die Gemeinheit liegt in den Leerzeichen. Diese sind wichtig da nur
     * mit den Leerzeichen die richtigen Zahlen untereinander stehen.
     * Man kann also nicht einfach wie in Teil 1 überflüssige Leerzeichen entfernen.
     * Der Trick ist, dass man muss zuerst erkennen muss wo eine Spalte endet und die
     * nächste beginnt. Das ist immer dann der Fall wenn alle Zeilen jeweils
     * ein Leerzeichen an der selben Position über bzw. unterinander haben.
     * Damit das funktioniert müssen alle Zeilen gleich lang sein (was sie nicht sind, und mit einem
     * Leerzeichen enden. Deswegen müssen sie rechts mit Leerzeichen aufgefüllt werden.

     */
    public void day6part2() {
        List<String> input = Tools.readUrlContent("https://adventofcode.com/2025/day/6/input", SESSION_COOKIE);
        //Testdaten

        //List<String> input =new ArrayList<>();
        //input.add("123 328  51 64");
        //input.add(" 45 64  387 23");
        //input.add("  6 98  215 314");
        //input.add("*   +   *   +");


        //längste zeile bestimmen
        int zeilenlaenge=0;
        for (String z:input) {
            if(z.length()>zeilenlaenge) {zeilenlaenge=z.length();}
        }
        zeilenlaenge++;
        //System.out.println("zeilenlänge:"+zeilenlaenge);
        //alle Zeilen gleich lang machen
        for (int i=0;i<input.size();i++) {
            //System.out.println("vorher zeile"+i+ ":"+input.get(i).length());
            String anhaengen="";
            if(input.get(i).length()<zeilenlaenge) {
                for (int zeichen=0;zeichen<zeilenlaenge-input.get(i).length();zeichen++) {
                    anhaengen+=" ";
                }
                input.set(i,input.get(i)+anhaengen);
            }
            //System.out.println("nachher zeile "+i+ ":"+input.get(i).length());
        }
       long loesung=0;
       List<String> spalte=new ArrayList<>();
       int spaltennummer=0;
       String operator="";
        for (int cursor=0;cursor<input.getFirst().length();cursor++) {
            //neue spalte beginnt wenn nur leerzeichen in der spalte sind
            boolean neuespalte=true;
            for (int zeile=0;zeile<input.size();zeile++) {
                if(input.get(zeile).charAt(cursor)!=' ') {neuespalte=false;}
                //initialiseren falls neue spalte
                if (spalte.size()<spaltennummer+1) {spalte.add(spaltennummer,"");}
                //Falls in Operator zeile, operator hinzufügen
                if (input.get(zeile).charAt(cursor)=='+' ||input.get(zeile).charAt(cursor)=='*') {
                    operator=""+input.get(zeile).charAt(cursor);
                }else {
                    //Ziffer in spalte eintragen
                    spalte.set(spaltennummer, spalte.get(spaltennummer) + input.get(zeile).charAt(cursor));
                }
                if (zeile==input.size()-1 ) {spalte.set(spaltennummer, spalte.get(spaltennummer) + operator);}
                //if (cursor==input.getFirst().length()-1) {neuespalte=true;}
            }
            if (neuespalte || cursor==input.getFirst().length()) {
                //alle Leerzeichen raus
                spalte.set(spaltennummer,spalte.get(spaltennummer).replaceAll("\\s+", ""));
                while(spalte.get(spaltennummer).endsWith("*") ||spalte.get(spaltennummer).endsWith("+") ) {
                    spalte.set(spaltennummer,spalte.get(spaltennummer).substring(0,spalte.get(spaltennummer).length()-1));
                }
                System.out.println(spalte.get(spaltennummer)+"="+Tools.calculateSimpleExpression(spalte.get(spaltennummer)));
                loesung+=Tools.calculateSimpleExpression(spalte.get(spaltennummer));
                spaltennummer++;
                //System.out.println(spaltennummer+" "+cursor);
                operator="";
            }
        }
        System.out.println("Loesung: "+loesung);
    }

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

    public static void main(String[] args) {
      //Aufgaben advent = new Aufgaben();
      //advent.day7part2();
        Day9 advent = new Day9();
        advent.part2();

    }
}