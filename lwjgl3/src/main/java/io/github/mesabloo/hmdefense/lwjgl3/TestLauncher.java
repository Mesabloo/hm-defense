package io.github.mesabloo.hmdefense.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.mesabloo.hmdefense.app.GameEntryPoint;
import io.github.mesabloo.hmdefense.data.GameSave;
import io.github.mesabloo.hmdefense.ui.GameScreen;
import scala.jdk.javaapi.FunctionConverters$;

import java.util.Date;

public class TestLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setTitle("Heavy Mach: Defense");

        GameSave save = new GameSave("Test", new Date());

        new Lwjgl3Application(
            new GameEntryPoint(
                FunctionConverters$.MODULE$.asScalaFromFunction(
                    game -> new GameScreen(game, 1, save)
                )
            ), config);
    }
}
