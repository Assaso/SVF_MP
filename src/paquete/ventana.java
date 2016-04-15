package paquete;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ventana extends JFrame implements ActionListener{

    JButton boton, boton1, boton2;
    JLabel label, title, etiqueta, label1, etiqueta2;
    JComboBox day, month, year, personal;
    JTextField OTMP;
    String dia, mes, ano, persona;
    String orden, fecha, proyecto;
    int i, j, ev, condicion = 1;
    int status, contador;
    String[] prow;

    //MODIFICAR RUTAS
    String localFile = "src/paquete/file/basededatos.xls";
    String ptText = "src/paquete/file/personaltemp.txt";
    String textdb = "src/paquete/file/dbt.txt";
    String jsguardado = "C:\\Users\\JGALLARDO\\IdeaProjects\\SVF_MP\\src\\paquete\\file\\savedb.vbs";
    String jsescritura =  "C:\\Users\\JGALLARDO\\IdeaProjects\\SVF_MP\\src\\paquete\\file\\writedb.vbs";

/**---------------------------------------------------------SE CREA VENTANA EN GRAL.---------------------------------------------------------------------*/

    public ventana() {
        String[] personas = new String[0];
        String[] days = new String[0];
        String[] months = {"", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        String[] years = {"", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        String phat = "/images/logominepower.png";
        ImageIcon imagen = new ImageIcon(getClass().getResource(phat));
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
                if (personal.getSelectedItem().toString().equals("")||day.getSelectedItem().toString().equals("")||month.getSelectedItem().toString().equals("") || year.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(ventana.this, " Se deben llenar campos de Fecha y/o Personal");
                } else {
                    orden = OTMP.getText().toUpperCase();
                    dia = day.getSelectedItem().toString();
                    mes = month.getSelectedItem().toString();
                    ano = year.getSelectedItem().toString();
                    persona = personal.getSelectedItem().toString();
                    fecha = dia + "/" + mes + "/" + ano;
                    System.out.println(fecha);
                    proyecto = orden + "=" + fecha + "=" + persona + "|";
                    if (OTMP.getText().equals("")) {
                        JOptionPane.showMessageDialog(ventana.this, " OTMP no puede estar vacia ");
                    } else {
                        try {
                            buscar(orden, condicion);
                            /**------------------- aqui se pierde el valor de condicion y se inicializa en 0 -----------------------*/
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (BiffException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                actualizarPantalla();
            }
        });

        boton.addActionListener(this);
        boton1 = new JButton("Generar reporte");
        boton1.setBounds(420, 300, 250, 50);
        add(boton1);
        title = new JLabel("ORDEN DE TRABAJO");
        title.setBounds(15, 15, 250, 50);
        add(title);
        etiqueta = new JLabel("FECHA DE INICIO DE FABRICACIÓN");
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
        day.addItem("");
        add(day);
        etiqueta2 = new JLabel("ASIGNACIÓN DE PERSONAL");
        etiqueta2.setBounds(100, 195, 250, 25);
        add(etiqueta2);
        /**-------------------------------PERSONAL---------------**/
        personal = new JComboBox(personas);
        personal.setBounds(75, 230, 205, 25);
        String line = "";
        File fp = new File(ptText);

        if (fp.exists()){
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(fp));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String[] n = line.split("-");
        for(int i = 0; i < n.length; i++){
            String[] n1 = n[i].split("/");
            String n2 = "";
            n2 = n2 + n1[1];
            personal.addItem(n2);
        }



        add(personal);
        /**--------------CREAR LABEL DE PROYECTOS PENDIENTES----------*/
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
        llenado();
        for (int i = 0; i<contador; i++){
            String[] y = prow[i+1].split("/");
            String y1 = y[0];
            String y2 = y[1];
            int yi2 = Integer.parseInt(y2);
            label1 = new JLabel(y1 + " Dias restantes: " + y2, SwingConstants.CENTER);
            label1.setOpaque(true);
            if (i==0) {
                label1.setBounds(400,0, 300, 30);
                if (yi2 > 3){
                    label1.setBackground(Color.GREEN);
                }else{
                    if (yi2 <= 3 && yi2 >= 0){
                        label1.setBackground(Color.YELLOW);
                    }else{
                        label1.setBackground(Color.RED);
                    }
                }
                add(label1);
            }else{
                label1.setBounds(400, (15 + 30) * (i), 300, 30);
                label1.setOpaque(true);
                if (yi2 > 3){
                    label1.setBackground(Color.GREEN);
                }else{
                    if (yi2 <= 3 && yi2 >= 0){
                        label1.setBackground(Color.YELLOW);
                    }else{
                        label1.setBackground(Color.RED);
                    }
                }
                add(label1);
            }
        }
        label = new JLabel();
        label.setBounds(50, 0, 300, 350);
        label.setOpaque(true);
        label.setIcon(imagen);
        add(label);
    }

/**-------------------------------------------------------------GUARDAR--------------------------------------------------------------------------------*/

    public void guardar(String proyecto, int condicion, String persona) throws IOException {
        if (condicion == 0) {
            File file = new File(textdb);
            if (file.exists()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(textdb));
                bw.write(proyecto);
                bw.flush();
                bw.close();
            }

            File file1 = new File(ptText);
            if(file1.exists()){
                BufferedReader br = new BufferedReader(new FileReader(file1));
                String linep = br.readLine();
                String[] linep1 = linep.split("-");
                String[] linep2;
                String completo = "";
                for (int i = 0; i < linep1.length; i++){
                    linep2 = linep1[i].split("/");
                    if(linep2[1].equals(persona)){
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
                try {
                    Runtime r = Runtime.getRuntime();
                    Process p = r.exec(cmd);
                    ev = p.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**--SEPARACION DE LLENADO--*/
                llenado();
                for (int i = 0; i < contador; i++) {
                    String[] y = prow[i + 1].split("/");
                    String y1 = y[0];
                    String y2 = y[1];
                    int yi2 = Integer.parseInt(y2);
                    label1 = new JLabel(y1 + " Dias restantes: " + y2, SwingConstants.CENTER);
                    if (i == 0) {
                        label1.setBounds(400, 0, 300, 30);
                        label1.setOpaque(true);
                        if (yi2 > 3){
                            label1.setBackground(Color.GREEN);
                        }else{
                            if (yi2 <= 3 && yi2 >= 0){
                                label1.setBackground(Color.YELLOW);
                            }else{
                                label1.setBackground(Color.RED);
                            }
                        }
                        add(label1);
                    } else {
                        label1.setBounds(400, (15 + 30) * (i), 300, 30);
                        label1.setOpaque(true);
                        if (yi2 > 3){
                            label1.setBackground(Color.GREEN);
                        }else{
                            if (yi2 <= 3 && yi2 >= 0){
                                label1.setBackground(Color.YELLOW);
                            }else{
                                label1.setBackground(Color.RED);
                            }
                        }
                        add(label1);
                    }
                }
            }
    }


/**---------------------------------------------------------COMBO BOX MESES---------------------------------------------------------------*/

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
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
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
            case 2:
                day.removeAllItems();
                if (j == 1 || j == 5 || j == 9 || j == 13) {
                    days = new String[30];
                    for (i = 1; i < days.length; i++) {
                        if (i < 10) {
                            days[i] = "0" + i;
                        } else {
                            days[i] = "" + i;
                        }
                        day.addItem(days[i]);
                    }
                } else {
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
    }

/**---------------------------------------------------------------ACTUALIZAR PANTALLA-----------------------------------------------------------------*/

    public void actualizarPantalla(){
        SwingUtilities.updateComponentTreeUI(this);
        synchronized (getTreeLock()){
            validateTree();
        }
    }

    /**-------------------------------------------------------BOTON SALIR-------------------------------------------------------------------------*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton) {
            System.exit(-1);
       }
    }

/**------------------------------------------------------------------BUSCAR---------------------------------------------------------------------*/

    public void buscar(String orden, int condicion) throws IOException, BiffException {
        int x = 0;
        try{
            Workbook workbook = Workbook.getWorkbook(new File(localFile));
            Sheet sheet = workbook.getSheet(0);
            String cell = sheet.getCell(x,0).getContents();
            if(cell.equals("")){
                condicion = 0;
            }
            while(!cell.equals("")){
                if (cell.equals(orden)){
                    condicion = 1;
                    JOptionPane.showMessageDialog(ventana.this, "OTMP ya existente");
                    break;
                }else {
                    condicion = 0;
                    x = x +1;
                    cell = sheet.getCell(0, x).getContents();
                }
            }
            try {
                guardar(proyecto, condicion, persona);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

/**------------------------------------------------------LLENADO----------------------------------------------------------------------------------*/

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

/**---------------------------------------------------------VENTANA-------------------------------------------------------------------------------------------*/

    public static void main(String[] args){
        ventana v = new ventana();
        v.setBounds(0,0,700,400);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setTitle("Seguimiento de OTMP");
        v.setVisible(true);

    }
}
