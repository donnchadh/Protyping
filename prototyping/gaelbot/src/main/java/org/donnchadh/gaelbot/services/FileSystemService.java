package org.donnchadh.gaelbot.services;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

public class FileSystemService {
    
    private final SecureRandom randomNumberGenerator;

    public FileSystemService() {
        randomNumberGenerator = new SecureRandom(new SecureRandom().generateSeed(16));
    }
    
    public long addContent(File root, byte[] content) {
        long id = generateNewId(root);
        File file = generateFile(root, id);
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
        // TODO - write to file.
        return id;
    }
    
    private long generateNewId(File root) {
        long candidate;
        do {
            candidate = randomNumberGenerator.nextLong();
        } while (generateFile(root, candidate).exists());
        return candidate;
    }

    public byte[] getContent(File root, long id) {
        File file = generateFile(root, id);
        // TODO read file
        return new byte[] {};
    }

    private File generateFile(File root, long id) {
        File file = new File(root, generateRelativePath(id));
        return file;
    }

    private String generateRelativePath(long id) {
        int[] somePrimes = {151,157,163,167,173,179,181,191,193};
        long quotient = id;
        if (quotient < 0) {
            quotient = -quotient;
        }
        int[] remainders = new int[9];
        for (int i = 0; i < remainders.length; i++) {
            remainders[i] = (int)(quotient % somePrimes[i]);
            quotient /= somePrimes[i];
        }
        StringBuilder buffer = new StringBuilder();
        for (int i : remainders) {
            buffer.append(i);
            buffer.append('/');
        }
        return buffer.toString();
    }
}
