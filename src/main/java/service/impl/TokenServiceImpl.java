package service.impl;

import model.Video;
import model.VideoSub;
import model.VideoSubTag;
import model.VideoSubTokenTag;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import service.TokenService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TokenServiceImpl implements TokenService {

    private static String sentenceModel = "D:\\Git\\dataParser\\src\\main\\PLNModels\\pt-sent.bin";

    private static String perceptronModel = "D:\\Git\\dataParser\\src\\main\\PLNModels\\pt-pos-perceptron.bin";

    @Override
    public void process() {
        DataServiceImpl dataService = new DataServiceImpl();
        List<Video> listaVideos = dataService.consultaVideos();
        listaVideos.forEach(video -> {
            List<VideoSub> listaSub = dataService.consultaSubByVideoId(video.getId());
            listaSub.forEach(sub -> {

                List<VideoSubTokenTag> vstt = dataService.consultaTokensByVideoSubId(sub.getId());

                try {
                    InputStream inputStream = new FileInputStream(sentenceModel);

                    SentenceModel model = new SentenceModel(inputStream);
                    SentenceDetectorME detector = new SentenceDetectorME(model);
                    String[] sequences = detector.sentDetect(sub.getSub());

                    InputStream inputStream2 = new FileInputStream(perceptronModel);
                    POSModel modelPOS = new POSModel(inputStream2);
                    POSTaggerME tagger = new POSTaggerME(modelPOS);
                    SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;


                    for (String s : sequences) {
                        String tokens[] = simpleTokenizer.tokenize(s);

                        //SE A LEGENDA NÃO FOI PROCESSADA, REALIZA O PROCESSAMENTO
                        if (vstt == null || vstt.isEmpty()) {

                            String[] tags = tagger.tag(tokens);

                            for (int i = 0; i < tokens.length; i++) {
                                //System.out.println(tokens[i] + " - " + tags[i]);
                                VideoSubTag tag = dataService.consultarSubTag(tags[i]);
                                if (tag == null) {
                                    //TAGS SÃO AS MORFOLOGIAS
                                    tag = dataService.salvarSubTags(tags[i]);
                                }

                                VideoSubTokenTag token = new VideoSubTokenTag();
                                token.setToken(tokens[i]);
                                token.setTag(tag.getTag());
                                token.setVideoSubId(sub.getId());

                                //TOKEN SÃO AS PALAVRAS
                                dataService.salvarSubTokens(token);

                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        });
    }

}
