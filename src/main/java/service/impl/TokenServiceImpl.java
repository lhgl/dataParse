package service.impl;

import model.Video;
import model.VideoSub;
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

    @Override
    public void process() {
        DataServiceImpl dataService = new DataServiceImpl();
        List<Video> listaVideos = dataService.consultaVideos();
        listaVideos.forEach(video -> {
            List<VideoSub> listaSub = dataService.consultaSubByVideoId(video.getId());
            listaSub.forEach(sub -> {
                InputStream inputStream = null;

                try {
                    inputStream = new FileInputStream("D:\\Git\\dataParser\\src\\main\\PLNModels\\pt-sent.bin");

                    SentenceModel model =  new SentenceModel(inputStream);

                    SentenceDetectorME detector = new SentenceDetectorME(model);

                    String[] sequences = detector.sentDetect(sub.getSub());

                    inputStream = new FileInputStream("D:\\Git\\dataParser\\src\\main\\PLNModels\\pt-pos-perceptron.bin");

                    POSModel modelPOS = new POSModel(inputStream);

                    POSTaggerME tagger = new POSTaggerME(modelPOS);

                    SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

                    for (String s : sequences) {
                        //Tokenizing the given sentence
                        String tokens[] = simpleTokenizer.tokenize(s);

                        String[] tags = tagger.tag(tokens);
                        for (int i = 0; i < tokens.length; i++) {
                            System.out.println(tokens[i] + " - " + tags[i]);

//                                VideoSubTokenTag vstt = consultaTokensByVideoSubId(sub.getId());
//                                if (vstt == null){
//                                //TAGS SÃO AS MORFOLOGIAS
//                                VideoSubTag tag = salvarSubTags(tags[i]);
//
//                                VideoSubTokenTag token = new VideoSubTokenTag();
//                                token.setToken(tokens[i]);
//                                token.setTag(tag.getTag());
//                                token.setVideoSubId(sub.getId());
//
//                                //TOKEN SÃO AS PALAVRAS
//                                salvarSubTokens(token);
//                              }
//
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
