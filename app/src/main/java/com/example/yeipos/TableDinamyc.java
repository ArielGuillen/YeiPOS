package com.example.yeipos;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDinamyc {

    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList< String[] > data;
    private TableRow tableRow;
    private TextView textCell;
    private int indexC;
    private int indexR;
    private boolean multiColor = false;
    private int firstColor;
    private int secondColor;
    private int textColor;

    //-------------------------Constructores------------------------------
    public TableDinamyc(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }
    private void newRow(){
        tableRow = new TableRow( context );
    }

    private void newCell(){
        textCell = new TextView( context );
        textCell.setGravity(Gravity.CENTER);
        textCell.setTextSize( 25 );
    }


    //------------------------Agregar celdas-------------------------------

    public void addHeader( String [] header ) {
        this.header = header;
        this.createHeader();
    }

    public void addData( ArrayList< String[] > data ){
        this.data = data;
        this.createDataTable();
    }


    //--------------------Crear Header y Contenido---------------------------
    private void createHeader(){

        indexC = 0;
        newRow();
        while ( indexC < header.length ){
            newCell();
            textCell.setText( header[indexC++] );
            tableRow.addView( textCell, newTableRowParams() );
        }
        tableLayout.addView( tableRow );

    }

    private void createDataTable(){

        String info = "";
        for( indexR = 1; indexR < header.length; indexR++ ){
            newRow();
            for( indexC = 0; indexC < header.length; indexC++ ){
                newCell();
                String [] row = data.get( indexR-1 );
                info = ( indexC < row.length ) ? row[indexC]: "";
                textCell.setText( info );
                tableRow.addView( textCell, newTableRowParams() );
            }
            tableLayout.addView( tableRow );
        }

    }

    //----------------------Añadir elementos----------------------

    public void addItems(String [] item ){

        String info;
        data.add( item );
        indexC = 0;
        newRow();
        while( indexC < header.length ){

            newCell();
            info = ( indexC < item.length ) ? item[indexC++]: "";
            textCell.setText( info );
            tableRow.addView( textCell, newTableRowParams() );

        }
        tableLayout.addView( tableRow, data.size()-1 );
        reColoring();
    }

    //-----------------------------Getters---------------------------------
    private TableRow getRow( int index ){
        return (TableRow) tableLayout.getChildAt( index );
    }
    private TextView getCell( int rowIndex, int colIndex ){
        tableRow = getRow( rowIndex );
        return (TextView) tableRow.getChildAt( colIndex );
    }


    //--------------------Modificar Color de fondo--------------------------------

    public void backgroundHeader( int color ) {

        indexC = 0;
        while ( indexC < header.length ){
            textCell =  getCell( 0, indexC++ );
            textCell.setBackgroundColor( color );
        }
    }

    public void backgroundData( int firstColor, int secondColor ){

        for( indexR = 1; indexR < header.length; indexR++ ){
            multiColor = !multiColor;
            for( indexC = 0; indexC < header.length; indexC++ ){
                textCell = getCell( indexR, indexC );
                textCell.setBackgroundColor((multiColor)?firstColor:secondColor );
            }
        }
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    public void reColoring( ) {

        indexC = 0;
        multiColor = !multiColor;
        while ( indexC < header.length ){
            textCell =  getCell( data.size()-1, indexC++ );
            textCell.setBackgroundColor((multiColor)?firstColor:secondColor);
            textCell.setTextColor( textColor );
        }
    }
    public void textColorData( int color) {
        for( indexR = 1; indexR < header.length; indexR++ )
            for( indexC = 0; indexC < header.length; indexC++ )
                getCell( indexR, indexC ).setTextColor( color );
        textColor = color;
    }
    public void textColorHeader( int color) {
        indexC = 0;
        while( indexC < header.length )
                getCell( 0, indexC++ ).setTextColor( color );
    }



    private TableRow.LayoutParams newTableRowParams(){

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins( 2, 2, 2, 2 );
        params.weight = 2;
        return params;

    }
}

