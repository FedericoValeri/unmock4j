package unicam.phd.unmock;

import com.github.lalyos.jfiglet.FigletFont;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unicam.phd.unmock.bootstrap.ApplicationBootstrap;
import unicam.phd.unmock.bootstrap.UnmockApplication;
import unicam.phd.unmock.config.Config;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelContext;
import unicam.phd.unmock.infrastructure.llm.LargeLanguageModelFactory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            printBanner();
            if (Arrays.asList(args).contains("--llm-check")) {
                // LangChain4j logs a full stack trace on every retry; keep the check output clean.
                ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("dev.langchain4j.internal.RetryUtils"))
                        .setLevel(ch.qos.logback.classic.Level.ERROR);
                try {
                    checkLlm();
                } catch (Exception | ExceptionInInitializerError ex) { // the latter: Config failed to load .env
                    log.error("LLM check failed: {}", readableMessage(ex));
                    System.exit(1);
                }
                return;
            }
            UnmockApplication app = new ApplicationBootstrap().create();
            app.run();
            log.info("Done.");
        } catch (Exception ex) {
            log.error("Application failed.", ex);
            System.exit(1);
        }
    }

    private static void checkLlm() {
        if ("openai".equalsIgnoreCase(Config.PROVIDER)
                && (Config.OPENAI_API_KEY == null || Config.OPENAI_API_KEY.isBlank())) {
            throw new IllegalStateException("OPENAI_API_KEY is missing in .env");
        }
        LargeLanguageModelContext ctx = new LargeLanguageModelFactory().create();
        ChatResponse r = ctx.llm().chat(UserMessage.from("Reply with exactly: OK"));
        log.info("LLM check passed ({} - {}). Reply: \"{}\", tokens in/out: {}/{}",
                ctx.provider(), ctx.model(), r.aiMessage().text().trim(),
                r.tokenUsage().inputTokenCount(), r.tokenUsage().outputTokenCount());
    }

    // Provider errors carry a JSON body like {"error":{"message":"..."}}; show just that message.
    private static String readableMessage(Throwable ex) {
        for (Throwable c = ex; c != null; c = c.getCause()) {
            if (c instanceof java.net.ConnectException) {
                return "cannot connect to the LLM server (is it running and reachable?)";
            }
            if (c instanceof io.github.cdimascio.dotenv.DotenvException) {
                return ".env file not found in " + System.getProperty("user.dir") + " (run from the repo root)";
            }
        }
        Throwable t = ex;
        while (t.getMessage() == null && t.getCause() != null) t = t.getCause();
        String msg = t.getMessage();
        if (msg == null) return t.getClass().getSimpleName();
        Matcher m = Pattern.compile("\"message\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(msg);
        return m.find() ? m.group(1) : msg.strip();
    }

    private static void printBanner() throws Exception {
        System.out.println(FigletFont.convertOneLine("UnMock"));
    }
}