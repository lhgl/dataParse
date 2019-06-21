package dao;

import dao.data.impl.DatabaseConImpl;
import model.VideoInfo;

import java.sql.PreparedStatement;

public class VideoInfoDAO{

    public void insertVideoInfo(VideoInfo v) {
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Insert into video_info (info_key, value, video_id) values (?,?,?)");
            pstmt.setString(1, v.getKey());
            pstmt.setString(2, v.getValue());
            pstmt.setInt(3, v.getVideoId());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

    }
}
