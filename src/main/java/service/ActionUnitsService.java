package service;

import model.ActionUnitInfo;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.List;

public interface ActionUnitsService {
    void readAndSaveActionUnitsReport(List<File> filesList, String path);

    ActionUnitInfo getActionUnitInfo(CSVRecord csvRecord);
}
