package de.do1eh.Advent2015;

import de.do1eh.Advent2025.Tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;



public class Day04 {

    public void part1() {
        String input = "yzbqklnj";
        //Test
        //String input = "abcdef";

        boolean fertig=false;
        long i=1;
        while(!fertig) {
            //System.out.println(i);
            String md5 = calculateMD5(input + i);

            if (md5.startsWith("000000")) {
                fertig=true;
                System.out.println("Lösung:"+i);
            }
            i++;
        }
    }

    public void part2() {
    }
    String input = "yzbqklnj";


public static String calculateMD5(String input) {
    try {
        // 1. Hole eine Instanz des MessageDigest-Algorithmus für MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // 2. Führe die Hash-Berechnung durch
        // Konvertiere den String in ein Byte-Array (UTF-8 Standard)
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

        // 3. Konvertiere das Byte-Array in einen Hexadezimal-String
        // Dies ist notwendig, damit der Hash lesbar ist.
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            // Konvertiere das Byte in einen Hex-String und stelle sicher, dass es zwei Zeichen lang ist.
            // '& 0xff' stellt sicher, dass nur die unteren 8 Bits verwendet werden (wichtig für Java-Bytes).
            // '| 0x100' stellt sicher, dass die Länge immer 3 ist, wenn es in Hex umgewandelt wird (z.B. 'f' wird zu '10f').
            // .substring(1) schneidet die führende '1' ab, was zu '0f' führt, wenn nötig.
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
