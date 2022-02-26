package main.java.reader;

public interface CSV<T> {

	public T from(String[] fields);
}
