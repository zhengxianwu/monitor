package com.monitor.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.monitor.monitor.been.Test;

public class search {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Test> list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			list.add(new Test(i, "a " + i));
		}
        List<Test> t = list.stream().filter(a -> a.getId() == 2).collect(Collectors.toList());
        Test test = t.get(0);
        System.out.println(test);
	}

}
