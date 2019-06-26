package dao;

import dao.data.impl.DatabaseConImpl;
import model.ActionUnitInfo;
import subtitleFile.Time;

import java.sql.PreparedStatement;

public class ActionUnitDAO {
    public void insertActionUnit(ActionUnitInfo aus) {
        DatabaseConImpl con = new DatabaseConImpl();
        try {

            PreparedStatement pstmt = con.getConnection().prepareStatement("Insert into video_action_units (" +
                    "video_id, frame, face_id, capture_time, confidence, success, AU01_r, AU02_r, AU04_r, AU05_r," +
                    " AU06_r, AU07_r, AU09_r, AU10_r, AU12_r, AU14_r, AU15_r, AU17_r, AU20_r, AU23_r, AU25_r, AU26_r," +
                    " AU45_r, AU01_c, AU02_c, AU04_c, AU05_c, AU06_c, AU07_c, AU09_c, AU10_c, AU12_c, AU14_c, AU15_c," +
                    " AU17_c, AU20_c, AU23_c, AU25_c, AU26_c, AU28_c, AU45_c) values (" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?)");

            pstmt.setInt(1, aus.getVideoId());
            pstmt.setFloat(2, aus.getFrame());
            pstmt.setFloat(3, aus.getFace_id());
            //pstmt.setTime(4, new Time(Long.valueOf((int) aus.getTimestamp() * 1000)));

            pstmt.setString(4, new Time((int) (aus.getTimestamp() * 1000)).toString("hh:mm:ss,ms").replace(",", "."));

            pstmt.setFloat(5, aus.getConfidence());
            pstmt.setFloat(6, aus.getSuccess());
            pstmt.setFloat(7, aus.getAU01_r());
            pstmt.setFloat(8, aus.getAU02_r());
            pstmt.setFloat(9, aus.getAU04_r());
            pstmt.setFloat(10, aus.getAU05_r());
            pstmt.setFloat(11, aus.getAU06_r());
            pstmt.setFloat(12, aus.getAU07_r());
            pstmt.setFloat(13, aus.getAU09_r());
            pstmt.setFloat(14, aus.getAU10_r());
            pstmt.setFloat(15, aus.getAU12_r());
            pstmt.setFloat(16, aus.getAU14_r());
            pstmt.setFloat(17, aus.getAU15_r());
            pstmt.setFloat(18, aus.getAU17_r());
            pstmt.setFloat(19, aus.getAU20_r());
            pstmt.setFloat(20, aus.getAU23_r());
            pstmt.setFloat(21, aus.getAU25_r());
            pstmt.setFloat(22, aus.getAU26_r());
            pstmt.setFloat(23, aus.getAU45_r());
            pstmt.setFloat(24, aus.getAU01_c());
            pstmt.setFloat(25, aus.getAU02_c());
            pstmt.setFloat(26, aus.getAU04_c());
            pstmt.setFloat(27, aus.getAU05_c());
            pstmt.setFloat(28, aus.getAU06_c());
            pstmt.setFloat(29, aus.getAU07_c());
            pstmt.setFloat(30, aus.getAU09_c());
            pstmt.setFloat(31, aus.getAU10_c());
            pstmt.setFloat(32, aus.getAU12_c());
            pstmt.setFloat(33, aus.getAU14_c());
            pstmt.setFloat(34, aus.getAU15_c());
            pstmt.setFloat(35, aus.getAU17_c());
            pstmt.setFloat(36, aus.getAU20_c());
            pstmt.setFloat(37, aus.getAU23_c());
            pstmt.setFloat(38, aus.getAU25_c());
            pstmt.setFloat(39, aus.getAU26_c());
            pstmt.setFloat(40, aus.getAU28_c());
            pstmt.setFloat(41, aus.getAU45_c());

            pstmt.execute();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.closeConnection();
        }

    }

}
