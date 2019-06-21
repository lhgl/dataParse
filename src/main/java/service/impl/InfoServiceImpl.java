package service.impl;

import org.json.JSONObject;
import org.json.JSONTokener;
import service.InfoService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class InfoServiceImpl implements InfoService {

    @Override
    public void readAndSaveInfos(List<File> listaArquivos, String path) {
        HashMap<String, HashMap<String, String>> jsonValues = new HashMap<String, HashMap<String, String>>();
        DataServiceImpl data = new DataServiceImpl();
        FileServiceImpl file = new FileServiceImpl();

        listaArquivos.forEach(arquivo -> {

            System.out.println("Capturando Infos do arquivo: " + arquivo.getName());
            try (FileReader reader = new FileReader(arquivo)) {
                JSONObject obj = new JSONObject(new JSONTokener(reader));
                System.out.println("Inicializando Insert de Infos do arquivo : " + arquivo.getName());
                obj.names().forEach(k -> {
                            data.saveInfos(arquivo.getName().replace(".info.json", ""), k.toString(), obj.get(k.toString()).toString());
                        }
                );
                System.out.println("Finalizado Insert de Infos do arquivo : " + arquivo.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Finalizado Captura de Infos do arquivo : " + arquivo.getName());
            System.out.println("Iniciando, mover arquivo : " + arquivo.getName());
            file.moveFile(arquivo, path);
            System.out.println("Finalizando, mover arquivo : " + arquivo.getName());

        });
    }

}
