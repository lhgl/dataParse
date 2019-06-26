package dao;

import dao.data.impl.DatabaseConImpl;
import model.VideoSub;
import subtitleFile.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoSubDAO {

    public VideoSub insertVideoSub(VideoSub videoSub) {
        DatabaseConImpl con = new DatabaseConImpl();
        try {
            /**
             * select id,video_id, date_format(start_time,'%T.%f'), date_format(end_time,'%T.%f'),sub from captura.video_sub;
             * */
            PreparedStatement pstmt = con.getConnection().prepareStatement("Insert into video_sub (video_id,start_time, end_time,sub) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, videoSub.getVideoId());
            /**
             * BUG JDBC Java.sql.Time não salva miliseconds (fractional)
             *
             * Foi necessário realizar transaformação de milissecond para String
             *
             * https://bugs.openjdk.java.net/browse/JDK-8186415
             *              * https://bugs.mysql.com/bug.php?id=76775
             *
             *
             *  pstmt.setTime(2, new Time(videoSub.getStartTime().getMseconds()));
             *  pstmt.setTime(3, new Time(videoSub.getEndTime().getMseconds()));
             */

            /**
             *
             * Necessário aterar a Classe Time do Package Subtitle.Time para criar um toString no formato abaixo.
             *
             * .JAR alterado, incluído no Projeto.
             * https://github.com/JDaren/subtitleConverter.git
             *
             * */
            pstmt.setString(2, videoSub.getStartTime().toString("hh:mm:ss,ms").replace(",", "."));
            pstmt.setString(3, videoSub.getEndTime().toString("hh:mm:ss,ms").replace(",", "."));

            pstmt.setString(4, videoSub.getSub());

            pstmt.execute();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            /**
             * ADD ID que foi inserido
             * */
            videoSub.setId(rs.getInt(1));

            pstmt.close();
            con.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return videoSub;
    }


    public VideoSub selectById(Integer key) {
        DatabaseConImpl con = new DatabaseConImpl();
        VideoSub videoSub = new VideoSub();
        try {
            /**
             * ;
             * */
            PreparedStatement pstmt = con.getConnection().prepareStatement("select id,video_id, MICROSECOND(date_format(start_time,'%T.%f')), MICROSECOND(date_format(end_time,'%T.%f')),sub from captura.video_sub where id = ? ");
            pstmt.setInt(1, key);

            ResultSet rs = pstmt.executeQuery();
            /**
             * DEVE VIR APENAS 1 NESTA CONSULTA POR ISTO GET(0)
             */

            videoSub = getVideoSubByResultSet(rs).get(0);

            pstmt.close();
            con.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return videoSub;

    }


    private List<VideoSub> getVideoSubByResultSet(ResultSet rs) {
        List<VideoSub> listaVideoSub = new ArrayList<VideoSub>();

        try {
            while (rs.next()) {
                VideoSub videoSub = new VideoSub();
                videoSub.setId(rs.getInt(1));
                videoSub.setVideoId(rs.getInt(2));
                /**
                 *
                 * Necessário aterar a Classe Time do Package Subtitle.Time para criar Construtor Time public.
                 *
                 * .JAR alterado, incluído no Projeto.
                 * https://github.com/JDaren/subtitleConverter.git
                 *
                 * */
                videoSub.setStartTime(new Time(rs.getInt(3)));
                videoSub.setEndTime(new Time(rs.getInt(4)));
                videoSub.setSub(rs.getString(5));

                listaVideoSub.add(videoSub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVideoSub;
    }

    public void updateEndTime(VideoSub lastVideoSub) {
        DatabaseConImpl con = new DatabaseConImpl();
        try {
            /**
             * select id,video_id, date_format(start_time,'%T.%f'), date_format(end_time,'%T.%f'),sub from captura.video_sub;
             * */
            PreparedStatement pstmt = con.getConnection().prepareStatement("update video_sub set end_time = ? where id = ? ");
            pstmt.setString(1, lastVideoSub.getEndTime().toString("hh:mm:ss,ms").replace(",", "."));
            pstmt.setInt(2, lastVideoSub.getId());

            pstmt.execute();

            pstmt.close();
            con.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

    }

    public List<VideoSub> getAllVideoSub(Integer videoId) {
        DatabaseConImpl con = new DatabaseConImpl();
        List<VideoSub> listaVideoSub = new ArrayList<VideoSub>();

        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("select id,video_id, MICROSECOND(date_format(start_time,'%T.%f')), MICROSECOND(date_format(end_time,'%T.%f')),sub from captura.video_sub where video_id = ? ");
            pstmt.setInt(1, videoId);

            ResultSet rs = pstmt.executeQuery();
            listaVideoSub.addAll(getVideoSubByResultSet(rs));

            pstmt.close();
            con.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }
        return listaVideoSub;

    }

}
