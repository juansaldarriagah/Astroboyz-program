package sync;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockManager {

    public static boolean withFileLock(String filePath, FileOperation operation) {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
             FileChannel channel = raf.getChannel();
             FileLock lock = channel.lock()) {

            operation.execute();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FunctionalInterface
    public interface FileOperation {
        void execute() throws IOException;
    }
}
