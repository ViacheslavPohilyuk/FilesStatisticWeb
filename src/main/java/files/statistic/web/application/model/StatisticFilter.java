package files.statistic.web.application.model;

import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by mac on 06.07.17.
 */
@Data
public class StatisticFilter {
    Integer linesCount;

    /* Filtering collections of TextFile objects by lines count */
    public Collection<TextFile> filtering(Collection<TextFile> files) {
        return files.stream()
                .filter(f -> {
                    if ((f.getLinesStatistic().size() >= linesCount) && (f.getLinesStatistic().size() <= (linesCount + 50))) {
                        return true;
                    } else if ((linesCount == 300) && (f.getLinesStatistic().size() >= linesCount)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
