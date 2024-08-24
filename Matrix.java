/*
 * Will C.
 * GUI Project
 * Matrix Class
 * contains a 2 by 2 matrix and methods to do matrix arithmetic
 * inlcudes methods for determinant, multiply by a matrix, and multiply by a vector
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

public class Matrix {

  private double[][] values;
  private Graph graph;

  public Matrix(double[][] values, Graph graph) {
    // array given to matrix must be 2 by 2
    if (values.length != 2 || values[0].length != 2) {
      throw new IllegalArgumentException();
    }
    this.values = values;
    this.graph = graph;
  }

  // getter methods
  public double[][] getValues() {
    return this.values;
  }

  public Graph getGraph() {
    return this.graph;
  }

  // returns new matrix that is product of this matrix and another
  public Matrix multiplyByMatrix(Matrix other) {
    double[][] product = new double[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < 2; k++) {
          product[i][j] += this.values[i][k] * other.getValues()[k][j];
        }
      }
    }
    return new Matrix(product, this.graph);
  }

  // returns new vector that is product of this matrix and a vector
  public Vector multiplyByVector(Vector vector) {
    double x = this.values[0][0] * vector.getX() + this.values[0][1] * vector.getY();
    double y = this.values[1][0] * vector.getX() + this.values[1][1] * vector.getY();
    return new Vector(x, y, this.graph);
  }

  // returns the determinant of the matrix
  public double determinant() {
    return (this.values[0][0]*this.values[1][1]) - (this.values[0][1]*this.values[1][0]);
  }

}
