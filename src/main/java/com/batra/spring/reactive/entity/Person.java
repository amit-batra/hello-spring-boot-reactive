package com.batra.spring.reactive.entity;

import java.util.StringJoiner;

public class Person {

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person() {
		this(null, 0);
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		if (age != person.age) return false;
		return name != null ? name.equals(person.name) : person.name == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + age;
		return result;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
			.add("name='" + name + "'")
			.add("age=" + age)
			.toString();
	}
}