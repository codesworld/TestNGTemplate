package utils;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    private static final String PATTERN = "\\b[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}\\b";

    public static List<String> readCarRegistrations(String path) {

        List<String> registrations = new ArrayList<>();

        Pattern pattern = Pattern.compile(PATTERN);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    registrations.add(matcher.group());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return registrations;
    }

    public static Map<String, List<String>> parseCarData(String path)  {
        Map<String, List<String>> carData = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                List<String> values = new ArrayList<>(Arrays.asList(line.split(",")));

            String key = values.remove(0);
                carData.put(key, values);
            }
        }catch (IOException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        }
        return carData;
    }
}
