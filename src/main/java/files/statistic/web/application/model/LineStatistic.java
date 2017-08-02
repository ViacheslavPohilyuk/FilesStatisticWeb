package files.statistic.web.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/* Lombok annotations
 * for generating getters, setters, toString and constructors */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
/*------------------------------------------------------------*/
@Entity
@Table(name = "LineStatistic")
public class LineStatistic implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "longestWord")
    private String longestWord;

    @Column(name = "shortestWord")
    private String shortestWord;

    @Column(name = "lineLength")
    private int lineLength;

    @Column(name = "averageWordLength")
    private int avgWordLength;

    /**
     * This method computes statistic of the all lines in text
     * <p>
     * This statistic consist:
     * - longest word in a line
     * - shortest word in a line
     * - length of a line
     * - average word length in a line
     *
     * @return an array with statistic of lines of the file that have been read.
     */
    public static LineStatistic[] computeLineStatistic(String text) {
        String[] lines = text.split(System.getProperty("line.separator"));

        LineStatistic[] lineStatistic = new LineStatistic[lines.length];

        int i = 0;
        for (String oneLine : lines) {
            int lineLength = oneLine.length();

            /* Processing each line of a text file */
            String[] wordsArray = oneLine.trim().split(" +");

            /* Count of words in a current line */
            int wordsLineCount = wordsArray.length;

            /* Index of longest and shortest words in the massive {@code wordsArray} */
            int indexMaxLenWord = 0;
            int indexMinLenWord = 0;

            /* Sum of letters of the all words in a line */
            int lengthOfAllLineWords = 0;

            /* String objects for storing longest and shortest words */
            String longestWord = "";
            String shortestWord = "";

            /* a valuable for storing average count of letters of each line*/
            int avgWordLength = 0;

            if (wordsLineCount != 0) {
                int j = 0;
                /* Creating a new object statistic of a line */
                lineStatistic[i] = new LineStatistic();
                /* Length of longest and shortest words */
                int MaxLenWordLetterCount = wordsArray[0].length();
                int MinLenWordLetterCount = wordsArray[0].length();

                for (String word : wordsArray) {
                    word = word.trim();

                    /* Finding a longest word in a line */
                    if (MaxLenWordLetterCount < word.length()) {
                        MaxLenWordLetterCount = word.length();
                        indexMaxLenWord = j;
                    }

                    /* Finding a shortest word in a line */
                    if (MinLenWordLetterCount > word.length()) {
                        MinLenWordLetterCount = word.length();
                        indexMinLenWord = j;
                    }
                    lengthOfAllLineWords += word.length();
                    j++;
                }
                longestWord = wordsArray[indexMaxLenWord];
                shortestWord = wordsArray[indexMinLenWord];
                avgWordLength = lengthOfAllLineWords / wordsLineCount;
            }

            /* Saving data of statistic of a current line
             * to a LineStatistic object */
            lineStatistic[i].setLongestWord(longestWord);
            lineStatistic[i].setShortestWord(shortestWord);
            lineStatistic[i].setAvgWordLength(avgWordLength);
            lineStatistic[i].setLineLength(lineLength);
            i++;
        }
        return lineStatistic;
    }
}
