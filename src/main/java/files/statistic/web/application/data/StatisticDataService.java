package files.statistic.web.application.data;

import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.TextFile;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by mac on 28.07.17.
 */
@Service
public class StatisticDataService {

    private SessionExecutor sessionExecutor;

    @Autowired
    public StatisticDataService(SessionExecutor sessionExecutor) {
        this.sessionExecutor = sessionExecutor;
    }

    public TextFile getTextFile(long id) {
        return sessionExecutor.readSession((s) -> (TextFile) s.get(TextFile.class, id));
    }

    public Collection<TextFile> getAllTextFiles() {
        return sessionExecutor.readSession((s) -> new LinkedHashSet(s.createCriteria(TextFile.class)
                .addOrder(Order.desc("id"))
                .list()));
    }

    public Long saveTextStatistic(String text) {
        return sessionExecutor.updateSession((s) -> {
            TextFile textFile = new TextFile("PlainText");
            LineStatistic[] linesStat = LineStatistic.computeLineStatistic(text);
            for (LineStatistic line : linesStat)
                textFile.getLinesStatistic().add(line);
            s.persist(textFile);
            s.flush();
            return textFile.getId();
        });
    }

    public Long saveUploadedTextFileStatistic(MultipartFile file) {
        return sessionExecutor.updateSession((s) -> {
            TextFile textFile = new TextFile(file.getOriginalFilename());
            String text = null;
            try {
                text = new String(file.getBytes(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            LineStatistic[] linesStat = LineStatistic.computeLineStatistic(text);
            for (LineStatistic line : linesStat)
                textFile.getLinesStatistic().add(line);
            s.persist(textFile);
            s.flush();
            return textFile.getId();
        });
    }
}
