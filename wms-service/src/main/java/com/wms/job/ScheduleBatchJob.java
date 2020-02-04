package com.wms.job;

import com.wms.batch.BatchReadListener;
import com.wms.service.sfExpress.sfXmlParse.ShunFengResponse;
import com.wms.service.sfExpress.sfXmlParse.XmlHelper;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

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

	public static void main(String[] args) {

		String requestXml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>OK</Head><Body><OrderResponse filter_result=\"2\" destcode=\"010\" mailno=\"SF1014921965943\" return_tracking_no=\"SF1060326759139\" origincode=\"021\" orderid=\"SO201912130063\"><rls_info rls_errormsg=\"SF1014921965943:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1014921965943\" sourceCityCode=\"021\" destCityCode=\"010\" destDeptCode=\"010HDAL010\" destTeamCode=\"010\" destTransferCode=\"010W\" destRouteLabel=\"010W-010HDAL010\" proName=\"顺丰特惠\" cargoTypeCode=\"T6\" limitTypeCode=\"T6\" expressTypeCode=\"B1\" codingMappingOut=\"3A\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'010W','k2':'010HDAL010','k3':'010','k4':'T6','k5':'SF1014921965943','k6':'','k7':'7f9f1af0'}\" proCode=\"T6\" printIcon=\"00001000\" checkCode=\"7f9f1af0\" destGisDeptCode=\"010HDAL\"/></rls_info><rls_info rls_errormsg=\"SF1060326759139:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1060326759139\" sourceCityCode=\"010\" destCityCode=\"021\" destDeptCode=\"021NA\" destTeamCode=\"141\" destTransferCode=\"021WG\" destRouteLabel=\"021WG-021NA\" proName=\"顺丰特惠\" cargoTypeCode=\"T6\" limitTypeCode=\"T6\" expressTypeCode=\"B1\" codingMapping=\"5NA\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'021WG','k2':'021NA','k3':'141','k4':'T6','k5':'SF1060326759139','k6':'','k7':'b467a88'}\" proCode=\"T6\" printIcon=\"00001000\" checkCode=\"b467a88\" destGisDeptCode=\"021NA\"/></rls_info></OrderResponse></Body></Response>";
//		String requestXml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>OK</Head><Body><OrderResponse filter_result=\"2\" destcode=\"020\" mailno=\"SF1014922990284\" return_tracking_no=\"SF1060326804974\" origincode=\"021\" orderid=\"SO201912130081\"><rls_info rls_errormsg=\"SF1014922990284:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1014922990284\" sourceCityCode=\"021\" destCityCode=\"020\" destDeptCode=\"020JG\" destDeptCodeMapping=\"020W\" destTeamCode=\"037\" destTransferCode=\"020W\" destRouteLabel=\"020W-020JG\" proName=\"顺丰特惠\" cargoTypeCode=\"T6\" limitTypeCode=\"T6\" expressTypeCode=\"B1\" codingMapping=\"F4\" codingMappingOut=\"3A\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'020W','k2':'020JG','k3':'037','k4':'T6','k5':'SF1014922990284','k6':'','k7':'c37a66d3'}\" proCode=\"T6\" printIcon=\"00001000\" checkCode=\"c37a66d3\" destGisDeptCode=\"020JG\"/></rls_info><rls_info rls_errormsg=\"SF1060326804974:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1060326804974\" sourceCityCode=\"020\" destCityCode=\"021\" destDeptCode=\"021NA\" destTeamCode=\"141\" destTransferCode=\"021WG\" destRouteLabel=\"021WG-021NA\" proName=\"顺丰特惠\" cargoTypeCode=\"T6\" limitTypeCode=\"T6\" expressTypeCode=\"B1\" codingMapping=\"5NA\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'021WG','k2':'021NA','k3':'141','k4':'T6','k5':'SF1060326804974','k6':'','k7':'36e17e1c'}\" proCode=\"T6\" printIcon=\"00001000\" checkCode=\"36e17e1c\" destGisDeptCode=\"021NA\"/></rls_info></OrderResponse></Body></Response>";
		//响应报文
//		String callRequestXml = CallExpressServiceTools.callSfExpressServiceByCSIM(requestXml);
		//解析响应报文
		ShunFengResponse shunFengResponse = XmlHelper.xmlToBeanForSF(requestXml);
		System.out.println(shunFengResponse.getOrderResponse());
	}
}
