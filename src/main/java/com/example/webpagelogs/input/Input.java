package com.example.webpagelogs.input;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Input {

    public static String getUrlFromUser() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a URL (or 'exit' to quit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting application...");
                System.exit(0);
            }

            if (input.isEmpty()) {
                System.err.println("Invalid input. Please enter a URL.");
                System.exit(1); // <- fixed here
            }

            // Prepend https:// if missing
            if (!input.startsWith("http://") && !input.startsWith("https://")) {
                input = "https://" + input;
            }

            try {
                URL validatedUrl = new URL(input); // Validate the URL
                return validatedUrl.toString();    // Return normalized URL
            } catch (MalformedURLException e) {
                System.err.println("Error processing URL: The supplied URL, '" + input + "', is malformed. " +
                        "Make sure it is an absolute URL, and starts with 'http://' or 'https://'. " );
            }
        }
    }

    public static void main(String[] args) {
        String url = getUrlFromUser();
        System.out.println("Validated URL: " + url);
    }
}
