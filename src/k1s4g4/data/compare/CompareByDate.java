package k1s4g4.data.compare;

import java.util.Comparator;

import k1s4g4.data.messages.Message;


public class CompareByDate implements Comparator<Message>{
	@Override
	public int compare(Message arg0, Message arg1) {
		return -arg0.getDateTime().compareTo(arg1.getDateTime());
	}
}
