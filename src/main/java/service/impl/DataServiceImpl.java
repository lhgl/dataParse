package service.impl;

import dao.ActionUnitDAO;
import dao.VideoDAO;
import dao.VideoInfoDAO;
import dao.VideoSubDAO;
import model.ActionUnitInfo;
import model.Video;
import model.VideoInfo;
import model.VideoSub;
import service.DataService;
import subtitleFile.Caption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DataServiceImpl implements DataService {


    @Override
    public void saveInfosMap(HashMap<String, HashMap<String, String>> jsonValues) {
        //TITLE - INFO - VALUES
//        System.out.println(jsonValues.get("mDAoLO4G4CQ").toString());
        jsonValues.forEach((id, values) -> {
            values.forEach((key, jsonValue) -> {
                this.saveInfos(id, key, jsonValue);
            });

        });

    }

    @Override
    public void saveInfos(String id, String key, String jsonValue) {
        VideoDAO videoDao = new VideoDAO();
        Video video = videoDao.findByTitle(id);
        if (video == null) {
            video = videoDao.insertVideo(new Video(id));
        }

        List<VideoInfo> listaVideoInfo = new ArrayList<VideoInfo>();

        if (jsonValue.length() <= 200) {
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setKey(key);
            videoInfo.setValue(jsonValue);
            videoInfo.setVideoId(video.getId());

            listaVideoInfo.add(videoInfo);
        } else {
            int keyNumber = 1;
            for (int i = 0; i < jsonValue.length(); i += 200) {
                VideoInfo videoInfo = new VideoInfo();
                videoInfo.setKey(String.valueOf(keyNumber) + " : " + key);
                videoInfo.setValue(jsonValue.substring(i, Math.min(i + 199, jsonValue.length())));
                videoInfo.setVideoId(video.getId());
                listaVideoInfo.add(videoInfo);
                keyNumber++;
            }

        }
        listaVideoInfo.forEach(v -> {
            VideoInfoDAO videoInfoDAO = new VideoInfoDAO();
            //Save TITULO/KEY/VALUE
            videoInfoDAO.insertVideoInfo(v);

        });
    }

    @Override
    public void saveSubtitlesMap(HashMap<String, TreeMap<Integer, Caption>> srtValues) {
        //TITLE - ID - LEGENDA (INICIO - FIM - VALOR)
        srtValues.forEach((id, values) -> {
            this.saveSubtitles(id, values);
        });
    }

    @Override
    public void saveSubtitles(String id, TreeMap<Integer, Caption> values) {
        final HashMap<Integer, String> lastSubtitle = new HashMap<Integer, String>();
        values.forEach((id_caption, caption) -> {
            VideoDAO videoDao = new VideoDAO();
            VideoSubDAO videoSubDao = new VideoSubDAO();
            Video video = videoDao.findByTitle(id);
            if (video == null) {
                /**
                 * Recupera o ID do Video, todos arquivos são previamente salvos com o ID do vídeo do YoutTube e esta
                 * informação é utilizada para buscar o ID do Vídeo no Database garanrindo o relacionamento entre as
                 * estruturas.
                 *
                 * */
                video = videoDao.insertVideo(new Video(id));
            }

            /**
             * Necessário alterar atributos da Classe caption para permitir recuperar o tempo de Star e End da Legenda
             *
             * .JAR alterado, incluído no Projeto.
             *
             * https://github.com/JDaren/subtitleConverter.git
             *
             *
             * */
            VideoSub videoSub = new VideoSub();
            videoSub.setVideoId(video.getId());
            videoSub.setStartTime(caption.getStart());
            videoSub.setEndTime(caption.getEnd());
            /**
             * O padrão de legendas SRT preve que na mesma linha haja o seguinte cenário:
             *
             *2
             * 00:00:09,650 --> 00:00:09,660 *********
             * bom dia boa tarde boa noite pessoal são
             *
             *
             * 3
             * 00:00:09,660 --> 00:00:11,270
             * bom dia boa tarde boa noite pessoal são *********
             * professional jesus do canal está
             *
             * Desta forma há repetição de frazes na legenda, pois a mesma fica mais tempo sendo exibida.
             * Removo para fins de análise da próxima legenda e altero o tempo de fim da legenda anterior
             * Isto facilita uma Análise sintática e garante as informações de inicio e fim de uma frase para
             * uma análise relacional à outros itens (AUs, Infos):
             *
             * 2
             * 00:00:09,650 --> 00:00:11,270 *********
             * bom dia boa tarde boa noite pessoal são
             *
             * 3
             * 00:00:09,660 --> 00:00:11,270
             * professional jesus do canal está
             *
             * */

            /**
             * Remove FRASES da legenda anterior da atual
             * */
            String presentSubtitle = caption.getContent().replace(
                    !lastSubtitle.isEmpty()
                            ? lastSubtitle.get(
                            lastSubtitle.entrySet().iterator().next().getKey()
                    )
                            : "", "");


            /**
             * Se não forem iguais é porque houve alteração na legenda atual
             * significa que a mesma frase estava na PRÓXIMA LEGENDA então  ALTERA DATA FIM da legenda anterior.
             *
             * */
            if (presentSubtitle.compareTo(caption.getContent()) != 0) {
                VideoSub lastVideoSub = videoSubDao.selectById(lastSubtitle.entrySet().iterator().next().getKey());
                lastVideoSub.setEndTime(videoSub.getEndTime());
                videoSubDao.updateEndTime(lastVideoSub);
            }

            lastSubtitle.put(0, caption.getContent());

            if (presentSubtitle != null && !presentSubtitle.isEmpty()) {
                videoSub.setSub(presentSubtitle);

                /**
                 * SAVE
                 */
                videoSub = videoSubDao.insertVideoSub(videoSub);
                Integer videoSubId = Integer.valueOf(videoSub.getId());
                /**
                 * Apaga Map para ter apenas 1 registro (Não pode ser utilizado LIST por conta da manipulação do LAMBDA JAVA 8)
                 * */
                lastSubtitle.clear();
                lastSubtitle.put(videoSubId, presentSubtitle);
            }


        });
    }

    @Override
    public void saveActionUnits(HashMap<String, List<ActionUnitInfo>> ausValues) {
        ausValues.forEach((id, ausList) -> {
            ausList.forEach(aus -> {
                this.saveAUs(id, aus);
            });
        });
    }

    @Override
    public void saveAUs(String id, ActionUnitInfo aus) {

        VideoDAO videoDao = new VideoDAO();
        Video video = videoDao.findByTitle(id);
        if (video == null) {
            video = videoDao.insertVideo(new Video(id));
        }

        ActionUnitDAO actionsDao = new ActionUnitDAO();

        aus.setVideoId(video.getId());

        actionsDao.insertActionUnit(aus);
    }

}
