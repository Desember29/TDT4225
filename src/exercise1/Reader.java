package exercise1;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.*;
import java.io.IOException;
import static java.nio.file.StandardOpenOption.READ;
	
public class Reader {
	private static final int BLOCKSIZE = 8192;
	private static final long NBLOCKS = 131072;
	
	//Function to read file of file size given by parameter fileSize in GBs
	private void readTest(int fileSize) {
		Path file = Paths.get(System.getProperty("user.dir"), "myjavadata");
		SeekableByteChannel in;
			try {
				in = Files.newByteChannel(file,EnumSet.of(READ));
				
				//Make a marker of start time for file reading
				long startTime = System.currentTimeMillis();
				//Counter for how many bytes have been read
				long totalBytes = 0;
				ByteBuffer buff = ByteBuffer.allocate(BLOCKSIZE);
				for (int n = 0; n < NBLOCKS * fileSize; n++) { 
					int bytesRead = in.read(buff);
					buff.clear(); 
					totalBytes+=bytesRead;
				}
				//Time spent reading file
				long totalTime = System.currentTimeMillis() - startTime;
				int throughput = (int) (fileSize*1024/((double) totalTime/1000));
				int throughput2 = (int) ((totalBytes/(1024*1024))/((double) totalTime/1000));
				System.out.println(fileSize + " GB\t\t" + throughput + "MB/s\t" + throughput2 + "MB/s\t" + (int) totalTime + " ms");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		System.out.println("EXT4/NIO\tThroughput\tThroughput2\tTime\n------------------------------------------------------------");
		reader.readTest(1);
		reader.readTest(2);
		reader.readTest(4);
		reader.readTest(8);
		reader.readTest(16);
		reader.readTest(32);
	}
}
