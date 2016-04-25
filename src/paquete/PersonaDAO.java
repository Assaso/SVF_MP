package paquete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */

public class PersonaDAO
{
    public File fp;

    public PersonaDAO(String path)
    {
        fp = new File(path);
    }

    public String[] leerPersonas()
    {
        String[] lista = null;
        String content;

        if (fp.exists())
        {
            BufferedReader br = null;
            try
            {
                br = new BufferedReader(new FileReader(fp));
                content = br.readLine();
                br.close();

                lista = convertToList(content);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return lista;
    }

    private String[] convertToList(String content)
    {
        List<String> results = new ArrayList<>();

        String[] n = content.split("-");

        for (int i = 0; i < n.length; i++)
        {
            String[] n1 = n[i].split("/");
            if (n1[0].trim().equals("1"))
            {
                String n2 = "";
                n2 = n2 + n1[1];
                results.add(n2);
            }
        }

        return results.toArray(new String[results.size()]);
    }
}
