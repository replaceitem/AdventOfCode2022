package net.replaceitem.days;

import net.replaceitem.Directory;
import net.replaceitem.FileSystemEntry;
import net.replaceitem.Util;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public class Day7 {
    public static void main(String[] args) throws IOException {
        List<String> lines = Util.loadInput("input7");

        Directory root = parseFileSystem(lines);

        List<Directory> smallDirectories = new ArrayList<>();
        accumulateDirectories(root, smallDirectories, directory -> directory.getOrEvaluateSize() <= 100000);
        System.out.println(smallDirectories.stream().mapToInt(Directory::getOrEvaluateSize).sum());

        List<Directory> bigDirectories = new ArrayList<>();
        int usedSpace = root.getOrEvaluateSize();
        int freeSpace = 70000000-usedSpace;
        int neededSpace = 30000000-freeSpace;
        accumulateDirectories(root, bigDirectories, directory -> directory.getOrEvaluateSize() >= neededSpace);

        System.out.println(bigDirectories.stream().mapToInt(Directory::getOrEvaluateSize).min().orElseThrow());
    }


    public static Directory parseFileSystem(List<String> lines) {
        final Directory root = new Directory("", null);
        Directory currentDir = root;

        for (int i = 0; i < lines.size(); i++) {
            String commandLine = lines.get(i);
            if (commandLine.charAt(0) != '$') throw new RuntimeException("Got non command line without prior ls call");
            String[] cmd = commandLine.substring(2).split(" ");
            String commandName = cmd[0];
            if (commandName.equals("cd")) {
                String location = cmd[1];
                currentDir = currentDir.navigate(location);
            } else if (commandName.equals("ls")) {
                while(true) {
                    i++;
                    if(i >= lines.size()) break;
                    String listLine = lines.get(i);
                    if(listLine.charAt(0) == '$') {
                        i--;
                        break;
                    }
                    String[] listElement = listLine.split(" ");
                    String type = listElement[0];
                    String name = listElement[1];
                    if(type.equals("dir")) {
                        currentDir.addDirectory(name);
                    } else {
                        currentDir.addFile(name, Integer.parseInt(type));
                    }
                }
            }
        }
        return root;
    }

    public static void accumulateDirectories(Directory directory, List<Directory> found, Predicate<Directory> predicate) {
        if(predicate.test(directory)) found.add(directory);
        for (FileSystemEntry fileSystemEntry : directory.getContent()) {
            if(fileSystemEntry.isDirectory()) {
                accumulateDirectories(fileSystemEntry.asDirectory(), found, predicate);
            }
        }
    }
}