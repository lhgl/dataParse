package dao;

import dao.data.impl.DatabaseConImpl;
import model.VideoSubTokenTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoSubTokenTagDAO {
    public List<VideoSubTokenTag> consultaTokensByVideoSubId(int id) {

        DatabaseConImpl con = new DatabaseConImpl();
        List<VideoSubTokenTag> listaVideoSubTag = new ArrayList<VideoSubTokenTag>();

        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("select id, video_sub_id, token,tag from video_sub_token_tag where video_sub_id =  ? ");
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            listaVideoSubTag.addAll(getVideoSubTokenTagByResultSet(rs));

            pstmt.close();
            con.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return listaVideoSubTag;

    }

    private List<VideoSubTokenTag> getVideoSubTokenTagByResultSet(ResultSet rs) {

        List<VideoSubTokenTag> listaVideoSubTokenTag = new ArrayList<VideoSubTokenTag>();

        try {
            while (rs.next()) {
                VideoSubTokenTag videoSubTokenTag = new VideoSubTokenTag();
                videoSubTokenTag.setId(rs.getInt(1));
                videoSubTokenTag.setVideoSubId(rs.getInt(2));
                videoSubTokenTag.setToken(rs.getString(3));
                videoSubTokenTag.setTag(rs.getString(4));

                listaVideoSubTokenTag.add(videoSubTokenTag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVideoSubTokenTag;

    }

    public void salvarSubTokens(VideoSubTokenTag token) {
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Insert into video_sub_token_tag (video_sub_id, token,tag ) values (?,?,?)");
            pstmt.setInt(1, token.getVideoSubId());
            pstmt.setString(2, token.getToken());
            pstmt.setString(3, token.getTag());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

    }
}
