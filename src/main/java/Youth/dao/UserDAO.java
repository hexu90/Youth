package Youth.dao;

import Youth.model.GeoIndex;
import Youth.model.User;
import ch.hsr.geohash.GeoHash;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import java.util.List;

@Slf4j
public class UserDAO extends AbstractDAO<User> {
  private GeoIndexDAO geoIndexDAO;

  public UserDAO(
      SessionFactory sessionFactory,
      GeoIndexDAO geoIndexDAO) {
    super(sessionFactory);
    this.geoIndexDAO = geoIndexDAO;
  }

  public User findByUserId(Long id) {
    return get(id);
  }

  public List<User> findByUserIds(List<Long> ids) {
    return currentSession().byMultipleIds(User.class).multiLoad(ids);
  }

  // Function used to update geo hash for each user in geo index table
  public void updateGeoHash() {
    Criteria builder = currentSession().createCriteria(User.class, "user");
    List<User> allUsers = builder.list();
    for (User user : allUsers) {
      log.info(String.valueOf(user.getLat()));
      log.info(String.valueOf(user.getLng()));
      GeoHash hashCode = GeoHash.withCharacterPrecision(user.getLat(), user.getLng(), 12);
      log.info(hashCode.toBase32());
      GeoIndex index = GeoIndex.builder().geoHash(hashCode.toBase32()).userId(user.getId()).build();
      geoIndexDAO.createOrUpdate(index);
    }
  }
}
