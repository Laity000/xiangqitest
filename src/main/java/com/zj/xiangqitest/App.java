package com.zj.xiangqitest;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
		
		System.out.println("\n------------------享骑退押金一键提交表单助手V1.0.1-----------------");
		System.out.println("                  作者：https://github.com/Laity000");
		System.out.println("                       个人开发，仅供测试");
		System.out.println("                       享骑垃圾，积极维权 ");
		//System.out.println("-------------------------------------------------------------------");
		System.out.println("\n---------------------测试Chrome谷歌浏览器---------------------------");
		
		if (args.length != 4) {
			System.out.println("输入的参数错误! (arg.length=" + args.length + ")");
			return;
		}
		
		//测试
		test(args);
		
		//09:00:00开始登记
		showTimer(args);
		//09:00:01再次开始登记	
		
		System.out.println("-----------------------------请耐心等待-----------------------------");
	}

	
	/**
	 * 任务自动登记
	 */
	public static void commit(String[] args) {
		
		
		System.setProperty("webdriver.chrome.driver", path);

		ChromeOptions options = new ChromeOptions();
		// 设置chrome的user-agent，模拟微信浏览器
		
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.0.54_r849063.501 NetType/WIFI");
		WebDriver driver = new ChromeDriver(options);

		//driver.get("http://gis1z4xshb2s37ki.mikecrm.com/dcSGLtc");
		driver.get(args[3]);
		
		List<WebElement> inputs = driver.findElements(By.tagName("input"));
		WebElement name = inputs.get(0);
		WebElement call = inputs.get(1);
		
		//WebElement name = driver.findElement(By.xpath("//*[@id=\"202474905\"]/div[2]/div/div/input"));
		//WebElement call = driver.findElement(By.xpath("//*[@id=\"202474906\"]/div[2]/div/div/input"));
		WebElement button = driver.findElement(By.id("form_submit"));
		
		//需要修改的姓名
		name.sendKeys(args[0]);
		//需要自己的手机号
		call.sendKeys(args[1]);
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
			System.out.println("[定时器任务] 登记失败！");
			
		}else {
			System.out.println("[定时器任务] " + result);
		}
		
		//driver.close();
		
		
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
	public static void showTimer(final String[] args) {
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {				
				System.out.println(new Date() + "[定时器任务]开始执行...");
				commit(args);
			}
		};

		// 设置执行时间
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 每天
		// 定制每天的执行时间
		String[] time = args[2].split(":");
		calendar.set(year, month, day, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		Date date = calendar.getTime();

		
		// 如果第一次执行定时任务的时间 小于 当前的时间
		if (date.before(new Date())) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
		}
		System.out.println("\n开启[定时器]:" + date);

		Timer timer = new Timer();
		int period = 24* 60* 60 * 1000;
		// 每天的date时刻执行task，隔天执行
		timer.schedule(task, date, period);
		// 每天的date时刻执行task, 仅执行一次
		// timer.schedule(task, date);

	}
	
	public static void test(String[] args) {
		System.setProperty("webdriver.chrome.driver", path);

		ChromeOptions options = new ChromeOptions();
		// 设置chrome的user-agent，模拟微信浏览器
		options.addArguments(
				"--user-agent=Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.0.54_r849063.501 NetType/WIFI");
		WebDriver driver = new ChromeDriver(options);

		driver.get(args[3]);
		driver.close();
		
		System.out.println("------------------------------测试成功-------------------------------");
	}	

}
