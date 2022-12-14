package net.replaceitem;

public abstract class FileSystemEntry {

    private final String name;
    private final Directory parent;

    public FileSystemEntry(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public abstract boolean isDirectory();

    public abstract Directory asDirectory();

    public abstract int getOrEvaluateSize();

    public String getName() {
        return name;
    }


    public Directory navigateRoot() {
        return this.navigateParent().navigateRoot();
    }

    public boolean hasParent() {
        return parent != null;
    }

    public Directory navigateParent() {
        if(parent == null) throw new RuntimeException("Could not navigate to parent directory");
        return this.parent;
    }
}
