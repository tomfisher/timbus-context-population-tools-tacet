package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import controller.ImportReader;

public class TestImportReader {

	@Test
	public void test() {

		String url1 = null;
		String url2 = null;
		String url3 = null;
		try {
			url1 = TestImportReader.class.getResource("fileWithHeader.csv").toURI().toString();
			url2 = TestImportReader.class.getResource("fileWithoutHeader.csv").toURI().toString();
			url3 = TestImportReader.class.getResource("Testdata_Headline.csv").toURI().toString();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		System.out.println(url1);
		
		
		ImportReader reader1 = new ImportReader(url1, true, "");
		ImportReader reader2 = new ImportReader(url2, false, ";");
		ImportReader reader3 = new ImportReader(url3, true, "");
		try {
			reader1.analyseLine();
			reader2.analyseLine();
			reader3.analyseLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
