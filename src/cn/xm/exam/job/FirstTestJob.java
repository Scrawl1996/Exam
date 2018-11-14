package cn.xm.exam.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class FirstTestJob {
	private static final Logger log = LoggerFactory.getLogger(FirstTestJob.class);
	private static int count;

	public void cron() {
		log.info("spring task execute times {}", count++);
	}
}
