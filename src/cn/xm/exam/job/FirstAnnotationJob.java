package cn.xm.exam.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FirstAnnotationJob {
	private static final Logger log = LoggerFactory.getLogger(FirstAnnotationJob.class);
	private static int count;

	// @Scheduled(fixedRate = 10000)
	public void cron() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error("InterruptedException ", e);
		}
		log.info("spring anno task execute times {}", count++);
	}
}
