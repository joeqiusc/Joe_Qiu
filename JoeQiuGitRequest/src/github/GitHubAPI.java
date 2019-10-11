package github;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import github.entity.Follower;
import github.entity.FollowerResponse;
import util.GsonUtils;
import util.HTTPUtils;

public class GitHubAPI {
	
	public static List<Follower> getGitHubFollowersById(String id) {
		String result = HTTPUtils.get("http://api.github.com/users/" + id + "/followers");
		List<FollowerResponse> followers = GsonUtils.getInstance().fromJson(result, new TypeToken<List<FollowerResponse>>() {}.getType());
		List<Follower> followerList = new ArrayList<>();
		for(FollowerResponse followerResponse: followers ) {
			Follower follower = new Follower();
			follower.setId(followerResponse.getId());
			follower.setName(followerResponse.getLogin());
			followerList.add(follower);
		}
		return followerList;
	}
	
	public static void main(String args[]) {
		List<Follower> followers = getGitHubFollowersById("brocaar");
		System.out.println(GsonUtils.getInstance().toJson(followers));
	}
	
}
