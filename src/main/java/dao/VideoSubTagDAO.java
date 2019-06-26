package dao;

import dao.data.impl.DatabaseConImpl;
import model.VideoSubTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoSubTagDAO {
    public VideoSubTag salvarSubTags(String tag) {

        VideoSubTag videoSubTag = null;
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Insert into video_sub_tag (tag) values (?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, tag);
            pstmt.execute();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            /**
             * ADD ID que foi inserido
             * */
            int id = rs.getInt(1);

            videoSubTag = new VideoSubTag(id, tag);

            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

        return videoSubTag;
    }

    public VideoSubTag consultarSubTags(String tag) {
        VideoSubTag videoSubTag = null;
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("select id, tag from video_sub_tag where tag = ? ");
            pstmt.setString(1, tag);
            ResultSet rs = pstmt.executeQuery();

            List<VideoSubTag> listaVideoSubTag = getVideoSubTagByResultSet(rs);

            /**
             * Deve vir apenas 1 tag
             * */
            if (listaVideoSubTag != null && !listaVideoSubTag.isEmpty()) {
                videoSubTag = listaVideoSubTag.get(0);
            }

            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

        return videoSubTag;
    }

    private List<VideoSubTag> getVideoSubTagByResultSet(ResultSet rs) {
        List<VideoSubTag> listaVideoSubTag = new ArrayList<VideoSubTag>();

        try {
            while (rs.next()) {
                VideoSubTag videoSubTag = new VideoSubTag();

                videoSubTag.setId(rs.getInt(1));
                videoSubTag.setTag(rs.getString(2));

                listaVideoSubTag.add(videoSubTag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVideoSubTag;
    }
}
