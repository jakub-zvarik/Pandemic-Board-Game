package support;

public interface EditDistance {

    /*
    Levenshtein algorithm is used to calculate distance between two strings. The distance is number of edits
    needed for the first string to transform into the second string. This is used within the chatbot
    to make sure it finds the correct user's intent or, if it fails to do so, it should be used to identify
    the most probable possibilities and output them to user.
    */
    static int levenshteinDistance(String input, String compare) {
        int INPUT_LENGTH = input.length();
        int COMPARE_LENGTH = compare.length();
        int[][] COMPARISON_MATRIX = new int[INPUT_LENGTH + 1][COMPARE_LENGTH + 1];
        // Initialise rows
        for (int row = 0; row <= INPUT_LENGTH; row++) {
            COMPARISON_MATRIX[row][0] = row;
        }
        // Initialise first row's columns
        for (int column = 0; column <= COMPARE_LENGTH; column++) {
            COMPARISON_MATRIX[0][column] = column;
        }
        // Fill out rest of the matrix
        for (int row = 1; row <= INPUT_LENGTH; row++) {
            for (int column = 1; column <= COMPARE_LENGTH; column++) {
                int cost = input.charAt(row - 1) == compare.charAt(column - 1) ? 0 : 1;
                COMPARISON_MATRIX[row][column] = Math.min(COMPARISON_MATRIX[row - 1][column] + 1,
                        Math.min(COMPARISON_MATRIX[row][column - 1] + 1, COMPARISON_MATRIX[row - 1][column - 1] + cost));
            }
        }
        // Return the final result
        return COMPARISON_MATRIX[INPUT_LENGTH][COMPARE_LENGTH];
    }

}
