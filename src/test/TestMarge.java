package test;

import conf.Configure;
import core.Mediator;
import junit.framework.TestCase;

public class TestMarge extends TestCase {

	private Mediator med;
	private Configure cfg;
	private int index = 0;

	public void setUp() {
		med = new Mediator();
		cfg = new Configure("marge", med, 6501);
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
		DiffFiles diff = new DiffFiles("downloads/marge/curs1.pdf",
				"downloads/bart/curs1.pdf");
		assertTrue("downloaded file curs1.pdf is identical to original",
				diff.compareFiles());
	}

	public void testStartStop() {
		++index;
		med.setFromValue("homer");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("C2.pdf");
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
		DiffFiles diff = new DiffFiles("downloads/homer/C2.pdf",
				"downloads/marge/C2.pdf");
		assertTrue("downloaded file curs1.pdf is identical to original",
				diff.compareFiles());
	}

	public void testPauseResume() {
		++index;
		med.setFromValue("lisa");
		med.setToValue(med.getCurrentUser());
		med.setFileValue("smp_5_CMP1.ppt");
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
		DiffFiles diff = new DiffFiles("downloads/marge/smp_5_CMP1.ppt",
				"downloads/lisa/smp_5_CMP1.ppt");
		assertTrue("downloaded file smp_5_CMP1.ppt is identical to original",
				diff.compareFiles());
	}

}
