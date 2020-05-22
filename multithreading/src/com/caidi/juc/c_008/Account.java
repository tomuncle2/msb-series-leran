/**
 * 面试题：模拟银行账户
 * 对业务写方法加锁
 * 对业务读方法不加锁
 * 这样行不行？
 *
 * 容易产生脏读问题（dirtyRead）
 * 加不加锁看实际业务允不允许脏读
 */

package com.caidi.juc.c_008;

import java.util.concurrent.TimeUnit;


public class Account {
	private String name = "张三";
	private int money = 1000;

	/**
	 * 存钱
	 * @date 15:45 2020/5/22
	 * @param money
	 * @return void
	 */
	public synchronized void setMoney(Integer money) {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.money = money;
		System.out.println(" 存取 " + this.getName() + " : " + money);
	}

	/**
	 * 查看余额
	 * @date 15:46 2020/5/22
	 * @param
	 * @return int
	 */
	public /*synchronized*/ void readMoney() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" 查看余额 " + this.getName() + " : " + money);
	}

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}

	public static void main(String[] args) {
		Account account = new Account();
		new Thread(()->{
			account.setMoney(account.getMoney() + 500);
		}).start();
		new Thread(()->{
			account.readMoney();
		}).start();
	}

}
