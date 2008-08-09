package org.donnchadh.gaelbot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.csvreader.CsvReader;

public class CsvReaderHelper {
	public void readFile(File file, CsvLineHandler handler) {
		try {
			CsvReader csvReader = new CsvReader(new FileInputStream(file), Charset.forName("UTF-8"));
			while (csvReader.readRecord()) {
	            String[] values = csvReader.getValues();
	            handler.handle(values);
	        }
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
