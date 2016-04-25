
package paquete;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author victor
 */
public class OrdenTrabajoDAO
{
    private String path;

    public OrdenTrabajoDAO(String thePathFile)
    {
        path = thePathFile;
    }

    public String[] leerOrdenesPendientes()
    {
        int status = 0;
        List<String> ordenesTrabajo = new ArrayList<>();
        Workbook workbook = abrir (path);
        Sheet sheet = workbook.getSheet(0);

        String cell;

        for (status = 0; status <   sheet.getRows(); status++)
        {

            cell = sheet.getCell(5, status).getContents();

            if (cell.trim().equals("0"))
            {
                String z = sheet.getCell(0, status).getContents() + "/" + sheet.getCell(4, status).getContents();
                ordenesTrabajo.add(z);
            }
        }

        workbook.close();

        return ordenesTrabajo.toArray(new String[ordenesTrabajo.size()]);
    }

    private Workbook abrir(String path)
    {
        Workbook workbook = null;
        try
        {
            workbook = Workbook.getWorkbook(new File(path));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (BiffException e)
        {
            e.printStackTrace();
        }

        return workbook;
    }
}
