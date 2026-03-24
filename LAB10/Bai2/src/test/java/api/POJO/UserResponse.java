package api.pojo;

public class UserResponse {

    private String name;
    private String job;
    private String id;
    private String createdAt;
    private String updatedAt;

    public UserResponse() {
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}