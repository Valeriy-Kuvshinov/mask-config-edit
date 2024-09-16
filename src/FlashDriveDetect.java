package src;

import java.io.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.*;
import javax.swing.filechooser.FileSystemView;

public class FlashDriveDetect {
    private ExecutorService executor;
    private volatile boolean isRunning = true;
    private Consumer<Boolean> flashDriveListener;
    private Set<File> connectedDrives = new HashSet<>();

    public FlashDriveDetect(Consumer<Boolean> listener) {
        this.flashDriveListener = listener;
    }

    public void startDetection() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::detectFlashDrive);
    }

    private void detectFlashDrive() {
        try {
            while (isRunning) {
                Set<File> currentDrives = getRemovableDrives();

                // Check for new drives
                for (File drive : currentDrives) {
                    if (!connectedDrives.contains(drive)) {
                        System.out.println("New flash drive detected: " + drive);
                        connectedDrives.add(drive);
                        notifyListener(true);
                    }
                }
                // Check for removed drives
                Iterator<File> iterator = connectedDrives.iterator();
                while (iterator.hasNext()) {
                    File drive = iterator.next();
                    if (!currentDrives.contains(drive)) {
                        System.out.println("Flash drive removed: " + drive);
                        iterator.remove();
                        notifyListener(false);
                    }
                }
                Thread.sleep(10000); // Check every 10 seconds to reduce spam
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Set<File> getRemovableDrives() {
        Set<File> removableDrives = new HashSet<>();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] roots = File.listRoots();
        for (File root : roots) {
            String description = fsv.getSystemTypeDescription(root);
            if (description != null && (description.contains("Removable") || description.contains("USB"))) {
                removableDrives.add(root);
                System.out.println("Detected removable drive: " + root + ", Description: " + description);
            }
        }
        return removableDrives;
    }

    private void notifyListener(boolean isConnected) {
        if (flashDriveListener != null) {
            flashDriveListener.accept(isConnected);
        }
    }

    public void shutdown() {
        isRunning = false;
        if (executor != null) {
            executor.shutdownNow();
        }
    }
}