import java.util.ArrayList;
import java.util.HashMap;

import org.apache.mina.core.session.IoSession;

public class CliLocator {
	
	private static HashMap<Integer, ArrayList<IoSession>> cliLocation;
	
	public CliLocator() {
		cliLocation = new HashMap<Integer, ArrayList<IoSession>>();
	}
	
	public void addCli(IoSession session, String s) {
		String[] sa = s.split(" ");
		ArrayList<IoSession> tempList = new ArrayList<IoSession>();
		
		for (int i=0; i<sa.length; i++) {
			int key = Integer.parseInt(sa[i]);
			tempList.clear();
/*			
			System.out.print(key + "-->");
			try {
				System.out.print(cliLocation.containsKey(key));
			}
			catch (Exception E) {
				System.out.println(E.toString());
			}
*/			
			if(cliLocation.containsKey(key)) {
				if(cliLocation.get(key) != null)
					tempList = cliLocation.get(key);
			} else {
				tempList.add(session);
				cliLocation.put(key, tempList);
				continue;
			}
			tempList.add(session);
			cliLocation.put(key, tempList);
		}
	}
	
	public ArrayList<IoSession> getCli(int receiver) {
		
		//System.out.println("Getting=====>" + receiver);
		try{
			if (cliLocation.containsKey(receiver)) {
				//System.out.println("true");
				return cliLocation.get(receiver);
			}
			else {
				//System.out.println("false");
				return null;
			}
		}
		catch (Exception E) {
			System.out.println(E.toString());
			return null;
		}
	}
	
	public void delCli(IoSession session){
		ArrayList<IoSession> tempList = new ArrayList<IoSession>();
		
		for(Integer inte : cliLocation.keySet()) {
			tempList = cliLocation.get(inte);
			if (tempList.contains(session)) {
				tempList.remove(session);
			}
		}
	}
	
} 
