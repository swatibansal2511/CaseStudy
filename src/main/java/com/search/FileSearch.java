package com.search;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSearch {

    public static void searchStringInFile(String option, String searchTerm) throws IOException, ParseException {

        switch (option) {
            case "1":
                searchStringMatch(searchTerm);
                break;
            case "2":
                searchRegularExpression(searchTerm);
                break;
            case "3":
                ReadIndexFromFile.searchIndex(searchTerm);
                break;
            default:
                System.out.println("Option not supported! Enter the valid option");
                break;
        }
    }

    private static void searchRegularExpression(String searchTerm) throws IOException {
        Pattern pattern = Pattern.compile(searchTerm);
        int count = 0;
        List<String> lines = Files.readAllLines(Paths.get("data1.txt"));
        Matcher matcher;
        for (String line : lines) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                count++;
                System.out.println("found: " + count + " : " + matcher.start() + " - " + matcher.end());
            } else {
                System.out.println("Not Found: " + count + ": Line:" + line);

            }
        }
    }

    private static void searchStringMatch(String searchTerm) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("data1.txt"));
        int count = 0;
        for (String line : lines) {
            if (line.contains(searchTerm)) {
                count++;
                System.out.println("found: " + count + " : Line :" + line);
            } else {
                System.out.println("Not Found: " + count + ": Line:" + line);

            }
        }
    }

    public static void main(String[] args) throws Exception {
        searchStringInFile("1", "Fully");
        searchStringInFile("2", "in");
        searchStringInFile("3", "agreeable");


    }
}
