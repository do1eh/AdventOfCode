package de.do1eh.Advent2025;
import de.do1eh.Utils.DB;
import java.sql.*;
import java.util.List;
import static de.do1eh.Advent2025.Session.SESSION_COOKIE;
/**
 * Puaha...doch noch geschafft...ich mag SQL
 * Die gemeinheit war dass man nicht wußte dass es eigentlich um verschmelzen von
 * Gruppen ging. Also: Jeder ist in einer eigenen Gruppe, sobald ein Pärchen gefunden
 * ist bekommen ALLE Boxen aus der Gruppe des zweiten die Gruppe des ersten.
 */
public class Day08 {
    public void part1() {
        DB db=new DB();
        Connection conn=db.getConnection();
        int loesung=1;
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/8/input",SESSION_COOKIE);
    //testdaten
    /*
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
*/
           //Input Tabelle erstellen
        try {
            Statement stmt = conn.createStatement();
            String sqlCreate = "create table input (gruppe decimal, x decimal,y decimal, z decimal);\n";
            stmt.executeUpdate(sqlCreate);
            int i = 1;
            for (String value : input) {
               String sqlInsert = "INSERT INTO input VALUES (" + i + "," + value + ")";
                stmt.executeUpdate(sqlInsert);
                i++;
            }
            //View erstellen die die Pärchen findet und den abstand berechnet
            String  sqlView = "CREATE VIEW abstaende AS " +
                    "SELECT " +
                    "x1, y1, z1, x2, y2, z2, abstand " +
                    "FROM (" +
                    "SELECT " +
                    "input.x x1, input.y y1, input.z z1, " +
                    "input2.x x2, input2.y y2, input2.z z2, " +
                    "SQRT(POWER((input2.x - input.x), 2) + POWER((input2.y - input.y), 2) + POWER((input2.z - input.z), 2)) AS abstand " +
                    "FROM " +
                    "input " +
                    "JOIN " +
                    "input input2 ON (INPUT.x < INPUT2.x) OR (INPUT.x = INPUT2.x AND INPUT.y < INPUT2.y) OR (INPUT.x = INPUT2.x AND INPUT.y = INPUT2.y AND INPUT.z < INPUT2.z) " +
                    ") AS Berechnungen " +
                    "WHERE abstand > 0;";

            stmt.executeUpdate(sqlView);
            //Gruppe auslesen
            PreparedStatement selectgruppestmt = conn.prepareStatement("select gruppe from input where x=? and y=? and z=?", ResultSet.TYPE_SCROLL_INSENSITIVE);
            //Die Gruppe aller Datensätze einer Gruppe ändern
            PreparedStatement updategruppestmt = conn.prepareStatement("update input set gruppe=? where gruppe=?");
            //Jetzt sortiert nach Abstand durch die Pärchen gehen:
            String sqlSelect = "SELECT * from abstaende order by abstand";
            ResultSet rs = stmt.executeQuery(sqlSelect);
            int zaehler = 0;
            //Für alle abstände
            while (rs.next() && zaehler < 1000) {
                zaehler++;
                int gruppennr1 = 0;
                int gruppennr2 = 0;

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
                //Wenn die Gruppen unterschiedlich sind eliminiere die 2. Gruppe
                if (gruppennr1 != gruppennr2) {
                    //update input set gruppe=? where gruppe=?
                    updategruppestmt.setInt(1, gruppennr1);
                    updategruppestmt.setInt(2, gruppennr2);
                    updategruppestmt.executeUpdate();
                }
            }
            //Fertig jetzt nur noch zählen und zuammenrechnen
            String sql = "select gruppe, count(*) as Anzahl from input group by gruppe order by Anzahl desc limit 3";
            ResultSet rs3 = stmt.executeQuery(sql);

            while (rs3.next()) {
                int anzahl=rs3.getInt(2);
                System.out.println("Gruppe:" + rs3.getInt(1) + "Anzahl:" + anzahl);
                loesung*=anzahl;
            }
        }
         catch (
                SQLException e) {
                e.printStackTrace();
         }
         System.out.println("Lösung: "+loesung);
     }

    /**
     * Wenn man Teil 1 kapiert hat: easypeasy.
     * Limit raus, Abbruchbedingung rein, eine SQL Abfrage mehr.
     */
     public void part2() {
        DB db=new DB();
        Connection conn=db.getConnection();
        int loesung=1;
        List<String> input= Tools.readUrlContent("https://adventofcode.com/2025/day/8/input",SESSION_COOKIE);
        //testdaten
    /*
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
*/
        //Input Tabelle erstellen
        try {
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            String sqlCreate = "create table input (gruppe decimal, x decimal,y decimal, z decimal);\n";
            stmt.executeUpdate(sqlCreate);
            int i = 1;
            for (String value : input) {
                String sqlInsert = "INSERT INTO input VALUES (" + i + "," + value + ")";
                stmt.executeUpdate(sqlInsert);
                i++;
            }
            //View erstellen die die Pärchen findet und den abstand berechnet
            String  sqlView = "CREATE VIEW abstaende AS " +
                    "SELECT " +
                    "x1, y1, z1, x2, y2, z2, abstand " +
                    "FROM (" +
                    "SELECT " +
                    "input.x x1, input.y y1, input.z z1, " +
                    "input2.x x2, input2.y y2, input2.z z2, " +
                    "SQRT(POWER((input2.x - input.x), 2) + POWER((input2.y - input.y), 2) + POWER((input2.z - input.z), 2)) AS abstand " +
                    "FROM " +
                    "input " +
                    "JOIN " +
                    "input input2 ON (INPUT.x < INPUT2.x) OR (INPUT.x = INPUT2.x AND INPUT.y < INPUT2.y) OR (INPUT.x = INPUT2.x AND INPUT.y = INPUT2.y AND INPUT.z < INPUT2.z) " +
                    ") AS Berechnungen " +
                    "WHERE abstand > 0;";

            stmt.executeUpdate(sqlView);
            //Gruppe auslesen
            PreparedStatement selectgruppestmt = conn.prepareStatement("select gruppe from input where x=? and y=? and z=?", ResultSet.TYPE_SCROLL_INSENSITIVE);
            //Die Gruppe aller Datensätze einer Gruppe ändern
            PreparedStatement updategruppestmt = conn.prepareStatement("update input set gruppe=? where gruppe=?");
            //Jetzt sortiert nach Abstand durch die Pärchen gehen:
            String sqlSelect = "SELECT * from abstaende order by abstand";
            ResultSet rs = stmt.executeQuery(sqlSelect);
            int zaehler = 0;
            //Für alle abstände
            boolean fertig=false;
            while (rs.next() && !fertig) {
                zaehler++;
                int gruppennr1 = 0;
                int gruppennr2 = 0;

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
                //Wenn die Gruppen unterschiedlich sind eliminiere die 2. Gruppe
                if (gruppennr1 != gruppennr2) {
                    //update input set gruppe=? where gruppe=?
                    updategruppestmt.setInt(1, gruppennr1);
                    updategruppestmt.setInt(2, gruppennr2);
                    updategruppestmt.executeUpdate();

                    //Ende prüfen
                    String sqlSelectfertig = "SELECT distinct gruppe from input";
                    ResultSet rsfertig = stmt2.executeQuery(sqlSelectfertig);
                    if (rsfertig.next()){
                        if (rsfertig.isLast()) {
                            fertig=true;
                            System.out.println("Lösung:"+(rs.getInt(1)*rs.getInt(4)));
                        }
                    }
                }
            }
        }
        catch (
                SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Lösung: "+loesung);
    }
}
