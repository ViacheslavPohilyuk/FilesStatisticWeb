package files.statistic.web.application.service;

import files.statistic.web.application.dao.TextFileDAO;
import files.statistic.web.application.model.LineStatistic;
import files.statistic.web.application.model.TextFile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by mac on 26.06.17.
 */
@Service
public class HibernateDBService {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TextFile getTextFile(long id) {
        TextFile dataSet = null;
        try {
            Session session = sessionFactory.openSession();
            TextFileDAO dao = new TextFileDAO(session);
            dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    public Collection<TextFile> getAllTextFiles() {
        Collection<TextFile> TFlist = null;
        try {
            Session session = sessionFactory.openSession();
            TextFileDAO dao = new TextFileDAO(session);
            TFlist = dao.getAll();

            /* Date format */
            for (TextFile tf : TFlist) {
                Date date = tf.getDateOfStatisticComputation();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.HOUR, -3);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tf.setFormatDate(df.format(cal.getTime()));
            }

            session.close();
            return TFlist;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return TFlist;
    }

    public void addTextStatistic(String text) {
        try {
            /*Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();*/

            TextFile textFile = new TextFile("PlainText");

            /* Compute statistic of lines of the current text*/
            LineStatistic[] linesStat = LineStatistic.computeLineStatistic(text);

            /* Add statistic of each line to the TextFile object*/
            for (LineStatistic line : linesStat)
                textFile.getLinesStatistic().add(line);

            System.out.println("TextFile: " + textFile.toString());
            /*session.persist(textFile);
            tx.commit();
            session.close();*/
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
