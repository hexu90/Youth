package Youth.dao;

import Youth.model.GeoIndex;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GeoIndexDAO extends AbstractDAO<GeoIndex> {
  private UserIntentionDAO userIntentionDAO;

  public GeoIndexDAO(SessionFactory sessionFactory, UserIntentionDAO userIntentionDAO) {
    super(sessionFactory);
    this.userIntentionDAO = userIntentionDAO;
  }

  public void createOrUpdate(GeoIndex index) {
    currentSession().save(index);
  }

  public List<Long> findNearbyUsers(Long id, String hashCode, int size) {
    Criteria builder = currentSession().createCriteria(GeoIndex.class, "geo");

    builder.add(Restrictions.between("geo.geoHash", hashCode, String.format("%s\uFFFD", hashCode)));
    builder.add(Restrictions.not(Restrictions.eq("geo.userId", id)));
    builder.add(Subqueries.propertyNotIn("geo.userId", userIntentionDAO.viewedUsers(id)));
    builder.add(Restrictions.sqlRestriction("1=1 order by rand()"));
    builder.setMaxResults(size);

    List<GeoIndex> recommendedUser = builder.list();
    return recommendedUser.stream().map(GeoIndex::getUserId).collect(Collectors.toList());
  }
}
