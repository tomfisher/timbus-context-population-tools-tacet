package edu.teco.tacet.readers.csv;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferedRandomAccessFile {
	private RandomAccessFile raf;
	private ByteBuffer buffer;
	
	private final int BUFSIZE = 262144;
	private long bstart;
	private long filelength;
	
	public BufferedRandomAccessFile(RandomAccessFile file) {
		raf = file;
		bstart = 0;
		filelength = -1;
		readBlockToBuffer();
	}
	
	private int readBlockToBuffer() {
		try {
			return readBlockToBuffer(raf.getFilePointer());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private int readBlockToBuffer(long position) {
		try {
			bstart = position;
			raf.seek(position);
			FileChannel channel = raf.getChannel();
			buffer = ByteBuffer.allocate(BUFSIZE);
			int N = channel.read(buffer);
			//channel.close();
			buffer.flip();
	        return N;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int next() {
		if(bstart + buffer.position() == getFileLength()){
			return -2;
		}
		if (bstart + buffer.position() >= getFileLength()) {
			return -1;
		}
		if (!buffer.hasRemaining()) {
			if (readBlockToBuffer() < 0) return -1; 
		}
		return buffer.get();
	}
	
	public int prev() {
		if (buffer.position() == 0 && bstart == 0) return -1;
		if (buffer.position() == 0) {
			long pos = bstart - 1;
			long s = pos - BUFSIZE/2;
			if (s < 0) s = 0;
			readBlockToBuffer(s);
			buffer.position((int) (pos - s));
			int r = buffer.get();
			buffer.position((int) (pos - s));
			return r;
		}
		
		buffer.position(buffer.position() - 1);
		int r = buffer.get();
		buffer.position(buffer.position() - 1);
		return r;
	}

	public void skip(int n) {
		if (n > buffer.remaining()) {
			readBlockToBuffer(bstart + buffer.position() + n);
		}
		else {
			buffer.position(buffer.position() + n);
		}
	}

	public void back(int n) {
		if (n > buffer.position()) {
			long pos = bstart + buffer.position() - n;
			if (pos < 0) back((int) (n+pos));
			readBlockToBuffer(pos);
		}
		else {
			buffer.position(buffer.position() - n);
		}
	}
	
	public void gotoPosition(long n) {
		if (bstart > n || bstart + buffer.limit() < n) {
			readBlockToBuffer(n);
		}
		else {
			buffer.position((int) (n - bstart));
		}
	}
	
	public void gotoBeginning() {
		gotoPosition(0);
	}
	
	public void gotoEnd() {
		gotoPosition(getFileLength());
	}

	public long getFileLength() {
		if (filelength != -1) return filelength;
		try {
			return filelength = raf.length();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public long getCurrentPointer() {
		return bstart + buffer.position();
	}
}
