package cn.isohard.yuque.export.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends Base {
    private String type;
    private Long spaceId;
    private Long accountId;
    private String login;
    private String name;
    private String avatarUrl;
    private Long booksCount;
    private Long publicBooksCount;
    private Long followersCount;
    private Long followingCount;
    @JsonProperty("public")
    private Long _public;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    @JsonProperty("_serializer")
    private String _serializer;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Long getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Long booksCount) {
        this.booksCount = booksCount;
    }

    public Long getPublicBooksCount() {
        return publicBooksCount;
    }

    public void setPublicBooksCount(Long publicBooksCount) {
        this.publicBooksCount = publicBooksCount;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String get_serializer() {
        return _serializer;
    }

    public void set_serializer(String _serializer) {
        this._serializer = _serializer;
    }

    public Long get_public() {
        return _public;
    }

    public void set_public(Long _public) {
        this._public = _public;
    }
}
