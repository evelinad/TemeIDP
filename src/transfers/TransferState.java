/**
 * 
 */
package transfers;

/**
 * @author Serban
 *
 */
public abstract class TransferState {
	
	Transfer transfer;
	
	public TransferState(Transfer transfer)
	{
		this.transfer = transfer;
	}

	public void doAction() {}
}
