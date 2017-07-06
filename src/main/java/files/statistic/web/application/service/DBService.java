package files.statistic.web.application.service;

import files.statistic.web.application.dao.TextFileDAO;
import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.TextFile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by mac on 26.06.17.
 */
@Service
public class DBService {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private void updateSession(Consumer<Session> updateStmt) {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            updateStmt.accept(session);
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    private <T> T readSession(Function<Session, T> readStmt) {
        T result = null;
        try {
            Session session = sessionFactory.openSession();
            result = readStmt.apply(session);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    public TextFile getTextFile(long id) {
        return readSession((s) -> new TextFileDAO(s).get(id));
    }

    public Collection<TextFile> getAllTextFiles() {
        return readSession((s) -> new TextFileDAO(s).getAll());
    }

    public void saveTextStatistic(String text) {
        updateSession((s) -> {
            TextFile textFile = new TextFile("PlainText");
            LineStatistic[] linesStat = LineStatistic.computeLineStatistic(text);
            for (LineStatistic line : linesStat)
                textFile.getLinesStatistic().add(line);
            s.persist(textFile);
        });
    }

    public void saveUploadedTextFileStatistic(MultipartFile file) {
        updateSession((s) -> {
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
        });
    }
}