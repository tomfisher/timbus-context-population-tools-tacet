package edu.teco.tacet.readers.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.teco.tacet.readers.csv.BufferedRandomAccessFile;

public class BufferedRAFTest {
	
	@Before
	public void setup() {
		fill();
		
		Writer fw = null;

		try {
			fw = new FileWriter("test.txt");
			for (int i = 0; i < 10000; ++i) {
				fw.write(get(i));
			}
			fw.flush();
		}
		catch ( IOException e ) {
			System.err.println( "Konnte Datei nicht erstellen" );
		}
	}
	
	ArrayList<Character> chars;
	
	private Character get(int index) {
		return chars.get(index % chars.size());
	}
	
	private void fill() {
		chars = new ArrayList<>();
		String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\n";
		for (int i = 0; i < s.length(); ++i) {
			chars.add(s.charAt(i));
		}
	}
	
	
	@Test
	public void test() {
		try {
			BufferedRandomAccessFile bram = new BufferedRandomAccessFile(new RandomAccessFile("test.txt", "r"));
			
			assertTrue(bram.next() == get(0));
			assertTrue(bram.prev() == get(0));
			assertTrue(bram.next() == get(0));
			bram.skip(9);
			assertTrue(bram.next() == get(10));
			assertTrue(bram.next() == get(11));
			bram.skip(4988);
			assertTrue(bram.next() == get(5000));
			assertTrue(bram.next() == get(5001));
			bram.back(3002);
			assertTrue(bram.next() == get(2000));
			bram.gotoBeginning();
			assertTrue(bram.next() == get(0));
			assertTrue(bram.getFileLength() == 10000);
			bram.gotoEnd();
			assertTrue(bram.next() == -1);
			assertTrue(bram.prev() == get(9999));
			bram.gotoPosition(8192);
			assertTrue(bram.next() == get(8192));
			assertTrue(bram.prev() == get(8192));
			assertTrue(bram.prev() == get(8191));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
