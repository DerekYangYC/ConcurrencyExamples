package lmax.disruptor.consumers;

import com.lmax.disruptor.EventFactory;

public class Message {
    private Integer msg;

    public void setMsg(Integer msg) {
	this.msg = msg;
    }

    public Integer getMsg() {
	return msg;
    }

    public final static EventFactory<Message> EVENT_FACTORY = new EventFactory<Message>() {
	
	public Message newInstance() {
	    return new Message();
	}
    };

}