/**
 * 
 */
package transfers;

/**
 * Abstract class for transfer state
 *
 */
public abstract class TransferState {
	
	protected Transfer transfer;
	
	public TransferState(Transfer transfer)
	{
		this.transfer = transfer;
	}

	public void doAction() {}
}
