package transfers;

public abstract class AbstractTransfer extends Thread implements
		TransferStatusConstans {

	public abstract void updateProgress(long chunk);

	public abstract void setTransferState(int state);

	public abstract boolean isCompleted();

	public abstract long getProgress();

	public abstract int getTransferState();

	public abstract String getType();
}
