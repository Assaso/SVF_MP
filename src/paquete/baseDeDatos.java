package paquete;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JGALLARDO on 24/03/2016.
 */

public class baseDeDatos {

    static String localFile = "src/paquete/file/basededatos.xls";

    public baseDeDatos(){

    }

    public static void buscar(String orden) throws IOException, BiffException {
        int x = 0;
        int c = 0;
        try{
            Workbook workbook = Workbook.getWorkbook(new File(localFile));

            Sheet sheet = workbook.getSheet(0);

            Cell cell = sheet.getCell(x,0);
            while(!cell.toString().equals("")){
                if (cell.toString().equals(orden)){
                c = c + 1;
                    break;
                }else {
                    cell = sheet.getCell(x++, 0);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getCompare() {
        return c;
    }
    }

/**  public static void guardar() throws IOException {
        FileInputStream fis = null;
        List sheetData = new ArrayList();
        //System.out.println(localFile);
        try{
           fis = new FileInputStream(localFile);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()){
                HSSFRow row = (HSSFRow) rows.next();

                Iterator cells = row.cellIterator();
                List data = new ArrayList();
                while (cells.hasNext()){
                    HSSFCell cell = (HSSFCell) cells.next();

                    data.add(cell);
                }
                sheetData.add(data);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                fis.close();
            }
        }
        showExcelData(sheetData);
    }

    private static void showExcelData(List sheetData) {
        for (int i = 0; i < sheetData.size(); i++){
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (Cell) list.get(j);
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }
} */
