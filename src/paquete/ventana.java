package paquete;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class ventana extends JFrame implements ActionListener
{
    JPanel listaPendientes;
    JButton boton, boton1, boton2;
    JLabel label, title, etiqueta, etiqueta2;
    JComboBox day, month, year, personal;
    JTextField OTMP;
    String dia, mes, ano, persona;
    String orden, fecha, proyecto;
    int i, j, ev;

    //MODIFICAR RUTAS
    String localFile = "src/file/basededatos.xls";
    String ptText = "src/file/personaltemp.txt";
    String textdb = "src/file/dbt.txt";
    String jsguardado = "C:\\Users\\JGALLARDO\\IdeaProjects\\SVF_MP\\src\\file\\savedb.vbs";
    String jsescritura = "C:\\Users\\JGALLARDO\\IdeaProjects\\SVF_MP\\src\\file\\writedb.vbs";

    public ventana()
    {
        initComponents();
        ejecutarMacro();
        OrdenTrabajoDAO ordenes = new OrdenTrabajoDAO(localFile);
        pintarPendientes(ordenes.leerOrdenesPendientes());
    }

    private void pintarPendientes(String[] OrdenesTrabajo) throws NumberFormatException
    {
        JLabel label1;
        listaPendientes.removeAll();

        for (int i = 0; i < OrdenesTrabajo.length; i++)
        {
            String[] linea = OrdenesTrabajo[i].split("/");
            String no_orden = linea[0];
            String dias_restantes = linea[1];
            int color_dias = Integer.parseInt(dias_restantes);
            label1 = new JLabel();
            label1.setOpaque(true);
            label1.setText(no_orden + " Dias restantes: " + dias_restantes);
            label1.setOpaque(true);
            label1.setBounds(0, 25 * i + 5, 600, 30);
            if (color_dias > 3)
                label1.setBackground(Color.GREEN);
            else if (color_dias <= 3 && color_dias >= 0)
                label1.setBackground(Color.YELLOW);
            else
                label1.setBackground(Color.RED);
            listaPendientes.add(label1);
        }
    }

    /**----------CREAR_LABEL_DE_PROYECTOS_PENDIENTES----------*/
    private void ejecutarMacro()
    {
        String cmd = "cmd /C " + jsguardado;
        try
        {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmd);
            ev = p.waitFor();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void initComponents()
    {
        String[] months =
                {
                        "", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
                };
        String[] years =
                {
                        "", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"
                };

        String phat = "/images/logominepower.png";
        ImageIcon imagen = new ImageIcon(getClass().getResource(phat));
        setLayout(new BorderLayout());
        JPanel captura = new JPanel(null);
        JPanel acciones = new JPanel();
        JPanel reporte = new JPanel(new BorderLayout());

        boton = new JButton("SALIR");
        acciones.add(boton);
        boton2 = new JButton("GUARDAR");
        acciones.add(boton2);
        boton2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boton2_actionPerformed(e);
            }
        });

        boton.addActionListener(this);
        boton1 = new JButton("Generar reporte");
        boton1.setBounds(420, 300, 250, 50);
        reporte.add(boton1, BorderLayout.SOUTH);
        title = new JLabel("ORDEN DE TRABAJO");
        title.setBounds(15, 15, 250, 50);
        captura.add(title);
        etiqueta = new JLabel("FECHA DE INICIO DE FABRICACIÓN");
        etiqueta.setBounds(15, 100, 250, 50);
        captura.add(etiqueta);
        OTMP = new JTextField();
        OTMP.setBounds(15, 60, 200, 30);
        captura.add(OTMP);
        month = new JComboBox(months);
        month.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                cambio();
            }

        });
        month.setBounds(15, 150, 125, 25);
        captura.add(month);
        year = new JComboBox(years);
        year.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                cambioYear();
            }
        });
        year.setBounds(245, 150, 100, 25);
        captura.add(year);
        day = new JComboBox();
        day.setBounds(165, 150, 55, 25);
        day.addItem("");
        captura.add(day);
        etiqueta2 = new JLabel("ASIGNACIÓN DE PERSONAL");
        etiqueta2.setBounds(100, 195, 250, 25);
        captura.add(etiqueta2);

        PersonaDAO personas = new PersonaDAO(ptText);
        personal = new JComboBox(personas.leerPersonas());
        personal.setBounds(75, 230, 205, 25);
        captura.add(personal);

        label = new JLabel();
        label.setBounds(50, 0, 300, 350);
        label.setOpaque(true);
        label.setIcon(imagen);
        captura.add(label);

        listaPendientes = new JPanel(null);
        listaPendientes.setPreferredSize(new Dimension(200, 200));

        add(captura, BorderLayout.CENTER);
        reporte.add(listaPendientes, BorderLayout.CENTER);
        add(reporte, BorderLayout.EAST);
        add(acciones, BorderLayout.SOUTH);
    }

    //---------------------------------------------------------------------
    private void boton2_actionPerformed(ActionEvent e)
    {

        if (personal.getSelectedItem().toString().equals("") || day.getSelectedItem().toString().equals("") || month.getSelectedItem().toString().equals("") || year.getSelectedItem().toString().equals(""))
        {
            JOptionPane.showMessageDialog(ventana.this, " Se deben llenar campos de Fecha y/o Personal");
        }
        else
        {
            int condicion = 0;
            orden = OTMP.getText().toUpperCase();
            dia = day.getSelectedItem().toString();
            mes = month.getSelectedItem().toString();
            ano = year.getSelectedItem().toString();
            persona = personal.getSelectedItem().toString();
            fecha = dia + "/" + mes + "/" + ano;
            System.out.println(fecha);
            proyecto = orden + "=" + fecha + "=" + persona + "|";
            if (OTMP.getText().equals(""))
            {
                JOptionPane.showMessageDialog(ventana.this, " OTMP no puede estar vacia ");
            }
            else
            {
                try
                {
                    condicion = buscar(orden);
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                catch (BiffException e1)
                {
                    e1.printStackTrace();
                }

                try
                {
                    guardar(proyecto, condicion, persona);
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }

            }
        }
        actualizarPantalla();
    }

    /**-------------------GUARDAR-------------------*/
    public void guardar(String proyecto, int condicion, String persona) throws IOException
    {
        if (condicion == 0)
        {
            File file = new File(textdb);
            if (file.exists())
            {
                BufferedWriter bw = new BufferedWriter(new FileWriter(textdb));
                bw.write(proyecto);
                bw.flush();
                bw.close();
            }

            File file1 = new File(ptText);
            if (file1.exists())
            {
                BufferedReader br = new BufferedReader(new FileReader(file1));
                String linep = br.readLine();
                String[] linep1 = linep.split("-");
                String[] linep2;
                String completo = "";
                for (int i = 0; i < linep1.length; i++)
                {
                    linep2 = linep1[i].split("/");
                    if (linep2[1].equals(persona))
                    {
                        linep2[0] = "" + 0;
                    }
                    completo = completo + linep2[0] + "/" + linep2[1] + "-";
                }
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
                bw.write(completo);
                bw.flush();
                bw.close();
            }

            String cmd = "cmd /C " + jsescritura;
            try
            {
                Runtime r = Runtime.getRuntime();
                Process p = r.exec(cmd);
                ev = p.waitFor();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            personal.removeAllItems();

            OrdenTrabajoDAO ordenes = new OrdenTrabajoDAO(localFile);
            pintarPendientes(ordenes.leerOrdenesPendientes());
            actualizarPantalla();

            PersonaDAO personas = new PersonaDAO(ptText);
            personal.addItem(personas.leerPersonas());


        }

    }

    /**---------------------COMBO_BOX_MESES-------------------------*/
    public void cambioYear()
    {
        j = year.getSelectedIndex();
        System.out.println(j);
    }

    private void cambio()
    {
        cambioYear();
        day.removeAllItems();
        int index = month.getSelectedIndex();
        String[] days;
        switch (index)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = new String[32];
                for (int i = 1; i < days.length; i++)
                {
                    if (i < 10)
                    {
                        days[i] = "0" + i;
                    }
                    else
                    {
                        days[i] = "" + i;
                    }
                    day.addItem(days[i]);
                }
                break;
            case 2:
                day.removeAllItems();
                if (j == 1 || j == 5 || j == 9 || j == 13)
                {
                    days = new String[30];
                    for (i = 1; i < days.length; i++)
                    {
                        if (i < 10)
                        {
                            days[i] = "0" + i;
                        }
                        else
                        {
                            days[i] = "" + i;
                        }
                        day.addItem(days[i]);
                    }
                }
                else
                {
                    day.removeAllItems();
                    String[] days2 = new String[29];
                    for (i = 1; i < days2.length; i++)
                    {
                        if (i < 10)
                        {
                            days2[i] = "0" + i;
                        }
                        else
                        {
                            days2[i] = "" + i;
                        }
                        day.addItem(days2[i]);
                    }
                }
                break;
            default:
                days = new String[31];
                for (int i = 1; i < days.length; i++)
                {
                    if (i < 10)
                    {
                        days[i] = "0" + i;
                    }
                    else
                    {
                        days[i] = "" + i;
                    }
                    day.addItem(days[i]);
                }
                break;
        }
    }

    /**-----------------------------ACTUALIZAR_PANTALLA----------------------*/
    public void actualizarPantalla()
    {
        this.repaint();
    }

    /**--------------------BOTON_SALIR------------------*/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == boton)
        {
            System.exit(-1);
        }
    }

    /**--------------------------BUSCAR------------------------------*/
    public int buscar(String orden) throws IOException, BiffException
    {
        int condicion = 0;
        int x = 0;
        try
        {
            Workbook workbook = Workbook.getWorkbook(new File(localFile));
            Sheet sheet = workbook.getSheet(0);
            String cell = sheet.getCell(x, 0).getContents();
            if (cell.equals(""))
            {
                condicion = 0;
            }
            while (!cell.equals(""))
            {
                if (cell.equals(orden))
                {
                    condicion = 1;
                    JOptionPane.showMessageDialog(ventana.this, "OTMP ya existente");
                    break;
                }
                else
                {
                    condicion = 0;
                    x = x + 1;
                    cell = sheet.getCell(0, x).getContents();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return condicion;
    }

    /**----------------------------VENTANA----------------------------*/
    public static void main(String[] args)
    {
        ventana v = new ventana();
        v.setBounds(0, 0, 700, 400);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setTitle("Seguimiento de OTMP");
        v.setVisible(true);

    }
}
