package net.replaceitem;

import java.util.*;

public class Directory extends FileSystemEntry {

    private final static int UNKNOWN_SIZE = -1;

    protected Map<String, FileSystemEntry> content = new HashMap<>();
    protected int evaluatedSize = UNKNOWN_SIZE;

    public Directory(String name, Directory parent) {
        super(name, parent);
    }

    public Collection<FileSystemEntry> getContent() {
        return content.values();
    }

    public void addDirectory(String name) {
        add(new Directory(name, this));
    }

    public void addFile(String name, int size) {
        add(new File(name, size, this));
    }

    public void add(FileSystemEntry entry) {
        content.put(entry.getName(), entry);
    }

    public Directory navigate(String location) {
        if(location.equals("/")) {
            return navigateRoot();
        } else if(location.equals("..")) {
            return navigateParent();
        } else {
            return navigateSubDir(location);
        }
    }

    @Override
    public Directory navigateRoot() {
        if(!this.hasParent()) return this;
        return super.navigateRoot();
    }

    public Directory navigateSubDir(String location) {
        FileSystemEntry fileSystemEntry = content.get(location);
        if(fileSystemEntry == null) throw new RuntimeException("Could not navigate to " + location + " because it doesn't exist");
        if(fileSystemEntry instanceof File) throw new RuntimeException("Could not navigate to " + location + " because it isn't a directory");
        return fileSystemEntry.asDirectory();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public Directory asDirectory() {
        return this;
    }

    @Override
    public int getOrEvaluateSize() {
        if(evaluatedSize == UNKNOWN_SIZE) {
            evaluatedSize = content.values().stream().mapToInt(FileSystemEntry::getOrEvaluateSize).sum();
        }
        return evaluatedSize;
    }
}
