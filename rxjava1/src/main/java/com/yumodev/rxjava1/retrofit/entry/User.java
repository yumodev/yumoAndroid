package com.yumodev.rxjava1.retrofit.entry;

/**
 * Created by yumo on 2018/6/26.
 */

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;

    private int id;

    private String node_id;

    private String avatar_url;

    private String gravatar_id;

    private String url;

    private String html_url;

    private String followers_url;

    private String following_url;

    private String gists_url;

    private String starred_url;

    private String subscriptions_url;

    private String organizations_url;

    private String repos_url;

    private String events_url;

    private String received_events_url;

    private String type;

    private boolean site_admin;

    private String name;

    private String company;

    private String blog;

    private String location;

    private String email;

    private String hireable;

    private String bio;

    private int public_repos;

    private int public_gists;

    private int followers;

    private int following;

    private String created_at;

    private String updated_at;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getNode_id() {
        return this.node_id;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getAvatar_url() {
        return this.avatar_url;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getGravatar_id() {
        return this.gravatar_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getHtml_url() {
        return this.html_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowers_url() {
        return this.followers_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getFollowing_url() {
        return this.following_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getGists_url() {
        return this.gists_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getStarred_url() {
        return this.starred_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getSubscriptions_url() {
        return this.subscriptions_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getOrganizations_url() {
        return this.organizations_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getRepos_url() {
        return this.repos_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getEvents_url() {
        return this.events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getReceived_events_url() {
        return this.received_events_url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public boolean getSite_admin() {
        return this.site_admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getBlog() {
        return this.blog;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHireable(String hireable) {
        this.hireable = hireable;
    }

    public String getHireable() {
        return this.hireable;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getPublic_repos() {
        return this.public_repos;
    }

    public void setPublic_gists(int public_gists) {
        this.public_gists = public_gists;
    }

    public int getPublic_gists() {
        return this.public_gists;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowers() {
        return this.followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowing() {
        return this.following;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public static User fill(JSONObject jsonobj) {
        User entity = new User();
        try {
            if (jsonobj.has("login")) {
                entity.setLogin(jsonobj.getString("login"));
            }
            if (jsonobj.has("id")) {
                entity.setId(jsonobj.getInt("id"));
            }
            if (jsonobj.has("node_id")) {
                entity.setNode_id(jsonobj.getString("node_id"));
            }
            if (jsonobj.has("avatar_url")) {
                entity.setAvatar_url(jsonobj.getString("avatar_url"));
            }
            if (jsonobj.has("gravatar_id")) {
                entity.setGravatar_id(jsonobj.getString("gravatar_id"));
            }
            if (jsonobj.has("url")) {
                entity.setUrl(jsonobj.getString("url"));
            }
            if (jsonobj.has("html_url")) {
                entity.setHtml_url(jsonobj.getString("html_url"));
            }
            if (jsonobj.has("followers_url")) {
                entity.setFollowers_url(jsonobj.getString("followers_url"));
            }
            if (jsonobj.has("following_url")) {
                entity.setFollowing_url(jsonobj.getString("following_url"));
            }
            if (jsonobj.has("gists_url")) {
                entity.setGists_url(jsonobj.getString("gists_url"));
            }
            if (jsonobj.has("starred_url")) {
                entity.setStarred_url(jsonobj.getString("starred_url"));
            }
            if (jsonobj.has("subscriptions_url")) {
                entity.setSubscriptions_url(jsonobj.getString("subscriptions_url"));
            }
            if (jsonobj.has("organizations_url")) {
                entity.setOrganizations_url(jsonobj.getString("organizations_url"));
            }
            if (jsonobj.has("repos_url")) {
                entity.setRepos_url(jsonobj.getString("repos_url"));
            }
            if (jsonobj.has("events_url")) {
                entity.setEvents_url(jsonobj.getString("events_url"));
            }
            if (jsonobj.has("received_events_url")) {
                entity.setReceived_events_url(jsonobj.getString("received_events_url"));
            }
            if (jsonobj.has("type")) {
                entity.setType(jsonobj.getString("type"));
            }
            if (jsonobj.has("site_admin")) {
                entity.setSite_admin(jsonobj.getBoolean("site_admin"));
            }
            if (jsonobj.has("name")) {
                entity.setName(jsonobj.getString("name"));
            }
            if (jsonobj.has("company")) {
                entity.setCompany(jsonobj.getString("company"));
            }
            if (jsonobj.has("blog")) {
                entity.setBlog(jsonobj.getString("blog"));
            }
            if (jsonobj.has("location")) {
                entity.setLocation(jsonobj.getString("location"));
            }
            if (jsonobj.has("email")) {
                entity.setEmail(jsonobj.getString("email"));
            }
            if (jsonobj.has("hireable")) {
                entity.setHireable(jsonobj.getString("hireable"));
            }
            if (jsonobj.has("bio")) {
                entity.setBio(jsonobj.getString("bio"));
            }
            if (jsonobj.has("public_repos")) {
                entity.setPublic_repos(jsonobj.getInt("public_repos"));
            }
            if (jsonobj.has("public_gists")) {
                entity.setPublic_gists(jsonobj.getInt("public_gists"));
            }
            if (jsonobj.has("followers")) {
                entity.setFollowers(jsonobj.getInt("followers"));
            }
            if (jsonobj.has("following")) {
                entity.setFollowing(jsonobj.getInt("following"));
            }
            if (jsonobj.has("created_at")) {
                entity.setCreated_at(jsonobj.getString("created_at"));
            }
            if (jsonobj.has("updated_at")) {
                entity.setUpdated_at(jsonobj.getString("updated_at"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static List<User> fillList(JSONArray jsonarray) {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<User> olist = new ArrayList<User>();
        for (int i = 0; i < jsonarray.length(); i++) {
            try {
                olist.add(fill(jsonarray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return olist;
    }
}
