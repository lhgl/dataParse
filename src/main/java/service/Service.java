package service;

import service.impl.*;

import java.io.File;
import java.util.List;

public class Service {

    private static String path = "D:\\Git\\Download_Youtube_VID\\videos";
    private static String subtitleExt = ".srt";
    private static String infoExt = ".json";
    private static String auExt = ".csv";


    public void processaArquivoInfos() {
        FileServiceImpl file = new FileServiceImpl();
        List<File> filesList = file.getFiles(path, infoExt);

        InfoServiceImpl info = new InfoServiceImpl();
        info.readAndSaveInfos(filesList, path);

    }

    public void processaArquivoSubtitles() {
        FileServiceImpl file = new FileServiceImpl();
        List<File> filesList = file.getFiles(path, subtitleExt);

        SubtitleServiceImpl subtitle = new SubtitleServiceImpl();
        subtitle.readAndSaveSubtitles(filesList, path);

    }

    public void processaArquivoAUs() {
        FileServiceImpl file = new FileServiceImpl();
        List<File> filesList = file.getFiles(path, auExt);

        ActionUnitsServiceImpl aus = new ActionUnitsServiceImpl();
        aus.readAndSaveActionUnitsReport(filesList, path);

    }

    public void processaTokensFromSubtitles() {
        TokenServiceImpl tokenService = new TokenServiceImpl();
        tokenService.process();
    }

}
