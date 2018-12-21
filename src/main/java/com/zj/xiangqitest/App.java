package com.zj.xiangqitest;


import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class App 
{
	
	static String path = "resources\\chromedriver.exe";
	public static void main(String[] args) throws InterruptedException {
		//09:00:00开始登记
		showTimer(9, 0, 0, 1);
		//09:00:01再次开始登记
		showTimer(9, 0, 1, 2);
		//showTimer(9, 0, 2, 3);
		
	}

	
	
	/**
	 * 任务自动登记
	 */
	public static void task(int count) {
		
		
		System.setProperty("webdriver.chrome.driver", path);

		ChromeOptions options = new ChromeOptions();
		// 设置chrome的user-agent，模拟微信浏览器
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.0.54_r849063.501 NetType/WIFI");
		WebDriver driver = new ChromeDriver(options);

		driver.get("http://gis1z4xshb2s37ki.mikecrm.com/dcSGLtc");
		
		WebElement name = driver.findElement(By.xpath("//*[@id=\"202474905\"]/div[2]/div/div/input"));
		WebElement call = driver.findElement(By.xpath("//*[@id=\"202474906\"]/div[2]/div/div/input"));
		WebElement button = driver.findElement(By.id("form_submit"));
		
		//需要修改的姓名
		name.sendKeys("张三");
		//需要自己的手机号
		call.sendKeys("18300000000");
		button.click();
		
		//检查结果
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = driver.findElement(By.xpath("/html/body/form/div/div[1]/div[2]/div/div[2]/div[1]")).getText();
		if (result == null) {
			System.out.println("[任务" + count + "] 登记失败！");
			
		}else {
			System.out.println("[任务" + count + "] " + result);
		}
		
		driver.close();
		
		
		/*driver.get("http://www.baidu.com");
		WebElement searchBox = driver.findElement(By.id("index-kw"));

		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();

		List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"page-hd\"]/div/div[1]/a/img"));

		for (WebElement e : webElements) {
			System.out.println(e.getAttribute("src"));
		}

		driver.close();*/
	}
	
	
	/**
	 * 计数器 
	 * @param hours 小时
	 * @param minutes 分钟
	 * @param seconds 秒
	 * @param count 任务数
	 */
	public static void showTimer(int hours, int minutes, int seconds, final int count) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				System.out.println("时间=" + new Date() + " 开始执行[任务" + count + "]..."); // 1次
				task(count);
			}
		};

		// 设置执行时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 每天
		// 定制每天的执行时间
		calendar.set(year, month, day, hours,minutes, seconds);
		Date date = calendar.getTime();

		
		// 如果第一次执行定时任务的时间 小于 当前的时间
		if (date.before(new Date())) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
		}
		System.out.println("----------开启[定时器" + count + "]:" + date + "-------------");

		Timer timer = new Timer();
		int period = 24* 60* 60 * 1000;
		// 每天的date时刻执行task，隔天执行
		timer.schedule(task, date, period);
		// 每天的date时刻执行task, 仅执行一次
		// timer.schedule(task, date);

	}
	


}
