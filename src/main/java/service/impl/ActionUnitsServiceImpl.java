package service.impl;

import model.ActionUnitInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import service.ActionUnitsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ActionUnitsServiceImpl implements ActionUnitsService {

    @Override
    public void readAndSaveActionUnitsReport(List<File> filesList, String path) {

        DataServiceImpl data = new DataServiceImpl();
        FileServiceImpl file = new FileServiceImpl();

        filesList.forEach(arquivo -> {
            try (FileReader reader = new FileReader(arquivo)) {
                System.out.println("Capturando ActionUnits do arquivo: " + arquivo.getName());
                System.out.println("Iniciando Insert de  ActionUnits do arquivo: " + arquivo.getName());
                CSVParser csv = CSVParser.parse(reader,
                        CSVFormat
                                .EXCEL
                                .withDelimiter(',')
                                .withFirstRecordAsHeader()
                                .withTrim());
                for (CSVRecord csvRecord : csv) {

                    ActionUnitInfo auInfo = this.getActionUnitInfo(csvRecord);

                    data.saveAUs(arquivo.getName().replace(".csv", ""), auInfo);

                }
                System.out.println("Finalizando Insert de  ActionUnits do arquivo: " + arquivo.getName());
                System.out.println("Finalizando captura de ActionUnits do arquivo: " + arquivo.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Iniciando, mover arquivo : " + arquivo.getName());
            file.moveFile(arquivo, path);
            System.out.println("Finalizando, mover arquivo : " + arquivo.getName());
        });

    }

    @Override
    public ActionUnitInfo getActionUnitInfo(CSVRecord csvRecord) {
        return new ActionUnitInfo(
                Float.parseFloat(csvRecord.get("frame")),
                Float.parseFloat(csvRecord.get("face_id")),
                Double.parseDouble(csvRecord.get("timestamp")),
                Float.parseFloat(csvRecord.get("confidence")),
                Float.parseFloat(csvRecord.get("success")),
                Float.parseFloat(csvRecord.get("AU01_r")),
                Float.parseFloat(csvRecord.get("AU02_r")),
                Float.parseFloat(csvRecord.get("AU04_r")),
                Float.parseFloat(csvRecord.get("AU05_r")),
                Float.parseFloat(csvRecord.get("AU06_r")),
                Float.parseFloat(csvRecord.get("AU07_r")),
                Float.parseFloat(csvRecord.get("AU09_r")),
                Float.parseFloat(csvRecord.get("AU10_r")),
                Float.parseFloat(csvRecord.get("AU12_r")),
                Float.parseFloat(csvRecord.get("AU14_r")),
                Float.parseFloat(csvRecord.get("AU15_r")),
                Float.parseFloat(csvRecord.get("AU17_r")),
                Float.parseFloat(csvRecord.get("AU20_r")),
                Float.parseFloat(csvRecord.get("AU23_r")),
                Float.parseFloat(csvRecord.get("AU25_r")),
                Float.parseFloat(csvRecord.get("AU26_r")),
                Float.parseFloat(csvRecord.get("AU45_r")),
                Float.parseFloat(csvRecord.get("AU01_c")),
                Float.parseFloat(csvRecord.get("AU02_c")),
                Float.parseFloat(csvRecord.get("AU04_c")),
                Float.parseFloat(csvRecord.get("AU05_c")),
                Float.parseFloat(csvRecord.get("AU06_c")),
                Float.parseFloat(csvRecord.get("AU07_c")),
                Float.parseFloat(csvRecord.get("AU09_c")),
                Float.parseFloat(csvRecord.get("AU10_c")),
                Float.parseFloat(csvRecord.get("AU12_c")),
                Float.parseFloat(csvRecord.get("AU14_c")),
                Float.parseFloat(csvRecord.get("AU15_c")),
                Float.parseFloat(csvRecord.get("AU17_c")),
                Float.parseFloat(csvRecord.get("AU20_c")),
                Float.parseFloat(csvRecord.get("AU23_c")),
                Float.parseFloat(csvRecord.get("AU25_c")),
                Float.parseFloat(csvRecord.get("AU26_c")),
                Float.parseFloat(csvRecord.get("AU28_c")),
                Float.parseFloat(csvRecord.get("AU45_c"))
        );
    }
}
