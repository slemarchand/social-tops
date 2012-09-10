package social_tops.activity;

import java.util.List;

public interface TopActivityDAO {

	List<UserScore> executeTopQuery(TopQuery topQuery);

	List<UserActivityScore> executeActivityScoreQuery(
			ActivityScoreQuery activityScoreQuery);
}
