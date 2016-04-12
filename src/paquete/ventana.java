package paquete;

import javafx.application.Application;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.util.Set;

public class ventana extends JFrame implements ActionListener{

    JButton boton, boton1, boton2;
    JLabel label, title, etiqueta, label1;
    JComboBox day, month, year;
    JTextField OTMP;
    String dia, mes, ano;
    String orden, fecha, proyecto;
    int i, j, ev;
    int status, contador;
    String[] prow;
    //MODIFICAR RUTAS
    String localFile = "src/paquete/file/basededatos.xls";
    String textdb = "src/paquete/file/dbt.txt";
    String jsguardado = "C:\\Users\\JGALLARDO\\IdeaProjects\\SVF_MP\\src\\paquete\\file\\savedb.vbs";
    String jsescritura =  "C:\\Users\\JGALLARDO\\IdeaProjects\\SVF_MP\\src\\paquete\\file\\writedb.vbs";

    public ventana(){
        String[] days = new String[0];
        String[] months = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        String[] years = {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        setLayout(null);
        boton= new JButton("SALIR");
        boton.setBounds(15, 300, 100, 50);
        add(boton);
        boton2 = new JButton("GUARDAR");
        boton2.setBounds(240, 300, 100, 50);
        add(boton2);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orden = OTMP.getText().toUpperCase();
                dia = day.getSelectedItem().toString();
                mes = month.getSelectedItem().toString();
                ano = year.getSelectedItem().toString();
                fecha = dia + "/" + mes + "/" + ano;
                System.out.println(fecha);
                proyecto = orden + "=" + fecha + "|";
                if (OTMP.getText().equals("")) {
                    JOptionPane.showMessageDialog(ventana.this, "OTMP no puede estar vacia");
                } else {
                    try {
                        buscar(orden);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (BiffException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    guardar(proyecto);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });

        label = new JLabel();
        label.setBounds(200, 0, 300, 350);
        //label.setIcon();
        label.setForeground(Color.WHITE);
        add(label);
        boton.addActionListener(this);
        boton1 = new JButton("Generar reporte");
        boton1.setBounds(420, 300, 250, 50);
        add(boton1);
        title = new JLabel("ORDEN DE TRABAJO");
        title.setBounds(15, 15, 250, 50);
        add(title);

        etiqueta = new JLabel("FECHA DE ENTREGA DE PLANOS");
        etiqueta.setBounds(15, 100, 250, 50);
        add(etiqueta);

        OTMP = new JTextField();
        OTMP.setBounds(15, 60, 200, 30);
        add(OTMP);

        month = new JComboBox(months);
        month.addItemListener(e -> {
            cambio();
        });
        month.setBounds(15, 150, 125, 25);
        add(month);
        year = new JComboBox(years);
        year.addItemListener(xe -> {
            cambioYear();
        });

        year.setBounds(245, 150, 100, 25);
        add(year);

        day= new JComboBox(days);
        day.setBounds(165, 150, 55, 25);
        add(day);

        //CREAR LABEL DE PROYECTOS PENDIENTES
        String cmd = "cmd /C " + jsguardado;
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmd);
            ev = p.waitFor();
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ev);
        llenado();
        for (int i = 0; i<contador; i++){
            String[] y = prow[i+1].split("/");
            String y1 = y[0];
            String y2 = y[1];
            label1 = new JLabel(y1 + " Dias restantes: " + y2, SwingConstants.CENTER);
            if (i==0) {
                label1.setBounds(400,0, 300, 30);
                add(label1);
            }else{
                label1.setBounds(400, (15 + 30) * (i), 300, 30);
                add(label1);
            }
        }

    }

    public void guardar(String proyecto) throws IOException {
        File file = new File(textdb);

        if (file.exists()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(textdb));
                bw.write(proyecto);
            bw.flush();
            bw.close();
        }
        String cmd = "cmd /C " + jsescritura;
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmd);
            ev = p.waitFor();
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void cambioYear() {
        j = year.getSelectedIndex();
        System.out.println(j);
    }

    private void cambio() {
        cambioYear();
        day.removeAllItems();
        int index = month.getSelectedIndex();
        String[] days;
        switch (index) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                days = new String[32];
                for (int i = 1; i < days.length; i++) {
                    if (i < 10) {
                        days[i] = "0" + i;
                    } else {
                        days[i] = "" + i;
                    }
                    day.addItem(days[i]);
                }
                break;
            case 1:
                day.removeAllItems();
                if (j== 1 || j==5 || j==9 || j==13){
                    days = new String[30];
                    for (i = 1; i < days.length; i++) {
                        if (i < 10) {
                            days[i] = "0" + i;
                        } else {
                            days[i] = "" + i;
                        }
                        day.addItem(days[i]);
                    }
                }else{
                    day.removeAllItems();
                    String[] days2 = new String[29];
                    for (i = 1; i < days2.length; i++) {
                        if (i < 10) {
                            days2[i] = "0" + i;
                        } else {
                            days2[i] = "" + i;
                        }
                        day.addItem(days2[i]);
                    }
                }
                break;
            default:
                days = new String[31];
                for (int i = 1; i < days.length; i++) {
                    if (i < 10) {
                        days[i] = "0" + i;
                    } else {
                        days[i] = "" + i;
                    }
                    day.addItem(days[i]);
                }
                break;
        }
        System.out.println(index);
        System.out.println(year.getSelectedIndex());
        System.out.println(j);
    }

    public void paintComponente(Graphics g){
        Dimension dimension = getSize();
        String phat = "/images/logominepower.png";
        ImageIcon imagen = new ImageIcon(getClass().getResource(phat));
        g.drawImage(imagen.getImage(), 200, 0, dimension.width, dimension.height, null);

        super.paintComponents(g);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton) {
            System.exit(-1);
       }
    }


    public void buscar(String orden) throws IOException, BiffException {
        int x = 0;
        try{

            Workbook workbook = Workbook.getWorkbook(new File(localFile));

            Sheet sheet = workbook.getSheet(0);

            String cell = sheet.getCell(x,0).getContents();
            System.out.println(cell);
            while(!cell.equals("")){
                if (cell.equals(orden)){
                    JOptionPane.showMessageDialog(ventana.this, "OTMP ya existente");
                    break;
                }else {
                    x = x +1;
                    cell = sheet.getCell(0, x).getContents();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

        public void llenado() {
            contador = 0;
            int status = 0;
            prow = new String[100];

            Workbook workbook = null;
            try {
                workbook = Workbook.getWorkbook(new File(localFile));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }

            Sheet sheet = workbook.getSheet(0);
            String cell = sheet.getCell(5, 0).getContents();
            while (!cell.equals("")) {
                if (cell.equals("0")) {
                    String z = sheet.getCell(0, status).getContents()+ "/" + sheet.getCell(4, status).getContents();
                    contador = contador + 1;
                    prow[contador]=z;
                    System.out.println(z);
                }
                status = status + 1;
                cell = sheet.getCell(5, status).getContents();
            }
            workbook.close();

        }




    public static void main(String[] args){
        ventana v = new ventana();
        v.setBounds(0,0,700,400);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setTitle("Seguimiento de OTMP");
        v.setVisible(true);

    }
}
