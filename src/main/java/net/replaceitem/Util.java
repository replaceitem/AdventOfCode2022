package net.replaceitem;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<String> loadInput(InputStream stream) throws IOException {
        return new ArrayList<>(List.of(new String(stream.readAllBytes()).split(System.lineSeparator())));
    }

    public static @NotNull List<String> loadInput(String location) throws IOException {
        try (InputStream resourceAsStream = Util.class.getClassLoader().getResourceAsStream("inputs/" + location)) {
            if(resourceAsStream == null) throw new IOException();
            return loadInput(resourceAsStream);
        }
    }

    public static int clamp(int v, int a, int b) {
        return Math.max(a,(Math.min(v, b)));
    }
}
