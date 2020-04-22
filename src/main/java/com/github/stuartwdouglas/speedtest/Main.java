package com.github.stuartwdouglas.speedtest;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {

    public static void main(String... args) throws Exception {
        String path = args[0];
        for (; ; ) {
            long start = System.currentTimeMillis();
            final URLClassLoader ucl = new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()});
            Thread.currentThread().setContextClassLoader(ucl);

            Class appClass = ucl.loadClass("io.quarkus.runner.ApplicationImpl");
            Object app = appClass.newInstance();
            app.getClass().getMethod("start", String[].class).invoke(app, (Object) new String[0]);
            app.getClass().getMethod("stop").invoke(app);


            long ms = System.currentTimeMillis() - start;
            System.out.println("Time: " + ms);


        }
    }

}
