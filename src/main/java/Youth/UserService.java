package Youth;

import Youth.core.YouthException;
import Youth.dao.GeoIndexDAO;
import Youth.dao.UserDAO;
import Youth.dao.UserIntentionDAO;
import Youth.dao.UserMatchDAO;
import Youth.model.*;
import ch.hsr.geohash.GeoHash;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class UserService {
  private UserDAO userDAO;
  private UserIntentionDAO userIntentionDAO;
  private UserMatchDAO userMatchDAO;
  private GeoIndexDAO geoIndexDAO;

  /**
   * Handle the logic behind swipe
   * It looks for if this is a match and returns it.
   * At same time, it creates the use intention record.
   */
  public boolean swipe(Long primaryUserId, Long viewedUserId, Boolean isLikeOrNot)
      throws YouthException {
    boolean matchResult = false;

    try {
      if (isLikeOrNot) {
        // Check if is a match
        Optional<UserIntention> isLikedUser =
            userIntentionDAO.isLikedUser(viewedUserId, primaryUserId);
        if (isLikedUser.isPresent()) {
          // Match. Updated match table
          matchResult = true;
          Long smallerUserId = Math.min(primaryUserId, viewedUserId);
          Long largeUserId = Math.max(primaryUserId, viewedUserId);
          UserMatch machRecord =
              UserMatch.builder()
                  .id(
                      UserMatchId.builder()
                          .primaryUserId(smallerUserId)
                          .matchedUserId(largeUserId)
                          .build())
                  .build();
          userMatchDAO.createOrUpdate(machRecord);
        }
      }

      // Create new swipe records
      UserIntentionId intentionId =
          UserIntentionId.builder().primaryUserId(primaryUserId).viewedUserId(viewedUserId).build();

      UserIntention newIntention =
          UserIntention.builder()
              .id(intentionId)
              .likeOrDislike(isLikeOrNot)
              .build();
      userIntentionDAO.createOrUpdate(newIntention);
      return matchResult;

    } catch (Exception e) {
      log.error("Running error in createOrUpdate in UserIntentionDAO: {}", e.getMessage());
      throw new YouthException(500, e.getMessage());
    }
  }

  public List<User> recommend(Long userId, double lat, double lng, int size, int radius) {
    GeoHash hash = GeoHash.withCharacterPrecision(lat, lng, 12);
    String hashCode = hash.toBase32().substring(0, radius);
    List<Long> recommendUserIds = geoIndexDAO.findNearbyUsers(userId, hashCode, size);
    return userDAO.findByUserIds(recommendUserIds);
  }

  // Find all matched users based on userId
  public List<User> findAllMatches(Long userId) {
    List<Long> matchedUsers =
        userMatchDAO.findAllMatches(userId).stream()
            .map(
                userMatch -> {
                  if (userMatch.getId().getMatchedUserId().longValue() == userId) {
                    return userMatch.getId().getPrimaryUserId();
                  } else {
                    return userMatch.getId().getMatchedUserId();
                  }
                })
            .collect(Collectors.toList());
    return userDAO.findByUserIds(matchedUsers);
  }
}
