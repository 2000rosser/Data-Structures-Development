import java.util.Arrays;

public class ArrayQueue<E> implements Queue<E> {

	private static final int CAPACITY = 1000;//default array capacity
	private E[] data;
	private int f = 0;
	private int sz = 0;

	public ArrayQueue() {
		this(CAPACITY);
	}

	public ArrayQueue(int capacity){
		data = (E[]) new Object[capacity];
	}

	public static void main(String[] args) {
		ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
		arrayQueue.enqueue(1);
		arrayQueue.enqueue(2);
		arrayQueue.enqueue(3);
		arrayQueue.enqueue(-4);
		System.out.println(arrayQueue);

		System.out.println("First: " + arrayQueue.first());

		arrayQueue.dequeue();
		System.out.println(arrayQueue);
		arrayQueue.dequeue();
		System.out.println(arrayQueue);
		System.out.println("First: " + arrayQueue.first());

	}

	@Override
	public int size() {
		return sz;
	}

	@Override
	public boolean isEmpty() {
		return sz==0;
	}


	@Override	//adds element at end of queue
	public void enqueue(E e) throws IllegalStateException {
		if(sz== data.length) throw new IllegalStateException("Full queue");
		int avail = (f + sz)%data.length;
		data[avail] = e;
		sz++;
	}

	@Override
	public E first() {
		if (isEmpty()) return null;
		return data[f];
	}

	@Override
	public E dequeue() {
		if(isEmpty()) return null;
		E answer = data[f];
		data[f] = null;
		f= (f+1)%data.length;
		sz--;
		return answer;
	}

	@Override
	public String toString() {
		return "ArrayQueue{" +
				"data=" + Arrays.toString(data) +
				", frontIndex=" + f +
				", numberOfElements=" + sz +
				'}';
	}

}
