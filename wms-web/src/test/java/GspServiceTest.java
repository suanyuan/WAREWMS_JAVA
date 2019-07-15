import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.CommonMybatisDao;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.service.CommonService;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.service.GspOperateDetailService;
import com.wms.tools.FieldUtil;
import com.wms.utils.BeanUtils;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.form.GspBusinessLicenseForm;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/applicationContext.xml",
        "classpath*:/conf/spring-datasource.xml",
        "classpath*:/conf/spring-mybatis.xml"

})
public class GspServiceTest {

    @Autowired
    private GspEnterpriseInfoService gspEnterpriseInfoService;
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private CommonMybatisDao commonMybatisDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private GspOperateDetailService gspOperateDetailService;

    @Test
    public void test(){
        try{
            GspEnterpriseInfo gspEnterpriseInfo = new GspEnterpriseInfo();
            gspEnterpriseInfo.setContacts("123");

            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(gspEnterpriseInfo);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    System.out.println(name+":"+ propertyUtilsBean.getNestedProperty(gspEnterpriseInfo, name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void beanTest() throws Exception{
        GspBusinessLicenseForm gspBusinessLicenseForm = new GspBusinessLicenseForm();
        gspBusinessLicenseForm.setEnterpriseId("asdas");
        System.out.println(BeanUtils.isEmptyFrom(gspBusinessLicenseForm));
    }

    @Test
    public void testSec(){
       /*Map<String,Object> map = new HashMap<>();
        map.put("warehouseid","WH01");
        map.put("no", Constant.APLCUSNO);
        map.put("resultNo","");
        map.put("resultCode","");
       //String str = docAsnHeaderMybatisDao.getIdSequence(map);
        //commonMybatisDao.getIdSequence(map);
       System.out.println(commonService.generateSeq(Constant.APLCUSNO,"WH01"));
*/
        //String s = "edbf5a04a7a743f1bfcf51ec52c0ddfc";
        List<String> arrlicense = new ArrayList<>();
        List<String> arroperate = new ArrayList<>();
        List<GspOperateDetailVO> licenseDetails = gspOperateDetailService.queryOperateDetailByLicense("edbf5a04a7a743f1bfcf51ec52c0ddfc");
        List<GspOperateDetailVO> operateLicenseDetails = gspOperateDetailService.queryOperateDetailByLicense("73f9fb81c1514eb0961d80dd4c6b8564");
        for(GspOperateDetailVO v : licenseDetails){
            arrlicense.add(v.getOperateId());
        }
        for(GspOperateDetailVO v : operateLicenseDetails){
            arroperate.add(v.getOperateId());
        }
        for(String s : arroperate){
            if(!arrlicense.contains(s)){
                System.out.println(true);
            }
        }
        System.out.println(true);

    }



}