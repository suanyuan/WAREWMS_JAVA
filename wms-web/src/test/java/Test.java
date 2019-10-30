import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/9
 */
public class Test {

    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow=sdf.format(new Date());
        String fileName="委托客户"+timeNow;
        System.out.println(fileName);
    }

    private static void toStr(){
        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list.toString().substring(1,list.toString().length()-1));
    }

    private static String initScope(String scope){
        String[] scopeArr = scope.split(",");
        StringBuffer resultArr = new StringBuffer();
        for(String str : scopeArr){
            resultArr.append("{");
            resultArr.append("enterpriseId:''");
            resultArr.append(",operateId:'"+str+"'");
            resultArr.append("},");

        }
        return "["+resultArr.substring(0,resultArr.length()-1)+"]";
    }
}