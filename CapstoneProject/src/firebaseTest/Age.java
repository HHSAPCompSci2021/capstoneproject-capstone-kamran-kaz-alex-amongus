package firebaseTest;

import java.util.ArrayList;

public class Age {

	private int age;
	private ArrayList<ArrayList<String>> years;
	
	public Age() {
		age = 0;
		years = new ArrayList<ArrayList<String>>();
	}
	
	public Age(int age) {
		this.age = age;
		years = new ArrayList<ArrayList<String>>();
	}
	
	public void add(ArrayList<String> year) {
		years.add(year);
	}
	
	public int getAge() {
		return age;
	}
	
	public ArrayList<ArrayList<String>> getYears() {
		return years;
	}
	
	@Override
	public String toString() {
		return "age: " + age + "years: " + years.toString();
	}
}
