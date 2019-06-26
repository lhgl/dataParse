package service;

import model.*;
import subtitleFile.Caption;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public interface DataService {
    void saveInfosMap(HashMap<String, HashMap<String, String>> jsonValues);

    void saveInfos(String id, String key, String jsonValue);

    void saveSubtitlesMap(HashMap<String, TreeMap<Integer, Caption>> srtValues);

    void saveSubtitles(String id, TreeMap<Integer, Caption> caption);

    void saveActionUnitsMap(HashMap<String, List<ActionUnitInfo>> ausValues);

    void saveAUs(String id, ActionUnitInfo aus);

    List<Video> consultaVideos();

    List<VideoSub> consultaSubByVideoId(int id);

    List<VideoSubTokenTag> consultaTokensByVideoSubId(int id);

    VideoSubTag salvarSubTags(String tag);

    void salvarSubTokens(VideoSubTokenTag token);

    VideoSubTag consultarSubTag(String tag);
}
