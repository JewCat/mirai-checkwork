package mirai.checkwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class App {
	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Ho_Chi_Minh")));
		System.out.println(LocalDate.now());
		System.out.println(Time.valueOf(LocalTime.now()));
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
