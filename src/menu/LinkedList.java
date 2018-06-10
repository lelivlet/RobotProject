package menu;

public class LinkedList {

	private String value;
	private LinkedList previousElement = null;
	private LinkedList nextElement = null;

	public LinkedList() {
		this(null);
	}

	public LinkedList(String value) {
		this.value = value;
	}

	public void add(String value) {
		if (this.value == null) {
			this.value = value;
		} else if (this.nextElement == null) {
			this.nextElement = new LinkedList(value);
		} else {
			nextElement.add(value);
		}
	}

	public boolean contain(String value) {
		if (this.value.equals(value)) {
			return true;
		} else if (this.nextElement == null) {
			return false;
		}
		return nextElement.contain(value);
	}

	public String get(int index) {
		if (index == 0) {
			return value;
		} else {
			return nextElement.get(index - 1);
		}
	}

	public LinkedList getNext() {
		return nextElement;
	}

	public LinkedList getPrevious() {
		return previousElement;
	}

	public String remove(int index) {
		if (index == 0) {
			String deletedValue = value;
			if (nextElement == null) {
				this.value = null;
			} else {
				this.value = nextElement.get(0);
				nextElement = nextElement.getNext();
			}
			return deletedValue;
		} else {
			return nextElement.remove(index - 1);
		}
	}

	public void set(int index, String value) {
		if (index == 0) {
			set(value);
		} else {
			nextElement.set(index - 1, value);
		}
	}

	public void set(String value) {
		this.value = value;
	}

	public int size() {
		if (value == null) {
			return 0;
		} else if (nextElement == null) {
			return 1;
		} else {
			return 1 + nextElement.size();
		}
	}

}
