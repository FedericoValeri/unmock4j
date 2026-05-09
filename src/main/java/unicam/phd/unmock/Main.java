package unicam.phd.unmock;

import com.github.lalyos.jfiglet.FigletFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unicam.phd.unmock.bootstrap.ApplicationBootstrap;
import unicam.phd.unmock.bootstrap.UnmockApplication;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            printBanner();
            UnmockApplication app = new ApplicationBootstrap().create();
            app.run();
            log.info("Done.");
        } catch (Exception ex) {
            log.error("Application failed.", ex);
            System.exit(1);
        }
    }

    private static void printBanner() throws Exception {
        System.out.println(FigletFont.convertOneLine("UnMock"));
    }
}