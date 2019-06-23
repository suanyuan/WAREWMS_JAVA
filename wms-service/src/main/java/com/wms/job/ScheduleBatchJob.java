package com.wms.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wms.batch.BatchReadListener;

@Component
public class ScheduleBatchJob {
	private static final Logger logger = Logger.getLogger(BatchReadListener.class);
//	@Autowired
//	private JobLauncher jobLauncher;
	
//	@Autowired
//	private Job batch001;
	
//	@Scheduled(cron = "*/1 * * * * ?")
	@Scheduled(fixedRate = 10000)
	private void exportData() throws Exception {
		System.out.println(new Date());
//		A job instance already exists and is complete for parameters={}. If you want to run this job again, change the parameters.
//		JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
//		JobExecution execution = jobLauncher.run(batch001, jobParameters);
//		logger.info(execution.getStartTime());
	}
}
