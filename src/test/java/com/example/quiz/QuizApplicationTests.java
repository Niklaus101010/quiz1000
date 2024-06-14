package com.example.quiz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuizApplicationTests {

	@Test
	public void test() {
		List<String> list = List.of("A", "B", "C", "D", "E");
		String str = "AABBBCDDAEEEAACCDDD";
		// 計算 A, B, C, D, E 出現幾次
		for (String s : list) {
			int count = 0;
			while (str.contains(s)) {
				count++;
				str = str.replaceFirst(s, "");
			}
			System.out.println(s + " 出現 " + count + " 次");
		}
	}

	@Test
	public void test2() {
		List<String> list = List.of("A", "B", "C", "D", "E");
		String str = "AABBBCDDAEEEAACCDDD";
		// 計算 A, B, C, D, E 出現幾次
		Map<String, Integer> map = new HashMap<>();
		for (String item : list) {
			String newStr = str.replace(item, "");

			int count = str.length() - newStr.length();
			map.put(item, count);
		}
		System.out.println(map);

	}
	
	@Test
	public void test3() {
		String str = "AABBBCDDAEEEAACCDDD";
		String[] arr = str.split("");
		System.out.println(arr);
	}
	
	@Test
	public void test4() {
		String str = "AABBBCDEAACCDDD";
		String[] arr = str.split("A");
		System.out.println(arr);
	}
}
