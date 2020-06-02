package Youth.dao;

import Youth.model.UserIntention;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Cacheable;
import java.util.List;
import java.util.Optional;

@Slf4j
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserIntentionDAO extends AbstractDAO<UserIntention> {
  UserMatchDAO userMatchDAO;

  public UserIntentionDAO(SessionFactory sessionFactory, UserMatchDAO userMatchDAO) {
    super(sessionFactory);
    this.userMatchDAO = userMatchDAO;
  }

  // Find all viewed user by user Id
  public DetachedCriteria viewedUsers(Long id) {
    DetachedCriteria subQuery = DetachedCriteria.forClass(UserIntention.class, "intention");
    subQuery.add(Restrictions.eq("intention.id.primaryUserId", id));
    subQuery.setProjection(Projections.property("intention.id.viewedUserId"));
    log.info("Testing logging");
    List<UserIntention> us = subQuery.getExecutableCriteria(currentSession()).list();
    log.info(us.toString());
    return subQuery;
  }

  public void createOrUpdate(UserIntention intention) {
    currentSession().saveOrUpdate(intention);
  }

  // Find if the primaryUser like viewed user
  public Optional<UserIntention> isLikedUser(Long primaryUserId, Long viewedUserId) {
    Criteria builder = currentSession().createCriteria(UserIntention.class);
    builder.add(Restrictions.eq("id.primaryUserId", primaryUserId));
    builder.add(Restrictions.eq("id.viewedUserId", viewedUserId));
    builder.add(Restrictions.eq("likeOrDislike", true));
    List<UserIntention> intentions = builder.list();
    if (intentions.size() == 1) {
      return Optional.ofNullable(intentions.get(0));
    }
    return Optional.empty();
  }
}
