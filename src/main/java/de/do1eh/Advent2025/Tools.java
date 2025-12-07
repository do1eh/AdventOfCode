package de.do1eh.Advent2025;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tools
{
   /**
         * Liest den Inhalt einer URL zeilenweise und gibt ihn als Liste von Strings zurück.
         *
         * @param urlString Die URL als String, deren Inhalt gelesen werden soll (z.B. "https://beispiel.de/datei.txt").
         * @return Eine List<String>, wobei jeder String einer Zeile des URL-Inhalts entspricht.
         * Gibt eine leere Liste zurück, wenn ein Fehler auftritt (z.B. ungültige URL oder Netzwerkfehler).
         */
        public static List<String> readUrlContent(String urlString, String SESSION_COOKIE) {

            List<String> lines = new ArrayList<>();

            try {
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Cookie", "session=" + SESSION_COOKIE);
                connection.setRequestMethod("GET");
                connection.connect();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }
            } catch (java.net.MalformedURLException e) {
                System.err.println("Fehler: Ungültige URL-Formatierung: " + urlString);
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Fehler beim Lesen des URL-Inhalts von: " + urlString);
                e.printStackTrace();
            }
            return lines;
        }


    public static long calculateSimpleExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Der Ausdruck darf nicht leer sein.");
        }


        String cleanExpression = expression.replaceAll("\\s", "");

        String operator = "";
        String[] parts;


        if (cleanExpression.contains("+")) {
            operator = "+";
            parts = cleanExpression.split("\\+");
        }

        else if (cleanExpression.contains("*")) {
            operator = "*";
            // Der Stern "*" muss in der split-Methode escaped werden, da er ein Regex-Sonderzeichen ist
            parts = cleanExpression.split("\\*");
        }

        else {
            try {

                return Long.parseLong(cleanExpression);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ungültiger Ausdruck oder ungültige Zahl: " + expression);
            }
        }


        long result = (operator.equals("+")) ? 0 : 1;


        for (String part : parts) {
            if (part.isEmpty()) {
                throw new IllegalArgumentException("Ungültiges Format (z.B. '5++3' oder '5+'): " + expression);
            }
            try {
                double number = Double.parseDouble(part);

                if (operator.equals("+")) {
                    result += number;
                } else if (operator.equals("*")) {
                    result *= number;
                }
            } catch (NumberFormatException e) {

                throw new IllegalArgumentException("Teil des Ausdrucks ist keine gültige Zahl: " + part);
            }
        }

        return result;
    }

}
