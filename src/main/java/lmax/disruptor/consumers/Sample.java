package lmax.disruptor.consumers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Sample {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {

	ExecutorService exec = Executors.newCachedThreadPool();

	Disruptor<Message> disruptorService = new Disruptor<Message>(
		Message.EVENT_FACTORY, 2048, exec, ProducerType.SINGLE,
		new BusySpinWaitStrategy());

	final long startTime = System.nanoTime();

	final EventHandler<Message> handler = new EventHandler<Message>() {

	    public void onEvent(Message event, long seq, boolean endOfBatch)
		    throws Exception {

		Integer value = event.getMsg();
		if (value % 10000 == 0) {
		    System.out.println("By Handler : ValueEvent: " + value
			    + " Sequence: " + seq);
		    double timeINnanos = (System.nanoTime() - startTime);
		    double timetaken = (timeINnanos / 1e9);
		    System.out.println("Time Taken till now in sec "
			    + timetaken);
		}
	    }
	};

	final EventHandler<Message> handler2 = new EventHandler<Message>() {

	    public void onEvent(Message event, long seq, boolean endOfBatch)
		    throws Exception {

		Integer value = event.getMsg();
		if (value % 10000 == 0) {
		    System.out.println("By Handler2 :ValueEvent: " + value
			    + " Sequence: " + seq);
		    double timeINnanos = (System.nanoTime() - startTime);
		    double timetaken = (timeINnanos / 1e9);
		    System.out.println("Time Taken till now in sec "
			    + timetaken);
		}
	    }
	};

	disruptorService.handleEventsWith(handler, handler2);

	RingBuffer<Message> ringBuffer = disruptorService.start();

	Producer producer = new Producer(ringBuffer);

	Thread p = new Thread(producer);
	p.start();

	try {
	    p.join();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
