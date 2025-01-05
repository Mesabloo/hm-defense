package io.github.mesabloo.hmdefense.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.mesabloo.hmdefense.app.utils.AITester;

public class AITesterLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setTitle("Heavy Mach: Defense -- AI tester");
        new Lwjgl3Application(new AITester(), config);
    }
}
