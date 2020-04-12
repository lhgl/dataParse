import service.Service;

public class Start {

    public static void main(String[] args) {

        Service service = new Service();
//        service.processaArquivoSubtitles();
//        service.processaArquivoInfos();
//        service.processaArquivoAUs();
//        service.processaTokensFromSubtitles();
        service.processaApriori();
        /**
         * TODO Avaliar Refactor deste programa, o mesmo está se tornando um grande serviço com várias regras de negócio envolvidas
         */


    }
}
