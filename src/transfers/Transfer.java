package transfers;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import core.Mediator;

/**
 * 
 * Transfer class for handling data of an ongoing transfer
 * 
 */
public class Transfer extends AbstractTransfer  {
	private final static Logger LOGGER = Logger.getLogger(Transfer.class .getName()); 
	private String file;
	private String toUser;
	private String fromUser;
	private Mediator med;
	private long fileSize;
	private long downloaded = 0;
	private int index;
	ByteBuffer receivingBufferPeer;
	final int BYTE_BUFFER_SIZE = 4096;
	final int NR_BYTES_SIZE = 4;
	int remotePort;
	long startFragment;
	/**
	 * 0 - Download; 1 - Upload;
	 */
	private String type;
	/**
	 * 0 - started; 1 - active; 2 - paused; 3 - stopped; 4 - completed;
	 */
	private int state;
	private long progress = 0;

	public Transfer(String file, String fromUser, String toUser, Mediator med,
			int type, int remotePort, long fragment, int index) {
		this.file = file;
		this.toUser = toUser;
		this.fromUser = fromUser;
		state = STARTED;
		this.med = med;
		this.index = index;
		this.remotePort = remotePort;
		this.receivingBufferPeer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);
		this.startFragment = fragment;
		if (type == DOWNLOAD)
			this.type = new String("Downloading");
		else
			this.type = new String("Uploading");

	}

	public void updateProgress(long chunk) {
		downloaded += chunk;
		LOGGER.info("Received "+chunk +" bytes");
		progress = (long) ((double) downloaded / (double) fileSize * (double) 100);
		if (progress == 100) {
			progress = 100;
			state = COMPLETED;
		}
		med.updateProgress(progress, index);
	}

	public void setTransferState(int state) {
		this.state = state;
		LOGGER.info("Transfer " +this.index+" state changed to "+state);
	}

	public boolean isCompleted() {
		if (progress == 100) {
			LOGGER.info("Transfer " +this.index+" is completed");
			
			return true;
		}
		return false;
	}

	public long getProgress() {
		return this.progress;
	}

	public int getTransferState() {
		return this.state;
	}

	public String getType() {
		return type;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		
		try {

			/* open Selector and ServerSocketChannel */
			Selector selector = Selector.open();
			SocketChannel socketChannel = SocketChannel.open();
			LOGGER.info("Opening socket channel and trying to connect to remote peer");
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
								LOGGER.info("Connection succeded");
								/* close pendent connections */
								if (keySocketChannel.isConnectionPending()) {
									keySocketChannel.finishConnect();
								}

								RandomAccessFile f = new RandomAccessFile(
										"downloads/" + toUser + "/"+this.file , "rw");

								byte[] message = new byte[BYTE_BUFFER_SIZE];
								byte[] messageBytes = ("size " + "downloads/" + fromUser + "/"+this.file)
										.getBytes();
								System.arraycopy(messageBytes, 0, message, 0,
										messageBytes.length);
								/* send the request to the remote peer */
								ByteBuffer sendingBuffer = ByteBuffer
										.wrap(message);
								keySocketChannel.write(sendingBuffer);
								int numRead = 0;
								int numBytes = 0;
								long fileSize = 0;
								receivingBufferPeer.clear();

								while ((numRead += keySocketChannel
										.read(receivingBufferPeer)) < BYTE_BUFFER_SIZE);
								receivingBufferPeer.flip();
								byte[] data = new byte[numRead];
								System.arraycopy(receivingBufferPeer.array(),
										0, data, 0, numRead);
								/*
								 * first 4 bytes represent the size of the file
								 * fragment
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
								LOGGER.info("Requested file "+this.file+" size "+fileSize);
								this.fileSize = fileSize;
								long fragmentNo = fileSize / (long) 4092;
								if (fileSize % 4092 != 0)
									fragmentNo++;
								/* ask for a specific fragment and write it in the file */
								LOGGER.info("Started file fragment transfer");
								for (; startFragment < fragmentNo; startFragment++) {
									message = new byte[BYTE_BUFFER_SIZE];
									messageBytes = ("fragment " +  "downloads/" + fromUser + "/"+this.file
											+ " " + startFragment).getBytes();
									System.arraycopy(messageBytes, 0, message,
											0, messageBytes.length);

									ByteBuffer sendingBuffer2 = ByteBuffer
											.wrap(message);
									keySocketChannel.write(sendingBuffer2);

									long positionToJump = (BYTE_BUFFER_SIZE - NR_BYTES_SIZE)
											* ((long) startFragment);
									f.seek(positionToJump);
									receivingBufferPeer.clear();

									numRead = 0;
									while ((numRead += keySocketChannel
											.read(receivingBufferPeer)) < BYTE_BUFFER_SIZE)
										;
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
									updateProgress(numBytes);

									if (receivingBufferPeer.hasRemaining()) {
										receivingBufferPeer.compact();
									} else {
										receivingBufferPeer.clear();
									}
									
								}
								f.close();
							}

						} catch (IOException ex) {
							LOGGER.error(ex.toString());

						}
					}
				}

			}
		} catch (Exception ex) {
			LOGGER.error(ex.toString());
		}

	}
}
