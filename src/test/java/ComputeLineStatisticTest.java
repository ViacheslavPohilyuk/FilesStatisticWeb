import files.statistic.web.application.model.LineStatistic;
import org.junit.Test;

/**
 * Created by mac on 25.06.17.
 */
public class ComputeLineStatisticTest {
    @Test
    public void testRegularLines() throws Exception {
        String test = "JavaScript is a multi-paradigm, dynamic language with types and operators, standard built-in objects, and methods.";
        LineStatistic line = LineStatistic.computeLineStatistic(test)[0];

        boolean isLineStatisticRight = isMatchSpecificLineStatistic(line,
                "multi-paradigm,", "a", 114, 6);

        assert (isLineStatisticRight);
    }

    @Test
    public void testLinesWithSeveralSpacesGoOneByOne() throws Exception {
        String test = " A            re-introduction  to   JavaScript    (JS tutorial)";
        LineStatistic line = LineStatistic.computeLineStatistic(test)[0];

        boolean isLineStatisticRight = isMatchSpecificLineStatistic(line,
                "re-introduction", "A", 63, 6);

        assert (isLineStatisticRight);
    }

    @Test
    public void testEmptyLines() throws Exception {
        String test = "";
        LineStatistic line = LineStatistic.computeLineStatistic(test)[0];

        boolean isLineStatisticRight = isMatchSpecificLineStatistic(line,
                "", "", 0, 0);

        assert (isLineStatisticRight);
    }

    private boolean isMatchSpecificLineStatistic(LineStatistic lineStatistic, String longestWord, String shortestWord, int lineLength, int avgWordLength) {
        return (lineStatistic.getLongestWord().equals(longestWord) &&
                lineStatistic.getShortestWord().equals(shortestWord) &&
                lineStatistic.getLineLength() == lineLength &&
                lineStatistic.getAvgWordLength() == avgWordLength);
    }
}