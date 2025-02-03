package com.ftp.server.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Volume {
    private final File rootDirectory;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Volume(String rootPath) {
        this.rootDirectory = new File(rootPath);

        if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
            throw new IllegalArgumentException("Invalid root directory: " + rootPath);
        }
    }

    public File getRootDirectory() {
        return rootDirectory;
    }

    public File getFile(String path) {
        return new File(rootDirectory, path);
    }

    public boolean createDirectory(String path) {
        lock.writeLock().lock();

        try {
            File dir = new File(rootDirectory, path);

            return dir.mkdirs();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean deleteFile(String path) {
        lock.writeLock().lock();

        try {
            File file = new File(rootDirectory, path);

            try {
                Files.delete(file.toPath());
                return true;
            } catch (IOException e) {
                return false;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean renameFile(String oldPath, String newPath) {
        lock.writeLock().lock();

        try {
            File oldFile = new File(rootDirectory, oldPath);
            File newFile = new File(rootDirectory, newPath);

            return oldFile.renameTo(newFile);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public File[] listFiles() {
        return rootDirectory.listFiles();
    }
}