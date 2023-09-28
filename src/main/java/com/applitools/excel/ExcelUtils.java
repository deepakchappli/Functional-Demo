package com.applitools.excel;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelUtils implements Closeable{
	
	private static ExcelUtils excelUtils;
	private Fillo filloInstance;
	private Connection connection;
	
	
	private ExcelUtils() throws FilloException {
		filloInstance = new Fillo();
		Properties appProperties = new Properties();

		Path appPropertiesFile = Paths.get("src/test/resources/application.properties");

		try (FileInputStream fis = new FileInputStream(appPropertiesFile.toFile())) {
			appProperties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String dataSheetPath = appProperties.getProperty("test.data.sheet.path", "src/test/resources/test_data/test_data.xlsx");
		
		connection = filloInstance.getConnection(dataSheetPath);
	}
	
	public static ExcelUtils instance() throws FilloException {
		if(Objects.isNull(excelUtils)) {
			excelUtils = new ExcelUtils();
		}
		
		return excelUtils;
	}
	
	public Recordset getData(String sheetName) throws FilloException {
		String query = String.format("select * from %s", sheetName);
		
		return connection.executeQuery(query);
	}

	@Override
	public void close() throws IOException {
		connection.close();
	}
	
}
