package luxoft.web.application.dao;

import luxoft.web.application.model.TextFile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class TextFileDAO {
    private Session session;

    public TextFileDAO(Session session) {
        this.session = session;
    }

    public TextFile get(long id) throws HibernateException {
        return (TextFile) session.get(TextFile.class, id);
    }

    public Collection<TextFile> getAll() throws HibernateException {
        return new LinkedHashSet(session.createCriteria(TextFile.class)
                .addOrder(Order.desc("dateOfStatisticComputation"))
                .list());

    }
}
