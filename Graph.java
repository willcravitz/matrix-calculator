/*
 * Will C.
 * GUI Project
 * Graph Class
 * contains a graph with number axes and a legend
 * contains methods for clearing, resizing, and adding to legend
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.paint.*;

public class Graph extends Pane {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private GridPane legend;
    private int elemInLegend;

    public Graph(int width, int height, double xBound, double xTickUnit, double yBound, double yTickUnit) {

      setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
      setPrefSize(width, height);
      setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

      // creates axes based on given parameters
      this.xAxis = new NumberAxis(xBound * -1, xBound, xTickUnit);
      this.xAxis.setSide(Side.BOTTOM);
      this.xAxis.setMinorTickVisible(false);
      this.xAxis.setPrefWidth(width);
      this.xAxis.setLayoutY(height / 2);

      this.yAxis = new NumberAxis(yBound * -1, yBound, yTickUnit);
      this.yAxis.setSide(Side.LEFT);
      this.yAxis.setMinorTickVisible(false);
      this.yAxis.setPrefHeight(height);
      this.yAxis.layoutXProperty().bind(Bindings.subtract((width / 2) + 1,yAxis.widthProperty()));

      this.legend = new GridPane();
      this.legend.setHgap(5);
      this.elemInLegend = 0;

      // sets axes onto the pane
      getChildren().setAll(this.xAxis, this.yAxis);
    }

    // getter methods
    public NumberAxis getXAxis() {
      return this.xAxis;
    }

    public NumberAxis getYAxis() {
      return this.yAxis;
    }

    // sets new number bounds for the axes
    public void setXBound(double bound) {
      this.xAxis.setLowerBound(-1*bound);
      this.xAxis.setUpperBound(bound);
    }

    public void setYBound(double bound) {
      this.yAxis.setLowerBound(-1*bound);
      this.yAxis.setUpperBound(bound);
    }

    public void addToLegend(String label, Color color) {
      Circle symbol = new Circle(5, color);
      Label element = new Label(label);
      this.legend.add(symbol, 0, this.elemInLegend);
      this.legend.add(element, 1, this.elemInLegend);
      this.elemInLegend++;
    }

    // display legend on graph
    public void showLegend() {
      getChildren().add(this.legend);
    }

    // clear lines on the graph and erase legend
    public void clearGraph() {
      this.elemInLegend = 0;
      this.legend.getChildren().clear();
      getChildren().clear();
      getChildren().setAll(this.xAxis, this.yAxis);
    }
}
