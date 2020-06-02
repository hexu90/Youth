package Youth;

import Youth.exception.RuntimeExceptionMapper;
import Youth.exception.YouthExceptionMapper;
import Youth.authentication.YouthAuthFilter;
import Youth.authentication.YouthAuthenticator;
import Youth.dao.GeoIndexDAO;
import Youth.dao.UserDAO;
import Youth.dao.UserIntentionDAO;
import Youth.dao.UserMatchDAO;
import Youth.model.GeoIndex;
import Youth.model.User;
import Youth.model.UserIntention;
import Youth.model.UserMatch;
import Youth.resources.YouthResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.cfg.Configuration;

public class YouthApplication extends Application<YouthConfiguration> {
  private final HibernateBundle<YouthConfiguration> hibernate =
      new HibernateBundle<YouthConfiguration>(User.class, UserIntention.class, GeoIndex.class, UserMatch.class) {
        @Override
        public void configure(Configuration configuration) {
          configuration.setProperty("hibernate.show_sql", "true");
          configuration.setProperty("hibernate.format_sql", "true");
          configuration.setProperty("hibernate.cache.use_second_level_cache", "true");
          configuration.setProperty(
              "hibernate.cache.region.factory_class",
              "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        }

        @Override
        public DataSourceFactory getDataSourceFactory(YouthConfiguration configuration) {
          return configuration.getDataSourceFactory();
        }
      };

  public static void main(final String[] args) throws Exception {
    new YouthApplication().run(args);
  }

  @Override
  public String getName() {
    return "Youth";
  }

  @Override
  public void initialize(final Bootstrap<YouthConfiguration> bootstrap) {
    bootstrap.addBundle(hibernate);
  }

  @Override
  public void run(final YouthConfiguration configuration, final Environment environment) {

    final UserMatchDAO userMatchDAO = new UserMatchDAO(hibernate.getSessionFactory());
    final UserIntentionDAO userIntentionDAO = new UserIntentionDAO(hibernate.getSessionFactory(), userMatchDAO);
    final GeoIndexDAO geoIndexDAO =
        new GeoIndexDAO(hibernate.getSessionFactory(), userIntentionDAO);
    final UserDAO userDAO =
        new UserDAO(hibernate.getSessionFactory(), geoIndexDAO);

    final UserService userService = new UserService(userDAO, userIntentionDAO, userMatchDAO, geoIndexDAO);

    // Customized Authentication
    YouthAuthenticator authenticator =
        new UnitOfWorkAwareProxyFactory(hibernate)
            .create(YouthAuthenticator.class, new Class[] {UserDAO.class}, new Object[] {userDAO});
    YouthAuthFilter filter = new YouthAuthFilter(authenticator);
    environment.jersey().register(RolesAllowedDynamicFeature.class);
    environment.jersey().register(filter);

    //    basicAuthenticator
    //     YouthBasicAuthenticator basicAuthenticator =
    //        new UnitOfWorkAwareProxyFactory(hibernate)
    //            .create(
    //                YouthBasicAuthenticator.class, new Class[] {UserDAO.class}, new Object[]
    // {userDAO});
    //    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    //
    //    environment
    //        .jersey()
    //        .register(
    //            new AuthDynamicFeature(
    //                new BasicCredentialAuthFilter.Builder<User>()
    //                    .setAuthenticator(basicAuthenticator)
    //                    .buildAuthFilter()));

    environment.jersey().register(new YouthResource(userService));
    environment.jersey().register(new YouthExceptionMapper());
    environment.jersey().register(new RuntimeExceptionMapper());
  }
}
