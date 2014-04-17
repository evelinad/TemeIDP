package test;

import conf.Configure;
import core.Mediator;
import junit.framework.TestCase;

public class TestBart extends TestCase {

	private Mediator med;
	private Configure cfg;
	private int index = 0;

	public void setUp() {
		med = new Mediator();
		cfg = new Configure("bart", med, 6501);
		cfg.setUpUsers();
	}

	public void testFullTransfer() {
		med.setFromValue("homer");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("homer.txt");
		med.doTransfer();
		med.setSelectedTransfer(index);
		med.startSelectedTransfer();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DiffFiles diff = new DiffFiles("downloads/bart/homer.txt",
				"downloads/homer/homer.txt");
		assertTrue("downloaded file homer.txt is identical to original",
				diff.compareFiles());
	}
	
	public void testStartStop() {
		++index;
		med.setFromValue("marge");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("marge.txt");
		med.doTransfer();
		med.setSelectedTransfer(index);
		med.startSelectedTransfer();
		med.stopSelectedTransfer();
		med.startSelectedTransfer();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DiffFiles diff = new DiffFiles("downloads/bart/marge.txt",
				"downloads/marge/marge.txt");
		assertTrue("downloaded file marge.txt is identical to original",
				diff.compareFiles());
	}
	
	public void testPauseResume() {
		++index;
		med.setFromValue("lisa");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("lisa.txt");
		med.doTransfer();
		med.setSelectedTransfer(index);
		med.startSelectedTransfer();
		med.pauseSelectedTransfer();
		med.resumeSelectedTransfer();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DiffFiles diff = new DiffFiles("downloads/bart/lisa.txt",
				"downloads/lisa/lisa.txt");
		assertTrue("downloaded file lisa.txt is identical to original",
				diff.compareFiles());
	}

}
