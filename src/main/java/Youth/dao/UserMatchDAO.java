package Youth.dao;

import Youth.model.UserMatch;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserMatchDAO extends AbstractDAO<UserMatch> {

    public UserMatchDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<UserMatch> findAllMatches(Long userId) {
        Criteria builder = currentSession().createCriteria(UserMatch.class);
        Criterion primaryUserIdQuery = Restrictions.eq("id.primaryUserId", userId);
        Criterion matchedUserIdQuery = Restrictions.eq("id.matchedUserId", userId);
        builder.add(Restrictions.or(primaryUserIdQuery, matchedUserIdQuery));
        return builder.list();
    }

    public void createOrUpdate(UserMatch match) {
        currentSession().saveOrUpdate(match);
    }
}
