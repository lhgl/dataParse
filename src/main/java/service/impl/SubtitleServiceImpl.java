package service.impl;

import service.SubtitleService;
import subtitleFile.FormatSRT;
import subtitleFile.TimedTextObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class SubtitleServiceImpl implements SubtitleService {
    @Override
    public void readAndSaveSubtitles(List<File> listaArquivos, String path) {

        DataServiceImpl data = new DataServiceImpl();
        FileServiceImpl file = new FileServiceImpl();

        listaArquivos.forEach(arquivo -> {
            System.out.println("Capturando Legendas do arquivo: " + arquivo.getName());
            try {
                FileInputStream fin = new FileInputStream(arquivo);
                FormatSRT f = new FormatSRT();
                TimedTextObject t = f.parseFile(arquivo.getName(), fin);

                System.out.println("Inicializando Insert de Subtitles do arquivo : " + arquivo.getName());
                //file has name .pt.srt
                data.saveSubtitles(arquivo.getName().replace(".pt.srt", ""), t.captions);

                System.out.println("Finalizando Insert de Subtitles do arquivo : " + arquivo.getName());

                fin.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Finalizado Captura de Legendas do arquivo: " + arquivo.getName());
            System.out.println("Iniciando, mover arquivo : " + arquivo.getName());
            file.moveFile(arquivo, path);
            System.out.println("Finalizando, mover arquivo : " + arquivo.getName());
        });

    }
}
