package io.github.mesabloo.hmdefense.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.mesabloo.hmdefense.app.utils.AnimationTester;

public class AnimationTesterLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setTitle("Heavy Mach: Defense -- Machine/Turret tester");
        new Lwjgl3Application(new AnimationTester(), config);
    }
}
