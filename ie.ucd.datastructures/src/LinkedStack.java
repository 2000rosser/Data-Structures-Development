public class LinkedStack<E> implements Stack<E> {

	private SinglyLinkedList<E> list = new SinglyLinkedList<E>();

	public static void main(String[] args) {
		LinkedStack<Integer> S = new LinkedStack<Integer>();
		System.out.println(S.isEmpty());
		S.push(3);
		System.out.println(S);
		System.out.println(S.isEmpty());
		S.push(2);
		S.push(1);
		System.out.println(S);
		System.out.println(S.size());
		System.out.println(S.pop());
		System.out.println(S);
		S.pop();
		System.out.println(S);
		System.out.println(S.top());
		S.pop();
		System.out.println(S.isEmpty());
		System.out.println(S.size());
	}

	public LinkedStack(){
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void push(E e) {
		list.addFirst(e);

	}

	@Override
	public E top() {
		return list.first();
	}

	@Override
	public E pop() {
		return list.removeFirst();
	}

	public String toString() {
		return list.toString();
	}

}