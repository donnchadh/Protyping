package org.donnchadh.gaelbot.services;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import org.donnchadh.gaelbot.domainmodel.services.IFileSystemService;

public class FileSystemService implements IFileSystemService {
    
    private final SecureRandom randomNumberGenerator;

    public FileSystemService() {
        randomNumberGenerator = new SecureRandom(new SecureRandom().generateSeed(16));
    }
    
    public long addContent(File root, byte[] content) {
        long id = generateNewId(root);
        File file = getFile(root, id);
        // TODO - write to file.
        return id;
    }
    
    public byte[] getContent(File root, long id) {
        File file = getFile(root, id);
        // TODO read file
        return new byte[] {};
    }

    private long generateNewId(File root) {
        long candidate;
        String path;
        File file;
        do {
            candidate = randomNumberGenerator.nextLong();
            path = generateRelativePath(root, candidate);
            file = new File(root, path);
        } while (file.exists());
        File folder = file.getParentFile();
        folder.mkdirs();
		if (!folder.exists()) {
            throw new RuntimeException("Could not create folder: " + folder.getAbsolutePath());
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Could not create file: " + file.getAbsolutePath(), e);
        }
        return candidate;
    }

    private String generateRelativePath(File root, long id) {
        String path = null;
        for (int i = 1; i < 3; i++) {
            path = generateRelativePath(id, i);
            File file = new File(root, path);
            if (file.getParentFile().list().length < 750) {
                return path;
            }
        }
        return path;
    }

    private File getFile(File root, long id) {
        for (int i = 1; i < 3; i++) {
            String path = generateRelativePath(id, i);
            File file = new File(root, path);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    private String generateRelativePath(long id, int limit) {
        if (limit > 3) {
            throw new IllegalArgumentException("Limit too large: " + limit);
        }
        int[] somePrimes = {1531,1523,1511};
        long quotient = id;
        if (quotient < 0) {
            quotient = -quotient;
        }
        int[] remainders = new int[somePrimes.length];
        for (int i = 0; i < limit; i++) {
            remainders[i] = (int)(quotient % somePrimes[i]);
            quotient /= somePrimes[i];
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            buffer.append(remainders[i]);
            buffer.append('/');
        }
        buffer.append(quotient);
        return buffer.toString();
    }

}
