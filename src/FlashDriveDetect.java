package src;

import java.io.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class FlashDriveDetect {
    private ExecutorService executor;
    private volatile boolean isRunning = true;
    private Consumer<Boolean> flashDriveListener;
    private Set<String> connectedDrives = new HashSet<>();
    private FileSystemView fsv = FileSystemView.getFileSystemView();
    private String currentUsbDrivePath = "";

    public FlashDriveDetect(Consumer<Boolean> listener) {
        this.flashDriveListener = listener;
    }

    public void startDetection() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::detectFlashDrive);
    }

    private void detectFlashDrive() {
        System.out.println("USB detection thread started.");
        while (isRunning) {
            checkForDriveChanges();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void checkForDriveChanges() {
        try {
            Set<String> currentDrives = getRemovableDrives();

            // Check for new drives
            for (var drive : currentDrives) {
                if (!connectedDrives.contains(drive)) {
                    System.out.println("Flash drive connected: " + drive);
                    connectedDrives.add(drive);
                    currentUsbDrivePath = drive;
                    notifyListener(true);
                }
            }
            // Check for removed drives
            Iterator<String> iterator = connectedDrives.iterator();
            while (iterator.hasNext()) {
                String drive = iterator.next();
                if (!currentDrives.contains(drive)) {
                    System.out.println("Flash drive disconnected: " + drive);
                    iterator.remove();
                    currentUsbDrivePath = "";
                    notifyListener(false);
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking for drive changes:");
            e.printStackTrace();
        }
    }

    private Set<String> getRemovableDrives() {
        Set<String> removableDrives = new HashSet<>();
        File[] roots = File.listRoots();

        for (var root : roots) {
            if (isRemovableDrive(root)) {
                removableDrives.add(root.getAbsolutePath());
            }
        }
        return removableDrives;
    }

    private boolean isRemovableDrive(File drive) {
        var description = fsv.getSystemTypeDescription(drive);
        var isRemovable = fsv.isDrive(drive) && !fsv.isFileSystemRoot(drive);
        var isNotNetworkDrive = !fsv.isComputerNode(drive);

        // Criteria to verify if a drive is in fact, a USB drive
        var isUSBDrive = (description != null
                && (description.toLowerCase().contains("usb") || description.toLowerCase().contains("removable"))) ||
                (isRemovable && isNotNetworkDrive && drive.getTotalSpace() > 0);

        return isUSBDrive;
    }

    public String getCurrentUsbDrivePath() {
        return currentUsbDrivePath;
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