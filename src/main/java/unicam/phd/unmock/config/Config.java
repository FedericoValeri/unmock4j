package unicam.phd.unmock.config;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static final String PROVIDER =
            dotenv.get("PROVIDER", "openai");

    public static final String MODEL =
            dotenv.get("MODEL", "gpt-4o-mini");

    public static final String OPENAI_API_KEY =
            dotenv.get("OPENAI_API_KEY");

    public static final double TEMPERATURE = 0.0;

    public static final int MAX_INPUT_TOKENS = 32000;
}
