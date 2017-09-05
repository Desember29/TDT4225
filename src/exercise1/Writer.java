package exercise1;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Writer {
	private static final int BLOCKSIZE = 8192;
	private static final long NBLOCKS = 131072;
	
	//Function to write file of file size given by parameter fileSize in GBs
	private void writeTest(int fileSize) {
		Path file = Paths.get(System.getProperty("user.dir"), "myjavadata");
		SeekableByteChannel out;
			try {
				out = Files.newByteChannel(file,EnumSet.of(CREATE, APPEND));
				
				//Make a marker of start time for file creation
				long startTime = System.currentTimeMillis();
				for (int n = 0; n < NBLOCKS * fileSize; n++) {
					ByteBuffer buff = ByteBuffer.allocate(BLOCKSIZE);					
					out.write(buff);
				}
				//Time spent writing files
				long totalTime = System.currentTimeMillis() - startTime;
				int throughput = (int) (fileSize*1024/((double) totalTime/1000));
				System.out.println(fileSize + " GB\t\t" + throughput + " MB/s\t" + (int) totalTime + " ms");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	public static void main(String[] args) {
		Writer writer = new Writer();
		System.out.println("EXT4/NIO\tThroughput\tTime\n-------------------------------------------");
		writer.writeTest(1);
		writer.writeTest(2);
		writer.writeTest(4);
		writer.writeTest(8);
		writer.writeTest(16);
		writer.writeTest(32);
	}
}

