package net.replaceitem;

public class File extends FileSystemEntry {

    private final int size;

    public File(String name, int size, Directory parent) {
        super(name, parent);
        this.size = size;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public Directory asDirectory() {
        return null;
    }

    @Override
    public int getOrEvaluateSize() {
        return this.getSize();
    }

    public int getSize() {
        return size;
    }
}
