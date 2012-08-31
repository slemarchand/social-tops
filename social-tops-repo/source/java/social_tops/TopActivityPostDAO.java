package social_tops;

import java.util.List;

public interface TopActivityPostDAO {

	List<TopUser> executeTopQuery(TopQuery topQuery);
}
