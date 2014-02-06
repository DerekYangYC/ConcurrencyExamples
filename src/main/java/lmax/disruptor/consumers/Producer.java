package lmax.disruptor.consumers;

import com.lmax.disruptor.RingBuffer;

public class Producer implements Runnable {

    public static Integer maxMsg = 1000000;
    public static int multiply = 10;
    private RingBuffer<Message> rb;

    public Producer(RingBuffer<Message> rb) {
	this.rb = rb;
    }

    public void run() {

	for (int i = 0; i < maxMsg * multiply; i++) {

	    long seq = rb.next();
	    Message msg = rb.get(seq);
	    msg.setMsg(i);

	    rb.publish(seq);

	}

	System.out.println("done sending " + maxMsg * multiply + " messages");

    }

}
