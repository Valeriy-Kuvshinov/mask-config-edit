package src;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class FlashDriveDetect {
    private ExecutorService executor;
    private volatile boolean isRunning = true;
    private Consumer<Boolean> flashDriveListener;
    private Set<Path> connectedDrives = new HashSet<>();

    public FlashDriveDetect(Consumer<Boolean> listener) {
        this.flashDriveListener = listener;
    }

    public void startDetection() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::detectFlashDrive);
    }

    private void detectFlashDrive() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path rootPath = Paths.get("/");
            rootPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            while (isRunning) {
                WatchKey key = watchService.poll(1, TimeUnit.SECONDS);
                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        Path eventPath = (Path) event.context();
                        Path fullPath = rootPath.resolve(eventPath);

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            if (isRemovableDrive(fullPath)) {
                                System.out.println("Flash drive connected: " + fullPath);
                                connectedDrives.add(fullPath);
                                notifyListener(true);
                            }
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            if (connectedDrives.remove(fullPath)) {
                                System.out.println("Flash drive disconnected: " + fullPath);
                                notifyListener(false);
                            }
                        }
                    }
                    key.reset();
                }
                // Periodically check for any missed events
                checkForMissedEvents();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isRemovableDrive(Path path) {
        if (Files.isDirectory(path)) {
            try {
                FileStore store = Files.getFileStore(path);
                return store.type().toLowerCase().contains("removable") ||
                        store.name().toLowerCase().contains("usb");
            } catch (IOException e) {
                // Ignore errors, assume it's not removable
            }
        }
        return false;
    }

    private void checkForMissedEvents() {
        Set<Path> currentDrives = getRemovableDrives();

        // Check for new drives
        for (Path drive : currentDrives) {
            if (!connectedDrives.contains(drive)) {
                System.out.println("New flash drive detected: " + drive);
                connectedDrives.add(drive);
                notifyListener(true);
            }
        }

        // Check for removed drives
        Iterator<Path> iterator = connectedDrives.iterator();
        while (iterator.hasNext()) {
            Path drive = iterator.next();
            if (!currentDrives.contains(drive)) {
                System.out.println("Flash drive removed: " + drive);
                iterator.remove();
                notifyListener(false);
            }
        }
    }

    private Set<Path> getRemovableDrives() {
        Set<Path> removableDrives = new HashSet<>();
        FileSystem fs = FileSystems.getDefault();
        for (Path root : fs.getRootDirectories()) {
            if (isRemovableDrive(root)) {
                removableDrives.add(root);
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