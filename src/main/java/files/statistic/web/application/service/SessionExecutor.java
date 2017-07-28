package files.statistic.web.application.service;

import files.statistic.web.application.dao.TextFileDAO;
import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.TextFile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by mac on 26.06.17.
 */
@Component
public class SessionExecutor {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    Long updateSession(Function<Session, Long> updateStmt) {
        Long generatedId = null;
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            generatedId = updateStmt.apply(session); // update operation
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    <T> T readSession(Function<Session, T> readStmt) {
        T result = null;
        try {
            Session session = sessionFactory.openSession();
            result = readStmt.apply(session); // read data operation
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }
}
