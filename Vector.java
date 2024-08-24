/*
 * Will C.
 * GUI Project
 * Vector Class
 * contains x and y coordinates and functions to graph and do vector arithmetic
 * includes methods to add, dot, and scale vectors as well as translate onto graph and turn into a line
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

public class Vector {

  private double x;
  private double y;
  private Graph graph;

  public Vector(double x, double y, Graph graph) {
    this.x = x;
    this.y = y;
    this.graph = graph;
  }

  // getter methods
  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public Graph getGraph() {
    return this.graph;
  }

  // convert into line that can be graphed (includes option to start at another vector)
  public Line toLine(Color color, Vector start) {
    Line line = new Line();
    line.setStroke(color);
    line.setStrokeWidth(3);
    line.setStartX(mapX(0.0 + start.getX(), this.graph));
    line.setStartY(mapY(0.0 + start.getY(), this.graph));
    line.setEndX(mapX(this.x + start.getX(), this.graph));
    line.setEndY(mapY(this.y + start.getY(), this.graph));
    return line;
  }

  // same as above but starts a zero
  public Line toLine(Color color) {
    Vector start = new Vector(0.0, 0.0, this.graph);
    return toLine(color, start);
  }

  // scales the vector
  public void scaleVector(double scalar) {
    this.x *= scalar;
    this.y *= scalar;
  }

  // returns new vector that is sum of this vector and another
  public Vector addVector(Vector other) {
    double newx = this.x + other.getX();
    double newy = this.y + other.getY();
    Vector newvector = new Vector(newx, newy, graph);
    return newvector;
  }

  // returns dot product of two vectors
  public double dotProduct(Vector other) {
    return (this.x * other.getX()) + (this.y * other.getY());
  }

  // translates vector coordinates onto graph coordinates
  public double mapX(double x, Graph graph) {
      double start = graph.getPrefWidth() / 2;
      double scale = graph.getPrefWidth() /
         (graph.getXAxis().getUpperBound() -
          graph.getXAxis().getLowerBound());

      return x * scale + start;
  }

  public double mapY(double y, Graph graph) {
      double start = graph.getPrefHeight() / 2;
      double scale = graph.getPrefHeight() /
          (graph.getYAxis().getUpperBound() -
           graph.getYAxis().getLowerBound());

      return -y * scale + start;
  }

}
