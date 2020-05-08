package com.tt.concurrent.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ImmutableCollectionsExample {
	private List emptyList = Collections.EMPTY_LIST;
	private List<Integer> unmodifiableList;
	private BlockingQueue<String> blockingQueue;
	private Map<String, String> concurrentMap;
	
	public ImmutableCollectionsExample() {
		super();
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(1);
		ids.add(2);
		ids.add(3);
		this.unmodifiableList = Collections.unmodifiableList(ids);
		this.blockingQueue = new LinkedBlockingQueue<String>(3);
		try {
			blockingQueue.put("Test");
			blockingQueue.put("Test1");
			blockingQueue.put("Test2");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.concurrentMap = new ConcurrentHashMap<String, String>();
		concurrentMap.put("key", "value");
	}
	
	public List getEmptyList() {
		return emptyList;
	}

	public List<Integer> getUnmodifiableList() {
		return unmodifiableList;
	}

	public BlockingQueue<String> getBlockingQueue() {
		return blockingQueue;
	}

	public Map<String, String> getConcurrentMap() {
		return concurrentMap;
	}

	public static void main(String[] args) {
		final ImmutableCollectionsExample ice = new ImmutableCollectionsExample();
		// Dodawanie do pustej listy
		try {
			ice.getEmptyList().add("Test");		
		} catch (UnsupportedOperationException uoe) {
			System.err.print("Unsupported Operation!\n");
		}
		// Dodawanie do niemodyfikowalnej listy
		try {
			ice.getUnmodifiableList().add(4);
		} catch (UnsupportedOperationException uoe) {
			System.err.print("Unsupported Operation!\n");
		}
		// Dodawanie do pełnej kolejki
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ice.getBlockingQueue().put("Test4");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
				System.out.println("Dodano do kolejki");
			}
		});
	}
	
}
