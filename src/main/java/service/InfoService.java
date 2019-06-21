package service;

import java.io.File;
import java.util.List;

public interface InfoService {
    public void readAndSaveInfos(List<File> listaArquivos, String path);
}
