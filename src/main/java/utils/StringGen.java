package utils;

import java.util.Random;

public class StringGen {

    //Generates Random String of a fixed length of 6 Characters.
    public static String generateRandomChars() {
        int length = 6;
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

}

