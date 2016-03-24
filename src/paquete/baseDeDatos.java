package paquete;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by JGALLARDO on 24/03/2016.
 */

public class baseDeDatos {

    public baseDeDatos(){

    }

    public static void guardar() {
        String localFile = "src/paquete/file/basededatos.xls";
        FileInputStream fis = null;
        System.out.println(localFile);
        try{
           fis = new FileInputStream(localFile);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
