package de.do1eh.Advent2025;
import java.util.ArrayList;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;
public class Day6 {

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
}
