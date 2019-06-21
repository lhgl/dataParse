package service;

import java.io.File;
import java.util.List;

public abstract interface SubtitleService {
    public void readAndSaveSubtitles(List<File> listaArquivos, String path);
}
