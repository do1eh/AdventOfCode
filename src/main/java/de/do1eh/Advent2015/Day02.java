package de.do1eh.Advent2015;

import de.do1eh.Advent2025.Tools;

import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;

public class Day02 {

    public void part1(){
       List<String> input= Tools.readUrlContent("https://adventofcode.com/2015/day/2/input", SESSION_COOKIE);
      /*
       List<String> input=new ArrayList<>();
        input.add("2x3x4");
        input.add("4x3x2");
        input.add("2x4x3");
        input.add("4x2x3");
        input.add("3x4x2");
        input.add("3x2x4");
        //input.add("1x1x10");
       // input.add("10x10x10");
      */
        long loesung=0;
        long loesung2=0;
        for (String geschenk:input) {
            System.out.println(geschenk);
            String[] dim=geschenk.split("x");
           //2*l*w + 2*w*h + 2*h*l
            long l=Integer.parseInt(dim[0]);
            long w=Integer.parseInt(dim[1]);
            long h=Integer.parseInt(dim[2]);

            long papier=(2*l*w) + (2*w*h) + (2*h*l);
            long f1=l*w;
            long f2=w*h;
            long f3=h*l;

            long kleinsteflaeche=1000;
            if (f1<kleinsteflaeche) {kleinsteflaeche=f1;}
            if (f2<kleinsteflaeche) {kleinsteflaeche=f2;}
            if (f3<kleinsteflaeche) {kleinsteflaeche=f3;}
            papier+=kleinsteflaeche;
            loesung+=papier;

            long kleinsteseite=1000;
            long zweitkleinsteseite=1000;

            if (l<kleinsteseite) {kleinsteseite=l;}
            if (w<=kleinsteseite) {zweitkleinsteseite=kleinsteseite; kleinsteseite=w;} else {zweitkleinsteseite=w;};
            if (h<=kleinsteseite) {zweitkleinsteseite=kleinsteseite;kleinsteseite=h;} else {
                if (h<zweitkleinsteseite) {zweitkleinsteseite=h;}
            }

            //System.out.println("kleinste seiten:"+kleinsteseite+" "+zweitkleinsteseite);
            long bandlaenge=2*kleinsteseite+2*zweitkleinsteseite;
            //System.out.println("Bandlänge:"+bandlaenge);
            //System.out.println("Volumen:"+l*w*h);
            bandlaenge+=l*w*h;
            loesung2+=bandlaenge;
        }

        System.out.println("Lösung="+loesung);
        System.out.println("Lösung2="+loesung2);
        }
    }


