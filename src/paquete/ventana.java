package paquete;

import jxl.read.biff.BiffException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;

public class ventana extends JFrame implements ActionListener{

    JButton boton, boton1, boton2;
    JLabel label, title, etiqueta;
    JComboBox day, month, year;
    JTextField OTMP;
    int dia, mes, ano;
    String orden;
    int i, j;


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
                orden = OTMP.getText();
                if (OTMP.getText().equals("")) {
                    JOptionPane.showMessageDialog(ventana.this, "OTMP no puede estar vacia");
                } else {
                    try {
                        baseDeDatos.buscar(orden);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (BiffException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        baseDeDatos.guardar();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
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
            case 7:
            case 8:
            case 10:
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



    public static void main (String[] args){
        ventana v = new ventana();
        v.setBounds(0,0,700,400);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setTitle("Seguimiento de OTMP");
        v.setVisible(true);
    }
}
