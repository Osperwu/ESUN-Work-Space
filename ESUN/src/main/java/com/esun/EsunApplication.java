package com.esun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EsunApplication {

	public static void main(String[] args) {
		System.out.println("test");
		CheckEnvironment();

		SpringApplication.run(EsunApplication.class, args);
	}

		public static void CheckEnvironment() {
			// 檢查 Java 運行時的基本資訊
			String javaVersion = System.getProperty("java.version");
			String javaVendor = System.getProperty("java.vendor");
			String javaHome = System.getProperty("java.home");

			System.out.println("Java Version: " + javaVersion);
			System.out.println("Java Vendor: " + javaVendor);
			System.out.println("Java Home: " + javaHome);

			// 檢查是否是通過 JVM 運行
			if (javaVersion != null && javaHome != null) {
				System.out.println("This application is running in a Java environment.");
			} else {
				System.out.println("This application is not running in a Java environment.");
			}
		}
}
