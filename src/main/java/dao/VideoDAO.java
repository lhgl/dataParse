package dao;

import dao.data.impl.DatabaseConImpl;
import model.Video;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class VideoDAO{

    public Video findByTitle(String titulo) {
        Video video = null;
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Select id, title, data_criacao from video where title = ?");
            pstmt.setString(1, titulo);
            ResultSet rs = pstmt.executeQuery();
            video = getVideo(rs);
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return video;
    }

    private Video getVideo(ResultSet rs) {
        Video v = null;
        try {
            if (rs != null && rs.next()) {
                rs.beforeFirst();
                v = new Video();
                while (rs.next()) {
                    v.setId(rs.getInt("id"));
                    v.setTitle(rs.getString("title"));
                    v.setDataCriacao(rs.getDate("data_criacao"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public Video insertVideo(Video video) {
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Insert into video (title) values (?)");
            pstmt.setString(1, video.getTitle());
            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

        return this.findByTitle(video.getTitle());
    }
}
