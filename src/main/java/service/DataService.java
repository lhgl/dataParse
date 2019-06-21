package service;

import model.ActionUnitInfo;
import subtitleFile.Caption;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public interface DataService {
    void saveInfosMap(HashMap<String, HashMap<String, String>> jsonValues);

    void saveInfos(String id, String key, String jsonValue);

    void saveSubtitlesMap(HashMap<String, TreeMap<Integer, Caption>> srtValues);

    void saveSubtitles(String id, TreeMap<Integer, Caption> caption);

    void saveActionUnits(HashMap<String, List<ActionUnitInfo>> ausValues);

    void saveAUs(String id, ActionUnitInfo aus);
}
