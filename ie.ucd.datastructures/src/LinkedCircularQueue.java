/**
 * Realization of a circular FIFO queue as an adaptation of a
 * CircularlyLinkedList. This provides one additional method not part of the
 * general Queue interface. A call to rotate() is a more efficient simulation of
 * the combination enqueue(dequeue()). All operations are performed in constant
 * time.
 */

public class LinkedCircularQueue<E> implements Queue<E> {

	public static void main(String[] args) {

		LinkedCircularQueue<Integer> queue = new LinkedCircularQueue<>();
		queue.enqueue(5);
		queue.enqueue(2);
		queue.enqueue(3);
		System.out.println(queue);
		System.out.println("Size- " + queue.size());
		System.out.println("First element- " + queue.first());
		queue.dequeue();
		System.out.println(queue);
	}

	private CircularlyLinkedList<E> list = new CircularlyLinkedList<>();

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Enqueues a given element, adding it to the end of the queue.
	 * @param e the element to be inserted
	 */
	@Override
	public void enqueue(E e) {
		list.addLast(e);
	}

	/**
	 * Returns the element at the front of the queue.
	 * @return the element at the front of the queue.
	 */
	@Override
	public E first() {
		return list.first();
	}

	/**
	 * Returns the element at the front of the queue.
	 * @return the element at the front of the queue.
	 */
	@Override
	public E dequeue() {
		return list.removeFirst();
	}


	public String toString(){
		return list.toString();
	}

}
