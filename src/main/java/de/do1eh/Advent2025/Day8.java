package de.do1eh.Advent2025;
import de.do1eh.Utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Day8 {

    public void part1() {

        DB db=new DB();
        Connection conn=db.getConnection();

        int loesung=0;
    //List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/7/input",SESSION_COOKIE);
    //testdaten
     List<String> input =new ArrayList<>();

           input.add("162,817,812");
           input.add("57, 618,57");
           input.add("906,360,560");
           input.add("592,479,940");
           input.add("352,342,300");
           input.add("466,668,158");
           input.add("542, 29,236");
           input.add("431,825,988");
           input.add("739,650,466");
           input.add("52, 470,668");
           input.add("216,146,977");
           input.add("819,987,18");
           input.add("117,168,530");
           input.add("805, 96,715");
           input.add("346,949,466");
           input.add("970,615,88");
           input.add("941,993,340");
           input.add("862, 61,35");
           input.add("984, 92,344");
           input.add("425,690,689");


           //Input Tabelle erstellen
        try {
            Statement stmt = conn.createStatement();
            String sqlCreate = "create table input (gruppe decimal, x decimal,y decimal, z decimal);\n";
            stmt.executeUpdate(sqlCreate);
            for (String value : input) {
                String sqlInsert = "INSERT INTO input VALUES (0," + value + ")";
                stmt.executeUpdate(sqlInsert);
            }

            String sqlView = "CREATE VIEW abstaende AS " +
                    "select input.x x1,input.y y1 ,input.z z1 ,input2.x x2,input2.y y2,input2.z z2, " +
                    "sqrt(power((input2.x-input.x),2)+power((input2.y-input.y),2)+power((input2.z-input.z),2))  abstand " +
                    "from input  " +
                    "join input input2 " +
                    "ON(INPUT.x < INPUT2.x) " +
                    "OR (INPUT.x = INPUT2.x AND INPUT.y < INPUT2.y) " +
                    "OR (INPUT.x = INPUT2.x AND INPUT.y = INPUT2.y " +
                    "AND INPUT.z < INPUT2.z) " +
                    "where abstand>0";

            sqlView = "CREATE VIEW abstaende AS " +
                    "SELECT " +
                    "x1, y1, z1, x2, y2, z2, abstand " + // Wähle die Aliasnamen aus der Subquery
                    "FROM (" +
                    // Subquery: Hier wird der 'abstand' berechnet und als Alias bekannt gemacht
                    "SELECT " +
                    "input.x x1, input.y y1, input.z z1, " +
                    "input2.x x2, input2.y y2, input2.z z2, " +
                    "SQRT(POWER((input2.x - input.x), 2) + POWER((input2.y - input.y), 2) + POWER((input2.z - input.z), 2)) AS abstand " +
                    "FROM " +
                    "input " +
                    "JOIN " +
                    "input input2 ON (INPUT.x < INPUT2.x) OR (INPUT.x = INPUT2.x AND INPUT.y < INPUT2.y) OR (INPUT.x = INPUT2.x AND INPUT.y = INPUT2.y AND INPUT.z < INPUT2.z) " +
                    ") AS Berechnungen " + // Alias für die Subquery ist nötig
                    "WHERE abstand > 0;"; // Filtern des Aliases in der äußeren WHERE-Klausel

            stmt.executeUpdate(sqlView);

            PreparedStatement selectgruppestmt = conn.prepareStatement("select gruppe from input where x=? and y=? and z=?", ResultSet.TYPE_SCROLL_INSENSITIVE);
            PreparedStatement updategruppestmt = conn.prepareStatement("update input set gruppe=? where x=? AND y=? AND z=?");

            String sqlSelect = "SELECT * from abstaende order by abstand";
            ResultSet rs = stmt.executeQuery(sqlSelect);

            int hoechstegruppennr = 0;
            int zaehler = 0;
            //Für alle abstände
            while (rs.next() && zaehler<10) {
                zaehler++;
                System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
                int gruppennr1 = 0;
                int gruppennr2 = 0;
                boolean flag = false;
                //Gruppe prüfen
                //select gruppe from input where (x=? and y=? and z=?)
                selectgruppestmt.setInt(1, rs.getInt(1));
                selectgruppestmt.setInt(2, rs.getInt(2));
                selectgruppestmt.setInt(3, rs.getInt(3));
                ResultSet rsGruppe = selectgruppestmt.executeQuery();
                if (rsGruppe.next()) {
                    gruppennr1 = rsGruppe.getInt(1);
                }

                selectgruppestmt.setInt(1, rs.getInt(4));
                selectgruppestmt.setInt(2, rs.getInt(5));
                selectgruppestmt.setInt(3, rs.getInt(6));
                rsGruppe = selectgruppestmt.executeQuery();

                if (rsGruppe.next()) {
                    gruppennr2 = rsGruppe.getInt(1);
                }

                int gruppennr = 0;

                if (0 == gruppennr1 && 0 == gruppennr2) {
                    hoechstegruppennr++;
                    gruppennr = hoechstegruppennr;
                }
                if (0 == gruppennr1 && 0 < gruppennr2) {
                    gruppennr = gruppennr2;
                }
                if (0 == gruppennr2 && 0 < gruppennr1) {
                    gruppennr = gruppennr1;
                }

                if (gruppennr2 ==gruppennr1 && gruppennr1>0) {
                    gruppennr = gruppennr1;
                }

                System.out.println("G1:"+gruppennr1);
                System.out.println("G2:"+gruppennr2);
                System.out.println(gruppennr);
                //Gruppe für x1,y1,z1 setzen
                //update input set gruppe=? where x=? AND y=? & z=?
                if (gruppennr1 == 0 && (gruppennr2==0 ||gruppennr2==gruppennr)) {
                    updategruppestmt.setInt(1, gruppennr);
                    updategruppestmt.setInt(2, rs.getInt(1));
                    updategruppestmt.setInt(3, rs.getInt(2));
                    updategruppestmt.setInt(4, rs.getInt(3));
                    updategruppestmt.executeUpdate();
                }
                //Gruppe für x2,y2,z3 setzen
                if (gruppennr2 == 0  && (gruppennr1==0 || gruppennr1==gruppennr)) {
                    updategruppestmt.setInt(1, gruppennr);
                    updategruppestmt.setInt(2, rs.getInt(4));
                    updategruppestmt.setInt(3, rs.getInt(5));
                    updategruppestmt.setInt(4, rs.getInt(6));
                    updategruppestmt.executeUpdate();
                }
            }

                //Fertig
                String sql = "SELECT * from input";
                ResultSet rs3 = stmt.executeQuery(sql);

                while (rs3.next()) {

                    System.out.println("G:" + rs3.getInt(1) + "K:" + rs3.getInt(2) + " " + rs3.getInt(3) + " " + rs3.getInt(4));
                }

             sql = "select gruppe, count(*) as Anzahl from input group by gruppe order by Anzahl desc ";
             rs3 = stmt.executeQuery(sql);
            while (rs3.next()) {

                System.out.println("G:" + rs3.getInt(1) + "Anzahl:" + rs3.getInt(2));
            }

            }


     catch (
    SQLException e) {
        e.printStackTrace();
    }






        System.out.println("Lösung: "+loesung);
}


    public static void main(String[] args) {
        Day8 advent = new Day8();
        advent.part1();
    }


}
