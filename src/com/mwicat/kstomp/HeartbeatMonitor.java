package com.mwicat.kstomp;

public class HeartbeatMonitor extends Thread {
	
	private StompSession session;
	private boolean running;
	private int lastReadCount;
	private int timeoutInterval;

	public HeartbeatMonitor(StompSession sesssion, int timeoutInterval) {
		super();
		this.session = sesssion;
		this.timeoutInterval = timeoutInterval;
	}
	
	public void run() {
		try {
			running = true;
			checkAliveLoop();
		} catch (InterruptedException e) {
		} finally {
			running = false;
		}
	}
	
	private void checkAliveLoop() throws InterruptedException {
		boolean dead = false;
		while (running && !dead) {
			Thread.sleep(timeoutInterval);
			//#debug info
			System.out.println("Checking session");
			int currReadCount = session.getReadCount();
			dead = currReadCount == lastReadCount;
			lastReadCount = currReadCount;
		}
		if (dead) {
			//#debug info
			System.out.println("Session dead - disconnecting");
			session.disconnect();
		}
	}
	
	public void shutdown() {
		running = false;
		interrupt();
	}

}
