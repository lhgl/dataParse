package dao;

import dao.data.impl.DatabaseConImpl;
import model.Video;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VideoDAO{

    public Video findByTitle(String titulo) {
        Video video = null;
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Select id, title, data_criacao from video where title = ?");
            pstmt.setString(1, titulo);
            ResultSet rs = pstmt.executeQuery();
            /**
             * Deve vir apenas 1 vídeo
             * */
            video = getVideo(rs).get(0);
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return video;
    }

    private List<Video> getVideo(ResultSet rs) {
        List<Video> listaVideo = new ArrayList<Video>();
        try {
            if (rs != null && rs.next()) {
                rs.beforeFirst();

                while (rs.next()) {
                    Video v = new Video();
                    v.setId(rs.getInt("id"));
                    v.setTitle(rs.getString("title"));
                    v.setDataCriacao(rs.getDate("data_criacao"));
                    listaVideo.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVideo;
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

    public List<Video> getAllVideos() {
        List<Video> listaVideo = new ArrayList<Video>();

        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Select id, title, data_criacao from video");

            ResultSet rs = pstmt.executeQuery();
            listaVideo = getVideo(rs);
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return listaVideo;
    }
}
