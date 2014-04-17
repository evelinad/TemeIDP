package test;

import conf.Configure;
import core.Mediator;
import junit.framework.TestCase;

public class TestLisa extends TestCase {

	private Mediator med;
	private Configure cfg;
	private int index = 0;

	public void setUp() {
		med = new Mediator();
		cfg = new Configure("lisa", med, 6501);
		cfg.setUpUsers();
	}

	public void testFullTransfer() {
		med.setFromValue("bart");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("curs1.pdf");
		med.doTransfer();
		med.setSelectedTransfer(index);
		med.startSelectedTransfer();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DiffFiles diff = new DiffFiles("downloads/lisa/curs1.pdf",
				"downloads/bart/curs1.pdf");
		assertTrue("downloaded file curs1.pdf is identical to original",
				diff.compareFiles());
	}

	public void testStartStop() {
		++index;
		med.setFromValue("marge");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("l04_2013.pdf");
		med.doTransfer();
		med.setSelectedTransfer(index);
		med.startSelectedTransfer();
		med.stopSelectedTransfer();
		med.startSelectedTransfer();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DiffFiles diff = new DiffFiles("downloads/lisa/l04_2013.pdf",
				"downloads/marge/l04_2013.pdf");
		assertTrue("downloaded file l04_2013.pdf is identical to original",
				diff.compareFiles());
	}

	public void testPauseResume() {
		++index;
		med.setFromValue("homer");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("C2.pdf");
		med.doTransfer();
		med.setSelectedTransfer(index);
		med.startSelectedTransfer();
		med.pauseSelectedTransfer();
		med.resumeSelectedTransfer();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DiffFiles diff = new DiffFiles("downloads/homer/C2.pdf",
				"downloads/lisa/C2.pdf");
		assertTrue("downloaded file C2.pdf is identical to original",
				diff.compareFiles());
	}

}
