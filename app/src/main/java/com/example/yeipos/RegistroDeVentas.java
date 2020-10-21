package com.example.yeipos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class RegistroDeVentas extends AppCompatActivity {

        LineChartView lineChartView;
        
        //---------------------------Datos que se muestran en la gráfica----------------------------
        String[] DatosEjeX = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sept",
                "Oct", "Nov", "Dec"};
        float[] DatosEjeY = {2, 3.2f, 2.5f, 2.350f, 2.1f, 2.6f, 2.5f, 2.41f, 2.450f, 3.12f, 1.98f, 1.8f};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro_de_ventas);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );

            lineChartView = findViewById(R.id.chart);

            List ValoresEjeY = new ArrayList();
            List ValoresEjeX = new ArrayList();


            Line line = new Line(ValoresEjeY).setColor(Color.parseColor("#9fa8da"));

            for (int i = 0; i < DatosEjeX.length; i++) {
                ValoresEjeX.add(i, new AxisValue(i).setLabel(DatosEjeX[i]));
            }

            for (int i = 0; i < DatosEjeY.length; i++) {
                ValoresEjeY.add(new PointValue(i, DatosEjeY[i]));
            }

            List lines = new ArrayList();
            lines.add(line);

            LineChartData data = new LineChartData();
            data.setLines(lines);

            Axis axis = new Axis();
            axis.setValues(ValoresEjeX);
            axis.setTextSize(14);
            axis.setTextColor(Color.parseColor("#8e0000"));
            data.setAxisXBottom(axis);

            Axis yAxis = new Axis();
            yAxis.setName(this.getApplicationContext().getString(R.string.cant));
            yAxis.setTextColor(Color.parseColor("#8e0000"));
            yAxis.setTextSize(14);
            data.setAxisYLeft(yAxis);

            lineChartView.setLineChartData(data);
            Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
            viewport.top = 5;
            viewport.bottom = 0;
            lineChartView.setMaximumViewport(viewport);
            lineChartView.setCurrentViewport(viewport);

        }
}