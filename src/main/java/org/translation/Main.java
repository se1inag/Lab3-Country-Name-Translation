package org.translation;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 */
public class Main {

    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        runProgram(translator);
    }

    public static void runProgram(Translator translator) {
        CountryCodeConverter countryConverter = new CountryCodeConverter();
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();

        while (true) {
            String country = promptForCountry(translator, countryConverter);

            if (QUIT.equals(country)) {
                break;
            }

            String countryCode = countryConverter.fromCountry(country);
            String language = promptForLanguage(translator, countryCode, languageConverter);

            if (QUIT.equals(language)) {
                break;
            }

            String languageCode = languageConverter.fromLanguage(language);

            System.out.println(country + " in " + language + " is "
                    + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");

            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    // TODO Task: convert country codes to names, sort, and print one per line.
    private static String promptForCountry(Translator translator,
                                           CountryCodeConverter countryConverter) {
        List<String> countries = translator.getCountries();
        List<String> countryNames = countries.stream()
                .map(countryConverter::fromCountryCode)
                .collect(java.util.stream.Collectors.toList());

        Collections.sort(countryNames);

        for (String country : countryNames) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    // TODO Task: convert language codes to names, sort, and print one per line.
    private static String promptForLanguage(Translator translator,
                                            String countryCode,
                                            LanguageCodeConverter languageConverter) {
        List<String> languageNames = translator.getCountryLanguages(countryCode).stream()
                .map(languageConverter::fromLanguageCode)
                .collect(java.util.stream.Collectors.toList());

        Collections.sort(languageNames);

        for (String language : languageNames) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}