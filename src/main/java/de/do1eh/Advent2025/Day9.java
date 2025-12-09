package de.do1eh.Advent2025;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.List;

import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
import static de.do1eh.Advent2025.Tools.*;

public class Day9 {

    public void part1() {

        List<String> input= readUrlContent("https://adventofcode.com/2025/day/9/input", SESSION_COOKIE);
        /*
         List<String> input =new ArrayList<String>();
        input.add("7,1");
        input.add("11,1");
        input.add("11,7");
        input.add("9,7");
        input.add("9,5");
        input.add("2,5");
        input.add("2,3");
        input.add("7,3");
        */
        long loesung = 0;
        System.out.println("size:" + input.size());
        for (int i = 0; i < input.size(); i++) {
            //System.out.println(input.get(i).trim());
            String ref = input.get(i);
            long refx = Integer.parseInt(input.get(i).trim().substring(0, input.get(i).trim().indexOf(',')));
            long refy = Integer.parseInt(input.get(i).trim().substring(input.get(i).trim().indexOf(',') + 1));

            for (int a = i + 1; a < input.size(); a++) {

                long x = Integer.parseInt(input.get(a).trim().substring(0, input.get(a).trim().indexOf(',')));
                long y = Integer.parseInt(input.get(a).trim().substring(input.get(a).trim().indexOf(',') + 1));

                long difx;
                long dify;
                if (x > refx) {
                    difx = x - refx + 1;
                } else {
                    difx = refx - x + 1;
                }
                if (y > refy) {
                    dify = y - refy + 1;
                } else {
                    dify = refy - y + 1;
                }

                long flaeche = difx * dify;
                if (flaeche > loesung) {
                    loesung = flaeche;
                }


            }


        }
        System.out.println(loesung);
    }

    public void part2() {

        List<String> input= readUrlContent("https://adventofcode.com/2025/day/9/input", SESSION_COOKIE);
/*
        List<String> input = new ArrayList<String>();
        input.add("7,1");
        input.add("11,1");
        input.add("11,7");
        input.add("9,7");
        input.add("9,5");
        input.add("2,5");
        input.add("2,3");
        input.add("7,3");
*/
        long loesung = 0;
        //System.out.println("size:"+input.size());
        GeometryFactory factory = new GeometryFactory();
        Coordinate[] coords = new Coordinate[input.size()+1];
        for (int i = 0; i < input.size(); i++) {

            int inputx = Integer.parseInt(input.get(i).trim().substring(0, input.get(i).trim().indexOf(',')));
            int inputy = Integer.parseInt(input.get(i).trim().substring(input.get(i).trim().indexOf(',') + 1));

            coords[i] = new Coordinate(inputx, inputy);
        }
        //am ende noch einmal die erste um den ring zu schließen
        int inputx = Integer.parseInt(input.get(0).trim().substring(0, input.get(0).trim().indexOf(',')));
        int inputy = Integer.parseInt(input.get(0).trim().substring(input.get(0).trim().indexOf(',') + 1));

        coords[input.size()] = new Coordinate(inputx, inputy);

// 3. Erstellen Sie den Linearen Ring (die Hülle des Polygons)
        LinearRing shell = factory.createLinearRing(coords);

// 4. Erstellen Sie das Polygon (ohne innere Löcher, daher 'null' für innerHoles)
        Polygon polygonA = factory.createPolygon(shell, null);


        for (int i = 0; i < input.size(); i++) {
            //System.out.println(input.get(i).trim());
            String ref = input.get(i);
            long refx = Integer.parseInt(input.get(i).trim().substring(0, input.get(i).trim().indexOf(',')));
            long refy = Integer.parseInt(input.get(i).trim().substring(input.get(i).trim().indexOf(',') + 1));

            for (int a = i + 1; a < input.size(); a++) {

                long x = Integer.parseInt(input.get(a).trim().substring(0, input.get(a).trim().indexOf(',')));
                long y = Integer.parseInt(input.get(a).trim().substring(input.get(a).trim().indexOf(',') + 1));

                long difx;
                long dify;
                if (x > refx) {
                    difx = x - refx + 1;
                } else {
                    difx = refx - x + 1;
                }
                if (y > refy) {
                    dify = y - refy + 1;
                } else {
                    dify = refy - y + 1;
                }

                long flaeche = difx * dify;
                if (flaeche > loesung) {
                    Coordinate[] coordsB = new Coordinate[]{
                            new Coordinate(x, y),
                            new Coordinate(refx, y),
                            new Coordinate(refx, refy),
                            new Coordinate(x, refy),
                            new Coordinate(x, y),

                    };

                    LinearRing shellB = factory.createLinearRing(coordsB);
                    Polygon polygonB = factory.createPolygon(shellB, null);

                    if (polygonA.contains(polygonB)) {
                        loesung = flaeche;
                    }
                }


            }

        }
        System.out.println(loesung);
    }

}
