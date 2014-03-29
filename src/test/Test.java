package test;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingWorker;

import users.User;
import core.Mediator;
/**
 * 
 * Contains a file list, and 2 user lists (online and offline).
 * Once every 20 seconds connects, disconnects or updates a user's file list.
 *
 */
public class Test extends SwingWorker<Integer, Integer> {
	
	private Mediator med;
	Random rand = new Random();
	
	private final int ADD_USER = 0;
	private final int RM_USER = 1;
	private final int UPDATE_FILELIST = 2;
	private final int ADD_FILE = 0;
	private final int RM_FILE = 1;
	/*private final int SELECT_FILE = 4;
	private final int SELECT_USER = 5;
	private final int ADD_TRANSFER = 6;
	private final int START_TRANSFER = 7;
	private final int STOP_TRANSFER = 8;
	private final int PAUSE_TRANSFER = 9;
	private final int RESUME_TRANSFER = 10;*/
	
	ArrayList<TestUsers> offline_users = new ArrayList<TestUsers>();
	ArrayList<TestUsers> online_users = new ArrayList<TestUsers>();
	ArrayList<String> fl = new ArrayList<>();
	int index;
	TestUsers user;
	
	public Test(Mediator med)
	{
		this.med = med;
		

		fl.add("Doombringer");
		fl.add("Foehammer");
		fl.add("Frost Blade");
		fl.add("Frost Axe");
		fl.add("The Rune Staff Stormcaller");
		fl.add("Morkai");
		fl.add("Spear of Russ");
		fl.add("Teeth of the Blizzard");
		fl.add("Rune Staff");
		fl.add("Anvil Shield");
		fl.add("Wolf Helm of Russ");
		fl.add("Great Wolf Pelt");
		fl.add("The Hood of Gnyrll");
		fl.add("Frostfang");
		fl.add("The Pelt of Wulfen");
		fl.add("The Pelt of Wulfen");
		fl.add("Raiment of the Silent Wolf");
		fl.add("Runic Armour");
		fl.add("Fenris-Pattern Wolf Helm");
		fl.add("Cup of Wulfen");
		fl.add("Fang of Morkai");
		fl.add("Wolf Amulet");
		fl.add("Wolf Tooth Necklace");
		
		TestUsers user = new TestUsers("Leman Russ");
		offline_users.add(user);
		user = new TestUsers("Bjorn the Fell-Handed");
		offline_users.add(user);
		user = new TestUsers("Logan Grimnar");
		offline_users.add(user);
		user = new TestUsers("Ragnar Blackmane");
		offline_users.add(user);
		user = new TestUsers("Sven Bloodhowl");
		offline_users.add(user);
		user = new TestUsers("Vaer Greyloc");
		offline_users.add(user);
	}

	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < offline_users.size(); i++) {
			int size = rand.nextInt(4) + 1;
			for (int j = 0; j < size; j++) {
				offline_users.get(i).add(fl.get(rand.nextInt(fl.size() -1)));
			}
		}
		
		index = rand.nextInt(offline_users.size() - 1 );
		user = offline_users.get(index);
		online_users.add(user);
		offline_users.remove(index);
		//TODO: add med function
		User u = new User(user.name());
		for (String str : user.toArray()) {
			u.insertFile(str);
		}
		med.addUserToModel(u);
		
		while(true)
		{
			switch (rand.nextInt(5)) {
			case ADD_USER:
				index = rand.nextInt(offline_users.size() - 1 );
				user = offline_users.get(index);
				online_users.add(user);
				offline_users.remove(index);
				//TODO: add med function
				u = new User(user.name());
				for (String str : user.toArray()) {
					u.insertFile(str);
				}
				med.addUserToModel(u);
				break;
				
			case RM_USER:
				index = rand.nextInt(online_users.size() - 1 );
				user = online_users.get(index);
				offline_users.add(user);
				online_users.remove(index);
				//TODO: add med function
				med.removeUserFromModel(user.name());
				break;
				
			case UPDATE_FILELIST:
				int size = rand.nextInt(2) + 1;
				index = rand.nextInt(online_users.size() -1);
				switch (rand.nextInt(1)) {
				case ADD_FILE:
					for (int i = 0; i < size; i++) {
						online_users.get(index).add(fl.get(rand.nextInt(fl.size() - 1)));
					}
					break;
					
				case RM_FILE:
					for (int i = 0; i < size; i++) {
						online_users.get(index).rm(rand.nextInt(online_users.get(index).size() - 1));
					}
					break;
				default:
					break;
				}
				//TODO: add med function
				med.addFilesToUser(online_users.get(index).name(), online_users.get(index).toArray());
				break;
				
			default:
				break;
			}
			
			Thread.sleep(20000);
		}
		
	}

}
