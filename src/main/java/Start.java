public class Start {

    public static void main(String[] args) {

        Service service = new Service();
        service.processaArquivoSubtitles();
        service.processaArquivoInfos();
        service.processaArquivoAUs();

    }
}
