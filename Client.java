/*
    DUMITRESCU EVELINA 341C3 Tema4 SPRC
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/*Server class used to handle I/O connections from other peers*/
class ServerPeer implements Runnable {

	int port;
	final int BYTE_BUFFER_SIZE = 4096;
	final int NR_BYTES_SIZE = 4;
	private ByteBuffer buffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);
	private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
	Charset charset;
	CharsetDecoder decoder;

	public ServerPeer(int port) {
		this.port = port;
    	this.charset = Charset.defaultCharset();
		this.decoder = charset.newDecoder();		
		(new Thread(this)).start();

	}
	public void run() {

	/* open Selector and ServerSocketChannel */
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel
					.open();
			/* check if they were successfully opened */
			if ((serverSocketChannel.isOpen()) && (selector.isOpen())) {
				/* non-blocking mode */
				serverSocketChannel.configureBlocking(false);
				/* bind the server socket channel to port */
				serverSocketChannel.bind(new InetSocketAddress(port));
				/* register the current channel with the given selector */
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("Wait for connections ..");
				while (true) {
					/* waiting for incomming events */
					selector.select();

					/* select a key for a processing an event */
					Iterator keys = selector.selectedKeys().iterator();

					while (keys.hasNext()) {
						SelectionKey key = (SelectionKey) keys.next();

						/* remove key such that the same key won't come up again */
						keys.remove();

						if (!key.isValid()) {
							continue;
						}
						/* new connection/reading event/writing event */
						if (key.isAcceptable()) {
							acceptOP(key, selector);
						} else if (key.isReadable()) {
							this.readOP(key);
						} else if (key.isWritable()) {
							this.writeOP(key);
						}
					}
				}

			} else {
			
			}
		} catch (IOException ex) {
			
		}
	}
	
	/* new connecton */
	private void acceptOP(SelectionKey key, Selector selector)
			throws IOException {

		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverChannel.accept();
		socketChannel.configureBlocking(false);
		System.out.println("Incoming connection from: "+ socketChannel.getRemoteAddress());
		/* register channel with selector for exchanging further messages */
		keepDataTrack.put(socketChannel, new ArrayList<byte[]>());
		socketChannel.register(selector, SelectionKey.OP_READ);
	}

	/* reading event */
	private void readOP(SelectionKey key) {
		try {
			SocketChannel socketChannel = (SocketChannel) key.channel();

			buffer.clear();

			int numRead = -1;
			try {
				numRead = socketChannel.read(buffer);
			} catch (IOException e) {
				System.out.println("Cannot read from socket");
			}

			if (numRead == -1) {
				this.keepDataTrack.remove(socketChannel);
				System.out.println("Connection closed by: "
						+ socketChannel.getRemoteAddress());
				socketChannel.close();
				key.cancel();
				return;
			}

			byte[] data = new byte[numRead];
			System.arraycopy(buffer.array(), 0, data, 0, numRead);

			/* respond to client */
			doEchoJob(key, data);
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
    
	/* writing event */
	private void writeOP(SelectionKey key)  {
	    try
	    {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		Iterator<byte[]> its = channelData.iterator();
		RandomAccessFile f;

		StringTokenizer st;
		String fileName;
		int fragment;
		String task;
		/* process all requests from channel queue */
		while (its.hasNext()) {
			byte[] it = its.next();
			its.remove();
			/* extract filename, fragment no and send back the file fragment */
			task = new String(it);
			/* remove trailing grabage characters from message */
			task = task.replaceAll("[^A-Za-z0-9. ]", "");
			String[] tokens = task.split(" ");
			fileName = tokens[1];
			if(tokens[0].equals("size") )
			{
			byte[] bufferRead = new byte[BYTE_BUFFER_SIZE];

	    	long size = new File(fileName).length();
	    	//System.out.println("file size "+ size);	    	
	    	for(int i=4;i<12;i++) {
	    	    bufferRead[i] = (byte)(size >> (i *8));
             bufferRead[0] = 8;


	    	}
 	          	socketChannel.write(ByteBuffer.wrap(bufferRead));	 	    	    
	    	}
	    	else
	    	{
	    	    fragment = Integer.parseInt(tokens[2]);
	    	   fileName = tokens[1];
       			f = new RandomAccessFile(fileName, "r");
    			long positionToJump = (BYTE_BUFFER_SIZE - NR_BYTES_SIZE) * fragment;
	    		f.seek(positionToJump);
	    		//System.out.println("file fragment "+fragment + " "+fileName);
    			byte[] bufferRead = new byte[BYTE_BUFFER_SIZE];
	    		int rd = f.read(bufferRead, 4, BYTE_BUFFER_SIZE - NR_BYTES_SIZE);
	    		for (int i = 0; i < 4; i++) {
	    			bufferRead[i] = (byte) (rd >> (i * 8));

	    		}
	    		socketChannel.write(ByteBuffer.wrap(bufferRead));       			
    			f.close();       			
            }
	    	  		


			
		}

		key.interestOps(SelectionKey.OP_READ);
		}
		catch(Exception exc)
		{
		    exc.printStackTrace();
        }
	
	}
	/* store in a queue the requests for further processing */
	private void doEchoJob(SelectionKey key, byte[] data) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		channelData.add(data);
		key.interestOps(SelectionKey.OP_WRITE);
	}	

}

/* client class used to send I/O requests to other peers */
class ClientPeer implements Runnable {
	ByteBuffer receivingBufferPeer;

	final int BYTE_BUFFER_SIZE = 4096;
	final int NR_BYTES_SIZE = 4;
	int remotePort;
	String downloadFile;
	public ClientPeer(int remotePort, String downloadFile) {
		this.remotePort = remotePort;
		this.downloadFile = downloadFile;
		this.receivingBufferPeer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);

		(new Thread(this)).start();
	}

	public void run() {
		try {

			/* open Selector and ServerSocketChannel */
			Selector selector = Selector.open();
			SocketChannel socketChannel = SocketChannel.open();
		
			// check if both of them are opened
			if ((socketChannel.isOpen()) && (selector.isOpen())) {

				/* non-blocking mode */
				socketChannel.configureBlocking(false);

				/* register the channel */
				socketChannel.register(selector, SelectionKey.OP_CONNECT);

				/* connect to remote host */
				socketChannel.connect(new java.net.InetSocketAddress(
						"localhost", remotePort));

				while (selector.select(1000) > 0) {

					/* get current selected keys */
					Set keys = selector.selectedKeys();
					Iterator its = keys.iterator();

					/* process each one */
					while (its.hasNext()) {
						SelectionKey key = (SelectionKey) its.next();

						/* remove the selected one */
						its.remove();

						/* get the socket channel for current key */
						try (SocketChannel keySocketChannel = (SocketChannel) key
								.channel()) {

							/* try to connect */
							if (key.isConnectable()) {

								/* close pendent connections */
								if (keySocketChannel.isConnectionPending()) {
									keySocketChannel.finishConnect();
								}

                                    RandomAccessFile f = new RandomAccessFile(
										this.downloadFile + "2", "rw");
								
									byte[] message = new byte[BYTE_BUFFER_SIZE];
									byte[] messageBytes = ("size "+this.downloadFile ).getBytes();
									System.arraycopy(messageBytes, 0, message,
											0, messageBytes.length);

									/* send the request to the remote peer */
									ByteBuffer sendingBuffer = ByteBuffer
											.wrap(message);
									keySocketChannel.write(sendingBuffer);
									int numRead = 0;
									int numBytes = 0;
									long fileSize = 0;
									long numBytesReceived = 0;
									receivingBufferPeer.clear();

									/* get the response and write the fragment */
									while ((numRead += keySocketChannel
											.read(receivingBufferPeer)) < BYTE_BUFFER_SIZE);
									receivingBufferPeer.flip();

									byte[] data = new byte[numRead];
									System.arraycopy(
											receivingBufferPeer.array(), 0,
											data, 0, numRead);
									/*
									 * first 4 bytes represent the size of the
									 * file fragment
									 */
									numBytes = ((0xFF & data[3]) << 24)
											+ ((0xFF & data[2]) << 16)
											+ ((0xFF & data[1]) << 8)
											+ (0xFF & data[0]);

                                    fileSize = ((0xFF & data[11]) << 54)
                                            + ((0xFF & data[10]) << 48) 
                                            + ((0xFF & data[9]) << 40)
                                            + ((0xFF & data[8]) << 32)
                                            + ((0xFF & data[7]) << 24)
											+ ((0xFF & data[6]) << 16)
											+ ((0xFF & data[5]) << 8)
											+ (0xFF & data[4]);
									System.out.println("Am primit "+numBytes+" "+ fileSize);																				
    								
    							long fragmentNo = fileSize/(long)4092;
    							if(fileSize % 4092 != 0) fragmentNo++;

	                           for (long fragment=0;fragment< fragmentNo; fragment++) {
									message = new byte[BYTE_BUFFER_SIZE];
									messageBytes = ("fragment "+this.downloadFile
											+ " " +  fragment).getBytes();
									System.arraycopy(messageBytes, 0, message,
											0, messageBytes.length);

									ByteBuffer sendingBuffer2 = ByteBuffer
											.wrap(message);
									keySocketChannel.write(sendingBuffer2);
									
									int positionToJump = (BYTE_BUFFER_SIZE - NR_BYTES_SIZE)
											* ((int) fragment);
									f.seek(positionToJump);
									receivingBufferPeer.clear();
								
                                    numRead = 0;
									while ((numRead += keySocketChannel
											.read(receivingBufferPeer)) < BYTE_BUFFER_SIZE);
									receivingBufferPeer.flip();
									data = new byte[numRead];
									System.arraycopy(
											receivingBufferPeer.array(), 0,
											data, 0, numRead);
								
									numBytes = ((0xFF & data[3]) << 24)
											+ ((0xFF & data[2]) << 16)
											+ ((0xFF & data[1]) << 8)
											+ (0xFF & data[0]);

									f.write(data, 4, numBytes);
								
									
									
									if (receivingBufferPeer.hasRemaining()) {
										receivingBufferPeer.compact();
									} else {
										receivingBufferPeer.clear();
									}
									//System.out.println("ma cac pe IDP"); 
								}
								f.close();   								
								}

							
						} catch (IOException ex) {
							ex.printStackTrace();

						}
					}
				}
			 

		}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
public class Client extends AbstractClient {


    public Client()
    {
        
    }

    
    public static void main(String[] args)
    {

        int port = Integer.parseInt(args[0]);
        int remotePort = Integer.parseInt(args[1]);
        String fileName = args[2];
        new Thread(new ServerPeer(port));
        //e server
        if(port!=1201)
            new Thread(new ClientPeer(remotePort,fileName)); 

        
    }

}

