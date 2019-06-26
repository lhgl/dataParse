package model;

public class VideoSubTokenTag {

    private int id;
    private int video_sub_id;
    private String token;
    private String tag;

    public VideoSubTokenTag() {
    }

    public VideoSubTokenTag(int id, int video_sub_id, String token, String tag) {
        this.id = id;
        this.video_sub_id = video_sub_id;
        this.token = token;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoSubId() {
        return video_sub_id;
    }

    public void setVideoSubId(int video_sub_id) {
        this.video_sub_id = video_sub_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
