package com.github.stuartwdouglas.speedtest;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main14 {

    public static void main(String... args) throws Exception {
        String path = args[0];
        for (; ; ) {
            long start = System.currentTimeMillis();
            final URLClassLoader ucl = new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()});
            Thread.currentThread().setContextClassLoader(ucl);

            ucl.loadClass("io.quarkus.runtime.ApplicationLifecycleManager")
                    .getMethod("setDefaultExitCodeHandler", Consumer.class)
                    .invoke(null, new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {

                        }
                    });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ucl.loadClass("io.quarkus.dev.appstate.ApplicationStateNotification")
                                .getMethod("waitForApplicationStart").invoke(null);

                        ucl.loadClass("io.quarkus.runtime.Quarkus")
                                .getMethod("asyncExit").invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            Class<?> main = ucl.loadClass("io.quarkus.runner.GeneratedMain");

            main.getMethod("main", String[].class).invoke(null, (Object) new String[0]);

            long ms = System.currentTimeMillis() - start;
            System.out.println("Time: " + ms);


        }
    }

}
